package pages.components;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionListener;

public class LessonCard extends JPanel {
    private JLabel titleLabel;
    private JLabel contentLabel;
    public  JButton completeBtn;
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

        completeBtn = new JButton("Complete");
        completeBtn.setPreferredSize(new Dimension(160, 30));
        completeBtn.setBackground(Color.BLUE);
        completeBtn.setForeground(Color.WHITE);
        completeBtn.setFocusPainted(false);
        completeBtn.setFont(new Font("Arial", Font.PLAIN, 20));
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

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(completeBtn, new GridBagConstraints());
        add(buttonPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.EAST);
    }

    public void setData(boolean state,String title, String content, boolean completed, ActionListener action) {
        completeBtn.setVisible(state);
        titleLabel.setText(title);
        contentLabel.setText("<html>" + content + "</html>");
        completeBtn.setText(completed ? "Completed" : "Complete");
        completeBtn.setEnabled(!completed);
        completeBtn.addActionListener(action);
        if (completed) completeBtn.setBackground(Color.GREEN);
        else completeBtn.setBackground(Color.BLUE);
    }
}
