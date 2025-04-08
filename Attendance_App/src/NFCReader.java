import com.fazecast.jSerialComm.SerialPort;
import exceptions.AttendanceMarkingException;

public class NFCReader {

    private attendance_DBMethods dbMethods;
    private SerialPort comPort;

    public NFCReader() {
        dbMethods = new attendance_DBMethods(); // Initialize your attendance DB methods
    }

    public void readUID() {
        // Detect and open the serial port (e.g., COM3 on Windows, ttyUSB0 on Linux)
        SerialPort[] ports = SerialPort.getCommPorts();
        
        if (ports.length == 0) {
            System.out.println("No serial ports available.");
            return;
        }

        comPort = ports[0];  // Adjust port number as needed
        comPort.setBaudRate(9600); // Set baud rate (match with NFC reader configuration)

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Unable to open the port.");
            return;
        }

        // Create a buffer to accumulate the UID data
        StringBuilder uidBuffer = new StringBuilder();

        try {
            while (true) {
                // Read data byte by byte
                while (comPort.bytesAvailable() > 0) {
                    byte[] readBuffer = new byte[1]; // Read one byte at a time
                    comPort.readBytes(readBuffer, readBuffer.length);

                    // Append the byte to the UID buffer as a character
                    char readChar = (char) readBuffer[0];
                    uidBuffer.append(readChar);

                    // Check for end-of-line characters (adjust this based on NFC reader behavior)
                    if (readChar == '\n' || readChar == '\r') {
                        // Process the complete UID
                        String uid = uidBuffer.toString().trim(); // Trim to remove newlines
                        System.out.println("UID Read: " + uid);

                        // Call the markAttendance method to register attendance in the database
                        try {
                            String result = dbMethods.markAttendance(uid);
                            System.out.println(result);
                        } catch (AttendanceMarkingException e) {
                            System.err.println("Attendance marking error: " + e.getMessage());
                        }

                        // Clear the buffer after processing the UID
                        uidBuffer.setLength(0);
                    }
                }
            }
        } finally {
            if (comPort != null && comPort.isOpen()) {
                comPort.closePort(); // Close the port when done
                System.out.println("Port closed.");
            }
        }
    }

    public static void main(String[] args) {
        NFCReader nfcReader = new NFCReader();
        nfcReader.readUID(); // Start reading the UID
    }
}
