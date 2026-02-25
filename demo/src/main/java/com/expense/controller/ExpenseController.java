package com.expense.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.expense.dto.ExpenseDTO;
import com.expense.model.Expense;
import com.expense.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000") 
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    //  ADD EXPENSE
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.saveExpense(expense));
    }

    // CSV UPLOAD
    @PostMapping("/upload")
    public ResponseEntity<List<ExpenseDTO>> uploadCSV(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(expenseService.saveFromCSV(file));
    }

    // SUMMARY
    @GetMapping("/summary")
    public ResponseEntity<List<Object[]>> getSummary() {
        return ResponseEntity.ok(expenseService.getSummary());
    }

    // TOP VENDORS
    @GetMapping("/top-vendors")
    public ResponseEntity<List<Object[]>> getTopVendors() {
        return ResponseEntity.ok(expenseService.getTopVendors());
    }

    // ANOMALIES
    @GetMapping("/anomalies")
    public ResponseEntity<List<ExpenseDTO>> getAnomalies() {
        return ResponseEntity.ok(expenseService.getAnomalies());
    }

    // IMPORTANT: Handle OPTIONS (Preflight)
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}