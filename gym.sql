DROP DATABASE gym;
CREATE DATABASE gym;
USE gym;

CREATE TABLE Trainer(
	trainerID VARCHAR(4),
	tName VARCHAR(50) NOT NULL,
	gender ENUM('M','F') NOT NULL,
	PRIMARY KEY (trainerID)
);
	
CREATE TABLE Specialism(
	trainerID VARCHAR(4),
	focus VARCHAR(20) NOT NULL,
	PRIMARY KEY (trainerID,focus),
	FOREIGN KEY (trainerID) REFERENCES Trainer(trainerID)
);

CREATE TABLE Client(
	clientID VARCHAR(4),
	cName VARCHAR(50) NOT NULL,
	PRIMARY KEY (clientID)
);
	
INSERT INTO Trainer
VALUES ('T001', 'Dwayne John', 'M'),
 ('T002', 'Sarah Chong', 'F'),
 ('T003', 'Adam Smith', 'M'),
 ('T004', 'Hana Becker', 'F');

 INSERT INTO Specialism
VALUES('T001', 'flexibility'),
('T002', 'weight-loss'),
('T002', 'muscle-gain'),
('T003', 'flexibility'),
('T004', 'muscle-gain'),
('T004', 'flexibility'),
('T004', 'weight-loss');
 

INSERT INTO Client
VALUES('C001', 'John White'),
('C002', 'Ann Beech'),
('C003', 'Mary Howe'),
('C004', 'Susan Brand'),
('C005', 'Julie Lee');

CREATE TABLE Booking(
	bookingID VARCHAR(4),
    trainerID VARCHAR(4) NOT NULL,
    clientID VARCHAR(4) NOT NULL,
    focus VARCHAR(20) NOT NULL,
	bookingDate DATE NOT NULL,
	bookingTime TIME NOT NULL,
    duration TIME NOT NULL,
    endtime TIME NOT NULL,
	PRIMARY KEY (bookingID),
	FOREIGN KEY (clientID) REFERENCES Client(clientID),
    FOREIGN KEY (trainerID, focus) REFERENCES Specialism(trainerID, focus)
);

INSERT INTO Booking
VALUES('B001', 'T001', 'C001', 'flexibility', '2020-10-01', 110000, 10000, 120000),
('B002', 'T002', 'C002', 'weight-loss', '2020-11-01', 130000, 13000, 143000),
('B003', 'T002', 'C003', 'muscle-gain', '2020-11-03', 103000, 20000, 123000),
('B004', 'T003', 'C002', 'flexibility', '2020-12-01', 110000, 13000, 123000),
('B005', 'T001', 'C004', 'flexibility', '2020-11-07', 140000, 13000, 163000),
('B006', 'T004', 'C005', 'muscle-gain', '2020-10-01', 90000, 10000, 100000),
('B007', 'T004', 'C003', 'flexibility', '2020-10-09', 100000, 13000, 113000),
('B008', 'T004', 'C002', 'weight-loss', '2020-10-09', 120000, 20000, 140000);




