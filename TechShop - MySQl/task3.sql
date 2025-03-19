USE techshop;

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
