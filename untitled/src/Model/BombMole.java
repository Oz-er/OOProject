package Model;

import Control.GameLogic;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class BombMole extends Mole {
    public BombMole(int x, int y, BufferedImage image, int width, int height) {
        super(
                x - ((int)(width * 1.4) - width) / 2 - 5,   // adjusted X
                y - ((int)(height * 1.4) - height) / 2 - 2, // adjusted Y
                image,
                (int)(width * 1.6),                         // visual width
                (int)(height * 1.6),                        // visual height
                "electrode_sound.wav"
        );

//----------------FOR HITBOX RESIZE-------------------
        int margin = 20;
        hitbox = new Rectangle(
                this.x + margin,
                this.y + margin,
                this.width - 2 * margin,
                this.height - 2 * margin
        );
    }

    @Override
    public void onHit(GameLogic logic) {
        playHitSound();
        logic.gameOver();
        hide();
    }
}
