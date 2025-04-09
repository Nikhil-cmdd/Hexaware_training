CREATE DATABASE LoanManagement;
USE LoanManagement;

-- 1. Customer Table
CREATE TABLE Customer (
    customerId INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(255),
    creditScore INT NOT NULL
);

-- 2. Loan Table (Base table for all loans)
CREATE TABLE Loan (
    loanId INT PRIMARY KEY,
    customerId INT,
    principalAmount DECIMAL(12,2) NOT NULL,
    interestRate DECIMAL(5,2) NOT NULL,
    loanTerm INT NOT NULL, -- in months
    loanType VARCHAR(20) CHECK (loanType IN ('HomeLoan', 'CarLoan')),
    loanStatus VARCHAR(20) CHECK (loanStatus IN ('Pending', 'Approved', 'Rejected')),
    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);

-- 3. HomeLoan Table (Extends Loan)
CREATE TABLE HomeLoan (
    loanId INT PRIMARY KEY,
    propertyAddress VARCHAR(255),
    propertyValue INT,
    FOREIGN KEY (loanId) REFERENCES Loan(loanId)
);

-- 4. CarLoan Table (Extends Loan)
CREATE TABLE CarLoan (
    loanId INT PRIMARY KEY,
    carModel VARCHAR(100),
    carValue INT,
    FOREIGN KEY (loanId) REFERENCES Loan(loanId)
);
