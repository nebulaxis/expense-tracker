package com.expense.dto;

import java.time.LocalDate;

public class ExpenseDTO {

    private Long id;
    private Double amount;
    private String category;
    private String vendor;
    private String description;
    private LocalDate date;
    private Boolean isAnomaly;

    // 🔹 Constructors

    public ExpenseDTO() {
    }

    public ExpenseDTO(Long id, Double amount, String category, String vendor,
                      String description, LocalDate date, Boolean isAnomaly) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.vendor = vendor;
        this.description = description;
        this.date = date;
        this.isAnomaly = isAnomaly;
    }

    // 🔹 Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getIsAnomaly() {
        return isAnomaly;
    }

    public void setIsAnomaly(Boolean isAnomaly) {
        this.isAnomaly = isAnomaly;
    }
}