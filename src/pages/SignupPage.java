package pages;

import pages.components.SignupButton;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;

// Andrew :)

public class SignupPage extends JPanel {
    public SignupPage() {
        /* Set background to gray */
        setBackground(new Color(224, 225, 223));
        /* Set Layout */
        setLayout(new GridBagLayout());

        /* White panel */
        JPanel whitePanel = new JPanel();
        whitePanel.setLayout(new BorderLayout());
        whitePanel.setBackground(Color.white);
        whitePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        whitePanel.setPreferredSize(new Dimension(300, 450));

        /* Form panel */
        JPanel form = new JPanel();
        form.setBackground(Color.white);
        form.setLayout(new GridBagLayout());

        /* Setting up grid placement */
        GridBagConstraints constraints = new GridBagConstraints();

        /* Main title */
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 20, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        form.add(titleLabel, constraints);

        /* Add padding of 10px in all directions */
        constraints.insets = new Insets(10, 10, 0, 10);

        /* Name label */
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        JLabel nameLabel = new JLabel("Name");
        form.add(nameLabel, constraints);

        /* Name field */
        JTextField nameInput = new JTextField();
        nameInput.setPreferredSize(new Dimension(200, 30));
        nameInput.setMargin(new Insets(5, 5, 5, 5));
        constraints.gridy = 2;
        form.add(nameInput, constraints);

        /* Email label */
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_START;
        JLabel emailLabel = new JLabel("Email");
        form.add(emailLabel, constraints);

        /* Email field */
        JTextField emailInput = new JTextField();
        emailInput.setPreferredSize(new Dimension(200, 30));
        emailInput.setMargin(new Insets(5, 5, 5, 5));
        constraints.gridy = 4;
        form.add(emailInput, constraints);

        /* Password label */
        JLabel pwdLabel = new JLabel("Password");
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.LINE_START;
        form.add(pwdLabel, constraints);

        /* Password field */
        JPasswordField pwdInput = new JPasswordField();
        pwdInput.setPreferredSize(new Dimension(200, 30));
        pwdInput.setMargin(new Insets(5, 5, 5, 5));
        constraints.gridy = 6;
        form.add(pwdInput, constraints);

        /* Role label */
        JLabel roleLabel = new JLabel("Role");
        constraints.gridy = 7;
        constraints.anchor = GridBagConstraints.LINE_START;
        form.add(roleLabel, constraints);

        /* Role combo box */
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Student", "Instructor","Admin"});
        roleComboBox.setPreferredSize(new Dimension(200, 30));
        constraints.gridy = 8;
        form.add(roleComboBox, constraints);

        /* Sign up button */
        JButton signupBtn = new SignupButton(nameInput, emailInput, pwdInput, roleComboBox);
        constraints.gridy = 9;
        constraints.insets = new Insets(30, 10, 0, 10);
        form.add(signupBtn, constraints);

        /* Login link */
        constraints.gridy = 10;
        constraints.insets = new Insets(20, 10, 10, 10);
        JLabel loginLabel = new JLabel("Already have an account? Login");
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainWindow.goTo("login");
            }
        });
        form.add(loginLabel, constraints);

        /* Add form */
        whitePanel.add(form, BorderLayout.CENTER);

        /* Add panel */
        add(whitePanel);
    }
}
