# 📋 NFC-Based Attendance Management System

A desktop application for marking and managing student attendance using NFC cards. Built with Java Swing for GUI, MySQL for backend storage, and integrated with serial port-based NFC reader hardware.

---

## ✨ Features

- 🔐 Admin login screen  
- 🧑‍🎓 Student registration with department/year selection  
- 📲 NFC card UID reading via serial port (using jSerialComm)  
- ✅ Real-time attendance marking and storage in MySQL  
- 📄 View, clear, and download attendance records  
- 🎨 Polished UI using Java Swing with custom styles  

---

## 📂 Project Structure

```te
.
├── AttendanceManagementSystem.java       # Entry point
├── LoginWindow.java                      # Login GUI
├── MainAttendanceWindow.java             # Dashboard GUI after login
├── NFCReader.java                        # Handles NFC UID reading via serial port
├── registerStudent_DB.java               # Student registration handler
├── attendance_DBMethods.java             # Attendance logic (record, view, clear, download)
├── attendance_DB.java                    # DB helper (if used)
├── BaseDB.java                           # Manages MySQL DB connection
├── AttendanceMarkingException.java       # Custom exception class
├── InvalidStudentDataException.java      # Custom validation exception
└── README.md                             # This file

⚙️ Prerequisites
Java installed and configured

MySQL Server installed and running

NFC reader connected via USB (configured at 9600 baud rate)

Add jSerialComm library to your project’s build path

📥 Download jSerialComm

🔧 Setup Instructions
🗃️ Step 1: MySQL Database Setup
Run the following SQL commands:


CREATE DATABASE students_db;

CREATE TABLE students (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    uid VARCHAR(50) UNIQUE,
    email VARCHAR(100),
    department VARCHAR(10),
    year INT
);

CREATE TABLE attendance (
    uid VARCHAR(50),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);
🔑 Step 2: Update Database Credentials
Edit the BaseDB.java file with your MySQL credentials:

protected static final String DB_URL = "jdbc:mysql://localhost:3306/students_db";
protected static final String USER = "root";
protected static final String PASS = "mysqlroot";
🖥️ Step 3: Run the Application
Compile and run AttendanceManagementSystem.java

Login with the following credentials:


Username: admin
Password: password
From the dashboard, you can:

Register students

Start attendance recording

View, download, or clear records

📁 Output Files
Attendance logs are saved as .txt files in the following directory:


/Attendance_data/attendance_record_<timestamp>.txt
🚧 Limitations
Static login credentials (admin/password)

No encryption in DB communication

Requires prior UID registration to mark attendance

📄 License
This project is licensed under the MIT License.
You are free to use, modify, and share it for educational purposes. ```
