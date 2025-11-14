package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Andrew :)

public class Student extends User {
    /* This represents course enrolled by their IDs to separate it between the 2 different databases */
    private List<Course> enrolledCourses = new ArrayList<>();
    /* Since not all lessons are necessarily from the same course, it is using a separate lesson ID to track progress */
    private Map<Integer, Boolean> lessonProgress = new HashMap<>();

    /* This constructor is necessary for JSON parsing */
    public Student() {}

    /* Getters & Setters */
    public Map<Integer, Boolean> getLessonProgress() {return lessonProgress;}
    public void setLessonProgress(Map<Integer, Boolean> lessonProgress) {this.lessonProgress = lessonProgress;}
    public List<Course> getEnrolledCourses() {return enrolledCourses;}
    public void setEnrolledCourses(List<Course> enrolledCourses) {this.enrolledCourses = enrolledCourses;}
}