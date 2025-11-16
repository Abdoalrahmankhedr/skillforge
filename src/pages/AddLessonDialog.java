package pages;

import databases.CourseDatabase;
import models.Course;
import models.Lesson;

import javax.swing.*;
import java.awt.*;

public class AddLessonDialog extends JDialog {
    private JTextField titleField;
    private JTextArea contentArea;
    private Course course;

    public AddLessonDialog(Frame parent, Course course) {
        super(parent, "Add New Lesson", true);
        this.course = course;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(600, 500);
        setLocationRelativeTo(getParent());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 51));
        JLabel headerLabel = new JLabel("Add New Lesson to " + course.getTitle());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
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

        // Lesson Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel titleLabel = new JLabel("Lesson Title:");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        titleField = new JTextField(30);
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(titleField, gbc);

        // Lesson Content
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(contentLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentArea = new JTextArea(10, 30);
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        formPanel.add(scrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add Lesson");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addButton.setBackground(new Color(0, 153, 51));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addLesson());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cancelButton.setBackground(new Color(204, 204, 204));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(addButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void addLesson() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            CourseDatabase.getInstance().addLesson(course.getId(), title, content);

            JOptionPane.showMessageDialog(this,
                    "Lesson added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

            // Refresh the course data
            Course updatedCourse = CourseDatabase.getInstance().getCourseById(course.getId());
            ManageCourseFrame.start(updatedCourse, course.getInstructorId());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error adding lesson: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showDialog(Course course, Frame parent) {
        AddLessonDialog dialog = new AddLessonDialog(parent, course);
        dialog.setVisible(true);
    }
}