package pages.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ChartFrame extends JPanel {
    private String chartTitle;
    private ChartType type;

    public enum ChartType {
        BAR, LINE, PIE
    }

    public ChartFrame(String title, ChartType type) {
        this.chartTitle = title;
        this.type = type;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 300));

        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);
    }

    /**
     * Render a bar chart
     * @param labels X-axis labels
     * @param values Y-axis values
     */
    public void renderBarChart(List<String> labels, List<Double> values) {
        if (labels.size() != values.size()) {
            throw new IllegalArgumentException("Labels and values must have same size");
        }

        JPanel chartPanel = new BarChartPanel(labels, values);
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Render a line chart
     * @param labels X-axis labels
     * @param values Y-axis values
     */
    public void renderLineChart(List<String> labels, List<Double> values) {
        if (labels.size() != values.size()) {
            throw new IllegalArgumentException("Labels and values must have same size");
        }

        JPanel chartPanel = new LineChartPanel(labels, values);
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Render completion percentages
     * @param data Map of course/lesson name to completion percentage
     */
    public void renderCompletionChart(Map<String, Double> data) {
        JPanel chartPanel = new CompletionChartPanel(data);
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Inner class for Bar Chart
    private static class BarChartPanel extends JPanel {
        private List<String> labels;
        private List<Double> values;

        public BarChartPanel(List<String> labels, List<Double> values) {
            this.labels = labels;
            this.values = values;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 40;
            int chartWidth = width - 2 * padding;
            int chartHeight = height - 2 * padding;

            if (values.isEmpty()) {
                g2d.drawString("No data available", width / 2 - 50, height / 2);
                return;
            }

            // Find max value for scaling
            double maxValue = values.stream().max(Double::compare).orElse(100.0);
            if (maxValue == 0) maxValue = 100.0;

            int barWidth = chartWidth / (labels.size() * 2);
            int spacing = barWidth / 2;

            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
            g2d.drawLine(padding, padding, padding, height - padding); // Y-axis

            // Draw bars
            for (int i = 0; i < labels.size(); i++) {
                double value = values.get(i);
                int barHeight = (int) ((value / maxValue) * chartHeight);
                int x = padding + spacing + i * (barWidth + spacing);
                int y = height - padding - barHeight;

                // Bar color gradient
                Color barColor = new Color(70, 130, 180);
                g2d.setColor(barColor);
                g2d.fillRect(x, y, barWidth, barHeight);

                // Bar border
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, barWidth, barHeight);

                // Value on top of bar
                g2d.setFont(new Font("Arial", Font.BOLD, 10));
                String valueStr = String.format("%.1f", value);
                int strWidth = g2d.getFontMetrics().stringWidth(valueStr);
                g2d.drawString(valueStr, x + (barWidth - strWidth) / 2, y - 5);

                // Label below bar
                g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                String label = labels.get(i);
                if (label.length() > 10) label = label.substring(0, 10) + "..";
                strWidth = g2d.getFontMetrics().stringWidth(label);
                g2d.drawString(label, x + (barWidth - strWidth) / 2, height - padding + 15);
            }

            // Y-axis labels
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i <= 5; i++) {
                int y = height - padding - (i * chartHeight / 5);
                double labelValue = (maxValue / 5) * i;
                String label = String.format("%.0f", labelValue);
                g2d.drawString(label, padding - 30, y + 5);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(padding, y, width - padding, y);
                g2d.setColor(Color.BLACK);
            }
        }
    }

    // Inner class for Line Chart
    private static class LineChartPanel extends JPanel {
        private List<String> labels;
        private List<Double> values;

        public LineChartPanel(List<String> labels, List<Double> values) {
            this.labels = labels;
            this.values = values;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 40;
            int chartWidth = width - 2 * padding;
            int chartHeight = height - 2 * padding;

            if (values.isEmpty()) {
                g2d.drawString("No data available", width / 2 - 50, height / 2);
                return;
            }

            // Find max value
            double maxValue = values.stream().max(Double::compare).orElse(100.0);
            if (maxValue == 0) maxValue = 100.0;

            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.drawLine(padding, height - padding, width - padding, height - padding);
            g2d.drawLine(padding, padding, padding, height - padding);

            // Calculate points
            int[] xPoints = new int[values.size()];
            int[] yPoints = new int[values.size()];

            for (int i = 0; i < values.size(); i++) {
                xPoints[i] = padding + (i * chartWidth / (values.size() - 1));
                yPoints[i] = height - padding - (int) ((values.get(i) / maxValue) * chartHeight);
            }

            // Draw line
            g2d.setColor(new Color(70, 130, 180));
            g2d.setStroke(new BasicStroke(2));
            for (int i = 0; i < values.size() - 1; i++) {
                g2d.drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
            }

            // Draw points
            for (int i = 0; i < values.size(); i++) {
                g2d.fillOval(xPoints[i] - 4, yPoints[i] - 4, 8, 8);

                // Value label
                g2d.setFont(new Font("Arial", Font.BOLD, 10));
                String valueStr = String.format("%.1f", values.get(i));
                g2d.drawString(valueStr, xPoints[i] - 10, yPoints[i] - 10);

                // X-axis label
                g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                String label = labels.get(i);
                if (label.length() > 10) label = label.substring(0, 10) + "..";
                g2d.drawString(label, xPoints[i] - 15, height - padding + 15);
            }

            // Y-axis labels
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            for (int i = 0; i <= 5; i++) {
                int y = height - padding - (i * chartHeight / 5);
                double labelValue = (maxValue / 5) * i;
                String label = String.format("%.0f", labelValue);
                g2d.drawString(label, padding - 30, y + 5);
            }
        }
    }

    // Inner class for Completion Chart
    private static class CompletionChartPanel extends JPanel {
        private Map<String, Double> data;

        public CompletionChartPanel(Map<String, Double> data) {
            this.data = data;
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            for (Map.Entry<String, Double> entry : data.entrySet()) {
                JPanel row = createCompletionRow(entry.getKey(), entry.getValue());
                add(row);
                add(Box.createVerticalStrut(10));
            }
        }

        private JPanel createCompletionRow(String name, double percentage) {
            JPanel row = new JPanel();
            row.setLayout(new BorderLayout(10, 0));
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            // Name label
            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            nameLabel.setPreferredSize(new Dimension(150, 30));
            row.add(nameLabel, BorderLayout.WEST);

            // Progress bar
            JPanel progressPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;

                    int width = getWidth();
                    int height = getHeight();

                    // Background
                    g2d.setColor(new Color(230, 230, 230));
                    g2d.fillRect(0, 0, width, height);

                    // Progress
                    int progressWidth = (int) (width * percentage / 100.0);
                    Color progressColor = percentage >= 75 ? new Color(76, 175, 80) :
                            percentage >= 50 ? new Color(255, 193, 7) :
                                    new Color(244, 67, 54);
                    g2d.setColor(progressColor);
                    g2d.fillRect(0, 0, progressWidth, height);

                    // Border
                    g2d.setColor(Color.GRAY);
                    g2d.drawRect(0, 0, width - 1, height - 1);

                    // Percentage text
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 11));
                    String text = String.format("%.1f%%", percentage);
                    int textWidth = g2d.getFontMetrics().stringWidth(text);
                    g2d.drawString(text, width - textWidth - 5, height / 2 + 4);
                }
            };
            progressPanel.setPreferredSize(new Dimension(200, 25));
            row.add(progressPanel, BorderLayout.CENTER);

            return row;
        }
    }
}