package models;

import java.util.ArrayList;
import java.util.List;

// Andrew :)

public class Instructor extends User {
    private List<Course> courses = new ArrayList<>();

    /* This constructor is necessary for JSON parsing */
    public Instructor() {}

    /* Getters & Setters */
    public List<Course> getCourses() {return courses;}
    public void setCourses(List<Course> courses) {this.courses = courses;}
}
