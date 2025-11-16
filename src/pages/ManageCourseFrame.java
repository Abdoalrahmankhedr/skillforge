package pages;
import pages.components.*;
import databases.CourseDatabase;
import models.Course;
import models.Lesson;
import services.InstructorService;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageCourseFrame extends JFrame {
    private static ManageCourseFrame instance;
    private static Course currentCourse;
    private static int currentInstructorId;

    private JPanel mainPanel;
    private JPanel headerPanel;
    private JButton backBtn;
    private JLabel courseTitleLabel;
    private JButton editCourseBtn;
    private JButton deleteCourseBtn;
    private JPanel infoPanel;
    private JLabel courseIdLabel;
    private JLabel lessonsCountLabel;
    private JLabel studentsCountLabel;
    private JPanel lessonsPanel;
    private JButton addLessonBtn;

    public ManageCourseFrame() {
        initComponents();
        instance = this;
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manage Course - SkillForge");
        setSize(1200, 700);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(204, 204, 204));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        backBtn = new JButton("Back");
        backBtn.setBackground(new Color(51, 51, 255));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        backBtn.setFocusPainted(false);

        courseTitleLabel = new JLabel("Course Title");
        courseTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        courseTitleLabel.setForeground(Color.BLACK);

        editCourseBtn = new JButton("Edit Course");
        editCourseBtn.setBackground(new Color(255, 153, 0));
        editCourseBtn.setForeground(Color.WHITE);
        editCourseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        editCourseBtn.setFocusPainted(false);

        deleteCourseBtn = new JButton("Delete Course");
        deleteCourseBtn.setBackground(new Color(204, 0, 0));
        deleteCourseBtn.setForeground(Color.WHITE);
        deleteCourseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        deleteCourseBtn.setFocusPainted(false);

        headerPanel.add(backBtn);
        headerPanel.add(Box.createHorizontalStrut(200));
        headerPanel.add(courseTitleLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(editCourseBtn);
        headerPanel.add(deleteCourseBtn);

        // Info Panel
        infoPanel = new JPanel();
        infoPanel.setBackground(new Color(102, 204, 255));
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        courseIdLabel = new JLabel("Course ID: 1");
        courseIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        lessonsCountLabel = new JLabel("Lessons: 0");
        lessonsCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        studentsCountLabel = new JLabel("Students: 0");
        studentsCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        infoPanel.add(courseIdLabel);
        infoPanel.add(lessonsCountLabel);
        infoPanel.add(studentsCountLabel);

        // Lessons Section
        JPanel lessonsSection = new JPanel();
        lessonsSection.setLayout(new BorderLayout());
        lessonsSection.setBackground(Color.WHITE);

        JPanel lessonHeaderPanel = new JPanel();
        lessonHeaderPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        lessonHeaderPanel.setBackground(Color.WHITE);

        JLabel lessonsLabel = new JLabel("Course Lessons");
        lessonsLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lessonsLabel.setForeground(new Color(0, 0, 255));

        addLessonBtn = new JButton("Add New Lesson");
        addLessonBtn.setBackground(new Color(0, 153, 51));
        addLessonBtn.setForeground(Color.WHITE);
        addLessonBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addLessonBtn.setFocusPainted(false);

        lessonHeaderPanel.add(lessonsLabel);
        lessonHeaderPanel.add(Box.createHorizontalStrut(20));
        lessonHeaderPanel.add(addLessonBtn);

        lessonsPanel = new JPanel();
        lessonsPanel.setBackground(new Color(204, 204, 204));

        lessonsSection.add(lessonHeaderPanel, BorderLayout.NORTH);
        lessonsSection.add(lessonsPanel, BorderLayout.CENTER);

        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(lessonsSection, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private static void refreshLessons() {
        List<Lesson> lessons = currentCourse.getLessons();

        JPanel lessonsInnerPanel = new JPanel();
        lessonsInnerPanel.setLayout(new BoxLayout(lessonsInnerPanel, BoxLayout.Y_AXIS));
        lessonsInnerPanel.setBackground(Color.WHITE);

        for (Lesson lesson : lessons) {
            JPanel lessonItemPanel = new JPanel();
            lessonItemPanel.setLayout(new BorderLayout(10, 0));
            lessonItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            lessonItemPanel.setBackground(Color.WHITE);
            lessonItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            LessonCard card = new LessonCard();
            card.setData(false, lesson.getTitle(), lesson.getContent(), false, e -> {});

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);

            JButton editBtn = new JButton("Edit");
            editBtn.setBackground(new Color(255, 153, 0));
            editBtn.setForeground(Color.WHITE);
            editBtn.setFont(new Font("Arial", Font.BOLD, 14));
            editBtn.setFocusPainted(false);
            editBtn.addActionListener(e -> EditLessonDialog.showDialog(currentCourse, lesson, instance));

            JButton deleteBtn = new JButton("Delete");
            deleteBtn.setBackground(new Color(204, 0, 0));
            deleteBtn.setForeground(Color.WHITE);
            deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
            deleteBtn.setFocusPainted(false);
            deleteBtn.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(instance,
                        "Are you sure you want to delete this lesson?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    CourseDatabase.getInstance().deleteLesson(lesson.getId());
                    currentCourse = CourseDatabase.getInstance().getCourseById(currentCourse.getId());
                    refreshLessons();
                    instance.lessonsCountLabel.setText("Lessons: " + currentCourse.getLessons().size());
                }
            });

            buttonPanel.add(editBtn);
            buttonPanel.add(deleteBtn);

            lessonItemPanel.add(card, BorderLayout.CENTER);
            lessonItemPanel.add(buttonPanel, BorderLayout.EAST);

            lessonsInnerPanel.add(lessonItemPanel);
            lessonsInnerPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(lessonsInnerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        instance.lessonsPanel.removeAll();
        instance.lessonsPanel.setLayout(new BorderLayout());
        instance.lessonsPanel.add(scrollPane, BorderLayout.CENTER);
        instance.lessonsPanel.revalidate();
        instance.lessonsPanel.repaint();
    }

    public static void start(Course course, int instructorId) {
        currentCourse = course;
        currentInstructorId = instructorId;

        instance.courseTitleLabel.setText(course.getTitle());
        instance.courseIdLabel.setText("Course ID: " + course.getId());
        instance.lessonsCountLabel.setText("Lessons: " + course.getLessons().size());
        instance.studentsCountLabel.setText("Students: " + course.getEnrolledStudents().size());

        instance.backBtn.addActionListener(e -> {
            instance.setVisible(false);
            InstructorDashboard.start(instructorId);
            MainWindow.goToFrame("InstructorDashboard");
        });

        instance.editCourseBtn.addActionListener(e -> {
            EditCourseDialog.showDialog(course, instructorId, instance);
        });

        instance.deleteCourseBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(instance,
                    "Are you sure you want to delete this course? This action cannot be undone.",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                CourseDatabase.getInstance().deleteCourse(course.getId());
                instance.setVisible(false);
                InstructorDashboard.start(instructorId);
                MainWindow.goToFrame("InstructorDashboard");
            }
        });

        instance.addLessonBtn.addActionListener(e -> {
            AddLessonDialog.showDialog(course, instance);
        });

        refreshLessons();

        MainWindow.addFrame("ManageCourseFrame", instance);
        MainWindow.goToFrame("ManageCourseFrame");
    }
}