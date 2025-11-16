package pages;

import models.Course;
import models.Student;
import services.*;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentDashBoard extends javax.swing.JFrame {
    private static StudentDashBoard instance;
    private static Student currentStudent;

    public StudentDashBoard() {
        initComponents();
        instance = this;
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        STudentid = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        enrolledcourses = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        coursespanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Student DashBoard");

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jButton1.setLabel("Logout");

        jButton2.setBackground(new java.awt.Color(0, 0, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jButton2.setText("View All courses");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(296, 296, 296)
                                .addComponent(jButton2)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));

        STudentid.setFont(new java.awt.Font("Segoe UI", 0, 18));
        STudentid.setForeground(new java.awt.Color(0, 0, 0));
        STudentid.setText("ID:9624");

        username.setFont(new java.awt.Font("Segoe UI", 0, 18));
        username.setForeground(new java.awt.Color(0, 0, 0));
        username.setText("UserName:Abdo12345");

        email.setFont(new java.awt.Font("Segoe UI", 0, 18));
        email.setForeground(new java.awt.Color(0, 0, 0));
        email.setText("Email:abdoalrahmankhedr95@gmail.com");

        enrolledcourses.setFont(new java.awt.Font("Segoe UI", 0, 18));
        enrolledcourses.setForeground(new java.awt.Color(0, 0, 0));
        enrolledcourses.setText("EnrolledCourses:9");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(STudentid, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(enrolledcourses)
                                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(STudentid)
                                        .addComponent(email)
                                        .addComponent(enrolledcourses)
                                        .addComponent(username))
                                .addGap(16, 16, 16))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 36));
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("My Courses");

        coursespanel.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout coursespanelLayout = new javax.swing.GroupLayout(coursespanel);
        coursespanel.setLayout(coursespanelLayout);
        coursespanelLayout.setHorizontalGroup(
                coursespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        coursespanelLayout.setVerticalGroup(
                coursespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 506, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coursespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(466, 466, 466)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coursespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {}

    private static JPanel createCourseCard(Course course,int id) {
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
        viewLessonsBtn.setBackground(new Color(0, 102, 204));
        viewLessonsBtn.setFont(new Font("Arial", Font.BOLD, 20));
        viewLessonsBtn.setForeground(Color.WHITE);
        viewLessonsBtn.setFocusPainted(false);
        viewLessonsBtn.setSize(200,80);
        viewLessonsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewLessonsBtn.addActionListener(e -> {
           StudentLessons.start(course,id);
           MainWindow.goToFrame("studentlessons");
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



    public static void start(int id) {
        currentStudent = StudentService.getStudent(id);

        instance.STudentid.setText("ID: " + currentStudent.getId());
        instance.email.setText("Email: " + currentStudent.getEmail());
        instance.username.setText("Name: " + currentStudent.getName());

        List<Course> enrolledCourses = StudentService.getEnrolledCourses(id);
        instance.enrolledcourses.setText("EnrolledCourses:" + enrolledCourses.size());

        int columns = 3;
        int rows = (int) Math.ceil(enrolledCourses.size() / (double) columns);

        JPanel inner = new JPanel(new GridLayout(rows, columns, 10, 10));
        inner.setBackground(Color.WHITE);

        for (Course c : enrolledCourses) {
            inner.add(createCourseCard(c, id));
        }

        JScrollPane scroll = new JScrollPane(inner);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jButton1.setForeground(Color.white);
        jButton2.setForeground(Color.white);
        jButton1.addActionListener(e -> {
            MainWindow.closeFrame("StudentDashBoard");
            MainWindow.goTo("login");
            MainWindow.start();
        });
        jButton2.addActionListener(e -> CoursesView.start(id));

        instance.coursespanel.removeAll();
        instance.coursespanel.setLayout(new BorderLayout());
        instance.coursespanel.add(scroll, BorderLayout.CENTER);
        instance.coursespanel.revalidate();
        instance.coursespanel.repaint();
    }


    private javax.swing.JLabel STudentid;
    private javax.swing.JPanel coursespanel;
    private javax.swing.JLabel email;
    private javax.swing.JLabel enrolledcourses;
    private static javax.swing.JButton jButton1;
    private static javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel username;
}
