package View;
import Control.MusicManager;

import Control.GameLogic;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    Image background;
    Image button;

    public MainMenuPanel(JPanel panel, CardLayout cardLayout, GameLogic logic) {

        background = new ImageIcon(MainMenuPanel.class.getResource("MenuPanel.png")).getImage();
        button = new ImageIcon(MainMenuPanel.class.getResource("button.png")).getImage();
        setLayout(null);

        //start Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 16));
        startButton.setBackground(new Color(150,75,0));
        startButton.setSize(130,30);
        startButton.setLocation(110,410);
        startButton.setOpaque(false);
        startButton.setFocusable(false);
        startButton.setVisible(true);
        startButton.setBorderPainted(false);
        add(startButton);

        startButton.addActionListener(e -> {
            LevelSelectPanel levelSelectPanel = new LevelSelectPanel(panel, cardLayout, logic);
            panel.add(levelSelectPanel, "LevelSelectPanel"); // Add or replace
            cardLayout.show(panel, "LevelSelectPanel");
        });

        //How to Play
        JButton playButton = new JButton("Help");
        playButton.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 16));
        playButton.setBackground(new Color(150,75,0));
        playButton.setSize(130,30);
        playButton.setLocation(110,460);
        playButton.setOpaque(false);
        playButton.setFocusable(false);
        playButton.setVisible(true);
        playButton.setBorderPainted(false);
        add(playButton);
        playButton.addActionListener(e -> cardLayout.show(panel, "HowToPlay"));

        //music button
        JLabel musicButton = new JLabel("Music");
        musicButton.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 16));
        musicButton.setBackground(new Color(150,75,0));
        musicButton.setSize(130,30);
        musicButton.setLocation(150,510);
        musicButton.setFocusable(false);
        musicButton.setOpaque(false);
        musicButton.setVisible(true);

        SwitchButton toggle = new SwitchButton();
        toggle.setBounds(250, 510, 70, 30);

        // Set initial state and add event listener
        MusicManager musicManager = MusicManager.getInstance();
        toggle.setSelected(musicManager.isMusicEnabled());

        toggle.addEventSelected(selected -> {
            musicManager.setMusicEnabled(selected);
        });

        add(toggle);
        add(musicButton);

        //about button
        JButton aboutButton = new JButton("About");
        aboutButton.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 16));
        aboutButton.setBackground(new Color(150,75,0));
        aboutButton.setSize(130,30);
        aboutButton.setLocation(110,560);
        aboutButton.setFocusable(false);
        aboutButton.setOpaque(false);
        aboutButton.setBorderPainted(false);
        aboutButton.setVisible(true);
        add(aboutButton);

        aboutButton.addActionListener(e -> cardLayout.show(panel, "AboutPanel"));


        //exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 16));
        exitButton.setBackground(new Color(150,75,0));
        exitButton.setSize(130,30);
        exitButton.setLocation(110,610);
        exitButton.setFocusable(false);
        exitButton.setOpaque(false);
        exitButton.setBorderPainted(false);
        exitButton.setVisible(true);
        exitButton.addActionListener(e -> {
            musicManager.cleanup();
            System.exit(0);
        });
        add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(button,100, 400, 150, 50, this);
        g.drawImage(button,100, 450, 150, 50, this);
        g.drawImage(button,100,500,150,50,this);
        g.drawImage(button,100,550,150,50,this);
        g.drawImage(button,100,600,150,50,this);
    }
}