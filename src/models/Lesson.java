package models;

import java.util.HashMap;
import java.util.Map;

// Andrew :)

public class Lesson implements Model {
    private int id;
    private String title;
    private String content;
    private Map<Integer, Progress> studentProgress = new HashMap<>();
    private Quiz quiz;

    /* This constructor is necessary for JSON parsing */
    public Lesson() {}

    public void addStudent(int studentId) {
        Progress progress = new Progress();
        progress.setUserId(studentId);
        this.studentProgress.put(studentId, progress);
    }

    public void addAttempt(QuizAttempt attempt) {
        if (this.studentProgress.getOrDefault(attempt.getUserId(), null) == null) {
            Progress progress = new Progress();
            progress.setUserId(attempt.getUserId());
            progress.addAttempt(attempt);
        } else {
            Progress progress = this.studentProgress.get(attempt.getUserId());
            if (progress.getAttempts().size() < this.quiz.getRetries()) {
                progress.addAttempt(attempt);
            }
        }
    }

    public void markAsComplete(int studentId) {
        if (quiz != null) {
            // There must be a quiz to allow completing a lesson
            int policy = quiz.getRetries();
            Progress progress = this.studentProgress.getOrDefault(studentId, null);

            if ((progress != null) &&
                (progress.getAttempts().size() <= policy) &&
                (progress.getAttempts().stream().anyMatch(QuizAttempt::isPassed))
            ) {
                // Only if the student has progress, and it is within retry policy
                // and that at least one of the attempts has passed
                progress.setLessonComplete(true);
            }
        }
    }

    /* Getters & Setters */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
    public Map<Integer, Progress> getStudentProgress() {return studentProgress;}
    public void setStudentProgress(Map<Integer, Progress> studentProgress) {this.studentProgress = studentProgress;}
    public Quiz getQuiz() {return quiz;}
    public void setQuiz(Quiz quiz) {this.quiz = quiz;}
}
