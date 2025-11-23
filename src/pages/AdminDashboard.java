package pages;

import databases.CourseDatabase;
import models.Course;
import models.User;
import services.AdminService;
import services.InstructorService;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JPanel {
    private static int adminId;
    private static String adminName;
    private static JLabel adminLabel;
    private static JPanel coursesPanel;
    private static JButton logoutButton;
    private static JButton refreshButton;
    private static JTextField searchField;
    private static JButton searchButton;
    private static JButton viewPendingBtn;
    private static JButton viewApprovedBtn;
    private static JButton viewRejectedBtn;

    public AdminDashboard() {
        initComponents();
    }

    public static void setAdmin(int id, String name) {
        adminId = id;
        adminName = name;
        if (adminLabel != null) {
            adminLabel.setText("Admin: " + adminName);
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(204, 204, 204));
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        titleLabel.setForeground(Color.BLACK);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        logoutButton.setForeground(Color.WHITE);

        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 102, 204));
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        refreshButton.setForeground(Color.WHITE);

        GroupLayout headerLayout = new GroupLayout(headerPanel);
        headerPanel.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
                headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(headerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logoutButton)
                                .addGap(150)
                                .addComponent(titleLabel)
                                .addGap(150)
                                .addComponent(refreshButton)
                                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
                headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(headerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(headerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(logoutButton)
                                        .addComponent(titleLabel)
                                        .addComponent(refreshButton))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(51, 153, 255));

        adminLabel = new JLabel("Admin: ");
        adminLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        adminLabel.setForeground(Color.BLACK);

        searchField = new JTextField(10);
        searchButton = new JButton("Search by ID");
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);

        viewPendingBtn = new JButton("Pending Courses");
        viewPendingBtn.setBackground(new Color(255, 153, 0));
        viewPendingBtn.setForeground(Color.WHITE);

        viewApprovedBtn = new JButton("Approved Courses");
        viewApprovedBtn.setBackground(new Color(0, 153, 0));
        viewApprovedBtn.setForeground(Color.WHITE);

        viewRejectedBtn = new JButton("Rejected Courses");
        viewRejectedBtn.setBackground(new Color(153, 0, 0));
        viewRejectedBtn.setForeground(Color.WHITE);

        infoPanel.add(adminLabel);
        infoPanel.add(searchField);
        infoPanel.add(searchButton);
        infoPanel.add(viewPendingBtn);
        infoPanel.add(viewApprovedBtn);
        infoPanel.add(viewRejectedBtn);

        coursesPanel = new JPanel();
        coursesPanel.setBackground(new Color(204, 204, 204));
        coursesPanel.setLayout(new BorderLayout());

        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(infoPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(coursesPanel, BorderLayout.CENTER);

        logoutButton.addActionListener(e -> {
            MainWindow.goTo("login");
        });

        refreshButton.addActionListener(e -> showPendingCourses());
        viewPendingBtn.addActionListener(e -> showPendingCourses());
        viewApprovedBtn.addActionListener(e -> showApprovedCourses());
        viewRejectedBtn.addActionListener(e -> showRejectedCourses());

        searchButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Course c = CourseDatabase.getInstance().getCourseById(id);
                if (c != null) showCourses(List.of(c));
                else JOptionPane.showMessageDialog(this, "Course not found", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        showPendingCourses();
    }

    private static void showPendingCourses() {
        List<Course> pending = AdminService.getPendingCourses();
        showCourses(pending);
    }

    private static void showApprovedCourses() {
        List<Course> approved = AdminService.getApprovedCourses();
        showCourses(approved);
    }

    private static void showRejectedCourses() {
        List<Course> rejected = AdminService.getRejectedCourses();
        showCourses(rejected);
    }

    private static void showCourses(List<Course> courses) {
        coursesPanel.removeAll();

        int columns = 3;
        int rows = (int) Math.ceil(courses.size() / (double) columns);
        if (rows == 0) rows = 1;

        JPanel innerPanel = new JPanel(new GridLayout(rows, columns, 10, 10));
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Course c : courses) {
            innerPanel.add(createCourseCard(c));
        }

        JScrollPane scroll = new JScrollPane(innerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        coursesPanel.add(scroll, BorderLayout.CENTER);
        coursesPanel.revalidate();
        coursesPanel.repaint();
    }

    private static JPanel createCourseCard(Course course) {
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

        JLabel idLabel = new JLabel("ID: " + course.getId(), SwingConstants.CENTER);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton approveBtn = new JButton("Approve");
        approveBtn.setBackground(new Color(0, 153, 0));
        approveBtn.setForeground(Color.WHITE);
        approveBtn.setFocusPainted(false);
        approveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        approveBtn.addActionListener(e -> {
            AdminService.approveCourse(adminId, course.getId());
            JOptionPane.showMessageDialog(null, "Course approved successfully");
            showPendingCourses();
        });

        JButton rejectBtn = new JButton("Reject");
        rejectBtn.setBackground(new Color(153, 0, 0));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.setFocusPainted(false);
        rejectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        rejectBtn.addActionListener(e -> {
            AdminService.rejectCourse(adminId, course.getId());
            JOptionPane.showMessageDialog(null, "Course rejected successfully");
            showPendingCourses();
        });

        card.add(Box.createVerticalStrut(10));
        card.add(titleArea);
        card.add(Box.createVerticalStrut(5));
        card.add(instructorLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(idLabel);
        card.add(Box.createVerticalGlue());
        card.add(approveBtn);
        card.add(Box.createVerticalStrut(5));
        card.add(rejectBtn);
        card.add(Box.createVerticalStrut(10));

        return card;
    }

    public static void start(int id) {
        adminId = id;
        User admin = AdminService.getAdmin(id);

        if (admin != null) {
            adminName = admin.getName();
            adminLabel.setText("Admin: " + adminName);
        }
    }
}