package com.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expense.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // 🔹 Find expenses by category
    List<Expense> findByCategory(String category);

    // 🔹 Get average amount by category (for anomaly detection)
    @Query("SELECT AVG(e.amount) FROM Expense e WHERE e.category = :category")
    Double findAverageByCategory(@Param("category") String category);

    // 🔹 Get total spending per category (Dashboard Summary)
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e GROUP BY e.category")
    List<Object[]> getCategorySummary();

    // 🔹 Get top vendors by total spend
    @Query("SELECT e.vendor, SUM(e.amount) as total FROM Expense e GROUP BY e.vendor ORDER BY total DESC")
    List<Object[]> getTopVendors();

    // 🔹 Get all anomalies
    List<Expense> findByIsAnomalyTrue();
}