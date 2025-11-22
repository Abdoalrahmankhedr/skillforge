import pages.*;
import windows.MainWindow;

public class Main {
    public static void main(String[] args) {
        /* Pages */
        MainWindow.addPage("login", new LoginPage());
        MainWindow.addPage("signup", new SignupPage());

        /* Student Frames */
        MainWindow.addPage("StudentDashBoard", new StudentDashBoard());
        MainWindow.addPage("CourseLessons", new CourseLessons());
        MainWindow.addPage("studentlessons", new StudentLessons());
        MainWindow.addPage("CoursesView", new CoursesView());

        /* Instructor Frames */
        MainWindow.addFrame("InstructorDashboard", new InstructorDashboard());
        MainWindow.addFrame("ManageCourseFrame", new ManageCourseFrame());
        MainWindow.addFrame("ViewStudentsFrame", new ViewStudentsFrame());

        /* Starting up main window */
        MainWindow.nameWindow("SkillForge");
        MainWindow.addIcon("src/resources/icon.png");
        // If resizing the window is needed, uncomment the line below
        // MainWindow.setDimensions(800, 600);
        MainWindow.goTo("login");
        MainWindow.start();
    }
}   