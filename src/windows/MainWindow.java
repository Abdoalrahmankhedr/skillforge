package windows;

import pages.AdminDashboard;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// Andrew :)

/*
    Usage of MainWindow:
    [] Adding a page to window
        1 - Create a class that extends JPanel
        2 - Finish the page you need within the class
        3 - use MainWindow.addPage("page_name", new Page());
        4 - when navigating to page is necessary, use MainWindow.goTo("page_name");

    [] Starting main window
        MainWindow.start() in main

    [] First page load
        MainWindow.addPage("page_name", new Page());
        MainWindow.goTo("page_name");
        MainWindow.start();
*/

/*
    The layout of the window is structured as such:
    The main JFrame will be the window, housing the CardLayout

    CardLayout allows for switching between JPanels through page names
    Each JPanel would represent a page in the application, and is assigned a name
    If a JButton or Event redirect to a specific page, using the page name will change the visible 'card'

    This keeps track of all pages added, allows adding pages with names,
    removing pages using their set names, and allow for navigation to pages using their set names

    Note:
        Any panels made as a page must not be set visible before adding!
        Only by going to the page would it be visible (use: goTo)

    Any feedback is appreciated ;)
*/

public class MainWindow {
    /* App Icon Image */
    private static ImageIcon icon;
    /* Window Name */
    private static String title;
    /* Window Dimensions */
    private static int[] dimensions = { 800, 600 };

    /* Layout */
    private static final CardLayout cardLayout = new CardLayout();
    /* Main Panel */
    private static final JPanel cardPanel = new JPanel(cardLayout);
    /* Pages Index */
    private static final Map<String, Component> pages = new HashMap<>();

    private static JFrame main;

    public static void nameWindow(String name) {
        title = name;
    }

    public static void addIcon(String path) {
        icon = new ImageIcon(path);
    }

    public static void setDimensions(int width, int height) {
        dimensions = new int[] { width, height };
    }

    public static void start() {
        if (main == null) {
            main = new JFrame();
            /* Set window dimensions */
            main.setSize(dimensions[0], dimensions[1]);
            if (title != null) main.setTitle(title);
            /* Window can not change size */
            main.setResizable(false);
            /* Close on exit behaviour */
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            /* Setting icon */
            if (icon != null) main.setIconImage(icon.getImage());
            /* Layout won't be changed since pages should take up full space */
            // setLayout(null);

            /* Add Main Panel */
            cardPanel.setLayout(cardLayout);
            cardPanel.setVisible(true);
            main.add(cardPanel, BorderLayout.CENTER);
            main.setVisible(true);
            main.setLocationRelativeTo(null);
        }
    }

    public static void addPage(String name, Component panel) {
        /* Add page to index */
        if (pages.getOrDefault(name, null) == null) {
            pages.put(name, panel);
            cardPanel.add(panel, name);
        }
    }

    public static void removePage(String name) {
        /* Remove unneeded page */
        Component page = pages.getOrDefault(name, null);
        if (page != null) {
            pages.remove(name);
            cardPanel.remove(page);
        }
    }

    public static void goTo(String name) {
        /* Navigate to specific page */
        Component page = pages.getOrDefault(name, null);
        if (page != null) {
            cardLayout.show(cardPanel, name);
        }
    }
    //For frames
    private static final Map<String, JFrame> frames = new HashMap<>();
    public static void addFrame(String name, JFrame frame) {
        if (!frames.containsKey(name)) {
            frames.put(name, frame);
        }
    }
    public static void goToFrame(String name) {
        for (JFrame f : frames.values()) {
            f.setVisible(false);
        }
        JFrame frame = frames.getOrDefault(name, null);
        if (frame != null) {
            frame.setVisible(true);
            frame.toFront();
            frame.requestFocus();
        }
    }
    public static void closeFrame(String name){
        JFrame frame = frames.getOrDefault(name, null);
        if (frame != null) {
            frame.setVisible(false);
            frame.toFront();
            frame.requestFocus();
        }
    }
    public static JFrame getFrame(String name){
        return frames.getOrDefault(name,null);
    }

}