package pages;

import databases.CourseDatabase;
import models.Lesson;
import models.Progress;
import models.Quiz;
import models.QuizAttempt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttemptsSummaryDialog extends JDialog {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final CourseDatabase courseDb = CourseDatabase.getInstance();
    
    public AttemptsSummaryDialog(JFrame parent, Lesson lesson, int studentId, int courseId) {
        super(parent, "Quiz Attempts History", true);
        
        setLayout(new BorderLayout());
        setSize(700, 500);
        setLocationRelativeTo(parent);
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header info
        JPanel headerPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        headerPanel.setBorder(BorderFactory.createTitledBorder("Quiz Information"));
        
        Quiz quiz = lesson.getQuiz();
        Progress progress = lesson.getStudentProgress().getOrDefault(studentId, null);
        
        int maxAttempts = quiz != null ? quiz.getRetries() : 0;
        int attemptsUsed = progress != null ? progress.getAttempts().size() : 0;
        
        headerPanel.add(new JLabel("Lesson:"));
        headerPanel.add(new JLabel(lesson.getTitle()));
        headerPanel.add(new JLabel("Max Attempts Allowed:"));
        headerPanel.add(new JLabel(String.valueOf(maxAttempts)));
        headerPanel.add(new JLabel("Attempts Used:"));
        headerPanel.add(new JLabel(String.valueOf(attemptsUsed)));
        
        // Attempts table
        String[] columnNames = {"#", "Date/Time", "Status", "Score", "Correct", "Wrong"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column is "editable" (has button)
            }
        };
        
        JTable attemptsTable = new JTable(model);
        attemptsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attemptsTable.setRowHeight(30);
        attemptsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Populate table
        if (progress != null && progress.getAttempts() != null) {
            List<QuizAttempt> attempts = progress.getAttempts();
            for (int i = 0; i < attempts.size(); i++) {
                QuizAttempt attempt = attempts.get(i);
                String dateTime = attempt.getStartTime() != null 
                    ? attempt.getStartTime().format(DATE_FORMATTER)
                    : "N/A";
                String status = attempt.getStatus() != null ? attempt.getStatus() : "unknown";
                String score = String.format("%.1f%%", attempt.getScore());
                int correct = attempt.getCorrectQuestions();
                int wrong = attempt.getWrongQuestions();
                
                model.addRow(new Object[]{
                    i + 1,
                    dateTime,
                    status,
                    score,
                    correct,
                    wrong,
                    "View Details"
                });
            }
        }
        JScrollPane scrollPane = new JScrollPane(attemptsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Attempts"));
        
        // Close button
        JButton closeBtn = new JButton("Close");
        closeBtn.setPreferredSize(new Dimension(100, 35));
        closeBtn.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeBtn);
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
    }
}

