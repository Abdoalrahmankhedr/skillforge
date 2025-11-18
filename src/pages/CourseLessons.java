package pages;

import pages.components.LessonCard;
import models.Course;
import models.Lesson;
import services.InstructorService;
import services.StudentService;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CourseLessons extends JPanel {
    private static JButton EnrollBtn;
    private static JButton BackBtn;
    private static JLabel Coursetitle;
    private static JLabel id;
    private static JLabel lessons;
    private static JLabel instructorname;
    private static JPanel jPanel4;

    private static JPanel jPanel1;
    private static JPanel jPanel2;
    private static JPanel jPanel3;
    private static JLabel jLabel1;

    private static CourseLessons instance;

    public CourseLessons() {
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jLabel1 = new JLabel();

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel2.setBackground(new Color(204, 204, 204));

        BackBtn = new JButton("Back");
        BackBtn.setBackground(new Color(51, 51, 255));
        BackBtn.setFont(new Font("Segoe UI", 0, 21));
        BackBtn.setForeground(Color.white);

        EnrollBtn = new JButton("Enroll Course");
        EnrollBtn.setBackground(new Color(0, 153, 51));
        EnrollBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        EnrollBtn.setForeground(Color.WHITE);
        EnrollBtn.setFocusPainted(false);

        Coursetitle = new JLabel("Course Title", SwingConstants.CENTER);
        Coursetitle.setFont(new Font("Segoe UI", 0, 24));
        Coursetitle.setForeground(new Color(0, 0, 0));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(BackBtn, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                .addGap(10)
                                .addComponent(EnrollBtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(Coursetitle, GroupLayout.PREFERRED_SIZE, 860, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(BackBtn)
                                        .addComponent(EnrollBtn)
                                        .addComponent(Coursetitle))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new Color(102, 204, 255));

        id = new JLabel("Course ID", SwingConstants.CENTER);
        id.setFont(new Font("Segoe UI", 0, 18));
        id.setForeground(new Color(0, 0, 0));

        lessons = new JLabel("Lessons Count", SwingConstants.CENTER);
        lessons.setFont(new Font("Segoe UI", 0, 18));
        lessons.setForeground(new Color(0, 0, 0));

        instructorname = new JLabel("Instructor Name", SwingConstants.CENTER);
        instructorname.setFont(new Font("Segoe UI", 0, 18));
        instructorname.setForeground(new Color(0, 0, 0));

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(id, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(lessons, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(instructorname, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(id)
                                        .addComponent(lessons)
                                        .addComponent(instructorname))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new Font("Segoe UI", 0, 24));
        jLabel1.setForeground(new Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Lessons In Courses");

        jPanel4 = new JPanel();

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                                .addGap(479))
                        .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        setLayout(new BorderLayout());
        add(jPanel1, BorderLayout.CENTER);
    }
    public static void start(Course course, int studentId) {
        List<Lesson> currentlessons = course.getLessons();

        Coursetitle.setText(course.getTitle());
        id.setText("CourseId:" + course.getId());
        lessons.setText("Lessons:" + currentlessons.size());
        instructorname.setText("InstructorName:" + InstructorService.getInstructor(course.getInstructorId()).getName());

        for (ActionListener al : BackBtn.getActionListeners()) BackBtn.removeActionListener(al);
        for (ActionListener al : EnrollBtn.getActionListeners()) EnrollBtn.removeActionListener(al);

        BackBtn.addActionListener(e -> {
            CoursesView.start(studentId);
            MainWindow.goTo("CoursesView");
        });

        JPanel lessonsPanel = new JPanel();
        lessonsPanel.setLayout(new BoxLayout(lessonsPanel, BoxLayout.Y_AXIS));
        lessonsPanel.setBackground(Color.WHITE);

        for (Lesson l : currentlessons) {
            LessonCard card = new LessonCard();
            card.setData(false, l.getTitle(), l.getContent(), l.getStudentProgress().getOrDefault(studentId, false), e -> {});
            lessonsPanel.add(card);
            lessonsPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(lessonsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        EnrollBtn.addActionListener(e -> {
            int response = StudentLessons.showMessage("Do you want to Enroll Course with name :" + course.getTitle());
            if (response == JOptionPane.YES_OPTION) {
                StudentService.enroll(studentId, course.getId());
                StudentDashBoard.start(studentId);
                MainWindow.goTo("StudentDashBoard");
            } else {
                CoursesView.start(studentId);
                MainWindow.goTo("CoursesView");
            }
        });

        jPanel4.removeAll();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(scrollPane, BorderLayout.CENTER);
        jPanel4.revalidate();
        jPanel4.repaint();
    }
}
