package pages.components;

import databases.UserDatabase;
import models.User;
import pages.InstructorDashboard;
import pages.StudentDashBoard;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;

// Andrew :)

public class LoginButton extends JButton {
    private final JTextField email;
    private final JPasswordField password;
    /* Loads user database to verify credentials */
    private final UserDatabase db = UserDatabase.getInstance();

    public LoginButton(JTextField email, JPasswordField password) {
        this.email = email;
        this.password = password;

        /* Design Login Button */
        setText("Login");
        setPreferredSize(new Dimension(200, 30));
        setBackground(new Color(30, 203, 225));
        setForeground(Color.white);
        setFont(new Font("Arial", Font.BOLD, 16));
        /* Border */
        setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

        /* On click event listener */
        addActionListener((e) -> verifyLogin());

        /* Disable button if not initialised correctly */
        if (email == null || password == null) {
            System.out.println("[LoginButton]: Must provide both email and password fields\n");
            setEnabled(false);
        }
    }

    private void verifyLogin() {
        /* Carry out login process */
        String currentEmail = email.getText();

        /* Retrieve password */
        StringBuilder pwdSb = new StringBuilder();
        for (char c : password.getPassword()) pwdSb.append(c);
        String currentPwd = pwdSb.toString();

        /* Log in console */
        System.out.printf("[LoginButton]: Entered %s and %s as login credentials\n", currentEmail, currentPwd);

        /* Verify & Display */
        if (currentEmail.isEmpty() && currentPwd.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter email and password",
                    "Invalid Credentials",
                    JOptionPane.WARNING_MESSAGE,
                    null);
        } else if (!currentEmail.isEmpty() && currentPwd.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your password",
                    "Missing Password",
                    JOptionPane.WARNING_MESSAGE,
                    null);
        } else if (currentEmail.isEmpty() && !currentPwd.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your email",
                    "Missing Email",
                    JOptionPane.WARNING_MESSAGE,
                    null);
        } else {
            Boolean result = db.login(currentEmail, currentPwd);
            if (result != null && result) {
                /* Go to next page */
                User user = db.getUserByEmail(currentEmail);
                email.setText("");
                password.setText("");

                // Add page after user login here
                // Will add based on roles once other dashboard is made

                /* Check user role and redirect accordingly */
                if (user.getRole().equalsIgnoreCase("Instructor")) {
                    InstructorDashboard.start(user.getId());
                    MainWindow.goToFrame("InstructorDashboard");
                } else if (user.getRole().equalsIgnoreCase("Student")) {
                    StudentDashBoard.start(user.getId());
                    MainWindow.goToFrame("StudentDashBoard");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Unknown user role",
                            "Error",
                            JOptionPane.ERROR_MESSAGE,
                            null);
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "Incorrect email or password",
                        "Invalid Credentials",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            }
        }
    }
}