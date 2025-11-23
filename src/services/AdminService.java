package services;

import databases.CourseDatabase;
import models.Course;

import java.util.List;
import java.util.stream.Collectors;

public class AdminService {

    private static CourseDatabase courseDb = CourseDatabase.getInstance();

    // Courses Management

    /** Get all pending courses that need admin review */
    public static List<Course> getPendingCourses() {
        return courseDb.getPendingCourses();
    }

    /** Approve a pending course with dynamic admin ID */
    public static void approveCourse(int courseId, int adminId) {
        Course course = courseDb.getCourseById(courseId);
        if (course != null && course.isPending()) {
            course.setApproved(true);
            course.setPending(false);
            course.setApprovedBy(adminId);
            courseDb.updateCourse(course);
        }
    }

    /** Reject a pending course with dynamic admin ID */
    public static void rejectCourse(int courseId, int adminId) {
        Course course = courseDb.getCourseById(courseId);
        if (course != null && course.isPending()) {
            course.setApproved(false);
            course.setPending(false);
            course.setApprovedBy(adminId);
            courseDb.updateCourse(course);
        }
    }

    /** Get all approved courses */
    public static List<Course> getApprovedCourses() {
        return courseDb.getApprovedCourses();
    }

    /** Get all rejected courses */
    public static List<Course> getRejectedCourses() {
        return courseDb.getRecords().stream()
                .filter(c -> !c.isApproved() && !c.isPending())
                .collect(Collectors.toList());
    }

    /** Delete a course */
    public void deleteCourse(int courseId) {
        courseDb.deleteCourse(courseId);
    }

    /** Get course by ID */
    public Course getCourseById(int courseId) {
        return courseDb.getCourseById(courseId);
    }
}
