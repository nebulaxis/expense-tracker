#  Expense Tracker & AI Anomaly Detection System

A backend-based expense tracking system built using Spring Boot and PostgreSQL that supports CSV uploads, vendor normalization, category classification, and anomaly detection.

---

##  Features

-  Upload expenses via CSV file
-  Store expenses in PostgreSQL database
-  Automatic category detection (Food, Travel, Shopping, etc.)
-  Vendor normalization (Amazon, Swiggy, Uber…)
-  Expense summary by category
-  Top vendors analysis
-  Anomaly detection for high-value transactions

---

##  Tech Stack

- Java (Spring Boot)
- PostgreSQL
- JPA / Hibernate
- REST APIs
- Maven

---

##  API Endpoints

### 1. Upload CSV

POST /api/expenses/upload


---

### 2. Get All Expenses

GET /api/expenses


---

### 3. Category Summary

GET /api/expenses/summary


**Response:**

[
["Food", 26550.0],
["Shopping", 183200.0],
["Travel", 11200.0]
]


---

### 4. Top Vendors

GET /api/expenses/top-vendors


**Response:**

[
["Amazon", 183200.0],
["Swiggy", 14000.0],
["Zomato", 12550.0],
["Uber", 11200.0]
]


---

### 5. Anomaly Detection

GET /api/expenses/anomalies


**Description:**
Returns transactions flagged as anomalies based on high spending patterns.

---

##  How Anomaly Detection Works

- Detects unusually high expenses compared to normal spending
- Uses threshold-based logic (can be extended to ML models like Isolation Forest)

---

##  CSV Format


amount,vendor,description,date
1200,Swiggy,Food Order,2026-01-10
15000,Amazon,Electronics Purchase,2026-01-12
800,Uber,Travel Ride,2026-01-15


---

##  Setup Instructions

### 1. Clone Repository

git clone https://github.com/your-username/expense-tracker.git

cd expense-tracker


---

### 2. Configure PostgreSQL

spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/expense_manager
spring.datasource.username=postgres
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.servlet.multipart.enabled=true


---

### 3. Run Application


mvn spring-boot:run


---

##  Testing

- Upload CSV file using Postman
- Check APIs:
  - `/summary`
  - `/top-vendors`
  - `/anomalies`


  postman testing 


GET http://localhost:8080/api/expenses/summary

browser
http://localhost:8080/api/expenses/top-vendors

http://localhost:8080/api/expenses/anomalies


Frontend is running on:

- Dashboard: http://localhost:3000/dashboard


##  Future Improvements

-  JWT Authentication
-  Frontend dashboard (React)
-  Advanced ML anomaly detection
-  Export reports


##  Notes

This project demonstrates backend engineering skills including:
- REST API design
- Data processing
- Database management
- Basic AI/ML logic implementation
