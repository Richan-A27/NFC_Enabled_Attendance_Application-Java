import javax.swing.*;

public class AttendanceManagementSystem {
    public static void main(String[] args) {
        // Set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and display the login window
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }
}
