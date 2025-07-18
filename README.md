# NFC_Enabled-Attendance-Tracking-Application

# 📋 NFC-Based Attendance Management System

This is a Java Swing desktop application that enables efficient, secure, and automated attendance tracking using NFC card readers. It integrates with a MySQL database to store student information and attendance logs. Designed as a part of a course project at **Karunya Institute of Technology and Sciences**.

---

## ✨ Features

- 🔐 **Login Screen** (Admin authentication)
- 🧑‍🏫 **Student Registration** with department & year categorization
- 📲 **NFC UID Reading** from physical NFC cards using serial port
- 📅 **Real-time Attendance Marking** into a MySQL database
- 📂 View, download, and clear attendance logs
- 💾 Stores data persistently in a MySQL database

---

## 📂 Project Structure

.
├── AttendanceManagementSystem.java # Entry point
├── LoginWindow.java # Login GUI
├── MainAttendanceWindow.java # Main dashboard after login
├── registerStudent_DB.java # Handles student registration logic
├── attendance_DBMethods.java # Attendance logic (record, view, clear, download)
├── NFCReader.java # NFC card reader logic using jSerialComm
├── BaseDB.java # MySQL DB connection management
├── attendance_DB.java # Attendance table definition logic
├── AttendanceMarkingException.java # Custom exception for marking failures
├── InvalidStudentDataException.java # Custom exception for invalid student registration
└── README.md # You are here

yaml
Copy
Edit

---

## 🛠️ Requirements

- Java 8 or later
- MySQL Server (e.g. XAMPP, WAMP, standalone)
- [jSerialComm](https://fazecast.github.io/jSerialComm/) library for serial communication
- NFC Reader (compatible with COM port, 9600 baud rate)

---

## ⚙️ Setup Instructions

### 🔌 Step 1: Database Setup

1. **Create database**:
   ```sql
   CREATE DATABASE students_db;
Create students table:

sql
Copy
Edit
CREATE TABLE students (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    uid VARCHAR(50) UNIQUE,
    email VARCHAR(100),
    department VARCHAR(10),
    year INT
);
Create attendance table:

sql
Copy
Edit
CREATE TABLE attendance (
    uid VARCHAR(50),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);
💻 Step 2: Code Configuration
Open the project in your IDE.

Update MySQL credentials in BaseDB.java:

java
Copy
Edit
protected static final String DB_URL = "jdbc:mysql://localhost:3306/students_db";
protected static final String USER = "root";
protected static final String PASS = "mysqlroot"; // or your password
Add jSerialComm to your project build path.

🧪 How to Use
1. Launch the app
Run AttendanceManagementSystem.java.

2. Login
Use default credentials:

pgsql
Copy
Edit
Username: admin
Password: password
3. Use Main Features
Click Register Student to add a new student with UID.

Click Record Attendance and scan an NFC card to mark attendance.

Use View, Download, or Clear Attendance for managing logs.

📥 Output
Attendance files are downloaded as .txt to:

bash
Copy
Edit
/Attendance_data/attendance_record_<timestamp>.txt
🧩 Known Limitations
Static admin login (can be enhanced with hashed credentials).

No encryption between app and MySQL (can be improved for production).

UID must be unique and pre-registered for valid attendance.

🧑‍💻 Contributors
Developed by:

Richan Abraham J.R.

Kersen (Co-Developer)
Karunya Institute of Technology and Sciences

📜 License
This project is provided under the MIT License. Free to use and modify for educational purposes.

python
Copy
Edit

Let me know if you'd like:

- A zipped project folder
- SQL file (`.sql`) for direct import
- Enhanced features like email alerts or attendance graphs  
- A GitHub-friendly project logo or badge section

I'll be happy to help!
