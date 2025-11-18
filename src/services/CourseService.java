package services;

import databases.CourseDatabase;
import databases.UserDatabase;
import models.Certificate;
import models.Lesson;
import models.Student;

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
        List<Lesson> enrolledLessons = StudentService.getEnrolledLessonsByCourse(studentId, courseId);
        List<Lesson> courseLessons = courseDb.getCourseById(courseId).getLessons();

        // This means the student at least took all lessons
        if (courseLessons.isEmpty() || courseLessons.size() != enrolledLessons.size()) return false;

        // This logic will change if studentProgress changes format
        for (Lesson l : enrolledLessons) {
            if ((!l.getStudentProgress().containsKey(studentId)) ||
                (!l.getStudentProgress().get(studentId))     // Student didn't lesson or it is incomplete
            ){
                return false;
            }
        }

        return true;
    }

    public Certificate getCertificate(int courseId, int studentId) {
        Student student = StudentService.getStudent(studentId);

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
