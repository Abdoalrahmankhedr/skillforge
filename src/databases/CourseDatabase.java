package databases;

import models.Course;
import models.Lesson;

import java.util.List;

// Andrew :)

/*
    Please refer to the documentation of Database<T> for detailed information.


*/

public class CourseDatabase extends Database<Course> {
    private int lessonIndex = 1;
    private static CourseDatabase instance;

    private CourseDatabase(String filename) {
        super(filename, Course.class);

        /* Determines the most recent ID for all lessons */
        for (Course c : this.records) {
            for (Lesson l : c.getLessons()) {
                this.lessonIndex = Math.max(l.getId(), this.lessonIndex);
            }
        }
    }

    public static CourseDatabase getInstance() {
        /* To ensure the database would stay consistent on data change */
        if (instance == null) {
            instance = new CourseDatabase("src/resources/courses.json");
        }
        return instance;
    }

    public void deleteCourse(int id) {deleteRecord(id);}
    public void deleteLesson(int id) {
        for (Course c : getRecords()) {
            c.getLessons().removeIf((t) -> t.getId() == id);
        }
    }


    public Course getCourseById(int id) {return getRecordById(id);}

    public Course getCourseByLesson(int lessonId) {
        for (Course c : getRecords()) {
            for (Lesson l : c.getLessons()) {
                if (l.getId() == lessonId) return c;
            }
        }
        return null;
    }

    public Lesson getLessonById(int id) {
        for (Course c : getRecords()) {
            for (Lesson l : c.getLessons()) {
                if (l.getId() == id) return l;
            }
        }
        return null;
    }

    public void addCourse(String title, int instructorId, List<Lesson> lessons) {
        Course course = new Course();
        course.setId(++this.index);
        course.setLessons(lessons);
        course.setTitle(title);
        course.setInstructorId(instructorId);

        insertRecord(course);
    }

    public void addLesson(int courseId, String title, String content) {
        /* Insert new lesson into an existing course */
        Lesson lesson = new Lesson();
        lesson.setContent(content);
        lesson.setTitle(title);
        lesson.setId(++this.lessonIndex);

        Course course = getRecordById(courseId);
        List<Lesson> newLessons = course.getLessons();
        newLessons.add(lesson);

        updateCourse(courseId, course.getTitle(), newLessons);
    }

    public void addLesson(int courseId, Lesson lesson) {
        /* Insert new lesson into an existing course */
        Course course = getRecordById(courseId);
        List<Lesson> newLessons = course.getLessons();
        newLessons.add(lesson);

        updateCourse(courseId, course.getTitle(), newLessons);
    }

    public void updateCourse(int id, String title, List<Lesson> lessons) {
        Course course = getRecordById(id);
        if (course == null) {
            System.out.printf("[%s]: User attempting to update doesn't exist [ID] %d\n", logName, id);
            return;
        }

        /* Change filters */
        if (!course.getTitle().equals(title)) course.setTitle(title);
        if (lessons != null && !lessons.equals(course.getLessons())) course.setLessons(lessons);

        /* Remove the old one */
        deleteRecord(id);
        /* Add the changed one */
        insertRecord(course);
    }

    public void updateCourse(Course course) {
        if (getCourseById(course.getId()) != null) deleteCourse(course.getId());
        insertRecord(course);
        saveToFile();
    }

    public void updateLesson(Lesson lesson) {
        Course course = getCourseByLesson(lesson.getId());
        deleteLesson(lesson.getId());
        addLesson(course.getId(), lesson);
    }

    public void startLesson(int studentId, int lessonId) {
        Lesson lesson = getLessonById(lessonId);
        lesson.addStudent(studentId);
        updateLesson(lesson);
    }

    public void completeLesson(int studentId, int lessonId) {
        Lesson lesson = getLessonById(lessonId);
        lesson.markAsComplete(studentId);
        updateLesson(lesson);
    }

    public void enroll(int studentId, int courseId) {
        Course course = getRecordById(courseId);
        course.enrollStudent(studentId);
        updateCourse(course);
    }
}
