package Object;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedItem extends Item {
    // ID: 3
    public SpeedItem(GamePanel gp, int x, int y) {
        super(gp, x, y);

        image = new BufferedImage[1];

        BufferedImageLoader loader = new BufferedImageLoader();
        image[0] = loader.loadImage("../../Res/powerup_speed.png");

        UtilityTool uTool = new UtilityTool();
        image[0] = uTool.scaleImage(image[0], 64, 64);

    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

    @Override
    public Rectangle getBound() {
        return super.getBound();
    }
}
