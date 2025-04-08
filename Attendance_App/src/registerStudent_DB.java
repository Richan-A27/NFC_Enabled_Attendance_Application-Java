//registerStudent_DB.java

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import exceptions.*;

public class registerStudent_DB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/students_db"; // Update with your DB URL
    private static final String DB_USER = "root"; // Update with your DB user
    private static final String DB_PASSWORD = "mysqlroot"; // Update with your DB password

    // Method to register a new student
    public void registerNewStudent(String studentId, String studentName, String studentUid, String email, String department, int yearOfStudy) throws SQLException {
        try {
            // Validate student data
            validateStudentData(studentId, studentName, studentUid, email, department, yearOfStudy);

            // Check if the student already exists
            if (isStudentExists(studentId)) {
                throw new StudentAlreadyExistsException("Student with ID " + studentId + " already exists.");
            }

            String insertStudentSQL = "INSERT INTO students (student_id, student_name, student_uid, email, department, year_of_study) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(insertStudentSQL)) {

                // Set the parameters
                preparedStatement.setString(1, studentId);
                preparedStatement.setString(2, studentName);
                preparedStatement.setString(3, studentUid); // Assuming studentUid is a String now
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, department);
                preparedStatement.setInt(6, yearOfStudy);

                // Execute the insert
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Student registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to register student.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while registering student: " + e.getMessage());
            }

        } catch (StudentAlreadyExistsException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (InvalidStudentDataException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Method to validate student data
    private void validateStudentData(String studentId, String studentName, String studentUid, String email, String department, int yearOfStudy) throws InvalidStudentDataException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new InvalidStudentDataException("Student ID cannot be empty.");
        }
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new InvalidStudentDataException("Student name cannot be empty.");
        }
        if (studentUid == null || studentUid.trim().isEmpty()) {
            throw new InvalidStudentDataException("Student UID cannot be empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidStudentDataException("Email cannot be empty.");
        }
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidStudentDataException("Department cannot be empty.");
        }
        if (yearOfStudy < 1) {
            throw new InvalidStudentDataException("Year of study must be a positive integer.");
        }
    }

    // Method to check if a student already exists
    private boolean isStudentExists(String studentId) throws SQLException {
        String checkStudentSQL = "SELECT COUNT(*) FROM students WHERE student_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(checkStudentSQL)) {
            preparedStatement.setString(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }
}
