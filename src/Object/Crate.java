package Object;

import Controller.UtilityTool;
import Main.GamePanel;
import Graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
//Duy
public class Crate extends SuperObject {

    public Crate(GamePanel gp) {
        super(gp);
        image = new BufferedImage[1];
        name = "Crate";
        try {
            image[0] = ImageIO.read(getClass().getResourceAsStream("../../Res/level2.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (gp.player.screenX > gp.player.worldX) {
            screenX = worldX;
        }
        if (gp.player.screenY > gp.player.worldY) {
            screenY = worldY;
        }

        int rightOffset = gp.screenWidth - gp.player.screenX;
        if (rightOffset > (gp.maxWorldCol * gp.tileSize) - gp.player.worldX) {
            screenX = gp.screenWidth - ((gp.maxWorldCol * gp.tileSize) - worldX);
        }
        int bottomOffset = gp.screenHeight - gp.player.screenY;
        if (bottomOffset > (gp.maxWorldRow * gp.tileSize) - gp.player.worldY) {
            screenY = gp.screenHeight - ((gp.maxWorldRow * gp.tileSize) - worldY);
        }

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image[0], screenX, screenY , null);
        }
        g2.setColor(Color.white);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        //Animation animation = new Animation(10, image);
    }
    public Rectangle getBound() {
        //return null;
        return new Rectangle(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
