package Object;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;
import Graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends SuperObject {
    public int waitingTime = 29;

    public Item(GamePanel gp, int x, int y) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        this.name = "Item";

        image = new BufferedImage[3];

        BufferedImageLoader loader = new BufferedImageLoader();
        image[0] = loader.loadImage("../../Res/powerup_bombs.png");
        image[1] = loader.loadImage("../../Res/powerup_flames.png");
        image[2] = loader.loadImage("../../Res/powerup_speed.png");

        UtilityTool uTool = new UtilityTool();
        for (int i = 0; i < image.length; i++) {
            image[i] = uTool.scaleImage(image[i], 64, 64);
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
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(image[0], screenX, screenY, null);
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
