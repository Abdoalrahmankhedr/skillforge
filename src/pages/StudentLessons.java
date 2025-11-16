
package pages;

import pages.components.*;
import models.Course;
import models.Lesson;
import services.InstructorService;
import services.StudentService;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentLessons extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StudentLessons.class.getName());
    public StudentLessons() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        BackBtn = new javax.swing.JButton();
        Coursetitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        id = new javax.swing.JLabel();
        lessons = new javax.swing.JLabel();
        Completed = new javax.swing.JLabel();
        uncompleted = new javax.swing.JLabel();
        progress = new javax.swing.JLabel();
        instructorname = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        BackBtn.setBackground(new java.awt.Color(51, 51, 255));
        BackBtn.setFont(new java.awt.Font("Segoe UI", 0, 21));
        BackBtn.setForeground(Color.white);
        // NOI18N
        BackBtn.setText("Back");

        Coursetitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Coursetitle.setForeground(new java.awt.Color(0, 0, 0));
        Coursetitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Coursetitle.setText("abdoalrahman");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(Coursetitle, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(BackBtn)
                                        .addComponent(Coursetitle))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 204, 255));

        id.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        id.setForeground(new java.awt.Color(0, 0, 0));
        id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        id.setText("jLabel1");

        lessons.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lessons.setForeground(new java.awt.Color(0, 0, 0));
        lessons.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lessons.setText("jLabel2");

        Completed.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Completed.setForeground(new java.awt.Color(0, 204, 51));
        Completed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Completed.setText("jLabel1");

        uncompleted.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        uncompleted.setForeground(new java.awt.Color(204, 0, 0));
        uncompleted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        uncompleted.setText("jLabel1");

        progress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        progress.setForeground(new java.awt.Color(0, 204, 0));
        progress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        progress.setText("jLabel2");

        instructorname.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        instructorname.setForeground(new java.awt.Color(0, 0, 0));
        instructorname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        instructorname.setText("jLabel1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lessons, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Completed, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uncompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(Completed)
                                        .addComponent(uncompleted)
                                        .addComponent(progress)
                                        .addComponent(instructorname))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("My Courses");

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
    }// </editor-fold>
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
            if(c.getTitle().equals(l.getTitle())||c == course.getLessons().get(course.getLessons().size() - 1)) return true;
            else if(!c.getStudentProgress().get(id)) return false;
        }
        return false;
    }
    private static int completed;
    private static int unCompleted;
    private static int total;
    private static List<Lesson> currentlessons;
    public  static void start(Course course,int ID){
        BackBtn.addActionListener(e->{
            StudentDashBoard.start(ID);
            MainWindow.goToFrame("StudentDashBoard");
        });
        total=course.getLessons().size();
        completed=unCompleted=0;
        if(!course.getLessons().get(0).getStudentProgress().containsKey(ID)){
            List<Lesson> lessons = course.getLessons();
            for (int i = 0; i < lessons.size(); i++) {
                Lesson l = lessons.get(i);
                Boolean status = l.getStudentProgress().get(ID);
                if (status == null) {
                    StudentService.takeLesson(ID, l.getId());
                }
            }
        }
        currentlessons=course.getLessons();
        for(Lesson l:course.getLessons()){
            if(l.getStudentProgress().get(ID)==null) return;
            if(l.getStudentProgress().get(ID)){
                completed++;
            }
            else unCompleted++;
        }
        Coursetitle.setText(course.getTitle());
        id.setText("CourseId:"+course.getId());
        lessons.setText("Lessons:"+total);
        instructorname.setText(InstructorService.getInstructor(course.getInstructorId()).getName());
        Completed.setText("Completed:"+completed);
        uncompleted.setText("UnCompleted:"+unCompleted);
        progress.setText("Progress:"+(completed/total)*100+"%");
        JPanel lessonsPanel = new JPanel();
        lessonsPanel.setLayout(new BoxLayout(lessonsPanel, BoxLayout.Y_AXIS));
        lessonsPanel.setBackground(Color.WHITE);
        for (Lesson l : currentlessons) {
            LessonCard card = new LessonCard();
            card.setData(
                    true,
                    l.getTitle(),
                    l.getContent(),
                    l.getStudentProgress().getOrDefault(ID, false),
                    e -> {
                        if(checkLastLessons(course,l,ID)){
                            //save the update to file
                            card.completeBtn.setText("Completed");
                            StudentService.completeLesson(ID,l.getId());
                            card.completeBtn.setBackground(Color.green);
                            card.completeBtn.setEnabled(false);
                            completed++;
                            unCompleted--;
                            Completed.setText("Completed:"+completed);
                            uncompleted.setText("UnCompleted:"+unCompleted);
                            progress.setText("Progress:"+(completed/total)*100+"%");
                        }
                        else{
                            showMessage("You Need To complete the last Lessons Before This First");
                        }
                    }
            );
            lessonsPanel.add(card);
            lessonsPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(lessonsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        instructorname.setText("InstructorName:"+InstructorService.getInstructor(course.getInstructorId()).getName());
        jPanel4.removeAll();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(scrollPane, BorderLayout.CENTER);
        jPanel4.revalidate();
        jPanel4.repaint();
    }
    // Variables declaration - do not modify
    private static   javax.swing.JButton BackBtn;
    private static   javax.swing.JLabel Completed;
    private static javax.swing.JLabel Coursetitle;
    private static javax.swing.JLabel id;
    private static javax.swing.JLabel instructorname;
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JPanel jPanel1;
    private static javax.swing.JPanel jPanel2;
    private static javax.swing.JPanel jPanel3;
    private static javax.swing.JPanel jPanel4;
    private static javax.swing.JLabel lessons;
    private static javax.swing.JLabel progress;
    private static javax.swing.JLabel uncompleted;
    // End of variables declaration
}
