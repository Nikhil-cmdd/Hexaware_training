CREATE DATABASE VirtualArtGallery;
USE VirtualArtGallery;

CREATE TABLE Artists (
ArtistID INT PRIMARY KEY,
Name VARCHAR(255) NOT NULL,
Biography TEXT,
Nationality VARCHAR(100)
);

CREATE TABLE Categories (
CategoryID INT PRIMARY KEY,
Name VARCHAR(100) NOT NULL
);

CREATE TABLE Artworks ( 
ArtworkID INT PRIMARY KEY, 
Title VARCHAR(255) NOT NULL, 
ArtistID INT, 
CategoryID INT, 
Year INT, 
Description TEXT, 
ImageURL VARCHAR(255), 
FOREIGN KEY (ArtistID) REFERENCES Artists (ArtistID), 
FOREIGN KEY (CategoryID) REFERENCES Categories (CategoryID)
);

CREATE TABLE Exhibitions ( 
ExhibitionID INT PRIMARY KEY, 
Title VARCHAR(255) NOT NULL, 
StartDate DATE, 
EndDate DATE, 
Description TEXT
); 

CREATE TABLE ExhibitionArtworks ( 
ExhibitionID INT, 
ArtworkID INT, 
PRIMARY KEY (ExhibitionID, ArtworkID), 
FOREIGN KEY (ExhibitionID) REFERENCES Exhibitions (ExhibitionID), 
FOREIGN KEY (ArtworkID) REFERENCES Artworks (ArtworkID)
);

INSERT INTO Artists (ArtistID, Name, Biography, Nationality) VALUES 
(1, 'Pablo Picasso', 'Renowned Spanish painter and sculptor.', 'Spanish'), 
(2, 'Vincent van Gogh', 'Dutch post-impressionist painter.', 'Dutch'), 
(3, 'Leonardo da Vinci', 'Italian polymath of the Renaissance.', 'Italian');

INSERT INTO Categories (CategoryID, Name) VALUES 
(1, 'Painting'), 
(2, 'Sculpture'), 
(3, 'Photography');

INSERT INTO Artworks (ArtworkID, Title, ArtistID, CategoryID, Year, Description, ImageURL) VALUES 
(1, 'Starry Night', 2, 1, 1889, 'A famous painting by Vincent van Gogh.', 'starry_night.jpg'), 
(2, 'Mona Lisa', 3, 1, 1503, 'The iconic portrait by Leonardo da Vinci.', 'mona_lisa.jpg'), 
(3, 'Guernica', 1, 1, 1937, 'Pablo Picasso\'s powerful anti-war mural.', 'guernica.jpg');

INSERT INTO Exhibitions (ExhibitionID, Title, StartDate, EndDate, Description) VALUES 
(1, 'Modern Art Masterpieces', '2023-01-01', '2023-03-01', 'A collection of modern art masterpieces.'), 
(2, 'Renaissance Art', '2023-04-01', '2023-06-01', 'A showcase of Renaissance art treasures.');

INSERT INTO ExhibitionArtworks (ExhibitionID, ArtworkID) VALUES 
(1, 1), 
(1, 2), 
(1, 3), 
(2, 2);

-- 1 a: Artists, aw: Artworks
SELECT a.Name, COUNT(aw.ArtworkID) AS ArtworkCount FROM Artists a LEFT JOIN Artworks aw ON a.ArtistID = aw.ArtistID GROUP BY a.ArtistID ORDER BY ArtworkCount DESC;

-- 2
SELECT aw.Title FROM Artworks aw JOIN Artists a ON aw.ArtistID = a.ArtistID WHERE a.Nationality IN ('Spanish', 'Dutch') ORDER BY aw.Year ASC;

-- 3 c: Categories
SELECT a.Name, COUNT(aw.ArtworkID) AS PaintingCount FROM Artists a JOIN Artworks aw ON a.ArtistID = aw.ArtistID
JOIN Categories c ON aw.CategoryID = c.CategoryID WHERE c.Name = 'Painting' GROUP BY a.ArtistID;

-- 4 e: Exhibitions, ea: ExhibitionArtworks
SELECT aw.Title AS ArtworkTitle, a.Name AS ArtistName, c.Name AS CategoryName FROM ExhibitionArtworks ea JOIN Artworks aw ON ea.ArtworkID = aw.ArtworkID
JOIN Artists a ON aw.ArtistID = a.ArtistID JOIN Categories c ON aw.CategoryID = c.CategoryID JOIN Exhibitions e ON ea.ExhibitionID = e.ExhibitionID WHERE e.Title = 'Modern Art Masterpieces';

-- 5
SELECT a.Name FROM Artists a JOIN Artworks aw ON a.ArtistID = aw.ArtistID GROUP BY a.ArtistID HAVING COUNT(aw.ArtworkID) > 2;

-- 6
SELECT aw.Title FROM ExhibitionArtworks ea1 JOIN ExhibitionArtworks ea2 ON ea1.ArtworkID = ea2.ArtworkID 
JOIN Exhibitions e1 ON ea1.ExhibitionID = e1.ExhibitionID  JOIN Exhibitions e2 ON ea2.ExhibitionID = e2.ExhibitionID  JOIN Artworks aw ON ea1.ArtworkID = aw.ArtworkID
WHERE e1.Title = 'Modern Art Masterpieces' AND e2.Title = 'Renaissance Art';

-- 7
SELECT c.Name AS CategoryName, COUNT(aw.ArtworkID) AS TotalArtworks FROM Categories c LEFT JOIN Artworks aw ON c.CategoryID = aw.CategoryID GROUP BY c.CategoryID;

-- 8
SELECT a.Name FROM Artists a JOIN Artworks aw ON a.ArtistID = aw.ArtistID GROUP BY a.ArtistID HAVING COUNT(aw.ArtworkID) > 3;

-- 9
SELECT aw.Title FROM Artworks aw JOIN Artists a ON aw.ArtistID = a.ArtistID WHERE a.Nationality = 'Spanish';

-- 10
SELECT e.Title FROM Exhibitions e
WHERE e.ExhibitionID IN (
SELECT ea.ExhibitionID FROM ExhibitionArtworks ea
JOIN Artworks aw ON ea.ArtworkID = aw.ArtworkID
JOIN Artists a ON aw.ArtistID = a.ArtistID
WHERE a.Name = 'Vincent van Gogh'
)
AND e.ExhibitionID IN (
SELECT ea.ExhibitionID FROM ExhibitionArtworks ea
JOIN Artworks aw ON ea.ArtworkID = aw.ArtworkID
JOIN Artists a ON aw.ArtistID = a.ArtistID
WHERE a.Name = 'Leonardo da Vinci'
);

-- 11
SELECT aw.Title FROM Artworks aw LEFT JOIN ExhibitionArtworks ea ON aw.ArtworkID = ea.ArtworkID WHERE ea.ExhibitionID IS NULL;

-- 12
SELECT a.Name FROM Artists a JOIN Artworks aw ON a.ArtistID = aw.ArtistID GROUP BY a.ArtistID HAVING COUNT(DISTINCT aw.CategoryID) = (SELECT COUNT(*) FROM Categories);

-- 13
SELECT c.Name AS CategoryName, COUNT(aw.ArtworkID) AS TotalArtworks FROM Categories c LEFT JOIN Artworks aw ON c.CategoryID = aw.CategoryID GROUP BY c.Name;

-- 14
SELECT a.Name FROM Artists a JOIN Artworks aw ON a.ArtistID = aw.ArtistID GROUP BY a.ArtistID HAVING COUNT(aw.ArtworkID) > 2;

-- 15
SELECT c.Name AS CategoryName, AVG(aw.Year) AS AverageYear FROM Categories c JOIN Artworks aw ON c.CategoryID = aw.CategoryID GROUP BY c.Name HAVING COUNT(aw.ArtworkID) > 1;

-- 16
SELECT aw.Title FROM ExhibitionArtworks ea JOIN Artworks aw ON ea.ArtworkID = aw.ArtworkID JOIN Exhibitions e ON ea.ExhibitionID = e.ExhibitionID WHERE e.Title = 'Modern Art Masterpieces';

-- 17
SELECT c.Name AS CategoryName FROM Categories c JOIN Artworks aw ON c.CategoryID = aw.CategoryID GROUP BY c.Name HAVING AVG(aw.Year) > (SELECT AVG(Year) FROM Artworks);

-- 18
SELECT aw.Title FROM Artworks aw LEFT JOIN ExhibitionArtworks ea ON aw.ArtworkID = ea.ArtworkID WHERE ea.ExhibitionID IS NULL;

-- 19
SELECT DISTINCT a.Name FROM Artists a JOIN Artworks aw1 ON a.ArtistID = aw1.ArtistID JOIN Categories c ON aw1.CategoryID = c.CategoryID  
WHERE c.CategoryID IN (  
    SELECT CategoryId  
    FROM Artworks  
    WHERE Title='Mona Lisa'
);

-- 20
SELECT a.Name AS ArtistName, COUNT(aw.ArtworkID) AS ArtworkCount FROM Artists a LEFT JOIN Artworks aw ON a.ArtistID = aw.ArtistID GROUP BY a.ArtistID;
