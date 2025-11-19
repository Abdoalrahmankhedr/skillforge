package models;

// Andrew :)

public class QuizAttempt {
    private int quizId;
    private int userId;
    private double score;
    private int correctQuestions;
    private boolean passed;

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
    public boolean isPassed() {return passed;}
    public void setPassed(boolean passed) {this.passed = passed;}
}
