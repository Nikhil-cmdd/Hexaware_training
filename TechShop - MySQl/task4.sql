USE techshop;

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
