package View;

import Control.GameLogic;
import Model.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LevelSelectPanel extends JPanel {
    private final GameLogic logic;

    public LevelSelectPanel(JPanel parentPanel, CardLayout cardLayout, GameLogic logic) {
        this.logic = logic;
        setLayout(null);

        JLabel title = new JLabel("Select Level", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBounds(200, 50, 400, 50);
        add(title);

        for (int i = 1; i <= 5; i++) {
            JButton levelBtn = new JButton("Level " + i);
            levelBtn.setBounds(300, 100 + i * 60, 200, 40);
            levelBtn.setFont(new Font("Arial", Font.BOLD, 18));
            levelBtn.setEnabled(i <= logic.getMaxUnlockedLevel());
            int level = i;

            levelBtn.addActionListener((ActionEvent e) -> {
                GamePanel gamePanel = new GamePanel(level, logic, parentPanel, cardLayout);
                parentPanel.add(gamePanel, "GamePanel");
                cardLayout.show(parentPanel, "GamePanel");
            });

            add(levelBtn);
        }

        JButton back = new JButton("Back");
        back.setBounds(10, 600, 100, 30);
        back.addActionListener(e -> cardLayout.show(parentPanel, "MainMenuPanel"));
        add(back);
    }
}
