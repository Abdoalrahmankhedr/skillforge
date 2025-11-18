package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Andrew :)

public class Course implements Model {
    private int id;
    private String title;
    private int instructorId;
    private Integer approvedBy = null;
    private boolean pending = true;
    private boolean approved = false;
    private List<Lesson> lessons = new ArrayList<>();
    private List<Integer> enrolledStudents = new ArrayList<>();

    /* This constructor is necessary for JSON parsing */
    public Course() {}

    public void addLesson(Lesson lesson) {
        /* Instead of rewriting list of lessons using setLessons() */
        this.lessons.add(lesson);
    }

    public void enrollStudent(int studentId) {
        /* Instead of rewriting list of students using setEnrolledStudents() */
        this.enrolledStudents.add(studentId);
    }

    /* Getters & Setters */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public List<Lesson> getLessons() {
        lessons.sort(Comparator.comparingInt(Lesson::getId));
        return lessons;
    }
    public void setLessons(List<Lesson> lessons) {this.lessons = lessons;}
    public int getInstructorId() {return instructorId;}
    public void setInstructorId(int instructorId) {this.instructorId = instructorId;}
    public List<Integer> getEnrolledStudents() {return enrolledStudents;}
    public void setEnrolledStudents(List<Integer> enrolledStudents) {this.enrolledStudents = enrolledStudents;}
    public Integer getApprovedBy() {return approvedBy;}
    public void setApprovedBy(Integer approvedBy) {this.approvedBy = approvedBy;}
    public boolean isPending() {return pending;}
    public void setPending(boolean pending) {this.pending = pending;}
    public boolean isApproved() {return approved;}
    public void setApproved(boolean approved) {this.approved = approved;}
}
