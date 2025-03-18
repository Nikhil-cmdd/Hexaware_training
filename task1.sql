CREATE DATABASE TechShop;
USE TechShop;

CREATE TABLE Customers(
CustomerID int AUTO_INCREMENT PRIMARY KEY,
FirstName varchar(15) NOT NULL,
LastName varchar(15) NOT NULL,
Email varchar(50) UNIQUE NOT NULL,
Phone varchar(15),
Address TEXT
);

CREATE TABLE Products(
ProductID int AUTO_INCREMENT PRIMARY KEY,
ProductName varchar(100) NOT NULL,
Descriptions text,
Price decimal(10,2) NOT NULL
);

CREATE TABLE Orders(
OrderID int AUTO_INCREMENT PRIMARY KEY,
CustomerID int,
OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
TotalAmount decimal(10,2) NOT NULL,
FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);

CREATE TABLE OrderDetails(
OrderDetailID int AUTO_INCREMENT PRIMARY KEY,
OrderID int,
ProductID int,
Quantity int NOT NULL,
FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);

CREATE TABLE Inventory(
InventoryID int AUTO_INCREMENT PRIMARY KEY,
ProductID int,
QuantityInStock int NOT NULL,
LastStockUpdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);

INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES
('Nikhil', 'Singh', 'nikhl.singh@gmail.com', 1234567890, 'Pune, Maharashtra'),
('Parth', 'Sakpal', 'parth.sakpal@gmail.com', 9876543210, 'Pune, Maharashtra'),
('Mayuresh', 'Firodiya', 'mayuresh.f@gmail.com', 1112223333, 'Pune, Maharashtra'),
('Mruganka', 'Chiplunkar', 'mruganka.c@gmail.com', 4445556666, 'Pune, Maharashtra'),
('Rishabarajan', 'C', 'rishabarajan.c@gmail.com', 7778889999, 'Chennai, Tamil Nadu'),
('Keerthika', 'R', 'keerthikar.@gmail.com', 6665554444, 'Chennai, Tamil Nadu'),
('Jhansi', 'k', 'jhansi.k@gmail.com', 3332221111, 'Chennai, Tamil Nadu'),
('JT', 'Sumetha', 'jt.sumetha@gmail.com', 8887776666, 'Chennai, Tamil Nadu'),
('Raja', 'N', 'raja.n@gmail.com', 9998887777, 'Chennai, Tamil Nadu'),
('Sushmita', 'S', 'sushmita.s@gmail.com', 2223334444, 'Chennai, Tamil Nadu');

INSERT INTO Products (ProductName, Descriptions, Price) VALUES
('Laptop', 'High performance laptop', 999.99),
('Smartphone', 'Latest model smartphone', 699.99),
('Headphones', 'Noise-cancelling headphones', 199.99),
('Monitor', '24-inch Full HD monitor', 149.99),
('Keyboard', 'Mechanical keyboard', 89.99),
('Mouse', 'Wireless mouse', 49.99),
('Smartwatch', 'Fitness tracking smartwatch', 249.99),
('Tablet', '10-inch tablet with stylus', 399.99),
('Speaker', 'Bluetooth speaker', 79.99),
('External Hard Drive', '1TB USB 3.0 hard drive', 109.99);

INSERT INTO Orders (CustomerID, TotalAmount) VALUES
(1, 1249.98),
(2, 699.99),
(3, 249.98),
(4, 199.99),
(5, 109.99),
(6, 89.99),
(7, 79.99),
(8, 149.99),
(9, 399.99),
(10, 999.99);

INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES
(1, 1, 1),
(1, 3, 2),
(2, 2, 1),
(3, 7, 1),
(3, 6, 1),
(4, 3, 1),
(5, 10, 1),
(6, 5, 1),
(7, 9, 1),
(8, 4, 1);

INSERT INTO Inventory (ProductID, QuantityInStock) VALUES
(1, 50),
(2, 30),
(3, 40),
(4, 25),
(5, 35),
(6, 60),
(7, 20),
(8, 15),
(9, 45),
(10, 30);