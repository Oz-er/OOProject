package Model;
import Control.GameLogic;
import java.awt.image.BufferedImage;

public class BlackMole extends Mole {
    public BlackMole(int x, int y, BufferedImage image, int width, int height) {
        super(
                x - ((int)(width * 1.2) - width) / 2,  y - ((int)(height * 1.2) - height) / 2-9, image, (int)(width * 1.2)-3, (int)(height * 1.2)-3, "gengar_sound.wav"
        );

//        super(x, y, image, width, height,"pikachu_sound.wav");
    }

    @Override
    public void onHit(GameLogic logic) {
        logic.decreaseScore(5);
        playHitSound();
        hide();
    }
}