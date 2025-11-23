package pages;

import databases.CourseDatabase;
import models.Course;
import models.Lesson;
import models.Question;
import models.Quiz;
import services.InstructorService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditQuizDialog extends JDialog {
    private Lesson lesson;
    private Quiz quiz;
    private Course course;
    private int instructorId;

    private JSpinner retriesSpinner;
    private JSpinner passingScoreSpinner;
    private JPanel questionsPanel;
    private List<QuestionPanel> questionPanels;
    private JScrollPane questionsScrollPane;

    public EditQuizDialog(Frame parent, Lesson lesson, Course course, int instructorId) {
        super(parent, "Edit Quiz", true);
        this.lesson = lesson;
        this.quiz = lesson.getQuiz();
        this.course = course;
        this.instructorId = instructorId;
        this.questionPanels = new ArrayList<>();

        initComponents();
        loadQuizData();
        setResizable(true);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(800, 600);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 153, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel headerLabel = new JLabel("Edit Quiz for: " + lesson.getTitle());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Settings Panel
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Quiz Settings",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14)
        ));
        settingsPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Number of Retries
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel retriesLabel = new JLabel("Number of Attempts:");
        retriesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        settingsPanel.add(retriesLabel, gbc);

        gbc.gridx = 1;
        retriesSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        retriesSpinner.setPreferredSize(new Dimension(100, 30));
        ((JSpinner.DefaultEditor) retriesSpinner.getEditor()).getTextField().setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        settingsPanel.add(retriesSpinner, gbc);

        // Passing Score
        gbc.gridx = 2;
        gbc.insets = new Insets(10, 30, 10, 10);
        JLabel passingLabel = new JLabel("Passing Score (%):");
        passingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        settingsPanel.add(passingLabel, gbc);

        gbc.gridx = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        passingScoreSpinner = new JSpinner(new SpinnerNumberModel(75.0, 0.0, 100.0, 5.0));
        passingScoreSpinner.setPreferredSize(new Dimension(100, 30));
        ((JSpinner.DefaultEditor) passingScoreSpinner.getEditor()).getTextField().setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        settingsPanel.add(passingScoreSpinner, gbc);

        // Delete Quiz Button
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 30, 10, 10);
        JButton deleteQuizBtn = new JButton("Delete Quiz");
        deleteQuizBtn.setBackground(new Color(204, 0, 0));
        deleteQuizBtn.setForeground(Color.WHITE);
        deleteQuizBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteQuizBtn.setFocusPainted(false);
        deleteQuizBtn.addActionListener(e -> deleteQuiz());
        settingsPanel.add(deleteQuizBtn, gbc);

        // Questions Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(settingsPanel, BorderLayout.NORTH);

        // Questions Container
        JPanel questionsContainer = new JPanel(new BorderLayout());
        questionsContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Questions",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14)
        ));
        questionsContainer.setBackground(Color.WHITE);

        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(Color.WHITE);

        questionsScrollPane = new JScrollPane(questionsPanel);
        questionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        questionsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        questionsScrollPane.setBorder(null);

        questionsContainer.add(questionsScrollPane, BorderLayout.CENTER);

        // Add Question Button
        JPanel addQuestionBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addQuestionBtnPanel.setBackground(Color.WHITE);

        JButton addQuestionBtn = new JButton("+ Add Question");
        addQuestionBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addQuestionBtn.setBackground(new Color(0, 102, 204));
        addQuestionBtn.setForeground(Color.WHITE);
        addQuestionBtn.setFocusPainted(false);
        addQuestionBtn.addActionListener(e -> addQuestion(null));

        addQuestionBtnPanel.add(addQuestionBtn);
        questionsContainer.add(addQuestionBtnPanel, BorderLayout.SOUTH);

        mainPanel.add(questionsContainer, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setBackground(new Color(255, 153, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(150, 40));
        saveButton.addActionListener(e -> saveQuiz());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cancelButton.setBackground(new Color(204, 204, 204));
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadQuizData() {
        // Load quiz settings
        retriesSpinner.setValue(quiz.getRetries());
        passingScoreSpinner.setValue(quiz.getPassingScore());

        // Load questions
        for (Question question : quiz.getQuestions()) {
            addQuestion(question);
        }
    }

    private void addQuestion(Question existingQuestion) {
        QuestionPanel qPanel = new QuestionPanel(questionPanels.size() + 1, existingQuestion);
        questionPanels.add(qPanel);
        questionsPanel.add(qPanel);
        questionsPanel.add(Box.createVerticalStrut(10));
        questionsPanel.revalidate();
        questionsPanel.repaint();

        // Scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = questionsScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private void deleteQuiz() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this quiz?\nAll student attempts will be lost!",
                "Confirm Delete Quiz",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                CourseDatabase.getInstance().deleteQuiz(quiz.getId());

                JOptionPane.showMessageDialog(this,
                        "Quiz deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();

                // Refresh the course view
                Course updatedCourse = CourseDatabase.getInstance().getCourseById(course.getId());
                ManageCourseFrame.start(updatedCourse, instructorId);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error deleting quiz: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveQuiz() {
        // Validate
        if (questionPanels.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please add at least one question",
                    "No Questions",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Collect questions
        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < questionPanels.size(); i++) {
            QuestionPanel qPanel = questionPanels.get(i);

            String questionText = qPanel.getQuestionText();
            if (questionText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Question " + (i + 1) + " cannot be empty",
                        "Invalid Question",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Map<String, Boolean> choices = qPanel.getChoices();
            if (choices.size() < 2) {
                JOptionPane.showMessageDialog(this,
                        "Question " + (i + 1) + " must have at least 2 choices",
                        "Invalid Question",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            long correctCount = choices.values().stream().filter(b -> b).count();
            if (correctCount != 1) {
                JOptionPane.showMessageDialog(this,
                        "Question " + (i + 1) + " must have exactly one correct answer",
                        "Invalid Question",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Question question = InstructorService.createQuestion(questionText, choices);
            if (question != null) {
                questions.add(question);
            }
        }

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No valid questions created",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int retries = (Integer) retriesSpinner.getValue();
            double passingScore = (Double) passingScoreSpinner.getValue();

            // Update quiz using InstructorService
            InstructorService.updateQuiz(quiz.getId(), retries, (int)passingScore, questions);

            JOptionPane.showMessageDialog(this,
                    "Quiz updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

            // Refresh the course view
            Course updatedCourse = CourseDatabase.getInstance().getCourseById(course.getId());
            ManageCourseFrame.start(updatedCourse, instructorId);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error updating quiz: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Reuse QuestionPanel class from AddQuizDialog with modification
    private class QuestionPanel extends JPanel {
        private int questionNumber;
        private JTextArea questionTextArea;
        private List<ChoicePanel> choicePanels;
        private JPanel choicesContainer;

        public QuestionPanel(int number, Question existingQuestion) {
            this.questionNumber = number;
            this.choicePanels = new ArrayList<>();

            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 153, 0), 2),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            setBackground(new Color(255, 248, 240));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

            // Header
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setOpaque(false);

            JLabel numberLabel = new JLabel("Question " + questionNumber);
            numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            numberLabel.setForeground(new Color(255, 153, 0));

            JButton deleteBtn = new JButton("✖");
            deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
            deleteBtn.setBackground(new Color(204, 0, 0));
            deleteBtn.setForeground(Color.WHITE);
            deleteBtn.setFocusPainted(false);
            deleteBtn.setPreferredSize(new Dimension(40, 30));
            deleteBtn.addActionListener(e -> deleteQuestion());

            headerPanel.add(numberLabel, BorderLayout.WEST);
            headerPanel.add(deleteBtn, BorderLayout.EAST);

            // Question text
            JPanel questionPanel = new JPanel(new BorderLayout(5, 5));
            questionPanel.setOpaque(false);

            JLabel questionLabel = new JLabel("Question Text:");
            questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            questionTextArea = new JTextArea(3, 40);
            questionTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            questionTextArea.setLineWrap(true);
            questionTextArea.setWrapStyleWord(true);
            if (existingQuestion != null) {
                questionTextArea.setText(existingQuestion.getHeader());
            }
            JScrollPane questionScroll = new JScrollPane(questionTextArea);

            questionPanel.add(questionLabel, BorderLayout.NORTH);
            questionPanel.add(questionScroll, BorderLayout.CENTER);

            // Choices
            JPanel choicesPanel = new JPanel(new BorderLayout(5, 5));
            choicesPanel.setOpaque(false);

            JLabel choicesLabel = new JLabel("Choices (mark one as correct):");
            choicesLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            choicesContainer = new JPanel();
            choicesContainer.setLayout(new BoxLayout(choicesContainer, BoxLayout.Y_AXIS));
            choicesContainer.setOpaque(false);

            // Load existing choices or add defaults
            if (existingQuestion != null && existingQuestion.getChoices() != null) {
                for (Question.Choice choice : existingQuestion.getChoices()) {
                    addChoice(choice);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    addChoice(null);
                }
            }

            JButton addChoiceBtn = new JButton("+ Add Choice");
            addChoiceBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            addChoiceBtn.setBackground(new Color(0, 153, 51));
            addChoiceBtn.setForeground(Color.WHITE);
            addChoiceBtn.setFocusPainted(false);
            addChoiceBtn.addActionListener(e -> addChoice(null));

            choicesPanel.add(choicesLabel, BorderLayout.NORTH);
            choicesPanel.add(choicesContainer, BorderLayout.CENTER);
            choicesPanel.add(addChoiceBtn, BorderLayout.SOUTH);

            // Add all components
            add(headerPanel, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setOpaque(false);
            contentPanel.add(questionPanel);
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(choicesPanel);

            add(contentPanel, BorderLayout.CENTER);
        }

        private void addChoice(Question.Choice existingChoice) {
            ChoicePanel choicePanel = new ChoicePanel(
                    (char)('A' + choicePanels.size()),
                    existingChoice
            );
            choicePanels.add(choicePanel);
            choicesContainer.add(choicePanel);
            choicesContainer.add(Box.createVerticalStrut(5));
            revalidate();
            repaint();
        }

        private void deleteQuestion() {
            int result = JOptionPane.showConfirmDialog(
                    EditQuizDialog.this,
                    "Are you sure you want to delete this question?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                questionPanels.remove(this);
                questionsPanel.remove(this);

                for (int i = 0; i < questionPanels.size(); i++) {
                    questionPanels.get(i).questionNumber = i + 1;
                    questionPanels.get(i).updateNumber();
                }

                questionsPanel.revalidate();
                questionsPanel.repaint();
            }
        }

        private void updateNumber() {
            Component[] components = ((JPanel)getComponent(0)).getComponents();
            if (components.length > 0 && components[0] instanceof JLabel) {
                ((JLabel)components[0]).setText("Question " + questionNumber);
            }
        }

        public String getQuestionText() {
            return questionTextArea.getText().trim();
        }

        public Map<String, Boolean> getChoices() {
            Map<String, Boolean> choices = new HashMap<>();
            for (ChoicePanel cp : choicePanels) {
                String text = cp.getChoiceText();
                if (!text.isEmpty()) {
                    choices.put(text, cp.isCorrect());
                }
            }
            return choices;
        }

        private class ChoicePanel extends JPanel {
            private char letter;
            private JTextField choiceField;
            private JCheckBox correctCheckbox;

            public ChoicePanel(char letter, Question.Choice existingChoice) {
                this.letter = letter;

                setLayout(new BorderLayout(10, 0));
                setOpaque(false);
                setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

                JLabel letterLabel = new JLabel(letter + ")");
                letterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                letterLabel.setPreferredSize(new Dimension(30, 30));

                choiceField = new JTextField();
                choiceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                if (existingChoice != null) {
                    choiceField.setText(existingChoice.getText());
                }

                correctCheckbox = new JCheckBox("Correct");
                correctCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                correctCheckbox.setOpaque(false);
                if (existingChoice != null) {
                    correctCheckbox.setSelected(existingChoice.isCorrect());
                }

                JButton deleteBtn = new JButton("✖");
                deleteBtn.setFont(new Font("Arial", Font.BOLD, 12));
                deleteBtn.setBackground(new Color(204, 0, 0));
                deleteBtn.setForeground(Color.WHITE);
                deleteBtn.setFocusPainted(false);
                deleteBtn.setPreferredSize(new Dimension(30, 30));
                deleteBtn.addActionListener(e -> deleteChoice());

                JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
                rightPanel.setOpaque(false);
                rightPanel.add(correctCheckbox);
                rightPanel.add(deleteBtn);

                add(letterLabel, BorderLayout.WEST);
                add(choiceField, BorderLayout.CENTER);
                add(rightPanel, BorderLayout.EAST);
            }

            private void deleteChoice() {
                if (choicePanels.size() <= 2) {
                    JOptionPane.showMessageDialog(
                            EditQuizDialog.this,
                            "A question must have at least 2 choices",
                            "Cannot Delete",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                choicePanels.remove(this);
                choicesContainer.remove(this);

                for (int i = 0; i < choicePanels.size(); i++) {
                    choicePanels.get(i).letter = (char)('A' + i);
                    choicePanels.get(i).updateLetter();
                }

                choicesContainer.revalidate();
                choicesContainer.repaint();
            }

            private void updateLetter() {
                Component[] components = getComponents();
                if (components.length > 0 && components[0] instanceof JLabel) {
                    ((JLabel)components[0]).setText(letter + ")");
                }
            }

            public String getChoiceText() {
                return choiceField.getText().trim();
            }

            public boolean isCorrect() {
                return correctCheckbox.isSelected();
            }
        }
    }

    public static void showDialog(Lesson lesson, Course course, int instructorId, Frame parent) {
        EditQuizDialog dialog = new EditQuizDialog(parent, lesson, course, instructorId);
        dialog.setVisible(true);
    }
}