package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class AboutPanel extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel parentPanel;

    private BufferedImage backgroundImage;
    private final ArrayList<BufferedImage> portfolioImages = new ArrayList<>();
    private int currentIndex = 0;

    private JLabel imageLabel;

    public AboutPanel(JPanel parentPanel, CardLayout cardLayout) {
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;

        setLayout(null);

        // Load images using File
        try {
            backgroundImage = ImageIO.read(new File("assets/background.png"));
            portfolioImages.add(ImageIO.read(new File("assets/omer.png")));
            portfolioImages.add(ImageIO.read(new File("assets/asshifa.png")));
            // Add more as needed
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Image label
        imageLabel = new JLabel();
        imageLabel.setBounds(100, 35, 600, 600); // Adjust size
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        updateDisplayedImage();
        add(imageLabel);

        // Previous Button
        JButton prevButton = new JButton("<");
        prevButton.setBounds(50, 300, 50, 30);
        prevButton.setFocusable(false);
        add(prevButton);
        prevButton.addActionListener(e -> {
            currentIndex = (currentIndex - 1 + portfolioImages.size()) % portfolioImages.size();
            updateDisplayedImage();
        });

        // Next Button
        JButton nextButton = new JButton(">");
        nextButton.setBounds(700, 300, 50, 30);
        nextButton.setFocusable(false);
        add(nextButton);
        nextButton.addActionListener(e -> {
            currentIndex = (currentIndex + 1) % portfolioImages.size();
            updateDisplayedImage();
        });

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(350, 600, 100, 40);
        backButton.setFocusable(false);
        add(backButton);
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "MainMenuPanel"));
    }

    private void updateDisplayedImage() {
        if (!portfolioImages.isEmpty()) {
            BufferedImage currentImage = portfolioImages.get(currentIndex);
            Image scaled = currentImage.getScaledInstance(500, 650, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
