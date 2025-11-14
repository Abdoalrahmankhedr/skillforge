package models;

import java.util.HashMap;
import java.util.Map;

public class Lesson implements Model {
    private int id;
    private String title;
    private String content;
    private Map<Integer, Boolean> studentProgress = new HashMap<>();

    /* This constructor is necessary for JSON parsing */
    public Lesson() {}

    public void addStudent(int studentId) {
        this.studentProgress.put(studentId, false);
    }

    public void markAsComplete(int studentId) {
        this.studentProgress.put(studentId, true);
    }

    /* Getters & Setters */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
    public Map<Integer, Boolean> getStudentProgress() {return studentProgress;}
    public void setStudentProgress(Map<Integer, Boolean> studentProgress) {this.studentProgress = studentProgress;}
}
