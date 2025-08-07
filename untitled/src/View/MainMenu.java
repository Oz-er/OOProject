package View;

import Control.GameLogic;
import Control.MusicManager;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    public static void main(String[] args) {

        //------------------------setting up main application window-------------------------

        JFrame frame = new JFrame("Whac-A-Mole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        //----------------------custom window picture-----------------------------------

        ImageIcon image = new ImageIcon(MainMenu.class.getResource("MenuPanel.png"));
        frame.setIconImage(image.getImage());

        //----------------------creating a gamelogic object for reuse----------------------

        GameLogic logic = new GameLogic();  // Declare once and reuse
        logic.setLevel(1);  // Starting at level 1

        CardLayout cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);

        //-------------------Add menu and GamePanels-----------------------

        MainMenuPanel menuPanel = new MainMenuPanel(panel, cardLayout, logic);
        LevelSelectPanel levelSelectPanel = new LevelSelectPanel(panel, cardLayout, logic);

        panel.add(new HowToPlay(panel, cardLayout),"HowToPlay");
        panel.add(menuPanel, "MainMenuPanel");
        panel.add(levelSelectPanel, "LevelSelectPanel");

        AboutPanel aboutPanel = new AboutPanel(panel, cardLayout);
        panel.add(aboutPanel, "AboutPanel");


        cardLayout.show(panel, "MainMenuPanel");

        frame.setContentPane(panel);

        // Start background music if enabled
        MusicManager musicManager = MusicManager.getInstance();
        if (musicManager.isMusicEnabled()) {
            musicManager.startBackgroundMusic();
        }

        frame.setVisible(true);
    }
}