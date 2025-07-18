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

.
├── AttendanceManagementSystem.java # App entry point
├── LoginWindow.java # Login GUI
├── MainAttendanceWindow.java # Attendance dashboard
├── NFCReader.java # UID reading from NFC card
├── registerStudent_DB.java # Handles student registration
├── attendance_DBMethods.java # Attendance logic
├── attendance_DB.java # Optional database helper
├── BaseDB.java # MySQL connection setup
├── AttendanceMarkingException.java # Custom exception for marking errors
├── InvalidStudentDataException.java # Custom exception for registration validation
└── README.md # This file

---

## 🧰 Tech Stack

- **Java 8+**
- **Java Swing**
- **MySQL**
- **jSerialComm** (for NFC reader serial communication)
- **JDBC** (for database interaction)

---

## ⚙️ Prerequisites

- Java installed and configured
- MySQL Server installed and running
- NFC reader connected via USB (configured at 9600 baud rate)
- Add `jSerialComm` library to your project’s build path (download from: https://fazecast.github.io/jSerialComm/)

---

## 🔧 Setup Instructions

### 🗃️ Step 1: MySQL Database Setup

1. Open MySQL client and run:

```sql
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
Update BaseDB.java with your MySQL credentials:

java
Copy
Edit
protected static final String DB_URL = "jdbc:mysql://localhost:3306/students_db";
protected static final String USER = "root";
protected static final String PASS = "mysqlroot";
🖥️ Running the App
Compile and run AttendanceManagementSystem.java

Login with:

pgsql
Copy
Edit
Username: admin
Password: password
From the dashboard, you can:

Register students

Start attendance recording

View, download, or clear records

📁 Output Files
Attendance logs are saved as .txt files in:

bash
Copy
Edit
/Attendance_data/attendance_record_<timestamp>.txt
🚧 Limitations
Static login credentials (admin/password)

No encryption in DB communication

Requires prior UID registration to mark attendance

👨‍💻 Developed By
Richan Abraham J.R.
Kersen
Karunya Institute of Technology and Sciences
Object-Oriented Programming Course Project

📄 License
This project is licensed under the MIT License.
You are free to use, modify, and share it for educational purposes.

