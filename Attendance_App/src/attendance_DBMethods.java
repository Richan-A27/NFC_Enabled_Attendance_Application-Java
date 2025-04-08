import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exceptions.AttendanceMarkingException;

public class attendance_DBMethods extends BaseDB {

    // Method to mark attendance
    public String markAttendance(String uid) throws AttendanceMarkingException {
        String studentId = getStudentIdFromUID(uid); // Get student ID from UID
        if (studentId == null) {
            throw new AttendanceMarkingException("Invalid student UID: " + uid);
        }

        String query = "INSERT INTO attendance (student_id, attendance_date, attendance_time) VALUES (?, CURDATE(), CURTIME())";
        
        try (Connection conn = connect(); // Use the connect method from BaseDB
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, studentId);
            pstmt.executeUpdate();
            return "Attendance marked for UID: " + uid;
        } catch (SQLException e) {
            throw new AttendanceMarkingException("Error marking attendance: " + e.getMessage());
        }
    }

    // Helper method to get the student_id using student_uid
    private String getStudentIdFromUID(String uid) {
        String query = "SELECT student_id FROM students WHERE student_uid = ?";
        try (Connection conn = connect(); // Use the connect method from BaseDB
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, uid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("student_id"); // Return the student ID corresponding to the UID
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student ID: " + e.getMessage());
        }
        return null; // Return null if no matching student_uid is found
    }

    // Method to view attendance records
    public String viewAttendance() {
        StringBuilder attendanceRecords = new StringBuilder();
        String query = "SELECT * FROM attendance";
        
        try (Connection conn = connect(); // Use the connect method from BaseDB
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            attendanceRecords.append("Attendance Records:\n");
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String date = rs.getDate("attendance_date").toString();
                String time = rs.getTime("attendance_time").toString();
                attendanceRecords.append("Student ID: " + studentId + ", Date: " + date + ", Time: " + time + "\n");
            }
        } catch (SQLException e) {
            return "Error retrieving attendance: " + e.getMessage();
        }
        return attendanceRecords.toString();
    }

    // Method to clear the attendance table
    public String clearAttendance() {
        String query = "DELETE FROM attendance";
        
        try (Connection conn = connect(); // Use the connect method from BaseDB
             Statement stmt = conn.createStatement()) {
             
            int rowsAffected = stmt.executeUpdate(query);
            return "Attendance cleared. Rows affected: " + rowsAffected;
        } catch (SQLException e) {
            return "Error clearing attendance: " + e.getMessage();
        }
    }

    // Method to download attendance records to a text file
    public String downloadAttendance(String filePath) {
        String query = "SELECT * FROM attendance";
        
        try (Connection conn = connect(); // Use the connect method from BaseDB
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
             
            writer.write("Student ID, Date, Time\n"); // Write header
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String date = rs.getDate("attendance_date").toString();
                String time = rs.getTime("attendance_time").toString();
                writer.write(studentId + ", " + date + ", " + time + "\n");
            }
            return "Attendance records downloaded to: " + filePath;
        } catch (SQLException | IOException e) {
            return "Error downloading attendance: " + e.getMessage();
        }
    }
}
