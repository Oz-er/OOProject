package Control;

public class GameLogic {
    private int score = 0;
    private int timeLeft = 60;
    private boolean running = true;
    private int currentLevel = 1;
    private int maxUnlockedLevel = 1;
    private int targetScore = 200;

    public void setLevel(int level) {
        this.currentLevel = level;
        this.targetScore = LevelConfig.getTarget(level);
    }

    public int getLevel() {
        return currentLevel;
    }

    public int getMaxUnlockedLevel() {
        return maxUnlockedLevel;
    }

    public void updateProgress() {
        if (score >= targetScore && currentLevel == maxUnlockedLevel && maxUnlockedLevel < 5) {
            maxUnlockedLevel++;
        }
    }

    public void increaseScore(int points) {
        score += points;
    }

    public void decreaseScore(int points) {
        score = Math.max(0, score - points);
    }

    public void gameOver() {
        running = false;
    }

    public void reset() {
        score = 0;
        timeLeft = 60;
        running = true;
    }

    public void tickTime() {
        timeLeft--;
        if (timeLeft <= 0) gameOver();
    }

    public int getScore() {
        return score;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public boolean isRunning() {
        return running;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTimeLeft(int time) {
        this.timeLeft = time;
    }
}