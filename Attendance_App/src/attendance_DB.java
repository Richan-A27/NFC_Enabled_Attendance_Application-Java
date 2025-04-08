//attendance_DB.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class attendance_DB {

    // 1. Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/students_db";  //DB name
    private static final String USER = "root";                                       //username
    private static final String PASSWORD = "mysqlroot";                              //password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {

            // 2. Establish a connection to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // 3. Create a statement object to send SQL queries
            statement = connection.createStatement();

            // 4. Students Table creation (changed student_uid to VARCHAR(8))
            String students_table = """ 
            CREATE TABLE IF NOT EXISTS students (
            student_id VARCHAR(11) NOT NULL, 
            student_name VARCHAR(100) NOT NULL, 
            student_uid VARCHAR(8) NOT NULL,
            email VARCHAR(100), 
            department VARCHAR(50), 
            year_of_study INT, 
            PRIMARY KEY (student_id));
            """;
            
            boolean result_students = statement.execute(students_table);
            if (result_students) 
                System.out.println("Connection successful and student table created!");
            
            // 5. Attendance table creation
            String attendance_table = """
                    CREATE TABLE IF NOT EXISTS attendance (
                    attendance_id INT AUTO_INCREMENT PRIMARY KEY, 
                    student_id VARCHAR(11) NOT NULL, 
                    attendance_date DATE NOT NULL, 
                    attendance_time TIME, 
                    status VARCHAR(10), 
                    FOREIGN KEY (student_id) REFERENCES students(student_id));
                    """;
        
            boolean result_attendance = statement.execute(attendance_table);
            if (result_attendance)
                System.out.println("Attendance Table has been created!");
                

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            // 6. Close the connections
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
