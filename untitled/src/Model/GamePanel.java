package Model;

import Control.GameLogic;
import Control.LevelConfig;
import View.LevelSelectPanel;
import View.CustomDialog;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class GamePanel extends JPanel implements MouseListener {
    // === Constants ===
    private static final int ROWS = 3, COLS = 3;
    private static final int HOLE_WIDTH = 120, HOLE_HEIGHT = 90;
    private static final int MOLE_WIDTH = 70, MOLE_HEIGHT = 80;

    // === Game Assets ===
    private BufferedImage background, holeImg, regularImg, blackImg, bombImg, malletImg;

    // === Game Logic ===
    private final GameLogic logic;
    private final int selectedLevel;
    private Mole currentMole = null;
    private int currentMoleHoleIndex = -1;
    private final Random rand = new Random();

    // === Timers ===
    private Timer moleTimer, gameClock, hideTimer;

    // === GUI ===
    private final JButton restartButton = new JButton("Restart");
    private final Point mousePosition = new Point(0, 0);

    // === Navigation ===
    private final JPanel parentPanel;
    private final CardLayout cardLayout;

    public GamePanel(int level, GameLogic logic, JPanel parentPanel, CardLayout cardLayout) {
        this.logic = logic;
        this.selectedLevel = level;
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;

        setPreferredSize(new Dimension(800, 700));
        setLayout(null);
        logic.setLevel(level);
        logic.reset();

        loadImages();
        setupCursor();
        setupRestartButton();
        setupMouseListeners();

        startWithDelay();
    }

    // === Setup Methods ===
    private void loadImages() {
        try {
            background = ImageIO.read(new File("assets/background.png"));
            holeImg = ImageIO.read(new File("assets/hole.png"));
            regularImg = ImageIO.read(new File("assets/regular_mole.png"));
            blackImg = ImageIO.read(new File("assets/black_mole.png"));
            bombImg = ImageIO.read(new File("assets/bomb_mole.png"));
            BufferedImage rawMallet = ImageIO.read(new File("assets/mallet.png"));

            malletImg = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = malletImg.createGraphics();
            g2d.drawImage(rawMallet, 0, 0, 30, 30, null);
            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupCursor() {
        BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), "blank");
        setCursor(blankCursor);
    }

    private void setupMouseListeners() {
        addMouseListener(this);
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                mousePosition.setLocation(e.getPoint());
                repaint();
            }
        });
    }

    private void setupRestartButton() {
        restartButton.setBounds(330, 600, 140, 40);
        restartButton.setFocusable(false);
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);
    }

    private void startWithDelay() {
        Timer delay = new Timer(1000, e -> {
            startMoleSpawning();
            startGameClock();
            ((Timer) e.getSource()).stop();
        });
        delay.setRepeats(false);
        delay.start();
    }

    // === Game Logic ===
    private void startMoleSpawning() {
        int moleDelay = LevelConfig.getMoleDelay(selectedLevel);
        int hideDelay = LevelConfig.getHideDelay(selectedLevel);

        moleTimer = new Timer(moleDelay, e -> {
            if (!logic.isRunning()) return;

            int holeIndex = rand.nextInt(9);
            int roll = rand.nextInt(100);
            int moleType = (roll < 70) ? 0 : (roll < 85) ? 1 : 2;

            Point pos = calculateMolePosition(holeIndex);
            currentMole = switch (moleType) {
                case 0 -> new RegularMole(pos.x, pos.y, regularImg, MOLE_WIDTH, MOLE_HEIGHT);
                case 1 -> new BlackMole(pos.x, pos.y, blackImg, MOLE_WIDTH, MOLE_HEIGHT);
                default -> new BombMole(pos.x, pos.y, bombImg, MOLE_WIDTH, MOLE_HEIGHT);
            };

            currentMole.show();
            currentMoleHoleIndex = holeIndex;
            playPopSound();
            repaint();

            if (hideTimer != null) hideTimer.stop();
            hideTimer = new Timer(hideDelay, evt -> {
                if (currentMole != null) {
                    currentMole.hide();
                    currentMole = null;
                    currentMoleHoleIndex = -1;
                    repaint();
                }
            });
            hideTimer.setRepeats(false);
            hideTimer.start();
        });
        moleTimer.start();
    }

    private void startGameClock() {
        gameClock = new Timer(1000, e -> {
            if (logic.isRunning()) {
                logic.tickTime();
                repaint();
            } else {
                stopAllTimers();
                logic.updateProgress();
                restartButton.setVisible(true);
            }
        });
        gameClock.start();
    }

    private void stopAllTimers() {
        if (moleTimer != null) moleTimer.stop();
        if (hideTimer != null) hideTimer.stop();
        if (gameClock != null) gameClock.stop();
    }

    private void restartGame() {
        stopAllTimers();
        logic.reset();
        logic.setLevel(selectedLevel);
        currentMole = null;
        currentMoleHoleIndex = -1;
        restartButton.setVisible(false);

        startMoleSpawning();
        startGameClock();
        repaint();
    }

    private Point calculateMolePosition(int index) {
        int spacingX = (getWidth() - COLS * HOLE_WIDTH) / (COLS + 1);
        int spacingY = (getHeight() - 200 - ROWS * HOLE_HEIGHT) / (ROWS + 1);
        int row = index / COLS;
        int col = index % COLS;
        int x = spacingX + col * (HOLE_WIDTH + spacingX);
        int y = 200 + spacingY + row * (HOLE_HEIGHT + spacingY);

        return new Point(x + (HOLE_WIDTH - MOLE_WIDTH) / 2 + 2, y + (HOLE_HEIGHT - MOLE_HEIGHT) / 2 - 22);
    }

    private void playPopSound() {
        try {
            File soundFile = new File("assets/popup.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // === Mouse Events ===
    @Override
    public void mousePressed(MouseEvent e) {
        if (!logic.isRunning() || currentMole == null || !currentMole.isVisible()) return;

        if (currentMole.isHit(e.getX(), e.getY())) {
            currentMole.onHit(logic);
            currentMole.hide();
            currentMole = null;
            currentMoleHoleIndex = -1;
            if (hideTimer != null) hideTimer.stop();
            repaint();

            if (logic.getScore() >= logic.getTargetScore()) {
                stopAllTimers();
                logic.updateProgress();
                showLevelCompletionDialog();
            }
        }
    }

    private void showLevelCompletionDialog() {
        SwingUtilities.invokeLater(() -> {
            int currentLevel = logic.getLevel();
            String message;

            if (currentLevel < 5) {
                message = "Level " + currentLevel + " Completed!\nScore: " + logic.getScore();
            } else {
                message = "Level " + currentLevel + " Completed!\nScore: " + logic.getScore()
                        + "\n\n🎉 CONGRATULATIONS! 🎉\nYou have completed all levels!";
            }

            CustomDialog dialog = new CustomDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                    "Level Completed!", message);
            dialog.setVisible(true);

            if (parentPanel != null && cardLayout != null) {
                LevelSelectPanel levelSelectPanel = new LevelSelectPanel(parentPanel, cardLayout, logic);
                parentPanel.add(levelSelectPanel, "LevelSelectPanel");
                cardLayout.show(parentPanel, "LevelSelectPanel");
            }
        });
    }

    // === Painting ===
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // Draw holes
        int spacingX = (getWidth() - COLS * HOLE_WIDTH) / (COLS + 1);
        int spacingY = (getHeight() - 200 - ROWS * HOLE_HEIGHT) / (ROWS + 1);
        for (int i = 0; i < 9; i++) {
            int row = i / COLS, col = i % COLS;
            int x = spacingX + col * (HOLE_WIDTH + spacingX);
            int y = 200 + spacingY + row * (HOLE_HEIGHT + spacingY);
            g2.drawImage(holeImg, x, y, HOLE_WIDTH, HOLE_HEIGHT, null);
        }

        // Draw mole
        if (currentMole != null) currentMole.draw(g2);

        // Draw UI
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("Score: " + logic.getScore(), 20, 30);
        g2.drawString("Time Left: " + logic.getTimeLeft() + "s", 20, 60);

        // Draw target score in top right
        g2.drawString("Target: " + logic.getTargetScore(), getWidth() - 200, 30);

        if (!logic.isRunning()) {
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            String msg = "GAME OVER!";
            int width = g2.getFontMetrics().stringWidth(msg);
            g2.setColor(Color.RED);
            g2.drawString(msg, (getWidth() - width) / 2, 150);
        }

        // Draw mallet
        if (malletImg != null) {
            int x = mousePosition.x - malletImg.getWidth() / 2;
            int y = mousePosition.y - malletImg.getHeight() / 2;
            g2.drawImage(malletImg, x, y, null);
        }
    }

    // === Unused Mouse Events ===
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}