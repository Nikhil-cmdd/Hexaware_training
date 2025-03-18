USE techshop;

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
