CREATE TABLE IF NOT EXISTS UserInfo (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    password VARCHAR(255),
    role ENUM('STUDENT', 'TUTOR')
);

CREATE TABLE IF NOT EXISTS AvailabilityTimeSlot (
    slotID INT PRIMARY KEY AUTO_INCREMENT,
    tutorID INT,
    tutorName VARCHAR(100),
    subject VARCHAR(100),
    startTime VARCHAR(50),
    endTime VARCHAR(50),
    capacity INT DEFAULT 1,
    bookedCount INT DEFAULT 0,
    status ENUM('AVAILABLE', 'BOOKED') DEFAULT 'AVAILABLE',
    FOREIGN KEY (tutorID) REFERENCES UserInfo(userID),
    UNIQUE KEY unique_slot (tutorID, startTime, endTime)
);

CREATE TABLE IF NOT EXISTS Appointment (
    appointmentID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,
    slotID INT,
    tutorID INT,
    bookedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'CONFIRMED',
    FOREIGN KEY (userID) REFERENCES UserInfo(userID),
    FOREIGN KEY (slotID) REFERENCES AvailabilityTimeSlot(slotID)
);

INSERT IGNORE INTO UserInfo (name, email, password, role) VALUES
('Daphne Chen', 'daphne@sjsu.edu', 'daphne123', 'TUTOR'),
('Juan Gomez', 'juan@sjsu.edu', 'juan456', 'TUTOR'),
('Telvin Smith', 'telvin@sjsu.edu', 'telvin789', 'TUTOR'),
('Carlos Martinez', 'carlos@sjsu.edu', 'carlos321', 'TUTOR'),
('Mark Johnson', 'mark@sjsu.edu', 'mark654', 'TUTOR'),
('William Davis', 'william@sjsu.edu', 'william987', 'TUTOR'),
('Karen Wilson', 'karen@sjsu.edu', 'karen111', 'TUTOR'),
('Joe Brown', 'joe@sjsu.edu', 'joe222', 'TUTOR'),
('Gary Taylor', 'gary@sjsu.edu', 'gary333', 'TUTOR'),
('Tanay Allaparti', 'tanay@sjsu.edu', 'tanay24', 'STUDENT'),
('Srihan Cheem', 'srihan@sjsu.edu', 'srihan456', 'STUDENT'),
('Aarav Baph', 'aarav@sjsu.edu', 'aarav789', 'STUDENT'),
('Bob Smith', 'bob@sjsu.edu', 'bob321', 'STUDENT'),
('Aaron Lee', 'aaron@sjsu.edu', 'aaron654', 'STUDENT'),
('Connor Gonzalez', 'connor@sjsu.edu', 'connor987', 'STUDENT'),
('Ryan Li', 'ryan@sjsu.edu', 'ryan111', 'STUDENT'),
('Jake Williams', 'jake@sjsu.edu', 'jake222', 'STUDENT'),
('Ethan Kim', 'ethan@sjsu.edu', 'ethan333', 'STUDENT'),
('Lucas Sinclair', 'lucas@sjsu.edu', 'lucas444', 'STUDENT');

INSERT IGNORE INTO AvailabilityTimeSlot (tutorID, tutorName, subject, startTime, endTime, capacity, bookedCount, status) VALUES
(1, 'Daphne Chen', 'CMPE', '3:00 PM', '4:00 PM', 5, 0, 'AVAILABLE'),
(2, 'Juan Gomez', 'Math', '11:00 AM', '12:00 PM', 5, 0, 'AVAILABLE'),
(3, 'Telvin Smith', 'CS', '5:00 PM', '6:00 PM', 5, 0, 'AVAILABLE'),
(4, 'Carlos Martinez', 'Physics', '2:00 PM', '3:00 PM', 4, 0, 'AVAILABLE'),
(5, 'Mark Johnson', 'Chemistry', '10:00 AM', '11:00 AM', 3, 0, 'AVAILABLE'),
(6, 'William Davis', 'Biology', '7:00 PM', '8:00 PM', 5, 0, 'AVAILABLE'),
(7, 'Karen Wilson', 'English', '9:00 AM', '10:00 AM', 4, 0, 'AVAILABLE'),
(8, 'Joe Brown', 'CMPE', '10:30 AM', '12:00 PM', 3, 0, 'AVAILABLE'),
(9, 'Gary Taylor', 'CS', '5:30 PM', '6:30 PM', 4, 0, 'AVAILABLE');