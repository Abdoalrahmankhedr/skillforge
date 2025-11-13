import windows.MainWindow;

public class Main {
    public static void main(String[] args) {
        /* Pages */
        // add pages here

        /* Starting up main window */
        MainWindow.nameWindow("SkillForge");
        MainWindow.addIcon("src/resources/icon.png");
        // If resizing the window is needed, uncomment the line below
        // MainWindow.setDimensions(800, 600);
        MainWindow.start();
    }
}
