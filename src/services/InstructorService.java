package services;

import databases.CourseDatabase;
import databases.UserDatabase;
import models.Course;
import models.Instructor;
import models.Lesson;
import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Andrew :)

/*
    A service that simplifies the majority of an instructor's permissions within the project.
    Can be used directly as a static service.
*/

public class InstructorService {
    private static final CourseDatabase courseDb = new CourseDatabase("src/resources/courses.json");
    private static final UserDatabase userDb = new UserDatabase("src/resources/users.json");

    public static Instructor getInstructor(int instructorId) {
        User user = userDb.getUserById(instructorId);
        List<Course> courses = getCourses(instructorId);

        // Convert to Instructor
        Instructor instructor = new Instructor();
        instructor.setId(user.getId());
        instructor.setCourses(courses);
        instructor.setEmail(user.getEmail());
        instructor.setPassword(user.getPassword());
        instructor.setRole("Instructor");

        return instructor;
    }

    public static void createCourse(int instructorId, String courseTitle, List<Lesson> lessons) {
        courseDb.addCourse(courseTitle, instructorId, lessons);
    }

    public static void createCourse(Instructor instructor, Course course) {
        courseDb.addCourse(course.getTitle(), instructor.getId(), course.getLessons());
    }

    public static void createLesson(int courseId, String title, String content) {
        courseDb.addLesson(courseId, title, content);
    }

    public static List<Course> getCourses(int instructorId) {
        return courseDb.getRecords().stream()
                .filter((c) -> c.getInstructorId() == instructorId)
                .collect(Collectors.toList());
    }

    public static List<Lesson> getLessons(int courseId) {
        return courseDb.getCourseById(courseId).getLessons();
    }

    public static List<User> getEnrolledStudents(int instructorId) {
        Instructor instructor = getInstructor(instructorId);
        List<User> users = new ArrayList<>();

        for (Course c : instructor.getCourses()) {
            users.addAll(c.getEnrolledStudents().stream()
                    .map(userDb::getUserById)
                    .collect(Collectors.toList())
            );
        }

        return users;
    }
}
