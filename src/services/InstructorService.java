package services;

import databases.CourseDatabase;
import databases.UserDatabase;
import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

//Hassan & Andrew :)

/*
    Extended InstructorService with editing & deleting capabilities
*/

public class InstructorService {
    private static final CourseDatabase courseDb = CourseDatabase.getInstance();
    private static final UserDatabase userDb = UserDatabase.getInstance();

    public static Instructor getInstructor(int instructorId) {
        User user = userDb.getUserById(instructorId);
        List<Course> courses = getCourses(instructorId);

        // Convert to Instructor
        Instructor instructor = new Instructor();
        instructor.setId(user.getId());
        instructor.setCourses(courses);
        instructor.setEmail(user.getEmail());
        instructor.setPassword(user.getPassword());
        instructor.setName(user.getName());
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

    public static Question createQuestion(String question, Map<String, Boolean> choices) {
        /* You can add as many choices as needed, each will have a String text and Boolean correct  */
        /* e.g. {
                    "Incorrect Answer": false,
                    "Correct Answer": true
                }
        */
        Question q = new Question();
        q.setHeader(question);

        if (choices.values().stream().filter((c) -> c).count() > 1) {
            System.out.println("[InstructorService]: Question must only have one correct choice");
            return null;
        }

        for (Map.Entry<String, Boolean> entry : choices.entrySet()) {
            q.addChoice(entry.getKey(), entry.getValue());
        }

        return q;
    }

    public static void createQuiz(int lessonId, int retries, int passingQuestions, List<Question> questions) {
        int passing = (passingQuestions <= 0) ? 0 : (Math.min(passingQuestions, questions.size()));
        courseDb.addQuiz(lessonId, retries, ((double) passing / (double) questions.size()), questions);
    }

    public static List<Course> getCourses(int instructorId) {
        return courseDb.getRecords().stream()
                .filter((c) -> c.getInstructorId() == instructorId)
                .collect(Collectors.toList());
    }

    public static List<Lesson> getLessons(int courseId) {
        Course c = courseDb.getCourseById(courseId);
        if (c == null) return new ArrayList<>();
        return c.getLessons();
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

        // Remove possible nulls (if user deleted) and duplicates
        return users.stream()
                .filter(u -> u != null)
                .distinct()
                .collect(Collectors.toList());
    }

    public static void updateQuiz(int quizId, int retries, int passingQuestions, List<Question> questions) {
        int passing = (passingQuestions <= 0) ? 0 : (Math.min(passingQuestions, questions.size()));
        Quiz quiz = courseDb.getQuizById(quizId);

        quiz.setRetries(Math.max(retries, 0));
        quiz.setPassingScore(Math.max(Math.min((((double) passing / (double) questions.size()) * 100), 100d), 0d));
        quiz.setQuestions(questions);

        courseDb.updateQuiz(quiz);
    }

    public static void editCourse(int courseId, String newTitle, List<Lesson> newLessons) {
        Course course = courseDb.getCourseById(courseId);
        if (course == null) {
            System.out.printf("[InstructorService]: Course with ID %d not found.\n", courseId);
            return;
        }

        // Use CourseDatabase's updateCourse which handles deletion+insertion
        courseDb.updateCourse(courseId, newTitle != null ? newTitle : course.getTitle(),
                newLessons != null ? newLessons : course.getLessons());
    }

    public static void deleteCourse(int courseId) {
        courseDb.deleteCourse(courseId);
    }

    public static void editLesson(int lessonId, String newTitle, String newContent) {
        Lesson lesson = courseDb.getLessonById(lessonId);
        if (lesson == null) {
            System.out.printf("[InstructorService]: Lesson with ID %d not found.\n", lessonId);
            return;
        }

        if (newTitle != null) lesson.setTitle(newTitle);
        if (newContent != null) lesson.setContent(newContent);

        courseDb.updateLesson(lesson);
    }

    public static void deleteLesson(int lessonId) {
        courseDb.deleteLesson(lessonId);
    }
    
    public static void enrollStudentToCourse(int studentId, int courseId) {
        courseDb.enroll(studentId, courseId);
    }
}
