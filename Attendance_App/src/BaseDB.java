import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDB {
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/students_db"; // Update with your database URL
    protected static final String USER = "root"; // Your database username
    protected static final String PASS = "mysqlroot"; // Your database password

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    protected void closeResources(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
