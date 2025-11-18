package pages;

import models.Course;
import models.Lesson;
import pages.components.LessonCard;
import services.InstructorService;
import services.StudentService;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StudentLessons extends JPanel {
    private static JButton BackBtn;
    private static JLabel Completed;
    private static JLabel Coursetitle;
    private static JLabel id;
    private static JLabel instructorname;
    private static JLabel jLabel1;
    private static JPanel jPanel1;
    private static JPanel jPanel2;
    private static JPanel jPanel3;
    private static JPanel jPanel4;
    private static JLabel lessons;
    private static JLabel progress;
    private static JLabel uncompleted;

    private static int completed;
    private static int unCompleted;
    private static int total;
    private static List<Lesson> currentlessons;

    public StudentLessons() {
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        BackBtn = new JButton();
        Coursetitle = new JLabel();
        jPanel3 = new JPanel();
        id = new JLabel();
        lessons = new JLabel();
        Completed = new JLabel();
        uncompleted = new JLabel();
        progress = new JLabel();
        instructorname = new JLabel();
        jLabel1 = new JLabel();
        jPanel4 = new JPanel();

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel2.setBackground(new Color(204, 204, 204));

        BackBtn.setBackground(new Color(51, 51, 255));
        BackBtn.setFont(new Font("Segoe UI", Font.PLAIN, 21));
        BackBtn.setForeground(Color.white);
        BackBtn.setText("Back");

        Coursetitle.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        Coursetitle.setForeground(new Color(0, 0, 0));
        Coursetitle.setHorizontalAlignment(SwingConstants.CENTER);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(BackBtn, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(Coursetitle, GroupLayout.PREFERRED_SIZE, 860, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(BackBtn)
                                        .addComponent(Coursetitle))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new Color(102, 204, 255));
        id.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        id.setForeground(new Color(0, 0, 0));
        id.setHorizontalAlignment(SwingConstants.CENTER);

        lessons.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lessons.setForeground(new Color(0, 0, 0));
        lessons.setHorizontalAlignment(SwingConstants.CENTER);

        Completed.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        Completed.setForeground(new Color(0, 204, 51));
        Completed.setHorizontalAlignment(SwingConstants.CENTER);

        uncompleted.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        uncompleted.setForeground(new Color(204, 0, 0));
        uncompleted.setHorizontalAlignment(SwingConstants.CENTER);

        progress.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        progress.setForeground(new Color(0, 204, 0));
        progress.setHorizontalAlignment(SwingConstants.CENTER);

        instructorname.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instructorname.setForeground(new Color(0, 0, 0));
        instructorname.setHorizontalAlignment(SwingConstants.CENTER);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(id, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lessons, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Completed, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uncompleted, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(progress, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(instructorname, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(id)
                                        .addComponent(lessons)
                                        .addComponent(Completed)
                                        .addComponent(uncompleted)
                                        .addComponent(progress)
                                        .addComponent(instructorname))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        jLabel1.setForeground(new Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("My Courses");

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 461, Short.MAX_VALUE)
        );

        GroupLayout jPanel1Layout = new GroupLayout(this);
        setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                                .addGap(479, 479, 479))
                        .addComponent(jPanel4, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
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

    public static boolean checkLastLessons(Course course, Lesson l, int id) {
        List<Lesson> lessons = course.getLessons();
        int currentIndex = lessons.indexOf(l);

        if (currentIndex == 0) return true;

        for (int i = 0; i < currentIndex; i++) {
            Boolean status = lessons.get(i).getStudentProgress().get(id);
            if (status == null || !status) {
                return false;
            }
        }

        return true;
    }

    public static void start(Course course, int ID) {
        for (ActionListener al : BackBtn.getActionListeners()) {
            BackBtn.removeActionListener(al);
        }
        BackBtn.addActionListener(e -> {
            StudentDashBoard.start(ID);
            MainWindow.goTo("StudentDashBoard");
        });

        total = completed = unCompleted = 0;
        if (course.getLessons().isEmpty()) {
            currentlessons = null;
        } else {
            currentlessons = course.getLessons();
            total = currentlessons.size();

            for (Lesson l : new ArrayList<>(currentlessons)) {
                if (!l.getStudentProgress().containsKey(ID)) {
                    StudentService.takeLesson(ID, l.getId());
                }
            }

            for (Lesson l : new ArrayList<>(currentlessons)) {
                if (l.getStudentProgress().containsKey(ID)) {
                    Boolean status = l.getStudentProgress().get(ID);
                    if (status != null && status) {
                        completed++;
                    } else {
                        unCompleted++;
                    }
                } else {
                    unCompleted++;
                }
            }
        }

        Coursetitle.setText(course.getTitle());
        id.setText("CourseId:" + course.getId());
        lessons.setText("Lessons:" + total);
        instructorname.setText(InstructorService.getInstructor(course.getInstructorId()).getName());
        Completed.setText("Completed:" + completed);
        uncompleted.setText("UnCompleted:" + unCompleted);
        progress.setText(String.format("Progress: %.2f", (((((double) completed / (double) total)) * 100))) + "%");

        JPanel lessonsPanel = new JPanel();
        lessonsPanel.setLayout(new BoxLayout(lessonsPanel, BoxLayout.Y_AXIS));
        lessonsPanel.setBackground(Color.WHITE);

        if (currentlessons != null) {
            for (Lesson l : currentlessons) {
                LessonCard card = new LessonCard();
                card.setData(
                        true,
                        l.getTitle(),
                        l.getContent(),
                        l.getStudentProgress().getOrDefault(ID, false),
                        e -> {
                            if (checkLastLessons(course, l, ID)) {
                                card.completeBtn.setText("Completed");
                                StudentService.completeLesson(ID, l.getId());
                                card.completeBtn.setBackground(Color.green);
                                card.completeBtn.setEnabled(false);
                                completed++;
                                unCompleted--;
                                Completed.setText("Completed:" + completed);
                                uncompleted.setText("UnCompleted:" + unCompleted);
                                progress.setText(String.format("Progress: %.2f", (((((double) completed / (double) total)) * 100))) + "%");
                            } else {
                                showMessage("You Need To complete the last Lessons Before This First");
                            }
                        }
                );
                lessonsPanel.add(card);
                lessonsPanel.add(Box.createVerticalStrut(5));
            }
        }

        JScrollPane scrollPane = new JScrollPane(lessonsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        instructorname.setText("InstructorName:" + InstructorService.getInstructor(course.getInstructorId()).getName());
        jPanel4.removeAll();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(scrollPane, BorderLayout.CENTER);
        jPanel4.revalidate();
        jPanel4.repaint();
    }
}
