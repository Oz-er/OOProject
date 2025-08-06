package Model;
import Control.GameLogic;
import java.awt.image.BufferedImage;

public class RegularMole extends Mole {
    public RegularMole(int x, int y, BufferedImage image, int width, int height) {
        super(x, y, image, width, height,"pikachu_sound.wav");
    }

    @Override
    public void onHit(GameLogic logic) {
        logic.increaseScore(10);
        playHitSound();
        hide();
    }
}