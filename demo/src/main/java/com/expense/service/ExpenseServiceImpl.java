package com.expense.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.expense.dto.ExpenseDTO;
import com.expense.model.Expense;
import com.expense.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final Map<String, String> vendorCategoryMap = new HashMap<>();

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;

        vendorCategoryMap.put("swiggy", "Food");
        vendorCategoryMap.put("zomato", "Food");
        vendorCategoryMap.put("amazon", "Shopping");
        vendorCategoryMap.put("uber", "Travel");
    }

    // ✅ SAVE EXPENSE (CENTRAL LOGIC)
   @Override
public ExpenseDTO saveExpense(Expense expense) {

    // ✅ Vendor normalization (IMPORTANT FIX)
    if (expense.getVendor() != null && !expense.getVendor().isEmpty()) {
        String vendor = expense.getVendor().trim().toLowerCase();
        vendor = vendor.substring(0,1).toUpperCase() + vendor.substring(1);
        expense.setVendor(vendor);
    }

    String category = getCategory(expense.getVendor());
    expense.setCategory(category);

    boolean isAnomaly = checkAnomaly(category, expense.getAmount());
    expense.setIsAnomaly(isAnomaly);

    Expense saved = expenseRepository.save(expense);

    return convertToDTO(saved);
}

    // ✅ VENDOR NORMALIZATION (BEST PRACTICE)
    private String normalizeVendor(String vendor) {
        if (vendor == null || vendor.trim().isEmpty()) return "Unknown";

        vendor = vendor.trim().toLowerCase();

        return vendor.substring(0, 1).toUpperCase() + vendor.substring(1);
    }

    // ✅ CATEGORY LOGIC
    @Override
    public String getCategory(String vendor) {
        if (vendor == null) return "Other";
        return vendorCategoryMap.getOrDefault(vendor.toLowerCase(), "Other");
    }

    // ✅ ANOMALY LOGIC
    @Override
    public boolean checkAnomaly(String category, Double amount) {
        if (amount == null) return false;

        Double avg = expenseRepository.findAverageByCategory(category);

        if (avg == null || avg == 0) return false;

        return amount > (3 * avg);
    }

    // ✅ CSV UPLOAD (ROBUST VERSION)
    @Override
    public List<ExpenseDTO> saveFromCSV(MultipartFile file) {

        List<ExpenseDTO> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                // ✅ Skip header
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // ✅ Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                // ✅ Validate columns
                if (data.length < 4) {
                    System.out.println("❌ Invalid row: " + line);
                    continue;
                }

                try {
                    Expense expense = new Expense();

                    String dateStr = data[0].trim();
                    String amountStr = data[1].trim();
                    String vendor = data[2].trim();
                    String description = data[3].trim();

                    // ✅ Safe Parsing
                    expense.setDate(
                        dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr)
                    );

                    expense.setAmount(
                        amountStr.isEmpty() ? 0.0 : Double.parseDouble(amountStr)
                    );

                    expense.setVendor(vendor);
                    expense.setDescription(description);

                    // ✅ Reuse main logic
                    result.add(saveExpense(expense));

                } catch (Exception e) {
                    System.out.println("❌ Error parsing row: " + line);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("❌ File processing error");
            e.printStackTrace();
        }

        return result;
    }

    // ✅ SUMMARY
    @Override
    public List<Object[]> getSummary() {
        return expenseRepository.getCategorySummary();
    }

    // ✅ TOP VENDORS
    @Override
    public List<Object[]> getTopVendors() {
        return expenseRepository.getTopVendors()
                .stream()
                .limit(5)
                .toList();
    }

    // ✅ ANOMALIES
    @Override
    public List<ExpenseDTO> getAnomalies() {
        return expenseRepository.findByIsAnomalyTrue()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ✅ DTO CONVERTER
    private ExpenseDTO convertToDTO(Expense expense) {
        return new ExpenseDTO(
                expense.getId(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getVendor(),
                expense.getDescription(),
                expense.getDate(),
                expense.getIsAnomaly()
        );
    }
}