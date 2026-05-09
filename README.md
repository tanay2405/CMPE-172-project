# Student Tutoring Service (CMPE 172 Project)

## Overview
The Student Tutoring Service is an online appointment scheduling system where students can book appointments for different tutoring sessions. 

## Features
- Browse available tutoring session appointments
- Book/Cancel appointments for tutoring sessions
- View your own appointment history
- Tutor management of appointment availability such as adding/canceling appointments
- Concurrency control to prevent double bookings
- Mock external notification service

## Technologies Used
- Java + Spring Boot
- MySQL Database accessed via JDBC

## Installation Instructions

1. Open the Project in IntelliJ

Go to File → Open and select the pom.xml file 
Choose Open as Project
Wait for Maven to finish downloading dependencies 

2. Set the SDK

Go to File → Project Structure (Cmd+; on Mac / Ctrl+Alt+Shift+S on Windows)
Under SDK, select Java 17
Click Apply → OK

3. Mark Sources Root

In the left panel, right-click the src/main/java folder
Select Mark Directory as → Sources Root

4. Set Up MySQL

Open MySQL and run:

sql  CREATE DATABASE tutoring_service;

Open src/main/resources/application.properties and update:

  spring.datasource.password=YOUR_MYSQL_PASSWORD

The tables and sample data will be created automatically on first run

5. Run the App

Open StudentTutoringServiceApplication.java
Click the play button next to the main method

Run the app on http://localhost:8080
