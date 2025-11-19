package models;

// Andrew :)

import java.util.ArrayList;
import java.util.List;

public class Quiz implements Model {
    private int id;
    private int retries;
    private double passingScore;
    private List<Question> questions = new ArrayList<>();

    /* This constructor is necessary for JSON parsing */
    public Quiz() {}

    /* Getters & Setters */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getRetries() {return retries;}
    public void setRetries(int retries) {this.retries = retries;}
    public double getPassingScore() {return passingScore;}
    public void setPassingScore(double passingScore) {this.passingScore = passingScore;}
    public List<Question> getQuestions() {return questions;}
    public void setQuestions(List<Question> questions) {this.questions = questions;}
}
