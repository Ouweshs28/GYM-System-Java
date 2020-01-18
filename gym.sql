DROP DATABASE gym;
CREATE DATABASE gym;
USE gym;
	
CREATE TABLE Booking(
	bookingID VARCHAR(4),
	staffID VARCHAR(4),
	trainerID VARCHAR(4),
	clientname VARCHAR(30) NOT NULL,
	gender ENUM('M','F') NOT NULL,
	focus VARCHAR(24) NOT NULL, 
	bookingdate DATE NOT NULL,
	bookingtime VARCHAR(5),
	duration VARCHAR(4),
	PRIMARY KEY (bookingID)
);
	
/* INSERT INTO Branch
VALUES ('B005', '22 Deer Rd', 'London', 'SW1 4EH'),
 ('B007', '16 Argyll St', 'Aberdeen', 'AB2 3SU'),
 ('B003', '163 Main St', 'Glasgow', 'G11 9QX'),
 ('B004', '32 Manse Rd', 'Bristol', 'BS99 1NZ'),
 ('B002', '56 Clover Dr', 'London', 'NW10 6EU');
 */
INSERT INTO Booking
VALUES ('B005','SL21','T023','John White', 'M','flexibility', '2020-10-01','11 00', '1.00'),
 ('B003','SG37','T012','Ann Beech','F','weight loss','2020-11-10', '13 00', '1.30'),
 ('B007','SG14','T031','David Ford', 'M','muscle gain', '2020-03-24', '09 00', '1.15'),
 ('B004','SA9','T007', 'Mary Howe','F','muscle gain','2020-02-19','10 00', '2.00'),
 ('B009','SG5','T006','Susan Brand','F', 'weight loss','2020-06-03', '16 00', '4.00'),
 ('B002','SL41','T011','Julie Lee','F', 'flexibility','2020-06-13', '13 30', '0.50');
 
