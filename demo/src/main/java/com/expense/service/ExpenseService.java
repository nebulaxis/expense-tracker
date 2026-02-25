package com.expense.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.expense.dto.ExpenseDTO;
import com.expense.model.Expense;

public interface ExpenseService {

    ExpenseDTO saveExpense(Expense expense);

    String getCategory(String vendor);

    boolean checkAnomaly(String category, Double amount);

    List<ExpenseDTO> saveFromCSV(MultipartFile file);

    List<Object[]> getSummary();

    List<Object[]> getTopVendors();

    List<ExpenseDTO> getAnomalies();
}