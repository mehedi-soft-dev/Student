CREATE DATABASE studendb;

CREATE TABLE Teacher(    
	Id INT PRIMARY KEY AUTO_INCREMENT,    
	FirstName VARCHAR(20),    
	LastName VARCHAR(20),    
	Email VARCHAR(30) 
);

INSERT INTO Teacher (FirstName, LastName, Email, Username, Password) VALUES('xiao', 'bao', 'xiaobao@mail.com', 'xiaobao', '123');

CREATE TABLE Course(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    	Name VARCHAR (20),
    	teacherId INT REFERENCES teacher(Id)
);

CREATE TABLE Student(    
	Id INT PRIMARY KEY AUTO_INCREMENT,    
	FirstName VARCHAR(20),    
	LastName VARCHAR(20),    
	Email VARCHAR(30) 
);

INSERT INTO Student (FirstName, LastName, Email, Username, Password) VALUES('Mehadi ', Hasan'mehadi@mail.com', 'mehadi', '123');


CREATE TABLE SelectedCourse(
	Id INT PRIMARY KEY AUTO_INCREMENT,
   	StudentId INT REFERENCES Student(Id),
    	CourseId INT REFERENCES Course(Id)
);

CREATE TABLE Score(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    	TeacherId INT REFERENCES Teacher(Id),
   	StudentId INT REFERENCES Student(Id),
   	CourseId INT REFERENCES Course(Id),
    	Score INT
);