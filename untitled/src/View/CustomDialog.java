package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class CustomDialog extends JDialog {
    private BufferedImage backgroundImage;
    private BufferedImage buttonImage;

    public CustomDialog(JFrame parent, String title, String message) {
        super(parent, title, true);

        loadImages();
        setupDialog(message);
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("assets/menuscreen2.png"));
            buttonImage = ImageIO.read(new File("assets/button.png"));
        } catch (Exception e) {
            System.out.println("Could not load dialog images: " + e.getMessage());
        }
    }

    private void setupDialog(String message) {
        // Set size to match the background image size
        int dialogWidth = backgroundImage != null ? backgroundImage.getWidth() : 400;
        int dialogHeight = backgroundImage != null ? backgroundImage.getHeight() : 300;

        setSize(dialogWidth, dialogHeight);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setUndecorated(true); // Remove title bar and borders
        setBackground(new Color(0, 0, 0, 0)); // Transparent background

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
                if (buttonImage != null) {
                    // Draw button background for OK button (adjust position based on actual image size)
                    int buttonX = (getWidth() - 100) / 2;
                    int buttonY = getHeight() - 80;
                    g.drawImage(buttonImage, buttonX, buttonY, 100, 40, this);
                }
            }
        };
        contentPanel.setOpaque(false); // Make panel transparent
        contentPanel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel(getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Impact", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 0));
        titleLabel.setBounds(50, dialogHeight/6, dialogWidth-100, 40);
        contentPanel.add(titleLabel);

        // Message area
        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Tahoma", Font.BOLD, 14));
        messageArea.setForeground(new Color(139, 69, 19));
        messageArea.setOpaque(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBounds(100, dialogHeight/3+50, dialogWidth-100, dialogHeight/3);
        contentPanel.add(messageArea);

        // OK button (centered)
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        okButton.setForeground(Color.WHITE);
        int buttonX = (dialogWidth - 100) / 2;
        int buttonY = dialogHeight - 80;
        okButton.setBounds(buttonX, buttonY, 100, 40);
        okButton.setFocusable(false);
        okButton.setBorderPainted(false);
        okButton.setContentAreaFilled(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPanel.add(okButton);

        setContentPane(contentPanel);
    }
}