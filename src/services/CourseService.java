package services;

import databases.CourseDatabase;
import databases.UserDatabase;
import models.*;

import java.util.List;

// Andrew :)

/*
    This static service provides these basic methods:
        - check if a student has completed a specific course: use isComplete()
        - get a certificate for a specific course for a student: use getCertificate()
        - issue a certificate to a student: use createCertificate()
*/

public class CourseService {
    private static final CourseDatabase courseDb = CourseDatabase.getInstance();
    private static final UserDatabase userDb = UserDatabase.getInstance();

    public static boolean isComplete(int courseId, int studentId) {
        Course course = courseDb.getCourseById(courseId);
        if (course == null) {
            return false;
        }

        List<Lesson> courseLessons = course.getLessons();
        if (courseLessons.isEmpty()) {
            return false;
        }

        // Check each lesson in the course
        for (Lesson l : courseLessons) {
            Progress progress = l.getStudentProgress().getOrDefault(studentId, null);

            if (progress == null) {
                return false; // Student hasn't started this lesson
            }

            // For quiz-based lessons, check if student has passed at least one attempt
            if (l.getQuiz() != null) {
                if (progress.getAttempts() == null || progress.getAttempts().isEmpty()) {
                    return false; // No attempts made yet
                }
                // Lesson is complete only if at least one attempt passed
                boolean hasPassedAttempt = progress.getAttempts().stream()
                        .anyMatch(QuizAttempt::isPassed);
                if (!hasPassedAttempt) {
                    return false; // No passed attempts
                }
            } else {
                // For non-quiz lessons, check if lesson is marked complete
                if (!progress.isLessonComplete()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Certificate getCertificate(int courseId, int studentId) {
        Student student = StudentService.getStudent(studentId);

        if (student == null || student.getCertificates() == null) {
            return null;
        }

        for (Certificate c : student.getCertificates()) {
            if (c.getCourseId() == courseId) {
                return c;
            }
        }

        return null;
    }

    public void createCertificate(int courseId, int studentId) {
        userDb.issueCertificate(studentId, courseId);
    }
}
