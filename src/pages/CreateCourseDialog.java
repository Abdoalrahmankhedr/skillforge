package pages;

import services.InstructorService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreateCourseDialog extends JDialog {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private int instructorId;

    public CreateCourseDialog(Frame parent, int instructorId) {
        super(parent, "Create New Course", true);
        this.instructorId = instructorId;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 51));
        JLabel headerLabel = new JLabel("Create New Course");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Course Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel titleLabel = new JLabel("Course Title:");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        titleField = new JTextField(30);
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(titleField, gbc);

        // Course Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(descLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descriptionArea = new JTextArea(5, 30);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton createButton = new JButton("Create Course");
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        createButton.setBackground(new Color(0, 153, 51));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.addActionListener(e -> createCourse());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cancelButton.setBackground(new Color(204, 204, 204));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(createButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void createCourse() {
        String title = titleField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a course title",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Create course with empty lessons list (can add lessons later)
            InstructorService.createCourse(instructorId, title, new ArrayList<>());

            JOptionPane.showMessageDialog(this,
                    "Course created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
            // Refresh the dashboard
            InstructorDashboard.start(instructorId);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error creating course: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showDialog(int instructorId, Frame parent) {
        CreateCourseDialog dialog = new CreateCourseDialog(parent, instructorId);
        dialog.setVisible(true);
    }
}