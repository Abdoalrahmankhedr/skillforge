package pages;

import databases.UserDatabase;
import models.Course;
import models.Lesson;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewStudentsFrame extends JFrame {
    private static ViewStudentsFrame instance;
    private Course course;

    private JPanel mainPanel;
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JButton backBtn;
    private JTable studentsTable;
    private DefaultTableModel tableModel;

    public ViewStudentsFrame() {
        initComponents();
        instance = this;
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Enrolled Students - SkillForge");
        setSize(900, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(204, 204, 204));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

        backBtn = new JButton("Back");
        backBtn.setBackground(new Color(51, 51, 255));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> dispose());

        titleLabel = new JLabel("Enrolled Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLACK);

        headerPanel.add(backBtn);
        headerPanel.add(Box.createHorizontalStrut(200));
        headerPanel.add(titleLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {"Student ID", "Name", "Email", "Progress"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentsTable = new JTable(tableModel);
        studentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentsTable.setRowHeight(30);
        studentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        studentsTable.getTableHeader().setBackground(new Color(102, 204, 255));

        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadStudents() {
        tableModel.setRowCount(0);

        UserDatabase userDb = UserDatabase.getInstance();
        List<Integer> studentIds = course.getEnrolledStudents();

        if (studentIds.isEmpty()) {
            Object[] row = {"No students", "enrolled yet", "", ""};
            tableModel.addRow(row);
            return;
        }

        for (Integer studentId : studentIds) {
            User student = userDb.getUserById(studentId);
            if (student != null) {
                // Calculate progress
                int totalLessons = course.getLessons().size();
                int completedLessons = 0;

                for (Lesson lesson : course.getLessons()) {
                    Boolean completed = lesson.getStudentProgress().get(studentId);
                    if (completed != null && completed) {
                        completedLessons++;
                    }
                }

                String progress = totalLessons > 0
                        ? String.format("%d/%d (%.1f%%)", completedLessons, totalLessons,
                        (completedLessons * 100.0 / totalLessons))
                        : "No lessons";

                Object[] row = {
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        progress
                };
                tableModel.addRow(row);
            }
        }
    }

    public static void start(Course course) {
        instance.course = course;
        instance.titleLabel.setText("Enrolled Students - " + course.getTitle());
        instance.loadStudents();
        instance.setVisible(true);
    }
}