package models;

// Andrew :)

import java.util.ArrayList;
import java.util.List;

public class Progress {
    private int userId;
    private boolean lessonComplete = false;
    private List<QuizAttempt> attempts = new ArrayList<>();

    /* This constructor is necessary for JSON parsing */
    public Progress() {}

    /* Getters & Setters */
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public boolean isLessonComplete() {return lessonComplete;}
    public void setLessonComplete(boolean lessonComplete) {this.lessonComplete = lessonComplete;}
    public List<QuizAttempt> getAttempts() {return attempts;}
    public void setAttempts(List<QuizAttempt> attempts) {this.attempts = attempts;}
}
