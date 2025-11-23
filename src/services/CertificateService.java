package services;

import databases.CourseDatabase;
import databases.UserDatabase;
import models.*;
import services.CourseService;
import services.InstructorService;
import services.StudentService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CertificateService {
    private static final CourseDatabase courseDb = CourseDatabase.getInstance();

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    public static String generateCertificatePDF(int studentId, int courseId) {
        Student student = StudentService.getStudent(studentId);
        Course course = courseDb.getCourseById(courseId);
        CourseService courseService = new CourseService();
        Certificate certificate = courseService.getCertificate(courseId, studentId);

        if (student == null || course == null) {
            return null;
        }

        // If certificate doesn't exist but course is complete, create it
        if (certificate == null && CourseService.isComplete(courseId, studentId)) {
            courseService.createCertificate(courseId, studentId);
            // Refresh student to get updated certificate
            student = StudentService.getStudent(studentId);
            certificate = courseService.getCertificate(courseId, studentId);
        }

        if (certificate == null) {
            return null;
        }

        // Create certificates directory if it doesn't exist
        File certDir = new File("certificates");
        if (!certDir.exists()) {
            certDir.mkdirs();
        }

        // Get the last quiz attempt date from the last lesson with a quiz
        LocalDateTime lastAttemptDate = getLastQuizAttemptDate(course, studentId);
        if (lastAttemptDate == null) {
            // Fallback to certificate issue date if no quiz attempt found
            lastAttemptDate = certificate.getIssued().atStartOfDay();
        }

        // Get instructor name
        String instructorName = InstructorService.getInstructor(course.getInstructorId()).getName();

        // Generate safe filename
        String safeStudentName = sanitizeFilename(student.getName());
        String safeCourseTitle = sanitizeFilename(course.getTitle());
        String issueDate = lastAttemptDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String certCode = generateCertificateCode(certificate.getId(), studentId, courseId);

        String filename = String.format("certificate_%s_%s_%s_%s.pdf",
                safeStudentName, safeCourseTitle, issueDate, certCode);
        String filepath = new File(certDir, filename).getAbsolutePath();

        try (PDDocument document = new PDDocument()) {
            // Create landscape page (11x8.5 inches)
            PDPage page = new PDPage(new PDRectangle(792, 612)); // 11x8.5 inches at 72 DPI
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Colorful gradient background (blue to purple)
                for (int y = 0; y < 612; y++) {
                    float ratio = (float) y / 612;
                    int r = (int) (135 + ratio * (75 - 135)); // 135 to 75
                    int g = (int) (206 + ratio * (0 - 206)); // 206 to 0
                    int b = (int) (250 + ratio * (130 - 250)); // 250 to 130
                    contentStream.setNonStrokingColor(new Color(r, g, b));
                    contentStream.addRect(0, y, 792, 1);
                    contentStream.fill();
                }

                // White content box with rounded appearance
                contentStream.setNonStrokingColor(Color.WHITE);
                contentStream.addRect(100, 200, 592, 212);
                contentStream.fill();

                // Border for content box
                contentStream.setStrokingColor(new Color(70, 130, 180));
                contentStream.setLineWidth(3);
                contentStream.addRect(100, 200, 592, 212);
                contentStream.stroke();

                // Title
                contentStream.setNonStrokingColor(new Color(25, 25, 112));
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                String titleText = "Certificate of Completion";
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(titleText) / 1000 * 24;
                contentStream.newLineAtOffset((792 - titleWidth) / 2, 360);
                contentStream.showText(titleText);
                contentStream.endText();

                // Certificate ID
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                String certIdLabel = "Certificate ID:";
                contentStream.newLineAtOffset(150, 330);
                contentStream.showText(certIdLabel);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                String certIdValue = String.valueOf(certificate.getId());
                contentStream.newLineAtOffset(300, 330);
                contentStream.showText(certIdValue);
                contentStream.endText();

                // Course ID
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                String courseIdLabel = "Course:";
                contentStream.newLineAtOffset(150, 300);
                contentStream.showText(courseIdLabel);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                String courseIdValue = String.valueOf(
                        CourseDatabase.getInstance().getCourseById(certificate.getCourseId()).getTitle()
                );
                contentStream.newLineAtOffset(300, 300);
                contentStream.showText(courseIdValue);
                contentStream.endText();

                // User ID
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                String userIdLabel = "User:";
                contentStream.newLineAtOffset(150, 270);
                contentStream.showText(userIdLabel);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                String userIdValue = String.valueOf(
                        UserDatabase.getInstance().getUserById(certificate.getUserId()).getName()
                );
                contentStream.newLineAtOffset(300, 270);
                contentStream.showText(userIdValue);
                contentStream.endText();

                // Issued Date
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                String issuedLabel = "Issued Date:";
                contentStream.newLineAtOffset(150, 240);
                contentStream.showText(issuedLabel);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                String issuedValue = certificate.getIssued().format(DATE_FORMATTER);
                contentStream.newLineAtOffset(300, 240);
                contentStream.showText(issuedValue);
                contentStream.endText();
            }

            document.save(filepath);
            return filepath;

        } catch (IOException e) {
            System.err.println("Error generating certificate PDF: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Downloads the certificate PDF by triggering a file download
     *
     * @param filepath The path to the PDF file
     * @return true if download was successful, false otherwise
     */
    public static boolean downloadCertificate(String filepath) {
        if (filepath == null || !new File(filepath).exists()) {
            return false;
        }

        try {
            File file = new File(filepath);
            java.awt.Desktop.getDesktop().open(file);
            return true;
        } catch (IOException e) {
            System.err.println("Error opening certificate file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates a unique certificate code
     */
    private static String generateCertificateCode(int certId, int studentId, int courseId) {
        return String.format("CERT-%04d-%04d-%04d", certId, studentId, courseId);
    }

    private static String sanitizeFilename(String name) {
        if (name == null)
            return "unknown";
        return name.replaceAll("[^a-zA-Z0-9\\s-_]", "").replaceAll("\\s+", "_").substring(0,
                Math.min(name.length(), 30));
    }

    public static boolean isCourseCompleted(int studentId, int courseId) {
        return CourseService.isComplete(courseId, studentId);
    }

    public static Certificate getOrCreateCertificate(int studentId, int courseId) {
        CourseService courseService = new CourseService();
        Certificate cert = courseService.getCertificate(courseId, studentId);

        if (cert == null && isCourseCompleted(studentId, courseId)) {
            courseService.createCertificate(courseId, studentId);
            // Refresh student to get updated certificate
            Student student = StudentService.getStudent(studentId);
            if (student.getCertificates() != null) {
                for (Certificate c : student.getCertificates()) {
                    if (c.getCourseId() == courseId) {
                        return c;
                    }
                }
            }
        }

        return cert;
    }

    private static LocalDateTime getLastQuizAttemptDate(Course course, int studentId) {
        if (course == null || course.getLessons() == null || course.getLessons().isEmpty()) {
            return null;
        }

        List<Lesson> lessons = course.getLessons();

        // Find the last lesson (highest ID) with a quiz that has attempts
        Lesson lastLessonWithQuiz = null;
        for (int i = lessons.size() - 1; i >= 0; i--) {
            Lesson lesson = lessons.get(i);
            if (lesson.getQuiz() != null) {
                Progress progress = lesson.getStudentProgress().get(studentId);
                if (progress != null && progress.getAttempts() != null && !progress.getAttempts().isEmpty()) {
                    lastLessonWithQuiz = lesson;
                    break; // Found the last lesson with a quiz and attempts
                }
            }
        }

        if (lastLessonWithQuiz == null) {
            return null;
        }

        // Get the last attempt from this last lesson
        Progress progress = lastLessonWithQuiz.getStudentProgress().get(studentId);
        if (progress == null || progress.getAttempts() == null || progress.getAttempts().isEmpty()) {
            return null;
        }

        List<QuizAttempt> attempts = progress.getAttempts();
        QuizAttempt lastAttempt = attempts.get(attempts.size() - 1);

        // Return finishTime if available, otherwise startTime
        return lastAttempt.getFinishTime() != null ? lastAttempt.getFinishTime() : lastAttempt.getStartTime();
    }
}
