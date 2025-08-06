package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import Control.GameLogic;

import java.io.File;
import javax.sound.sampled.*;

public abstract class Mole {
    protected int x, y;
    protected int width, height;
    protected BufferedImage image;
    protected boolean visible = false;
    protected Clip hitSound;
    protected Rectangle hitbox;

    public Mole(int x, int y, BufferedImage image, int width, int height, String soundFile) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x, y, width, height);  // create hitbox

        loadSound(soundFile);
    }

    public void show() { visible = true; }

    public void hide() { visible = false; }

    public boolean isVisible() { return visible; }

    public void draw(Graphics g) {
        if (visible && image != null) {
            g.drawImage(image, x, y, width, height, null);
        }


        // Debug hitbox
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(255, 0, 0, 120));
        g2.draw(hitbox);
    }

    // Simple rectangular hit detection
    public boolean isHit(int mouseX, int mouseY) {
        if (!visible || hitbox == null) return false;
        return hitbox.contains(mouseX, mouseY);
    }

    // Optional: Update hitbox if mole is ever moved (not currently used)
//    public void setPosition(int newX, int newY) {
//        this.x = newX;
//        this.y = newY;
//        if (hitbox != null) {
//            hitbox.setBounds(newX, newY, width, height);
//        }
//    }

    private void loadSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("assets/" + soundFile)
            );
            hitSound = AudioSystem.getClip();
            hitSound.open(audioInputStream);
        } catch (Exception e) {
            System.out.println("Could not load sound " + soundFile + ": " + e.getMessage());
            hitSound = null;
        }
    }

    protected void playHitSound() {
        if (hitSound != null) {
            try {
                hitSound.setFramePosition(0);
                hitSound.start();
            } catch (Exception e) {
                System.out.println("Could not play hit sound: " + e.getMessage());
            }
        }
    }

    // What happens when this mole is hit
    public abstract void onHit(GameLogic logic);
}
