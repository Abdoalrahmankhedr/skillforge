package pages;

import pages.components.Header;
import services.InstructorService;
import models.User;
import windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;
import java.awt.event.*;
import java.util.ArrayList;

public class EnrolledStudentsPage extends JPanel {

    private final int instructorId;
    private List<User> allStudents = new ArrayList<>();
    private List<User> shownStudents = new ArrayList<>();

    private final JComboBox<String> searchCombo;
    private final JComboBox<String> sortCombo;
    private final JTextField searchField;
    private final JPanel tablePanel = new JPanel(new BorderLayout());

    public EnrolledStudentsPage(int instructorId) {
        this.instructorId = instructorId;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        /* -------------------- HEADER ADDED HERE -------------------- */
        Header header = new Header("Enrolled Students", true);

        header.backButton.addActionListener(e -> {
            // Example: navigate back to instructor dashboard
            MainWindow.goTo("InstructorDashboardPage");
        });

        add(header, BorderLayout.NORTH);
        /* ------------------------------------------------------------ */

        // Controls panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(getBackground());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0;
        gbc.gridy = 0;

        searchCombo = new JComboBox<>(new String[]{
                "Search By Name",
                "Search By Email",
                "Search By ID"
        });
        searchCombo.setPreferredSize(new Dimension(250, 32));
        searchCombo.addActionListener(e -> applySearch());
        controlPanel.add(searchCombo, gbc);

        gbc.gridx = 1;

        sortCombo = new JComboBox<>(new String[]{
                "Sort by ID (Ascending)",
                "Sort by ID (Descending)",
                "Sort by Name (Ascending)",
                "Sort by Name (Descending)"
        });
        sortCombo.setPreferredSize(new Dimension(250, 32));
        sortCombo.addActionListener(e -> { applySort(); updateTable(shownStudents); });
        controlPanel.add(sortCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        searchField = new JTextField("Write Search Key...");
        searchField.setPreferredSize(new Dimension(520, 34));
        searchField.setForeground(Color.GRAY);

        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Write Search Key...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Write Search Key...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        searchField.addActionListener(e -> applySearch());
        controlPanel.add(searchField, gbc);

        add(controlPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Table
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(tablePanel, BorderLayout.CENTER);

        loadStudents();
    }

    private void loadStudents() {
        allStudents = InstructorService.getEnrolledStudents(instructorId);
        shownStudents = new ArrayList<>(allStudents);

        applySort();
        updateTable(shownStudents);
    }

    private void applySearch() {
        String searchBy = (String) searchCombo.getSelectedItem();
        String key = searchField.getText().trim();

        if (key.equals("Write Search Key...") || key.isEmpty()) {
            shownStudents = new ArrayList<>(allStudents);
            applySort();
            updateTable(shownStudents);
            return;
        }

        List<User> filtered = new ArrayList<>();

        for (User u : allStudents) {
            if (searchBy.equals("Search By Name") &&
                    u.getName().toLowerCase().contains(key.toLowerCase()))
                filtered.add(u);

            else if (searchBy.equals("Search By Email") &&
                    u.getEmail().toLowerCase().contains(key.toLowerCase()))
                filtered.add(u);

            else if (searchBy.equals("Search By ID") &&
                    Integer.toString(u.getId()).contains(key))
                filtered.add(u);
        }

        shownStudents = filtered;
        applySort();
        updateTable(shownStudents);
    }

    private void applySort() {
        String sort = (String) sortCombo.getSelectedItem();

        if (sort.equals("Sort by ID (Ascending)"))
            shownStudents.sort(Comparator.comparingInt(User::getId));

        else if (sort.equals("Sort by ID (Descending)"))
            shownStudents.sort(Comparator.comparingInt(User::getId).reversed());

        else if (sort.equals("Sort by Name (Ascending)"))
            shownStudents.sort(Comparator.comparing(User::getName));

        else if (sort.equals("Sort by Name (Descending)")) {
            shownStudents.sort(Comparator.comparing(User::getName).reversed());
        }
    }

    private void updateTable(List<User> students) {
        tablePanel.removeAll();

        String[] columns = {"ID", "Name", "Email"};
        Object[][] data = new Object[students.size()][3];

        for (int i = 0; i < students.size(); i++) {
            User u = students.get(i);
            data[i][0] = u.getId();
            data[i][1] = u.getName();
            data[i][2] = u.getEmail();
        }

        JTable table = new JTable(data, columns) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);
        tablePanel.add(scroll, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
