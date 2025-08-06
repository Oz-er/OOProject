
package Control;

public class LevelConfig {
    public static int getTarget(int level) {
        return switch (level) {
            case 1 -> 200;
            case 2 -> 250;
            case 3 -> 300;
            case 4 -> 350;
            case 5 -> 400;
            default -> 200;
        };
    }

    public static int getMoleDelay(int level) {
        return switch (level) {
            case 1 -> 1200;
            case 2 -> 1000;
            case 3 -> 900;
            case 4 -> 850;
            case 5 -> 850;
            default -> 1200;
        };
    }

    public static int getHideDelay(int level) {
        return getMoleDelay(level); // Same in your case
    }
}
