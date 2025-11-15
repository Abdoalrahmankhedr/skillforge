
package pages;

import Components.LessonCard;
import models.Course;
import models.Lesson;
import services.InstructorService;
import services.StudentService;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CourseLessons extends javax.swing.JFrame {
    private JButton EnrollBtn;
    public CourseLessons() {
        initComponents();
    }
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        BackBtn = new javax.swing.JButton();
        EnrollBtn = new javax.swing.JButton();
        Coursetitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        id = new javax.swing.JLabel();
        lessons = new javax.swing.JLabel();
        instructorname = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        BackBtn.setBackground(new java.awt.Color(51, 51, 255));
        BackBtn.setFont(new java.awt.Font("Segoe UI", 0, 21));
        BackBtn.setForeground(Color.white);
        BackBtn.setText("Back");

        EnrollBtn.setBackground(new java.awt.Color(0, 153, 51));
        EnrollBtn.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18));
        EnrollBtn.setForeground(Color.WHITE);
        EnrollBtn.setText("Enroll Course");
        EnrollBtn.setFocusPainted(false);

        Coursetitle.setFont(new java.awt.Font("Segoe UI", 0, 24));
        Coursetitle.setForeground(new java.awt.Color(0, 0, 0));
        Coursetitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Coursetitle.setText("Course Title");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(EnrollBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Coursetitle, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(BackBtn)
                                        .addComponent(EnrollBtn)
                                        .addComponent(Coursetitle))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 204, 255));

        id.setFont(new java.awt.Font("Segoe UI", 0, 18));
        id.setForeground(new java.awt.Color(0, 0, 0));
        id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        id.setText("Course ID");

        lessons.setFont(new java.awt.Font("Segoe UI", 0, 18));
        lessons.setForeground(new java.awt.Color(0, 0, 0));
        lessons.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lessons.setText("Lessons Count");

        instructorname.setFont(new java.awt.Font("Segoe UI", 0, 18));
        instructorname.setForeground(new java.awt.Color(0, 0, 0));
        instructorname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        instructorname.setText("Instructor Name");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lessons, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(instructorname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(id)
                                        .addComponent(lessons)
                                        .addComponent(instructorname))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lessons In Courses");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 461, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(479, 479, 479))
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    public static int showMessage(String message) {
        return JOptionPane.showConfirmDialog(
                null,
                message,
                "Message",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    public static boolean checkLastLessons(Course course,Lesson l,int id){
        for(Lesson c:course.getLessons()){
            if(c.getTitle().equals(l.getTitle())) return true;
            else if(!c.getStudentProgress().get(id)) return false;
        }
        return false;
    }
    private static int total;
    private static List<Lesson> currentlessons;
    public static void start(Course course, int ID){
        MainWindow.closeFrame("CorsesView");
        CourseLessons frame = new CourseLessons();
        frame.setVisible(true);

        List<Lesson> currentlessons = course.getLessons();

        frame.Coursetitle.setText(course.getTitle());
        frame.id.setText("CourseId:" + course.getId());
        frame.lessons.setText("Lessons:" + currentlessons.size());
        frame.instructorname.setText("InstructorName:" + InstructorService.getInstructor(course.getInstructorId()).getName());

        frame.BackBtn.addActionListener(e -> {
            frame.dispose();
            StudentDashBoard.start(ID);
        });

        JPanel lessonsPanel = new JPanel();
        lessonsPanel.setLayout(new BoxLayout(lessonsPanel, BoxLayout.Y_AXIS));
        lessonsPanel.setBackground(Color.WHITE);

        for (Lesson l : currentlessons) {
            LessonCard card = new LessonCard();
            card.setData(false, l.getTitle(), l.getContent(), l.getStudentProgress().getOrDefault(ID, false), e -> {});
            lessonsPanel.add(card);
            lessonsPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(lessonsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        frame.EnrollBtn.addActionListener(e -> {
            int response=showMessage("Do You Want to Enroll the Course with name:"+course.getTitle());
            if(response == JOptionPane.YES_OPTION){
                 StudentService.enroll(ID,course.getId());
                 frame.dispose();
                 StudentDashBoard.start(ID);
                 MainWindow.goToFrame("StudentDashBoard");
            }
        });

        frame.jPanel4.removeAll();
        frame.jPanel4.setLayout(new BorderLayout());
        frame.jPanel4.add(scrollPane, BorderLayout.CENTER);
        frame.jPanel4.revalidate();
        frame.jPanel4.repaint();
    }

    // Variables declaration - do not modify
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JPanel jPanel1;
    private static javax.swing.JPanel jPanel2;
    private static javax.swing.JPanel jPanel3;
    private JButton BackBtn;
    private JLabel Coursetitle;
    private JLabel id;
    private JLabel lessons;
    private JLabel instructorname;
    private JPanel jPanel4;

    // End of variables declaration
}
