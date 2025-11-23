package services;

import databases.CourseDatabase;
import databases.UserDatabase;
import models.Course;
import models.User;

import java.util.List;
import java.util.stream.Collectors;

public class AdminService {

    private static final CourseDatabase courseDb = CourseDatabase.getInstance();
    private static final UserDatabase userDb = UserDatabase.getInstance();

    /** Get Admin Model */
    public static User getAdmin(int id) {
        User user = userDb.getUserById(id);
        return (user.getRole().equalsIgnoreCase("Admin")) ? user : null;
    }

    // Courses Management

    /** Get all pending courses that need admin review */
    public static List<Course> getPendingCourses() {
        return courseDb.getPendingCourses();
    }

    /** Approve a pending course */
    public static void approveCourse(int adminId, int courseId) {
        if (getAdmin(adminId) != null) {
            Course course = courseDb.getCourseById(courseId);
            if (course != null && course.isPending()) {
                course.setApproved(true);
                course.setPending(false);
                course.setApprovedBy(adminId);
                courseDb.updateCourse(course);
            }
        }
    }

    /** Reject a pending course */
    public static void rejectCourse(int adminId, int courseId) {
        if (getAdmin(adminId) != null) {
            Course course = courseDb.getCourseById(courseId);
            if (course != null && course.isPending()) {
                course.setApproved(false);
                course.setPending(false);
                course.setApprovedBy(adminId);
                courseDb.updateCourse(course);
            }
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

    /** Get course by ID */
    public Course getCourseById(int courseId) {
        return courseDb.getCourseById(courseId);
    }
}
