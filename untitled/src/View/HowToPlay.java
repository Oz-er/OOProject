package View;

import javax.swing.*;
import java.awt.*;

public class HowToPlay extends JPanel {
    Image background;
    Image menuScreen;
    Image pikachu;
    Image gengar;
    Image electrode;
    Image backButtonImage;

    public HowToPlay(JPanel panel, CardLayout cardLayout) {
        setLayout(null);
        background = new ImageIcon("assets/background.png").getImage();
        menuScreen = new ImageIcon("assets/menuscreen2.png").getImage(); // Your wooden button image
        pikachu = new ImageIcon("assets/regular_mole.png").getImage();
        gengar = new ImageIcon("assets/black_mole.png").getImage();
        electrode = new ImageIcon("assets/bomb_mole.png").getImage();
        backButtonImage = new ImageIcon("assets/button.png").getImage(); // Add this button image

        // Calculate center positions for the bigger menu screen
        int screenWidth = 800;  // Assuming panel width
        int screenHeight = 700; // Assuming panel height
        int menuWidth = 650;    // Increased from 500
        int menuHeight = 650;   // Increased from 600
        int menuX = (screenWidth - menuWidth) / 2;
        int menuY = (screenHeight - menuHeight) / 2;

        // Title
        JLabel title = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
        title.setFont(new Font("Impact", Font.PLAIN, 40)); // Smaller font
        title.setForeground(new Color(0, 0, 0)); // Dark brown color
        title.setBounds(menuX + 30, menuY + 55, menuWidth - 100, 50);
        add(title);

        // Instructions text
        JTextArea instructions = new JTextArea();
        instructions.setEditable(false);
        instructions.setFont(new Font("Tahoma", Font.BOLD, 14)); // Smaller font
        instructions.setForeground(new Color(139, 69, 19));
        instructions.setBounds(menuX + 100, menuY + 170, menuWidth - 100, 65);
        instructions.setText("Click on Pikachu to get points.\nAvoid Gengar and Electrode!\nPoint Distribution:");
        instructions.setOpaque(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        add(instructions);

        // Pikachu points
        JTextArea pikachuText = new JTextArea();
        pikachuText.setEditable(false);
        pikachuText.setFont(new Font("Tahoma", Font.BOLD, 16)); // Smaller font
        pikachuText.setForeground(new Color(139, 69, 19));
        pikachuText.setBounds(menuX + 160, menuY + 255, 250, 30);
        pikachuText.setText(": +10 points");
        pikachuText.setOpaque(false);
        add(pikachuText);

        // Gengar points
        JTextArea gengarText = new JTextArea();
        gengarText.setEditable(false);
        gengarText.setFont(new Font("Tahoma", Font.BOLD, 16)); // Smaller font
        gengarText.setForeground(new Color(139, 69, 19));
        gengarText.setBounds(menuX + 160, menuY + 315, 250, 30);
        gengarText.setText(": -10 points");
        gengarText.setOpaque(false);
        add(gengarText);

        // Electrode points
        JTextArea electrodeText = new JTextArea();
        electrodeText.setEditable(false);
        electrodeText.setFont(new Font("Tahoma", Font.BOLD, 16)); // Smaller font
        electrodeText.setForeground(new Color(139, 69, 19));
        electrodeText.setBounds(menuX + 160, menuY + 375, 320, 45);
        electrodeText.setText(": 0 points(Ends the game instantly!)");
        electrodeText.setOpaque(false);
        electrodeText.setLineWrap(true);
        add(electrodeText);

        // Final instructions
        JTextArea finalText = new JTextArea();
        finalText.setEditable(false);
        finalText.setFont(new Font("Tahoma", Font.BOLD, 14)); // Smaller font
        finalText.setForeground(new Color(139, 69, 19));
        finalText.setBounds(menuX + 100, menuY + 425, menuWidth - 100, 65);
        finalText.setText("Reach the target score to advance\nto the next level.\nTry again if you fail!\nHave fun smashing Pikachus!");
        finalText.setOpaque(false);
        finalText.setLineWrap(true);
        finalText.setWrapStyleWord(true);
        add(finalText);

        // Back button with image background
        JButton back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.BOLD, 14)); // Smaller font
        back.setForeground(Color.WHITE);
        back.setBounds(menuX + 260, menuY + 500, 100, 40);
        back.setFocusable(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false); // Make button transparent so we can draw custom background
        back.addActionListener(e -> cardLayout.show(panel, "MainMenuPanel"));
        add(back);

        setPreferredSize(new Dimension(screenWidth, screenHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        // Calculate center positions for bigger menu screen
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        int menuWidth = 650;    // Increased from 500
        int menuHeight = 650;   // Increased from 600
        int menuX = (screenWidth - menuWidth) / 2;
        int menuY = (screenHeight - menuHeight) / 2;

        // Draw centered menu screen
        g2d.drawImage(menuScreen, menuX, menuY, menuWidth, menuHeight, this);

        // Draw character images next to their descriptions (bigger images)
        // Pikachu
        g2d.drawImage(pikachu, menuX + 110, menuY + 255, 50, 50, this); // Increased from 60x60

        // Gengar
        g2d.drawImage(gengar, menuX + 110, menuY + 315, 50, 50, this); // Increased from 60x60

        // Electrode
        g2d.drawImage(electrode, menuX + 95, menuY + 365, 80, 80, this); // Increased from 60x60

        // Draw back button image
        g2d.drawImage(backButtonImage, menuX + 270, menuY + 520, 100, 40, this);
    }
}