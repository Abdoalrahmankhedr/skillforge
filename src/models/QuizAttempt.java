package models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Andrew :)

public class QuizAttempt {
    private int quizId;
    private int userId;
    private double score;
    private int correctQuestions;
    private int wrongQuestions;
    private boolean passed;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private String status; // "started", "finished", "abandoned"
    private Map<Integer, Integer> questionAnswers = new HashMap<>(); // questionIndex -> choiceIndex

    /* This constructor is necessary for JSON parsing */
    public QuizAttempt() {}

    /* Getters & Setters */
    public int getQuizId() {return quizId;}
    public void setQuizId(int quizId) {this.quizId = quizId;}
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public double getScore() {return score;}
    public void setScore(double score) {this.score = score;}
    public int getCorrectQuestions() {return correctQuestions;}
    public void setCorrectQuestions(int correctQuestions) {this.correctQuestions = correctQuestions;}
    public int getWrongQuestions() {return wrongQuestions;}
    public void setWrongQuestions(int wrongQuestions) {this.wrongQuestions = wrongQuestions;}
    public boolean isPassed() {return passed;}
    public void setPassed(boolean passed) {this.passed = passed;}
    public LocalDateTime getStartTime() {return startTime;}
    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}
    public LocalDateTime getFinishTime() {return finishTime;}
    public void setFinishTime(LocalDateTime finishTime) {this.finishTime = finishTime;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public Map<Integer, Integer> getQuestionAnswers() {return questionAnswers;}
    public void setQuestionAnswers(Map<Integer, Integer> questionAnswers) {this.questionAnswers = questionAnswers;}
    
    public void addAnswer(int questionIndex, int choiceIndex) {
        this.questionAnswers.put(questionIndex, choiceIndex);
    }
}
