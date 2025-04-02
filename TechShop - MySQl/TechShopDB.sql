CREATE DATABASE TechShop;
USE TechShop;

-- task 1
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
('Nikhil', 'Singh', 'nikhil.singh@gmail.com', 1234567890, 'Pune, Maharashtra'),
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

-- task 2

SELECT FirstName, LastName, Email FROM Customers;

SELECT Orders.OrderID, Orders.OrderDate, Customers.FirstName, Customers.LastName FROM Orders JOIN Customers ON Orders.CustomerID = Customers.CustomerID;

INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES ('John', 'Doe', 'john.doe@example.com', '1234567890', '123 Elm St');
SET SQL_SAFE_UPDATES = 0;
UPDATE Products SET Price = ROUND(Price * 1.10, 2) WHERE ProductName IN ('Laptop', 'Smartphone', 'Smartwatch', 'Tablet', 'Headphones', 'Monitor');

DELETE FROM OrderDetails WHERE OrderID = 5;
DELETE FROM Orders WHERE OrderID =7;

INSERT INTO Orders (CustomerID, TotalAmount) VALUES (5, 500.00);

UPDATE Customers SET Email = 'sarvesh.shelke@gmail.com', Address = 'Mumbai, maharashtra' WHERE CustomerID = 5;

UPDATE Orders
SET TotalAmount = COALESCE((
    SELECT SUM(Products.Price * OrderDetails.Quantity)
    FROM OrderDetails
    JOIN Products ON OrderDetails.ProductID = Products.ProductID
    WHERE OrderDetails.OrderID = Orders.OrderID
), 0);

DELETE FROM OrderDetails WHERE OrderID IN (SELECT OrderID FROM Orders WHERE CustomerID = 5);

DELETE FROM Orders WHERE CustomerID = 5;

INSERT INTO Products (ProductName, Descriptions, Price) VALUES ('Gaming Laptop', 'High-performance gaming laptop with RGB keyboard', 1500.00);

ALTER TABLE Orders ADD COLUMN Status VARCHAR(20) DEFAULT 'Pending';

UPDATE Orders SET Status = 'Shipped' WHERE OrderID = 1;
SELECT OrderID, Status FROM Orders;

ALTER TABLE Customers ADD COLUMN OrderCount INT DEFAULT 0;

UPDATE Customers
SET OrderCount = (
    SELECT COUNT(*) FROM Orders WHERE Orders.CustomerID = Customers.CustomerID
);

SELECT CustomerID, FirstName, LastName, OrderCount FROM Customers;

-- task 3

SELECT OrderID, OrderDate, (SELECT FirstName FROM Customers WHERE Customers.CustomerID = Orders.CustomerID) AS FirstName, 
(SELECT LastName FROM Customers WHERE Customers.CustomerID = Orders.CustomerID) AS LastName FROM Orders;

SELECT Products.ProductName, SUM(Products.Price * OrderDetails.Quantity) AS TotalRevenue FROM OrderDetails
JOIN Products ON OrderDetails.ProductID = Products.ProductID GROUP BY Products.ProductName;

SELECT DISTINCT Customers.FirstName, Customers.LastName, Customers.Email, Customers.Phone FROM Customers
JOIN Orders ON Customers.CustomerID = Orders.CustomerID;

SELECT Products.ProductName, SUM(OrderDetails.Quantity) AS TotalQuantityOrdered FROM OrderDetails
JOIN Products ON OrderDetails.ProductID = Products.ProductID GROUP BY Products.ProductName
ORDER BY TotalQuantityOrdered DESC LIMIT 1;

UPDATE Products 
SET Category = 
CASE WHEN ProductName IN ('Laptop', 'Smartphone', 'Tablet', 'Smartwatch', 'Headphones', 'Monitor', 'Gaming Laptop') THEN 'Electronics'
WHEN ProductName IN ('Keyboard', 'Mouse', 'External Hard Drive', 'Speaker') THEN 'Accessories'
ELSE 'Other'
END;
SET SQL_SAFE_UPDATES = 0;

SELECT ProductName, Category FROM Products;

SELECT Customers.FirstName, Customers.LastName, AVG(Orders.TotalAmount) AS AvgOrderValue
FROM Orders JOIN Customers ON Orders.CustomerID = Customers.CustomerID GROUP BY Customers.CustomerID;

SELECT Orders.OrderID, Customers.FirstName, Customers.LastName, Orders.TotalAmount FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID ORDER BY Orders.TotalAmount DESC LIMIT 1;

SELECT Products.ProductName, COUNT(OrderDetails.OrderID) AS TimesOrdered FROM OrderDetails
JOIN Products ON OrderDetails.ProductID = Products.ProductID GROUP BY Products.ProductName;

SELECT DISTINCT Customers.FirstName, Customers.LastName, Customers.Email
FROM Customers JOIN Orders ON Customers.CustomerID = Orders.CustomerID JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID
JOIN Products ON OrderDetails.ProductID = Products.ProductID WHERE Products.ProductName = 'Laptop';  -- Change 'Laptop' to user input

SELECT SUM(TotalAmount) AS TotalRevenue 
FROM Orders WHERE OrderDate BETWEEN (SELECT MIN(OrderDate) FROM Orders) AND (SELECT MAX(OrderDate) FROM Orders);

-- task 4

SELECT FirstName, LastName FROM Customers WHERE CustomerID NOT IN (SELECT DISTINCT CustomerID FROM Orders);

SELECT COUNT(*) AS TotalProducts FROM Products;

SELECT SUM(TotalAmount) AS TotalRevenue FROM Orders;

SELECT AVG(Quantity) AS AvgQuantityOrdered FROM OrderDetails WHERE ProductID IN (SELECT ProductID FROM Products WHERE Category = 'Laptop');

SELECT SUM(TotalAmount) AS CustomerRevenue FROM Orders WHERE CustomerID = 1;  -- Change '1' to any customer ID

SELECT CustomerID, COUNT(OrderID) AS OrderCount FROM Orders GROUP BY CustomerID
ORDER BY OrderCount DESC LIMIT 1;

SELECT ProductID, SUM(Quantity) AS TotalOrdered FROM OrderDetails GROUP BY ProductID
ORDER BY TotalOrdered DESC LIMIT 1;

SELECT Customers.FirstName, Customers.LastName, SUM(Orders.TotalAmount) AS TotalSpent FROM Customers
JOIN Orders ON Customers.CustomerID = Orders.CustomerID GROUP BY Customers.CustomerID ORDER BY TotalSpent DESC LIMIT 1;

SELECT AVG(TotalAmount) AS AverageOrderValue FROM Orders;

SELECT Customers.FirstName, Customers.LastName, COUNT(Orders.OrderID) AS OrderCount FROM Customers
LEFT JOIN Orders ON Customers.CustomerID = Orders.CustomerID GROUP BY Customers.CustomerID;
