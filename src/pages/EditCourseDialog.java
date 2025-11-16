package pages;

import databases.CourseDatabase;
import models.Course;

import javax.swing.*;
import java.awt.*;

public class EditCourseDialog extends JDialog {
    private JTextField titleField;
    private Course course;
    private int instructorId;

    public EditCourseDialog(Frame parent, Course course, int instructorId) {
        super(parent, "Edit Course", true);
        this.course = course;
        this.instructorId = instructorId;
        initComponents();
        loadCourseData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 250);
        setLocationRelativeTo(getParent());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 153, 0));
        JLabel headerLabel = new JLabel("Edit Course Information");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

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

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setBackground(new Color(255, 153, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveCourse());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cancelButton.setBackground(new Color(204, 204, 204));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadCourseData() {
        titleField.setText(course.getTitle());
    }

    private void saveCourse() {
        String title = titleField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a course title",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            CourseDatabase.getInstance().updateCourse(course.getId(), title, course.getLessons());

            JOptionPane.showMessageDialog(this,
                    "Course updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

            // Refresh the course data
            Course updatedCourse = CourseDatabase.getInstance().getCourseById(course.getId());
            ManageCourseFrame.start(updatedCourse, instructorId);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error updating course: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showDialog(Course course, int instructorId, Frame parent) {
        EditCourseDialog dialog = new EditCourseDialog(parent, course, instructorId);
        dialog.setVisible(true);
    }
}