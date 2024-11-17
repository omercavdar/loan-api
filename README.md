# Loan Management API - README

## Overview

The Loan Management API is a backend service for managing loans for bank customers. This service allows bank employees to create loans, list existing loans, manage installment payments, and provide detailed information about customers' loans. This API is built using Spring Boot and employs a layered architecture following Domain-Driven Design (DDD) principles.

### Key Features:
- **Create Loan**: Employees can create loans for customers, specifying the amount, interest rate, and number of installments.
- **List Loans**: Retrieve a list of all loans for a specific customer.
- **List Installments**: List all installments for a particular loan.
- **Pay Loan**: Make payments towards a loan, including multiple installments.

### Tech Stack
- **Java**: Backend programming language.
- **Spring Boot**: Application framework for rapid development.
- **H2 Database**: In-memory database for easy setup and testing.
- **JUnit and Mockito**: Unit testing frameworks.

## Getting Started

### Prerequisites

Ensure you have the following installed:
- **Java 17** or later
- **Maven** (for building the project)

### Setting Up the Project

1. **Clone the Repository**
   ```sh
   git clone https://github.com/omercavdar/loan-api.git
   cd loan-management-api
   ```

2. **Build the Project**
   Run the following command to build the project:
   ```sh
   mvn clean install
   ```

3. **Run the Application**
   You can run the Spring Boot application using the following command:
   ```sh
   mvn spring-boot:run
   ```
   The application will start at `http://localhost:8080`.

### API Documentation

## Endpoints

### 1. Create Loan
- **URL**: `/loans`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "customerId": 1,
    "amount": 5000.0,
    "interestRate": 0.1,
    "numberOfInstallments": 12
  }
  ```
- **Response**: Returns the created loan details.

### 2. List Loans for Customer
- **URL**: `/loans?customerId=1&isPaid=true` (customerId mandatory, isPaid optional)
- **Method**: `GET`
- **Response**: Returns a list of all loans for the specified customer.

### 3. List Installments for Loan
- **URL**: `/loans/{loanId}/installments`
- **Method**: `GET`
- **Response**: Returns all installments for the given loan.

### 4. Pay Loan
- **URL**: `/loans/{loanId}/pay`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "amount": 2000.0
  }
  ```
- **Response**: Returns the payment result, including the number of installments paid and the total amount paid.

## Database Setup

The application uses an in-memory **H2 database** for easy setup. You can access the H2 console for querying the database:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- leave password field empty

## Running Tests

To run the unit tests and integration tests, use the following command:
```sh
mvn test
```

## Project Structure

The project follows a **Domain-Driven Design (DDD)** structure:
- **Domain Layer**: Contains core business logic (`domain/model`, `domain/service`).
- **Application Layer**: Handles application-specific logic and orchestration (`application/service`).
- **Interface Layer**: Contains REST controllers (`interfaces/controller`).

