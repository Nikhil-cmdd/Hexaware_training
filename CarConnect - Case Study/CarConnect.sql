CREATE DATABASE CarConnect;

USE CarConnect;

CREATE TABLE Customer (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15) UNIQUE NOT NULL,
    Address TEXT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Vehicle (
    VehicleID INT AUTO_INCREMENT PRIMARY KEY,
    Model VARCHAR(50) NOT NULL,
    Make VARCHAR(50) NOT NULL,
    Year INT NOT NULL,
    Color VARCHAR(30),
    RegistrationNumber VARCHAR(50) UNIQUE NOT NULL,
    Availability BOOLEAN DEFAULT TRUE,
    DailyRate DECIMAL(10,2) NOT NULL
);

CREATE TABLE Reservation (
    ReservationID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    VehicleID INT,
    StartDate DATETIME NOT NULL,
    EndDate DATETIME NOT NULL,
    TotalCost DECIMAL(10,2) NOT NULL,
    Status ENUM('pending', 'confirmed', 'completed', 'cancelled') DEFAULT 'pending',
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE CASCADE,
    FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID) ON DELETE CASCADE
);

CREATE TABLE Admin (
    AdminID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15) UNIQUE NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('super admin', 'fleet manager', 'support') NOT NULL,
    JoinDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber, Address, Username, Password) VALUES
('Rahul', 'Sharma', 'rahul.sharma@gmail.com', '9876543210', 'Pune, Maharashtra', 'rahul123', 'hashedpassword1'),
('Amit', 'Patel', 'amit.patel@gmail.com', '9876543211', 'Mumbai, Maharashtra', 'amitp', 'hashedpassword2'),
('Neha', 'Singh', 'neha.singh@gmail.com', '9876543212', 'Chennai, Tamil Nadu', 'nehas', 'hashedpassword3'),
('Priya', 'Rao', 'priya.rao@gmail.com', '9876543213', 'Pune, Maharashtra', 'priyar', 'hashedpassword4'),
('Suresh', 'Naik', 'suresh.naik@gmail.com', '9876543214', 'Mumbai, Maharashtra', 'sureshn', 'hashedpassword5'),
('Anita', 'Verma', 'anita.verma@gmail.com', '9876543215', 'Chennai, Tamil Nadu', 'anitav', 'hashedpassword6'),
('Vikas', 'Joshi', 'vikas.joshi@gmail.com', '9876543216', 'Pune, Maharashtra', 'vikasj', 'hashedpassword7'),
('Kavita', 'Menon', 'kavita.menon@gmail.com', '9876543217', 'Mumbai, Maharashtra', 'kavitam', 'hashedpassword8'),
('Rajesh', 'Pillai', 'rajesh.pillai@gmail.com', '9876543218', 'Chennai, Tamil Nadu', 'rajeshp', 'hashedpassword9'),
('Sunil', 'Kumar', 'sunil.kumar@gmail.com', '9876543219', 'Pune, Maharashtra', 'sunilk', 'hashedpassword10');

INSERT INTO Vehicle (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate) VALUES
('Swift', 'Maruti', 2020, 'White', 'MH12AB1234', TRUE, 1500.00),
('i20', 'Hyundai', 2021, 'Red', 'MH14CD5678', TRUE, 1800.00),
('City', 'Honda', 2019, 'Black', 'TN01EF9012', TRUE, 2000.00),
('Creta', 'Hyundai', 2022, 'Blue', 'MH12GH3456', TRUE, 2200.00),
('Fortuner', 'Toyota', 2023, 'Silver', 'TN10IJ7890', TRUE, 3500.00),
('Alto', 'Maruti', 2018, 'Gray', 'MH20KL1234', TRUE, 1200.00),
('Baleno', 'Maruti', 2021, 'Blue', 'MH15MN5678', TRUE, 1700.00),
('Verna', 'Hyundai', 2020, 'White', 'TN05OP9012', TRUE, 2100.00),
('Innova', 'Toyota', 2022, 'Black', 'MH18QR3456', TRUE, 3000.00),
('Kia Seltos', 'Kia', 2023, 'Red', 'TN07ST7890', TRUE, 2500.00);

INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status) VALUES
(1, 2, '2025-03-20 10:00:00', '2025-03-22 10:00:00', 3600.00, 'confirmed'),
(2, 3, '2025-03-21 12:00:00', '2025-03-23 12:00:00', 4000.00, 'pending'),
(3, 5, '2025-03-19 08:00:00', '2025-03-21 08:00:00', 7000.00, 'completed'),
(4, 1, '2025-03-22 14:00:00', '2025-03-24 14:00:00', 3000.00, 'cancelled'),
(5, 4, '2025-03-20 09:00:00', '2025-03-23 09:00:00', 6600.00, 'confirmed'),
(6, 7, '2025-03-18 07:00:00', '2025-03-20 07:00:00', 3400.00, 'completed'),
(7, 6, '2025-03-21 16:00:00', '2025-03-23 16:00:00', 2400.00, 'pending'),
(8, 9, '2025-03-24 06:00:00', '2025-03-26 06:00:00', 6000.00, 'confirmed'),
(9, 8, '2025-03-19 10:00:00', '2025-03-22 10:00:00', 6300.00, 'completed'),
(10, 10, '2025-03-25 11:00:00', '2025-03-27 11:00:00', 5000.00, 'pending');

INSERT INTO Admin (FirstName, LastName, Email, PhoneNumber, Username, Password, Role) VALUES
('Nikhil', 'Singh', 'nikhil.singh@gmail.com', '9999999999', 'admin1', 'admin1', 'super admin');
