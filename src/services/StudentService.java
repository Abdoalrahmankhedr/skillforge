package services;

import databases.CourseDatabase;
import databases.UserDatabase;
import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Andrew :)

/*
    A service that simplifies the majority of an student's permissions within the project.
    Can be used directly as a static service.
*/

public class StudentService {
    private static final CourseDatabase courseDb = CourseDatabase.getInstance();
    private static final UserDatabase userDb = UserDatabase.getInstance();

    public static Student getStudent(int studentId) {
        User user = userDb.getUserById(studentId);

        // Convert to Student
        Student student = new Student();
        student.setEnrolledCourses(getEnrolledCourses(studentId));

        // Checks for all lessons within all enrolled courses
        // only returns the lessons where the student has either started or completed
        Map<Integer, Progress> progress = new HashMap<>();
        for (Lesson l : getEnrolledLessons(studentId)) {
            if (l.getStudentProgress().getOrDefault(studentId, null) != null) {
                progress.put(l.getId(), l.getStudentProgress().get(studentId));
            }
        }
        student.setLessonProgress(progress);

        student.setId(user.getId());
        student.setCertificates(user.getCertificates());
        student.setEmail(user.getEmail());
        student.setPassword(user.getPassword());
        student.setName(user.getName());
        student.setRole("Student");

        return student;
    }

    public static List<Course> getEnrolledCourses(int studentId) {
        /* Get all courses a given student is enrolled in */
        return courseDb.getRecords().stream()
                .filter((c) -> c.getEnrolledStudents().contains(studentId))
                .collect(Collectors.toList());
    }

    public static List<Lesson> getEnrolledLessons(int studentId) {
        /* Get all lessons that a given student started or completed */
        List<Lesson> lessons = new ArrayList<>();

        for (Course c : getEnrolledCourses(studentId)) {
            lessons.addAll(c.getLessons());
        }

        return lessons;
    }

    public static List<Lesson> getEnrolledLessonsByCourse(int studentId, int courseId) {
        List<Lesson> lessons = new ArrayList<>();

        for (Course c : getEnrolledCourses(studentId)) {
            if (c.getId() == courseId) {
                lessons.addAll(c.getLessons());
            }
        }

        return lessons;
    }

    public static boolean isEnrolled(int studentId, int courseId) {
        Course course = courseDb.getCourseById(courseId);

        if (course != null) {
            for (Integer id : course.getEnrolledStudents()) {
                if (id == studentId) return true;
            }
        }

        return false;
    }

    public static void enroll(int studentId, int courseId) {
        courseDb.enroll(studentId, courseId);
    }

    public static void takeLesson(int studentId, int lessonId) {
        courseDb.startLesson(studentId, lessonId);
    }

    public static void completeLesson(int studentId, int lessonId) {
        courseDb.completeLesson(studentId, lessonId);
    }

    public static void takeAttempt(int studentId, int quizId, int correctQuestions) {
        /* The student has taken the quiz, and has achieved a correct {correctQuestions} number of questions */
        courseDb.addQuizAttempt(studentId, quizId, correctQuestions);
    }
}
