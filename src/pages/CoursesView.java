package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import databases.CourseDatabase;
import models.Course;
import services.InstructorService;
import services.StudentService;
import windows.MainWindow;

public class CoursesView extends javax.swing.JFrame {
    private static final CourseDatabase db = CourseDatabase.getInstance();
    private static CoursesView instance;
    private static int currentstudent=0;
    public CoursesView() {
        initComponents();
        setupSearchField();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void setupSearchField() {
        String placeholder = "Search Course By name...";
        searchfeild.setForeground(Color.GRAY);
        searchfeild.setText(placeholder);

        searchfeild.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchfeild.getText().equals(placeholder)) {
                    searchfeild.setText("");
                    searchfeild.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchfeild.getText().isEmpty()) {
                    searchfeild.setText(placeholder);
                    searchfeild.setForeground(Color.GRAY);
                }
            }
        });

        searchfeild.addActionListener(e -> filterCourses());
    }

    private void filterCourses() {
        String text = searchfeild.getText().trim();
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : db.getRecords()) {
            if (text.equals("") || text.equals("Search Course By name...") ||
                    c.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredCourses.add(c);
            }
        }
        showCards(filteredCourses,currentstudent);
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Backbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        searchfeild = new javax.swing.JTextField();
        coursepanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(Color.WHITE);

        jPanel2.setBackground(new Color(204, 204, 204));

        Backbtn.setBackground(new Color(0, 0, 255));
        Backbtn.setFont(new Font("Segoe UI", 0, 24));
        Backbtn.setForeground(Color.WHITE);
        Backbtn.setText("Back");

        jLabel1.setFont(new Font("Segoe UI", 0, 24));
        jLabel1.setForeground(Color.BLACK);
        jLabel1.setText("CourseView");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Backbtn)
                                .addGap(400, 400, 400)
                                .addComponent(jLabel1)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(Backbtn)
                                        .addComponent(jLabel1))
                                .addContainerGap())
        );

        searchfeild.setBackground(Color.WHITE);
        searchfeild.setFont(new Font("Segoe UI", 0, 30));
        searchfeild.setHorizontalAlignment(JTextField.CENTER);

        coursepanel.setBackground(new Color(204, 204, 204));
        coursepanel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout coursepanelLayout = new javax.swing.GroupLayout(coursepanel);
        coursepanel.setLayout(coursepanelLayout);
        coursepanelLayout.setHorizontalGroup(
                coursepanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        coursepanelLayout.setVerticalGroup(
                coursepanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 584, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coursepanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(391, Short.MAX_VALUE)
                                .addComponent(searchfeild, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
                                .addGap(359, 359, 359))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchfeild, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(coursepanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    private static JPanel createCourseCard(Course course, int id) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        card.setPreferredSize(new Dimension(300, 200));
        card.setBackground(new Color(240, 248, 255));

        JTextArea titleArea = new JTextArea(course.getTitle());
        titleArea.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setOpaque(false);
        titleArea.setFocusable(false);
        titleArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructorLabel = new JLabel("Instructor: " + InstructorService.getInstructor(course.getInstructorId()).getName(), SwingConstants.CENTER);
        instructorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        instructorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lessonsLabel = new JLabel("Lessons: " + course.getLessons().size(), SwingConstants.CENTER);
        lessonsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lessonsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("ID: " + course.getId(), SwingConstants.CENTER);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton viewLessonsBtn = new JButton("View Course Lessons");
        List<Course> enrolledCourses = StudentService.getEnrolledCourses(id);
        for (Course c : enrolledCourses) {
            if (c.getId() == course.getId()) {
                viewLessonsBtn.setText("Enrolled");
                viewLessonsBtn.setBackground(Color.GREEN);
                viewLessonsBtn.setEnabled(false);
                break;
            }
        }

        viewLessonsBtn.setBackground(new Color(0, 102, 204));
        viewLessonsBtn.setFont(new Font("Arial", Font.BOLD, 20));
        viewLessonsBtn.setForeground(Color.WHITE);
        viewLessonsBtn.setFocusPainted(false);
        viewLessonsBtn.setSize(200, 80);
        viewLessonsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewLessonsBtn.addActionListener(e -> {
            CourseLessons.start(course, id);
        });

        card.add(Box.createVerticalStrut(10));
        card.add(titleArea);
        card.add(Box.createVerticalStrut(5));
        card.add(instructorLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(lessonsLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(idLabel);
        card.add(Box.createVerticalGlue());
        card.add(viewLessonsBtn);
        card.add(Box.createVerticalStrut(10));

        return card;
    }

    public void showCards(List<Course> courses, int id) {
        int columns = 3;
        int rows = (int) Math.ceil(courses.size() / (double) columns);

        JPanel inner = new JPanel(new GridLayout(rows, columns, 10, 10));
        inner.setBackground(Color.WHITE);

        for (Course c : courses) {
            inner.add(createCourseCard(c, id));
        }

        JScrollPane scrollPane = new JScrollPane(inner,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        coursepanel.removeAll();
        coursepanel.setLayout(new BorderLayout());
        coursepanel.add(scrollPane, BorderLayout.CENTER);
        coursepanel.revalidate();
        coursepanel.repaint();
    }


    public static void start(int id) {
        currentstudent = id;
        if (instance == null) {
            instance = new CoursesView();
            MainWindow.addFrame("CoursesView", instance);
            instance.Backbtn.addActionListener(e -> {
                StudentDashBoard.start(id);
                MainWindow.goToFrame("StudentDashBoard");
            });
        }
        instance.showCards(db.getRecords(), id);
        MainWindow.goToFrame("CoursesView");
    }


    // Variables declaration
    private javax.swing.JButton Backbtn;
    private javax.swing.JPanel coursepanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField searchfeild;
}
