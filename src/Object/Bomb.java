package Object;

import Controller.UtilityTool;
import Graphics.Sprite;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;


public class Bomb extends SuperObject {

    int cycle = 0;
    int second;
    Timer timer;

    public Bomb() {
        collision = true;
        UtilityTool uTool = new UtilityTool();
        name = "Bomb";
        image = new BufferedImage[3];
        Sprite sprite = new Sprite("../../Res/bomb_sprite_sheet.png", 16, 16);
        image = sprite.getSpriteArray();
        for (int i = 0; i < image.length; i++) {
            image[i] = uTool.scaleImage(image[i], 64, 64);
        }
//        BufferedImageLoader loader = new BufferedImageLoader();
//        image = loader.loadImage("../../Res/bomb_sprite_sheet.png");
//        image = image.getSubimage(32, 0, 16, 16);
//        image = uTool.scaleImage(image, 64, 64);
    }

    public void setBomb(GamePanel gp) {
        this.worldX = gp.player.worldX + gp.player.solidArea.x;
        this.worldY = gp.player.worldY + gp.player.solidArea.y;
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

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
            g2.drawImage(image[gp.second % 3], screenX, screenY, null);
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            //Animation animation = new Animation(10, image);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(image[gp.second % 3], screenX, screenY , null);
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            //Animation animation = new Animation(10, image);
        }
    }
}
