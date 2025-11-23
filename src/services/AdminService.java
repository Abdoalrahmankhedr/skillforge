package services;

import databases.CourseDatabase;
import models.Course;

import java.util.List;
import java.util.stream.Collectors;

public class AdminService {

    private static final CourseDatabase courseDb = CourseDatabase.getInstance();
    private static final int adminId = 1; // SuperAdmin ID

    /** Get all pending courses */
    public static List<Course> getPendingCourses() {
        return courseDb.getPendingCourses();
    }

    /** Approve a pending course */
    public static void approveCourse(int courseId) {
        Course course = courseDb.getCourseById(courseId);
        if (course != null && course.isPending()) {
            course.setApproved(true);
            course.setPending(false);
            course.setApprovedBy(adminId);
            courseDb.updateCourse(course);
        }
    }

    /** Reject a pending course */
    public static void rejectCourse(int courseId) {
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
    public static void deleteCourse(int courseId) {
        courseDb.deleteCourse(courseId);
    }

    /** Get course by ID */
    public static Course getCourseById(int courseId) {
        return courseDb.getCourseById(courseId);
    }
}
