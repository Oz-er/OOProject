package View;


import javax.swing.*;
import java.awt.*;

public class MusicSettingPanel extends JPanel {

    Image musicPanel;
    Image background;

    public MusicSettingPanel(JPanel panel,CardLayout cardLayout) {
        setLayout(null);
        musicPanel = new ImageIcon("assets/background.png").getImage();
        background = new ImageIcon("assets/button.png").getImage();
        setLayout(null);

        JLabel label = new JLabel("Music");
        label.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 18));
        label.setForeground(Color.BLACK);
        label.setBounds(100, 500, 130, 30);

        add(label);

        SwitchButton toggle = new SwitchButton();
        toggle.setBounds(250, 500, 70, 30);
        add(toggle);

        JButton back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 15));
        back.setBackground(new Color(150,75,0));
        back.setForeground(Color.BLACK);
        back.setBounds(5,600,100,30);
        back.setOpaque(false);
        back.setFocusable(false);
        back.setBorderPainted(false);
        setVisible(true);
        back.addActionListener(e -> cardLayout.show(panel,"MainMenuPanel"));
        add(back);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(musicPanel, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(background,60,490,150,50,this);
        g.drawImage(background,10,590,100,50,this);

    }
}