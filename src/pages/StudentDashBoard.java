package pages;

import databases.CourseDatabase;
import models.Course;
import models.Student;
import services.*;
import services.CertificateService;
import services.CourseService;
import services.StudentService;
import services.InstructorService;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentDashBoard extends JPanel {
    private static StudentDashBoard instance;
    private static Student currentStudent;

    private static JLabel STudentid;
    private static JPanel coursespanel;
    private static JLabel email;
    private static JLabel enrolledcourses;
    private static JButton jButton1;
    private static JButton jButton2;
    private static JLabel jLabel1;
    private static JLabel jLabel5;
    private static JPanel jPanel2;
    private static JPanel jPanel3;
    private static JLabel username;

    public StudentDashBoard() {
        initComponents();
        instance = this;
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.setBackground(Color.WHITE);

        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(204, 204, 204));

        jLabel1 = new JLabel("Student DashBoard");
        jLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        jLabel1.setForeground(Color.BLACK);

        jButton1 = new JButton("Logout");
        jButton1.setBackground(Color.RED);
        jButton1.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        jButton2 = new JButton("View All courses");
        jButton2.setBackground(Color.BLUE);
        jButton2.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(200, 200, 200)
                                .addComponent(jButton2)
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel3 = new JPanel();
        jPanel3.setBackground(new Color(51, 153, 255));

        STudentid = new JLabel("ID:9624");
        STudentid.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        STudentid.setForeground(Color.BLACK);

        username = new JLabel("UserName:Abdo12345");
        username.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        username.setForeground(Color.BLACK);

        email = new JLabel("Email:test@gmail.com");
        email.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        email.setForeground(Color.BLACK);

        enrolledcourses = new JLabel("EnrolledCourses:0");
        enrolledcourses.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        enrolledcourses.setForeground(Color.BLACK);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(47)
                                .addComponent(username, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(STudentid, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                .addGap(40)
                                .addComponent(email, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
                                .addGap(27)
                                .addComponent(enrolledcourses)
                                .addContainerGap(100, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(STudentid)
                                        .addComponent(email)
                                        .addComponent(enrolledcourses)
                                        .addComponent(username))
                                .addGap(16)));

        jLabel5 = new JLabel("My Courses");
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        jLabel5.setForeground(Color.BLUE);

        coursespanel = new JPanel();
        coursespanel.setBackground(new Color(204, 204, 204));
        coursespanel.setLayout(new BorderLayout());

        JPanel titleWrapper = new JPanel();
        titleWrapper.setBackground(Color.WHITE);
        titleWrapper.add(jLabel5);

        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel1.add(titleWrapper, BorderLayout.SOUTH);

        add(jPanel1, BorderLayout.NORTH);
        add(coursespanel, BorderLayout.CENTER);
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

        JLabel instructorLabel = new JLabel(
                "Instructor: " + InstructorService.getInstructor(course.getInstructorId()).getName(),
                SwingConstants.CENTER);
        instructorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        instructorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lessonsLabel = new JLabel("Lessons: " + course.getLessons().size(), SwingConstants.CENTER);
        lessonsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lessonsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("ID: " + course.getId(), SwingConstants.CENTER);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton viewLessonsBtn = new JButton("View Course Lessons");
        viewLessonsBtn.setBackground(new Color(0, 102, 204));
        viewLessonsBtn.setFont(new Font("Arial", Font.BOLD, 20));
        viewLessonsBtn.setForeground(Color.WHITE);
        viewLessonsBtn.setFocusPainted(false);
        viewLessonsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        final int capturedCourseId = course.getId();
        final int capturedStudentId = id;

        viewLessonsBtn.setActionCommand("course_" + capturedCourseId);
        for (ActionListener al : viewLessonsBtn.getActionListeners()) {
            viewLessonsBtn.removeActionListener(al);
        }
        viewLessonsBtn.addActionListener(e -> {
            // Retrieve the course using the captured ID to ensure we get the correct course
            Course selectedCourse = CourseDatabase.getInstance().getCourseById(capturedCourseId);
            if (selectedCourse != null) {
                StudentLessons.start(selectedCourse, capturedStudentId);
                MainWindow.goTo("studentlessons");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Error: Course not found. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        // Download Certificate button (only visible if course is completed)
        JButton downloadCertBtn = new JButton("Download Certificate");

        downloadCertBtn.setBackground(new Color(0, 153, 0));
        downloadCertBtn.setFont(new Font("Arial", Font.BOLD, 16));
        downloadCertBtn.setForeground(Color.WHITE);
        downloadCertBtn.setFocusPainted(false);
        downloadCertBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Capture course ID and student ID for certificate download
        final int certCourseId = course.getId();
        final int certStudentId = id;
        boolean isCompleted = CourseService.isComplete(certCourseId, certStudentId);
        downloadCertBtn.setVisible(isCompleted);
        downloadCertBtn.setEnabled(isCompleted);

        // Remove any existing listeners first
        for (ActionListener al : downloadCertBtn.getActionListeners()) {
            downloadCertBtn.removeActionListener(al);
        }
        downloadCertBtn.addActionListener(e -> {
            // Show toast message
            JOptionPane.showMessageDialog(
                    null,
                    "Generating certificate — preparing your download...",
                    "Certificate Generation",
                    JOptionPane.INFORMATION_MESSAGE);

            // Get or create certificate
            CertificateService.getOrCreateCertificate(certStudentId, certCourseId);

            // Generate and download PDF
            String filepath = CertificateService.generateCertificatePDF(certStudentId, certCourseId);

            if (filepath != null) {
                boolean success = CertificateService.downloadCertificate(filepath);
                if (success) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Certificate generated successfully! The file should open automatically.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Certificate generated but could not open automatically. File saved to: " + filepath,
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to generate certificate. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
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
        if (isCompleted) {
            card.add(Box.createVerticalStrut(5));
            card.add(downloadCertBtn);
        }
        card.add(Box.createVerticalStrut(10));

        return card;
    }

    public static void start(int id) {
        for (ActionListener al : jButton2.getActionListeners()) {
            jButton2.removeActionListener(al);
        }
        for (ActionListener al : jButton1.getActionListeners()) {
            jButton1.removeActionListener(al);
        }
        currentStudent = StudentService.getStudent(id);

        STudentid.setText("ID: " + currentStudent.getId());
        email.setText("Email: " + currentStudent.getEmail());
        username.setText("Name: " + currentStudent.getName());

        List<Course> enrolledCourses = StudentService.getEnrolledCourses(id);
        enrolledcourses.setText("EnrolledCourses:" + enrolledCourses.size());

        int columns = 3;
        int rows = (int) Math.ceil(enrolledCourses.size() / (double) columns);
        if (rows == 0)
            rows = 1;

        JPanel innerPanel = new JPanel(new GridLayout(rows, columns, 10, 10));
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Course c : enrolledCourses) {
            innerPanel.add(createCourseCard(c, id));
        }

        JScrollPane scroll = new JScrollPane(innerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        jButton1.setForeground(Color.white);
        jButton2.setForeground(Color.white);

        // Capture student ID to avoid closure issues
        final int studentIdForButtons = id;

        // Remove existing listeners and add new ones
        for (ActionListener al : jButton1.getActionListeners()) {
            jButton1.removeActionListener(al);
        }
        jButton1.addActionListener(e -> {
            MainWindow.goTo("login");
            MainWindow.start();
        });

        for (ActionListener al : jButton2.getActionListeners()) {
            jButton2.removeActionListener(al);
        }
        jButton2.addActionListener(e -> {
            CoursesView.start(studentIdForButtons);
            MainWindow.goTo("CoursesView");
        });

        coursespanel.removeAll();
        coursespanel.setLayout(new BorderLayout());
        coursespanel.add(scroll, BorderLayout.CENTER);
        coursespanel.revalidate();
        coursespanel.repaint();
    }
}