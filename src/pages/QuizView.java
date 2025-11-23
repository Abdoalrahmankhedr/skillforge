package pages;

import databases.CourseDatabase;
import models.Lesson;
import models.Progress;
import models.Question;
import models.Quiz;
import models.QuizAttempt;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizView extends JPanel {
    private static QuizView instance;
    private static Quiz currentQuiz;
    private static Lesson currentLesson;
    private static int currentStudentId;
    private static int currentCourseId;
    private static QuizAttempt currentAttempt;
    private static int currentAttemptIndex;
    private static boolean isReviewMode = false;
    
    private static JButton backBtn;
    private static JButton finishBtn;
    private static JLabel attemptInfoLabel;
    private static JLabel answeredCountLabel;
    private static JPanel questionsPanel;
    private static JScrollPane scrollPane;
    
    private static Map<Integer, Integer> answers = new HashMap<>();
    private static Map<Integer, ButtonGroup> questionGroups = new HashMap<>();
    private static Map<Integer, List<JRadioButton>> allChoiceButtons = new HashMap<>();
    
    public QuizView() {
        initComponents();
        instance = this;
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with back button and info
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(100, 35));
        backBtn.setBackground(new Color(255, 100, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> handleBack());
        
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        attemptInfoLabel = new JLabel("Attempt: -");
        attemptInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        answeredCountLabel = new JLabel("Answered: 0/0");
        answeredCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(attemptInfoLabel);
        infoPanel.add(answeredCountLabel);
        
        topPanel.add(backBtn, BorderLayout.WEST);
        topPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Questions panel - shows all questions at once
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBorder(BorderFactory.createTitledBorder("Quiz Questions"));
        questionsPanel.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Bottom panel with Finish button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        finishBtn = new JButton("Finish Quiz");
        finishBtn.setPreferredSize(new Dimension(150, 40));
        finishBtn.setBackground(new Color(0, 153, 0));
        finishBtn.setForeground(Color.WHITE);
        finishBtn.setFont(new Font("Arial", Font.BOLD, 16));
        finishBtn.setFocusPainted(false);
        finishBtn.addActionListener(e -> handleFinish());
        
        bottomPanel.add(finishBtn);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public static void start(Lesson lesson, int studentId, int courseId) {
        if (instance == null) {
            instance = new QuizView();
            MainWindow.addPage("quizview", instance);
        }
        
        currentLesson = lesson;
        currentQuiz = lesson.getQuiz();
        currentStudentId = studentId;
        currentCourseId = courseId;
        answers.clear();
        questionGroups.clear();
        allChoiceButtons.clear();
        isReviewMode = false;
        
        if (currentQuiz == null) {
            JOptionPane.showMessageDialog(null, "Quiz not found for this lesson.", "Error", JOptionPane.ERROR_MESSAGE);
            StudentLessons.start(CourseDatabase.getInstance().getCourseById(courseId), studentId);
            MainWindow.goTo("studentlessons");
            return;
        }
        
        Progress progress = lesson.getStudentProgress().getOrDefault(studentId, null);
        if (progress == null) {
            lesson.addStudent(studentId);
            progress = lesson.getStudentProgress().get(studentId);
        }
        
        int maxAttempts = currentQuiz.getRetries();
        int attemptsUsed = (progress.getAttempts() != null) ? progress.getAttempts().size() : 0;
        
        if (attemptsUsed >= maxAttempts) {
            JOptionPane.showMessageDialog(null, "You have used all allowed attempts for this quiz.", "No Attempts Left", JOptionPane.INFORMATION_MESSAGE);
            StudentLessons.start(CourseDatabase.getInstance().getCourseById(courseId), studentId);
            MainWindow.goTo("studentlessons");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to start the quiz for '" + lesson.getTitle() + "'? This will use one attempt.",
            "Start Quiz Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result != JOptionPane.YES_OPTION) {
            StudentLessons.start(CourseDatabase.getInstance().getCourseById(courseId), studentId);
            MainWindow.goTo("studentlessons");
            return;
        }
        
        CourseDatabase courseDb = CourseDatabase.getInstance();
        currentAttempt = courseDb.startQuizAttempt(studentId, currentQuiz.getId());
        
        if (currentAttempt == null) {
            JOptionPane.showMessageDialog(null, "Failed to start quiz attempt. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            StudentLessons.start(CourseDatabase.getInstance().getCourseById(courseId), studentId);
            MainWindow.goTo("studentlessons");
            return;
        }
        
        progress = lesson.getStudentProgress().get(studentId);
        if (progress != null && progress.getAttempts() != null && !progress.getAttempts().isEmpty()) {
            currentAttemptIndex = progress.getAttempts().size() - 1;
        } else {
            currentAttemptIndex = 0;
        }
        
        attemptInfoLabel.setText("Attempt: " + (attemptsUsed + 1) + "/" + maxAttempts);
        attemptInfoLabel.setForeground(Color.BLACK);
        finishBtn.setVisible(true);
        backBtn.setText("Back");
        loadAllQuestions();
        updateAnsweredCount();
        
        MainWindow.goTo("quizview");
    }
    
    public static void showReview(Lesson lesson, QuizAttempt attempt, int studentId, int courseId, int attemptIndex) {
        if (instance == null) {
            instance = new QuizView();
            MainWindow.addPage("quizview", instance);
        }
        
        currentLesson = lesson;
        currentQuiz = lesson.getQuiz();
        currentStudentId = studentId;
        currentCourseId = courseId;
        currentAttempt = attempt;
        currentAttemptIndex = attemptIndex;
        isReviewMode = true;
        questionGroups.clear();
        allChoiceButtons.clear();
        
        if (currentQuiz == null || currentAttempt == null) {
            JOptionPane.showMessageDialog(null, "Quiz or attempt not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        answers.clear();
        if (currentAttempt != null && currentAttempt.getQuestionAnswers() != null) {
            // Create a new HashMap to ensure we have a fresh copy
            answers = new HashMap<>(currentAttempt.getQuestionAnswers());
        }
        
        backBtn.setText("Back to Lessons");
        for (ActionListener al : backBtn.getActionListeners()) {
            backBtn.removeActionListener(al);
        }
        backBtn.addActionListener(e -> {
            StudentLessons.start(CourseDatabase.getInstance().getCourseById(courseId), studentId);
            MainWindow.goTo("studentlessons");
        });
        
        finishBtn.setVisible(false);
        
        String scoreText = String.format("Score: %.1f%% (%s)",
            currentAttempt.getScore(),
            currentAttempt.isPassed() ? "PASSED" : "FAILED"
        );
        attemptInfoLabel.setText(scoreText);
        attemptInfoLabel.setForeground(currentAttempt.isPassed() ? Color.GREEN : Color.RED);
        attemptInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        updateAnsweredCount();
        loadAllQuestions();
        
        MainWindow.goTo("quizview");
    }
    
    private static void loadAllQuestions() {
        if (currentQuiz == null) {
            return;
        }
        
        questionsPanel.removeAll();
        questionGroups.clear();
        allChoiceButtons.clear();
        
        List<Question> questions = currentQuiz.getQuestions();
        
        for (int qIndex = 0; qIndex < questions.size(); qIndex++) {
            Question question = questions.get(qIndex);
            
            // Create question panel with border
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                    "Question " + (qIndex + 1) + " of " + questions.size()
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            questionPanel.setBackground(Color.WHITE);
            
            // Question text
            JLabel questionLabel = new JLabel("<html><b>" + question.getHeader() + "</b></html>");
            questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
            questionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            questionPanel.add(questionLabel);
            
            // Get student's answer and find all correct answers
            Integer studentAnswer = answers.get(qIndex);
            java.util.Set<Integer> correctAnswerIndices = new java.util.HashSet<>();
            for (int i = 0; i < question.getChoices().size(); i++) {
                if (question.getChoices().get(i).isCorrect()) {
                    correctAnswerIndices.add(i);
                }
            }
            
            // Check if student's answer is correct
            boolean isCorrect = studentAnswer != null && correctAnswerIndices.contains(studentAnswer);
            
            // Choices panel
            JPanel choicesPanel = new JPanel();
            choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
            choicesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            ButtonGroup group = new ButtonGroup();
            List<JRadioButton> buttons = new java.util.ArrayList<>();
            
            for (int i = 0; i < question.getChoices().size(); i++) {
                Question.Choice choice = question.getChoices().get(i);
                boolean isThisChoiceCorrect = correctAnswerIndices.contains(i);
                boolean isThisStudentAnswer = studentAnswer != null && studentAnswer == i;
                
                JRadioButton radioButton = new JRadioButton((char)('A' + i) + ") " + choice.getText());
                radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
                radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                radioButton.setEnabled(!isReviewMode);
                
                if (isThisStudentAnswer) {
                    radioButton.setSelected(true);
                }
                
                if (isReviewMode) {
                    boolean isWrongAnswer = isThisStudentAnswer && !isThisChoiceCorrect;
                    
                    if (isThisChoiceCorrect) {
                        radioButton.setForeground(Color.GREEN);
                        radioButton.setOpaque(true);
                        radioButton.setBackground(new Color(230, 255, 230));
                    } else if (isWrongAnswer) {
                        // Student's wrong answer - red (student selected this but it's wrong)
                        radioButton.setForeground(Color.RED);
                        radioButton.setOpaque(true);
                        radioButton.setBackground(new Color(255, 230, 230));
                        radioButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    } else {
                        // Other options (not correct, not student's answer) - gray
                        radioButton.setForeground(Color.GRAY);
                        radioButton.setOpaque(false);
                    }
                } else {
                    final int questionIdx = qIndex;
                    final int choiceIdx = i;
                    radioButton.addActionListener(e -> {
                        answers.put(questionIdx, choiceIdx);
                        updateAnsweredCount();

                        CourseDatabase.getInstance().updateQuizAttemptAnswers(
                            currentStudentId, currentQuiz.getId(), currentAttemptIndex, answers
                        );
                    });
                }
                
                group.add(radioButton);
                buttons.add(radioButton);
                choicesPanel.add(radioButton);
                choicesPanel.add(Box.createVerticalStrut(5));
            }
            
            // If no answer provided in review mode
            if (isReviewMode && studentAnswer == null) {
                JLabel noAnswerLabel = new JLabel("⚠ No answer provided");
                noAnswerLabel.setForeground(Color.ORANGE);
                noAnswerLabel.setFont(new Font("Arial", Font.BOLD, 12));
                noAnswerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                choicesPanel.add(noAnswerLabel);
            }
            
            questionGroups.put(qIndex, group);
            allChoiceButtons.put(qIndex, buttons);
            questionPanel.add(choicesPanel);
            
            questionsPanel.add(questionPanel);
            questionsPanel.add(Box.createVerticalStrut(15));
        }
        
        questionsPanel.add(Box.createVerticalGlue());
        questionsPanel.revalidate();
        questionsPanel.repaint();
    }
    
    private static void updateAnsweredCount() {
        int total = currentQuiz != null ? currentQuiz.getQuestions().size() : 0;
        int answered = answers.size();
        answeredCountLabel.setText(String.format("Answered: %d/%d", answered, total));
    }
    
    private static void handleBack() {
        if (isReviewMode) {
            StudentLessons.start(CourseDatabase.getInstance().getCourseById(currentCourseId), currentStudentId);
            MainWindow.goTo("studentlessons");
            return;
        }
        
        // Get current attempt info for warning message
        int maxAttempts = currentQuiz.getRetries();
        Progress progress = currentLesson.getStudentProgress().get(currentStudentId);
        int attemptsUsed = (progress != null && progress.getAttempts() != null) ? progress.getAttempts().size() : 0;
        int remainingAttempts = maxAttempts - attemptsUsed;
        
        int result = JOptionPane.showConfirmDialog(
            null,
            String.format("IF you leave now you will lose one of your quiz attempts.\n\n" +
                         "Attempts used: %d/%d\n" +
                         "Remaining attempts: %d\n\n" +
                         "Are you sure you want to leave? This will count as an abandoned attempt.",
                         attemptsUsed, maxAttempts, remainingAttempts),
            "Leave Quiz Warning",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Abandon attempt
            CourseDatabase.getInstance().abandonQuizAttempt(
                currentStudentId, currentQuiz.getId(), currentAttemptIndex
            );
            StudentLessons.start(CourseDatabase.getInstance().getCourseById(currentCourseId), currentStudentId);
            MainWindow.goTo("studentlessons");
        }
    }
    
    private static void handleFinish() {
        // Check if all required questions are answered
        if (answers.size() < currentQuiz.getQuestions().size()) {
            JOptionPane.showMessageDialog(
                null,
                "You must finish the quiz before clicking Finish. Answer all required questions.",
                "Incomplete Quiz",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Confirm finish
        int result = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to finish your attempt now? You will not be able to change your answers.",
            "Finish Quiz Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Finish and grade attempt
            CourseDatabase courseDb = CourseDatabase.getInstance();
            courseDb.finishQuizAttempt(currentStudentId, currentQuiz.getId(), currentAttemptIndex, answers);
            
            // Refresh lesson from database to get updated attempt data
            Lesson refreshedLesson = courseDb.getLessonById(currentLesson.getId());
            if (refreshedLesson != null) {
                currentLesson = refreshedLesson;
                Progress progress = currentLesson.getStudentProgress().get(currentStudentId);
                if (progress != null && progress.getAttempts() != null && currentAttemptIndex < progress.getAttempts().size()) {
                    currentAttempt = progress.getAttempts().get(currentAttemptIndex);
                    // Ensure answers are loaded from the attempt
                    if (currentAttempt.getQuestionAnswers() != null) {
                        answers = new HashMap<>(currentAttempt.getQuestionAnswers());
                    }
                }
            }
            // Show review mode
            showReview(currentLesson, currentAttempt, currentStudentId, currentCourseId, currentAttemptIndex);
        }
    }
}
