package Control;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicManager {
    private static MusicManager instance;
    private Clip backgroundClip;
    private boolean musicEnabled = true;

    private MusicManager() {
        loadBackgroundMusic();
    }

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    private void loadBackgroundMusic() {
        try {
            File musicFile = new File("assets/bg.wav");
            if (musicFile.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioIn);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Could not load background music: " + e.getMessage());
        }
    }

    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        if (backgroundClip != null) {
            if (enabled) {
                startBackgroundMusic();
            } else {
                stopBackgroundMusic();
            }
        }
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void startBackgroundMusic() {
        if (backgroundClip != null && musicEnabled) {
            if (!backgroundClip.isRunning()) {
                backgroundClip.setFramePosition(0);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    public void cleanup() {
        if (backgroundClip != null) {
            backgroundClip.close();
        }
    }
}