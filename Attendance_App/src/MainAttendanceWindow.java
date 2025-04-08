import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

class MainAttendanceWindow extends JFrame {
    private attendance_DBMethods attendanceMethods;
    private registerStudent_DB registerStudentMethods;

    public MainAttendanceWindow() {
        attendanceMethods = new attendance_DBMethods(); // Initialize attendance methods
        registerStudentMethods = new registerStudent_DB(); // Initialize student registration methods

        setTitle("Attendance Management");
        setSize(500, 600); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        setUIFont(new Font("Segoe UI", Font.PLAIN, 14));

        // main panel with a gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255), 0, getHeight(), new Color(240, 240, 240));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // the panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 10, 10)); // 7 buttons in a column with gaps
        buttonPanel.setBackground(new Color(255, 255, 255, 200)); // Slightly transparent white

        JButton recordAttendanceButton = createStyledButton("Record Attendance");
        recordAttendanceButton.addActionListener(e -> recordAttendance());
        buttonPanel.add(recordAttendanceButton);

        JButton closeAttendanceButton = createStyledButton("Close Attendance");
        closeAttendanceButton.addActionListener(e -> closeAttendance());
        buttonPanel.add(closeAttendanceButton);

        JButton viewAttendanceButton = createStyledButton("View Attendance");
        viewAttendanceButton.addActionListener(e -> viewAttendance());
        buttonPanel.add(viewAttendanceButton);

        JButton clearAttendanceButton = createStyledButton("Clear Attendance");
        clearAttendanceButton.addActionListener(e -> clearAttendance());
        buttonPanel.add(clearAttendanceButton);

        JButton downloadAttendanceButton = createStyledButton("Download Attendance");
        downloadAttendanceButton.addActionListener(e -> downloadAttendance());
        buttonPanel.add(downloadAttendanceButton);


        JButton registerStudentButton = createStyledButton("Register Student");
        registerStudentButton.addActionListener(e -> openRegisterStudentWindow());
        buttonPanel.add(registerStudentButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        JPanel fillerPanel = new JPanel();
        fillerPanel.setPreferredSize(new Dimension(0, 20)); // Set a preferred height
        mainPanel.add(fillerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 149, 237)); 
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10))); 
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Method to open the Register Student dialog
    private void openRegisterStudentWindow() {
        JDialog registerDialog = new JDialog(this, "Register New Student", true);
        registerDialog.setSize(400, 500);
        registerDialog.setLocationRelativeTo(this);

        // Set a new font for the dialog
        setUIFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Cpanel with a light background
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBackground(new Color(255, 255, 255, 200)); // Slightly transparent white

        panel.add(new JLabel("Student ID:"));
        JTextField studentIdField = new JTextField();
        panel.add(studentIdField);

        panel.add(new JLabel("Student Name:"));
        JTextField studentNameField = new JTextField();
        panel.add(studentNameField);

        panel.add(new JLabel("Student UID:"));
        JTextField studentUidField = new JTextField();
        panel.add(studentUidField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Department:"));

        //radio buttons for departments
        ButtonGroup departmentGroup = new ButtonGroup();
        JRadioButton cseRadioButton = new JRadioButton("CSE");
        JRadioButton eceRadioButton = new JRadioButton("ECE");
        JRadioButton btRadioButton = new JRadioButton("BT");
        JRadioButton ceRadioButton = new JRadioButton("CE");

        departmentGroup.add(cseRadioButton);
        departmentGroup.add(eceRadioButton);
        departmentGroup.add(btRadioButton);
        departmentGroup.add(ceRadioButton);

        JPanel departmentPanel = new JPanel(new GridLayout(1, 4)); // 4 radio buttons in a row
        departmentPanel.setBackground(new Color(255, 255, 255, 200)); // Slightly transparent white
        departmentPanel.add(cseRadioButton);
        departmentPanel.add(eceRadioButton);
        departmentPanel.add(btRadioButton);
        departmentPanel.add(ceRadioButton);

        panel.add(departmentPanel);

        panel.add(new JLabel("Year of Study:"));

        //dropdown menu for year selection
        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year", "PG"};
        JComboBox<String> yearComboBox = new JComboBox<>(years);
        panel.add(yearComboBox);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setBackground(new Color(50, 205, 50)); // Lime Green
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> {
            try {
                // Get selected department from radio buttons
                String selectedDepartment = "";
                if (cseRadioButton.isSelected()) {
                    selectedDepartment = "CSE";
                } else if (eceRadioButton.isSelected()) {
                    selectedDepartment = "ECE";
                } else if (btRadioButton.isSelected()) {
                    selectedDepartment = "BT";
                } else if (ceRadioButton.isSelected()) {
                    selectedDepartment = "CE";
                }

                // Convert year selection to integer
                int yearOfStudy = yearComboBox.getSelectedIndex() + 1; // 0-indexed to 1-indexed

                // Call registerStudent_DB's registerNewStudent method
                registerStudentMethods.registerNewStudent(
                        studentIdField.getText(),
                        studentNameField.getText(),
                        studentUidField.getText(),
                        emailField.getText(),
                        selectedDepartment,
                        yearOfStudy 
                );
                registerDialog.dispose(); // Close the dialog on successful registration
                JOptionPane.showMessageDialog(this, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error registering student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for Year of Study.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerDialog.add(panel, BorderLayout.CENTER);
        registerDialog.add(registerButton, BorderLayout.SOUTH);
        registerDialog.setVisible(true);
    }

    // Existing methods
    private void recordAttendance() {
        NFCReader nfcReader = new NFCReader();
        new Thread(nfcReader::readUID).start();
    }

    private void closeAttendance() {
        JOptionPane.showMessageDialog(this, "Attendance closed successfully.");
    }

    private void viewAttendance() {
        String records = attendanceMethods.viewAttendance();
        JOptionPane.showMessageDialog(this, records, "Attendance Records", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearAttendance() {
        String result = attendanceMethods.clearAttendance();
        JOptionPane.showMessageDialog(this, result);
    }

    private void downloadAttendance() {
        String defaultPath = "C:\\Users\\richa\\Documents\\Java Projects\\Attendance_App\\Attendance_data\\attendance_record_" + System.currentTimeMillis() + ".txt";
        String filePath = JOptionPane.showInputDialog(this, "Enter file path to save attendance records (leave blank for default):", defaultPath);
        if (filePath == null || filePath.trim().isEmpty()) {
            filePath = defaultPath; // Use the default file path
        }
        String result = attendanceMethods.downloadAttendance(filePath);
        JOptionPane.showMessageDialog(this, result);
    }

    // Method to set font for UI components
    private void setUIFont(Font font) {
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("JPanel.font", font);
        UIManager.put("JDialog.font", font);
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainAttendanceWindow window = new MainAttendanceWindow();
            window.setVisible(true);
        });
    }
}
