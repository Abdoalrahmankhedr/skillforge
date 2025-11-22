package pages.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LessonCard extends JPanel {
    private JLabel titleLabel;
    private JLabel contentLabel;
    public JButton takeQuizBtn;
    public JButton attemptsHistoryBtn;

    public LessonCard() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);

        titleLabel = new JLabel("Title", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        contentLabel = new JLabel("<html>Content</html>");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentLabel.setPreferredSize(new Dimension(250, 40));
        contentLabel.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));

        takeQuizBtn = new JButton("Take Quiz");
        takeQuizBtn.setPreferredSize(new Dimension(160, 30));
        takeQuizBtn.setBackground(Color.BLUE);
        takeQuizBtn.setForeground(Color.WHITE);
        takeQuizBtn.setFocusPainted(false);
        takeQuizBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        takeQuizBtn.setOpaque(true);
        takeQuizBtn.setContentAreaFilled(true);
        takeQuizBtn.setBorderPainted(true);

        attemptsHistoryBtn = new JButton("📋");
        attemptsHistoryBtn.setPreferredSize(new Dimension(35, 30));
        attemptsHistoryBtn.setBackground(new Color(240, 240, 240));
        attemptsHistoryBtn.setForeground(Color.BLACK);
        attemptsHistoryBtn.setFocusPainted(false);
        attemptsHistoryBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        attemptsHistoryBtn.setToolTipText("View Quiz Attempts History");
        attemptsHistoryBtn.setVisible(false);
        attemptsHistoryBtn.setEnabled(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(contentLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        add(infoPanel, BorderLayout.CENTER);

        // Button panel with vertical centering
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(Box.createVerticalGlue());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(attemptsHistoryBtn);
        buttonRow.add(takeQuizBtn);

        buttonPanel.add(buttonRow);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.EAST);
    }

    public void setData(boolean state, String title, String content, boolean quizAvailable,
                        boolean hasAttempts, boolean allAttemptsUsed, ActionListener quizAction, ActionListener historyAction) {
        takeQuizBtn.setVisible(state && quizAvailable);
        titleLabel.setText(title);
        contentLabel.setText("<html>" + content + "</html>");

        if (quizAvailable) {
            if (allAttemptsUsed) {
                takeQuizBtn.setText("Completed");
                takeQuizBtn.setBackground(new Color(0, 153, 0)); // Green completed color
                takeQuizBtn.setForeground(Color.WHITE);
                takeQuizBtn.setEnabled(false);
                takeQuizBtn.setOpaque(true);
                takeQuizBtn.setContentAreaFilled(true);
                takeQuizBtn.setBorderPainted(false);
                // Remove all action listeners when disabled
                for (ActionListener al : takeQuizBtn.getActionListeners()) {
                    takeQuizBtn.removeActionListener(al);
                }
            } else {
                takeQuizBtn.setText("Take Quiz");
                takeQuizBtn.setBackground(Color.BLUE);
                takeQuizBtn.setForeground(Color.WHITE);
                takeQuizBtn.setEnabled(true);
                takeQuizBtn.setOpaque(true);

                // Remove existing listeners from takeQuizBtn
                for (ActionListener al : takeQuizBtn.getActionListeners()) {
                    takeQuizBtn.removeActionListener(al);
                }
                if (quizAction != null) {
                    takeQuizBtn.addActionListener(quizAction);
                }
            }

            // Show attempts history button if student has at least one previous attempt
            attemptsHistoryBtn.setVisible(hasAttempts);
            attemptsHistoryBtn.setEnabled(hasAttempts);
            if (hasAttempts && historyAction != null) {
                // Remove existing listeners
                for (ActionListener al : attemptsHistoryBtn.getActionListeners()) {
                    attemptsHistoryBtn.removeActionListener(al);
                }
                attemptsHistoryBtn.addActionListener(historyAction);
            }
        } else {
            takeQuizBtn.setVisible(false);
            attemptsHistoryBtn.setVisible(false);
        }
    }

    // Backward compatibility method
    public void setData(boolean state, String title, String content, boolean completed, ActionListener action) {
        setData(state, title, content, false, false, false, action, null);
    }
}
