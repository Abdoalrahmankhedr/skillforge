package services;

import databases.CourseDatabase;
import models.*;

import java.util.*;

public class AnalyticsService {
    private static final CourseDatabase courseDb = CourseDatabase.getInstance();

    /**
     * Get average quiz score for each lesson in instructor's courses
     */
    public static Map<String, Double> getQuizAveragesByLesson(int instructorId) {
        Map<String, Double> averages = new LinkedHashMap<>();
        List<Course> courses = InstructorService.getCourses(instructorId);

        for (Course course : courses) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson.getQuiz() != null) {
                    double avg = calculateLessonQuizAverage(lesson);
                    String key = course.getTitle() + " - " + lesson.getTitle();
                    if (key.length() > 30) key = key.substring(0, 27) + "...";
                    averages.put(key, avg);
                }
            }
        }

        return averages;
    }

    /**
     * Calculate average quiz score for a specific lesson
     */
    private static double calculateLessonQuizAverage(Lesson lesson) {
        Map<Integer, Progress> progressMap = lesson.getStudentProgress();
        if (progressMap.isEmpty()) return 0.0;

        List<Double> allScores = new ArrayList<>();

        for (Progress progress : progressMap.values()) {
            if (progress.getAttempts() != null) {
                for (QuizAttempt attempt : progress.getAttempts()) {
                    if ("finished".equals(attempt.getStatus())) {
                        allScores.add(attempt.getScore());
                    }
                }
            }
        }

        if (allScores.isEmpty()) return 0.0;

        return allScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Get completion percentage for each course
     */
    public static Map<String, Double> getCourseCompletionRates(int instructorId) {
        Map<String, Double> completionRates = new LinkedHashMap<>();
        List<Course> courses = InstructorService.getCourses(instructorId);

        for (Course course : courses) {
            double rate = calculateCourseCompletionRate(course);
            String title = course.getTitle();
            if (title.length() > 25) title = title.substring(0, 22) + "...";
            completionRates.put(title, rate);
        }

        return completionRates;
    }

    /**
     * Calculate completion rate for a specific course
     */
    private static double calculateCourseCompletionRate(Course course) {
        List<Integer> enrolledStudents = course.getEnrolledStudents();
        if (enrolledStudents.isEmpty()) return 0.0;

        int completedCount = 0;

        for (Integer studentId : enrolledStudents) {
            if (CourseService.isComplete(course.getId(), studentId)) {
                completedCount++;
            }
        }

        return (completedCount * 100.0) / enrolledStudents.size();
    }

    /**
     * Get student performance data (average scores per student)
     */
    public static Map<String, Double> getStudentPerformance(int instructorId) {
        Map<String, Double> performance = new LinkedHashMap<>();
        List<User> students = InstructorService.getEnrolledStudents(instructorId);

        for (User student : students) {
            double avgScore = calculateStudentAverageScore(student.getId(), instructorId);
            String name = student.getName();
            if (name.length() > 20) name = name.substring(0, 17) + "...";
            performance.put(name, avgScore);
        }

        return performance;
    }

    /**
     * Calculate average score for a student across all instructor's courses
     */
    private static double calculateStudentAverageScore(int studentId, int instructorId) {
        List<Course> courses = InstructorService.getCourses(instructorId);
        List<Double> allScores = new ArrayList<>();

        for (Course course : courses) {
            if (!course.getEnrolledStudents().contains(studentId)) continue;

            for (Lesson lesson : course.getLessons()) {
                Progress progress = lesson.getStudentProgress().get(studentId);
                if (progress != null && progress.getAttempts() != null) {
                    for (QuizAttempt attempt : progress.getAttempts()) {
                        if ("finished".equals(attempt.getStatus())) {
                            allScores.add(attempt.getScore());
                        }
                    }
                }
            }
        }

        if (allScores.isEmpty()) return 0.0;

        return allScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}