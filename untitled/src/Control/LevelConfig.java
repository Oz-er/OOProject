
package Control;

public class LevelConfig {
    public static int getTarget(int level) {
        return switch (level) {
            case 1 -> 250;
            case 2 -> 400;
            case 3 -> 500;
            case 4 -> 600;
            case 5 -> 800;
            default -> 200;
        };
    }

    public static int getMoleDelay(int level) {
        return switch (level) {
            case 1 -> 1000;
            case 2 -> 700;
            case 3 -> 650;
            case 4 -> 600;
            case 5 -> 550;
            default -> 1200;
        };
    }

    public static int getHideDelay(int level) {
        return getMoleDelay(level); // Same in your case
    }
}
