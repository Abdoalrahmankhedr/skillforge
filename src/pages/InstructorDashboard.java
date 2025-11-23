package pages;

import models.Course;
import models.User;
import services.InstructorService;
import windows.MainWindow;
import pages.components.ChartFrame;
import services.AnalyticsService;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class InstructorDashboard extends JFrame {
    private static InstructorDashboard instance;
    private static int currentInstructorId;

    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel titleLabel;
    private JButton logoutBtn;
    private JButton createCourseBtn;
    private JPanel jPanel3;
    private JLabel instructorNameLabel;
    private JLabel instructorIdLabel;
    private JLabel instructorEmailLabel;
    private JLabel coursesCountLabel;
    private JLabel jLabel5;
    private JPanel coursesPanel;

    // Flag to track if listeners are already added
    private static boolean listenersAdded = false;
    private JPanel insightsPanel;
    private JButton toggleInsightsBtn;
    private boolean insightsVisible = false;

    public InstructorDashboard() {
        initComponents();
        instance = this;
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        titleLabel = new JLabel();
        logoutBtn = new JButton();
        createCourseBtn = new JButton();
        jPanel3 = new JPanel();
        instructorIdLabel = new JLabel();
        instructorNameLabel = new JLabel();
        instructorEmailLabel = new JLabel();
        coursesCountLabel = new JLabel();
        jLabel5 = new JLabel();
        coursesPanel = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instructor Dashboard - SkillForge");
        setSize(1400, 800);
        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel2.setBackground(new Color(204, 204, 204));

        titleLabel.setBackground(new Color(204, 204, 204));
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        titleLabel.setForeground(new Color(0, 0, 0));
        titleLabel.setText("Instructor Dashboard");

        logoutBtn.setBackground(new Color(255, 0, 0));
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setText("Logout");

        createCourseBtn.setBackground(new Color(0, 153, 51));
        createCourseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        createCourseBtn.setForeground(Color.WHITE);
        createCourseBtn.setText("Create New Course");

        // Insights Panel
        insightsPanel = new JPanel();
        insightsPanel.setBackground(Color.WHITE);
        insightsPanel.setLayout(new BoxLayout(insightsPanel, BoxLayout.Y_AXIS));
        insightsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        insightsPanel.setVisible(false);

        // Toggle Insights Button
        toggleInsightsBtn = new JButton("Show Insights");
        toggleInsightsBtn.setBackground(new Color(102, 102, 255));
        toggleInsightsBtn.setForeground(Color.WHITE);
        toggleInsightsBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        toggleInsightsBtn.setFocusPainted(false);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logoutBtn)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toggleInsightsBtn)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(titleLabel)
                                .addGap(200, 200, 200)
                                .addComponent(createCourseBtn)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(titleLabel)
                                        .addComponent(logoutBtn)
                                        .addComponent(toggleInsightsBtn)
                                        .addComponent(createCourseBtn))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new Color(51, 153, 255));

        instructorIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instructorIdLabel.setForeground(new Color(0, 0, 0));
        instructorIdLabel.setText("ID: 1");

        instructorNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instructorNameLabel.setForeground(new Color(0, 0, 0));
        instructorNameLabel.setText("Name: Instructor");

        instructorEmailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instructorEmailLabel.setForeground(new Color(0, 0, 0));
        instructorEmailLabel.setText("Email: instructor@email.com");

        coursesCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        coursesCountLabel.setForeground(new Color(0, 0, 0));
        coursesCountLabel.setText("Courses: 0");

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(instructorNameLabel, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(instructorIdLabel, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(instructorEmailLabel, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(coursesCountLabel)
                                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(instructorIdLabel)
                                        .addComponent(instructorEmailLabel)
                                        .addComponent(coursesCountLabel)
                                        .addComponent(instructorNameLabel))
                                .addGap(16, 16, 16))
        );

        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        jLabel5.setForeground(new Color(0, 0, 255));
        jLabel5.setText("My Courses");

        coursesPanel.setBackground(new Color(204, 204, 204));
        coursesPanel.setLayout(new BorderLayout());
        coursesPanel.setPreferredSize(new Dimension(0, 506));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(insightsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coursesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(466, 466, 466)
                                .addComponent(jLabel5)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(insightsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(coursesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // Wrap jPanel1 in a JScrollPane
        JScrollPane mainScrollPane = new JScrollPane(jPanel1);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.setBorder(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainScrollPane, BorderLayout.CENTER);

        pack();
    }

    private static void removeAllActionListeners(JButton button) {
        // Remove all existing ActionListeners
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    private static JPanel createCourseCard(Course course, int instructorId) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        card.setPreferredSize(new Dimension(300, 250));
        card.setBackground(new Color(240, 248, 255));

        JTextArea titleArea = new JTextArea(course.getTitle());
        titleArea.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setOpaque(false);
        titleArea.setFocusable(false);
        titleArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lessonsLabel = new JLabel("Lessons: " + course.getLessons().size(), SwingConstants.CENTER);
        lessonsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lessonsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel studentsLabel = new JLabel("Students: " + course.getEnrolledStudents().size(), SwingConstants.CENTER);
        studentsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        studentsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("ID: " + course.getId(), SwingConstants.CENTER);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton manageCourseBtn = new JButton("Manage Course");
        manageCourseBtn.setBackground(new Color(0, 102, 204));
        manageCourseBtn.setFont(new Font("Arial", Font.BOLD, 18));
        manageCourseBtn.setForeground(Color.WHITE);
        manageCourseBtn.setFocusPainted(false);
        manageCourseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageCourseBtn.addActionListener(e -> {
            ManageCourseFrame.start(course, instructorId);
        });

        JButton viewStudentsBtn = new JButton("View Students");
        viewStudentsBtn.setBackground(new Color(0, 153, 51));
        viewStudentsBtn.setFont(new Font("Arial", Font.BOLD, 18));
        viewStudentsBtn.setForeground(Color.WHITE);
        viewStudentsBtn.setFocusPainted(false);
        viewStudentsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewStudentsBtn.addActionListener(e -> {
            ViewStudentsFrame.start(course);
        });

        card.add(Box.createVerticalStrut(10));
        card.add(titleArea);
        card.add(Box.createVerticalStrut(5));
        card.add(idLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(lessonsLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(studentsLabel);
        card.add(Box.createVerticalGlue());
        card.add(manageCourseBtn);
        card.add(Box.createVerticalStrut(5));
        card.add(viewStudentsBtn);
        card.add(Box.createVerticalStrut(10));

        return card;
    }

    public static void start(int instructorId) {
        currentInstructorId = instructorId;
        User instructor = InstructorService.getInstructor(instructorId);

        instance.instructorIdLabel.setText("ID: " + instructor.getId());
        instance.instructorEmailLabel.setText("Email: " + instructor.getEmail());
        instance.instructorNameLabel.setText("Name: " + instructor.getName());

        List<Course> courses = InstructorService.getCourses(instructorId);
        instance.coursesCountLabel.setText("Courses: " + courses.size());

        // Calculate grid layout (3 columns)
        int columns = 3;
        int rows = (int) Math.ceil(courses.size() / (double) columns);
        if (rows == 0) rows = 1; // At least 1 row

        JPanel coursesInnerPanel = new JPanel();
        coursesInnerPanel.setLayout(new GridLayout(rows, columns, 10, 10));
        coursesInnerPanel.setBackground(Color.WHITE);
        coursesInnerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Course c : courses) {
            coursesInnerPanel.add(createCourseCard(c, instructorId));
        }

        // Fill empty cells if needed
        int emptyCells = (rows * columns) - courses.size();
        for (int i = 0; i < emptyCells; i++) {
            coursesInnerPanel.add(new JPanel());
        }

        JScrollPane scrollPane = new JScrollPane(coursesInnerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        // Add listeners only once
        if (!listenersAdded) {
            instance.logoutBtn.addActionListener(e -> {
                MainWindow.closeFrame("InstructorDashboard");
                MainWindow.goTo("login");
                MainWindow.start();
            });

            instance.createCourseBtn.addActionListener(e -> {
                CreateCourseDialog.showDialog(instructorId, instance);
            });

            instance.toggleInsightsBtn.addActionListener(e -> {
                instance.insightsVisible = !instance.insightsVisible;
                instance.insightsPanel.setVisible(instance.insightsVisible);
                instance.toggleInsightsBtn.setText(instance.insightsVisible ? "Hide Insights" : "Show Insights");

                if (instance.insightsVisible) {
                    loadInsights(currentInstructorId);
                }

                instance.revalidate();
                instance.repaint();
            });

            listenersAdded = true;
        }

        instance.coursesPanel.removeAll();
        instance.coursesPanel.setLayout(new BorderLayout());
        instance.coursesPanel.add(scrollPane, BorderLayout.CENTER);
        instance.coursesPanel.revalidate();
        instance.coursesPanel.repaint();
    }

    private static void loadInsights(int instructorId) {
        instance.insightsPanel.removeAll();

        // Title
        JLabel insightsTitle = new JLabel("📊 Analytics & Insights");
        insightsTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        insightsTitle.setForeground(new Color(70, 130, 180));
        insightsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        instance.insightsPanel.add(insightsTitle);
        instance.insightsPanel.add(Box.createVerticalStrut(20));

        // Charts container
        JPanel chartsContainer = new JPanel();
        chartsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        chartsContainer.setBackground(Color.WHITE);

        // 1. Student Performance Chart (Bar Chart)
        ChartFrame studentPerformanceChart = new ChartFrame("Student Performance", ChartFrame.ChartType.BAR);
        Map<String, Double> studentPerf = AnalyticsService.getStudentPerformance(instructorId);
        if (!studentPerf.isEmpty()) {
            studentPerformanceChart.renderBarChart(
                    new ArrayList<>(studentPerf.keySet()),
                    new ArrayList<>(studentPerf.values())
            );
        }
        chartsContainer.add(studentPerformanceChart);

        // 2. Quiz Averages Chart (Line Chart)
        ChartFrame quizAveragesChart = new ChartFrame("Quiz Averages per Lesson", ChartFrame.ChartType.LINE);
        Map<String, Double> quizAvgs = AnalyticsService.getQuizAveragesByLesson(instructorId);
        if (!quizAvgs.isEmpty()) {
            quizAveragesChart.renderLineChart(
                    new ArrayList<>(quizAvgs.keySet()),
                    new ArrayList<>(quizAvgs.values())
            );
        }
        chartsContainer.add(quizAveragesChart);

        // 3. Course Completion Rates
        ChartFrame completionChart = new ChartFrame("Course Completion Rates", ChartFrame.ChartType.BAR);
        Map<String, Double> completionRates = AnalyticsService.getCourseCompletionRates(instructorId);
        if (!completionRates.isEmpty()) {
            completionChart.renderCompletionChart(completionRates);
        }
        chartsContainer.add(completionChart);

        instance.insightsPanel.add(chartsContainer);
        instance.insightsPanel.revalidate();
        instance.insightsPanel.repaint();
    }
}