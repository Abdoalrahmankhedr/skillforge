package models;

import java.time.LocalDate;

// Andrew :)

public class Certificate {
    private int id;
    private int courseId;
    private int userId;
    private LocalDate issued;

    /* This constructor is necessary for JSON parsing */
    public Certificate() {}

    /* Getters & Setters */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getCourseId() {return courseId;}
    public void setCourseId(int courseId) {this.courseId = courseId;}
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public LocalDate getIssued() {return issued;}
    public void setIssued(LocalDate issued) {this.issued = issued;}
}
