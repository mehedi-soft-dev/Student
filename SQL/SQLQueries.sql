CREATE DATABASE studentdb;

USE studentdb;

CREATE TABLE Teacher(
	Id INT PRIMARY KEY IDENTITY(1,1),
	FirstName VARCHAR(20),
	LastName VARCHAR(20),
	Email VARCHAR(30),
	ContactNo VARCHAR(15),
	Address VARCHAR(50),
)