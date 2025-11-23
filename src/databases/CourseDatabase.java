package databases;

import models.*;
import services.CourseService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Andrew :)

/*
    Please refer to the documentation of Database<T> for detailed information.


*/

public class CourseDatabase extends Database<Course> {
    private int lessonIndex = 1;
    private int quizIndex = 1;
    private static CourseDatabase instance;

    private CourseDatabase(String filename) {
        super(filename, Course.class);

        /* Determines the most recent ID for all lessons & quizzes */
        for (Course c : this.records) {
            for (Lesson l : c.getLessons()) {
                this.lessonIndex = Math.max(l.getId(), this.lessonIndex);
                if(l.getQuiz()!=null){
                    this.quizIndex = Math.max(l.getQuiz().getId(), this.quizIndex);
                }
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

    public List<Course> getApprovedCourses() {
        return getRecords().stream()
                .filter(Course::isApproved)
                .collect(Collectors.toList());
    }

    public List<Course> getPendingCourses() {
        return getRecords().stream()
                .filter(Course::isPending)
                .collect(Collectors.toList());
    }

    public void deleteCourse(int id) {deleteRecord(id);}

    public void deleteLesson(int id) {
        for (Course c : getRecords()) {
            c.getLessons().removeIf((t) -> t.getId() == id);
        }
    }

    public void deleteQuiz(int id) {
        for (Course c : getRecords()) {
            for (Lesson l : c.getLessons()) {
                if (l.getQuiz() != null && l.getQuiz().getId() == id) l.setQuiz(null);
            }
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

    public Quiz getQuizById(int id) {
        for (Course c : getRecords()) {
            for (Lesson l : c.getLessons()) {
                if (l.getQuiz() != null && l.getQuiz().getId() == id) return l.getQuiz();
            }
        }
        return null;
    }

    public Lesson getLessonByQuiz(int quizId) {
        for (Course c : getRecords()) {
            for (Lesson l : c.getLessons()) {
                if (l.getQuiz() != null && l.getQuiz().getId() == quizId) return l;
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

    public void addQuiz(int lessonId, int retries, double passingScore, List<Question> questions) {
        Lesson lesson = getLessonById(lessonId);
        Quiz quiz = new Quiz();
        quiz.setId(++this.quizIndex);
        quiz.setQuestions(questions);
        quiz.setRetries(Math.max(retries, 0));
        quiz.setPassingScore(Math.max(Math.min(passingScore, 100d), 0d));

        lesson.setQuiz(quiz);
        updateLesson(lesson);
    }

    public void addQuizAttempt(int studentId, int quizId, int correctQuestions) {
        Lesson lesson = getLessonByQuiz(quizId);
        if (lesson == null) {
            return; // Lesson not found
        }

        Quiz quiz = lesson.getQuiz();
        if (quiz == null) {
            return; // Lesson doesn't have a quiz
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuizId(quizId);
        attempt.setUserId(studentId);
        attempt.setCorrectQuestions(Math.max(correctQuestions, 0));
        attempt.setScore(((double) correctQuestions / (double) quiz.getQuestions().size()) * 100);
        attempt.setPassed(attempt.getScore() >= quiz.getPassingScore());

        lesson.addAttempt(attempt);
        updateLesson(lesson);
    }

    public QuizAttempt startQuizAttempt(int studentId, int quizId) {
        Lesson lesson = getLessonByQuiz(quizId);
        if (lesson == null) {
            return null; // Lesson not found
        }

        Quiz quiz = lesson.getQuiz();
        if (quiz == null) {
            return null; // Lesson doesn't have a quiz
        }

        // Check if student has attempts remaining
        Progress progress = lesson.getStudentProgress().getOrDefault(studentId, null);
        if (progress == null) {
            lesson.addStudent(studentId);
            progress = lesson.getStudentProgress().get(studentId);
        }

        if (progress.getAttempts() == null) {
            progress.setAttempts(new java.util.ArrayList<>());
        }

        if (progress.getAttempts().size() >= quiz.getRetries()) {
            return null; // No attempts remaining
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuizId(quizId);
        attempt.setUserId(studentId);
        attempt.setStartTime(java.time.LocalDateTime.now());
        attempt.setStatus("started");
        attempt.setScore(0);
        attempt.setCorrectQuestions(0);
        attempt.setWrongQuestions(0);
        attempt.setPassed(false);

        lesson.addAttempt(attempt);
        updateLesson(lesson);
        return attempt;
    }

    public void updateQuizAttemptAnswers(int studentId, int quizId, int attemptIndex, Map<Integer, Integer> answers) {
        Lesson lesson = getLessonByQuiz(quizId);
        if (lesson == null) {
            return; // Lesson not found
        }

        Progress progress = lesson.getStudentProgress().get(studentId);
        if (progress != null && progress.getAttempts() != null && attemptIndex < progress.getAttempts().size()) {
            QuizAttempt attempt = progress.getAttempts().get(attemptIndex);
            attempt.setQuestionAnswers(answers);
            updateLesson(lesson);
        }
    }

    public void finishQuizAttempt(int studentId, int quizId, int attemptIndex, Map<Integer, Integer> answers) {
        Lesson lesson = getLessonByQuiz(quizId);
        if (lesson == null) {
            return; // Lesson not found
        }

        Quiz quiz = lesson.getQuiz();
        if (quiz == null) {
            return; // Lesson doesn't have a quiz
        }

        Progress progress = lesson.getStudentProgress().get(studentId);

        if (progress != null && attemptIndex < progress.getAttempts().size()) {
            QuizAttempt attempt = progress.getAttempts().get(attemptIndex);
            attempt.setQuestionAnswers(answers);
            attempt.setFinishTime(java.time.LocalDateTime.now());
            attempt.setStatus("finished");

            // Grade the attempt
            int correctCount = 0;
            int wrongCount = 0;
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                Question question = quiz.getQuestions().get(i);
                Integer studentAnswer = answers.get(i);

                if (studentAnswer != null) {
                    boolean isCorrect = false;
                    int correctChoiceIndex = -1;
                    for (int j = 0; j < question.getChoices().size(); j++) {
                        if (question.getChoices().get(j).isCorrect()) {
                            correctChoiceIndex = j;
                            break;
                        }
                    }

                    if (studentAnswer == correctChoiceIndex) {
                        isCorrect = true;
                        correctCount++;
                    } else {
                        wrongCount++;
                    }
                } else {
                    wrongCount++; // Unanswered questions are wrong
                }
            }

            attempt.setCorrectQuestions(correctCount);
            attempt.setWrongQuestions(wrongCount);
            attempt.setScore(((double) correctCount / (double) quiz.getQuestions().size()) * 100);
            attempt.setPassed(attempt.getScore() >= quiz.getPassingScore());

            // Re-evaluate lesson completion - mark as complete only if at least one attempt passed
            lesson.markAsComplete(studentId);

            updateLesson(lesson);

            // Check if course is now complete and issue certificate if needed
            Course course = getCourseByLesson(lesson.getId());
            if (course != null) {
                CourseService courseService = new CourseService();
                if (CourseService.isComplete(course.getId(), studentId)) {
                    // Course is complete - issue certificate if not already issued
                    Certificate cert = courseService.getCertificate(course.getId(), studentId);
                    if (cert == null) {
                        courseService.createCertificate(course.getId(), studentId);
                    }
                }
            }
        }
    }

    public void abandonQuizAttempt(int studentId, int quizId, int attemptIndex) {
        Lesson lesson = getLessonByQuiz(quizId);
        if (lesson == null) {
            return; // Lesson not found
        }

        Progress progress = lesson.getStudentProgress().get(studentId);

        if (progress != null && progress.getAttempts() != null && attemptIndex < progress.getAttempts().size()) {
            QuizAttempt attempt = progress.getAttempts().get(attemptIndex);
            attempt.setFinishTime(java.time.LocalDateTime.now());
            attempt.setStatus("abandoned");
            updateLesson(lesson);
        }
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

    public void updateQuiz(Quiz quiz) {
        Lesson lesson = getLessonByQuiz(quiz.getId());
        lesson.setQuiz(quiz);
        updateLesson(lesson);
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
