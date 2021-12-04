package Entities;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


//Duy
public class Enemy extends Entity{

    BufferedImage EnemyImage[] = new BufferedImage[3];
    public int actionLockCounter=0;
    boolean moving = false;
    int pixelCounter = 0;
    public Enemy(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        setEnemyImage();
        collision = true;
    }

    public void setEnemyImage() {
        UtilityTool uTool = new UtilityTool();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/sprite_sheet.png");
        for(int i=0;i<3;i++)
            EnemyImage[i] = uTool.scaleImage(sprite.getSubimage(96+i*32,0,32,32),64,64);
    }
    public void setAction() {

        actionLockCounter++;
        collision = false;
        gp.collisionChecker.checkTitle(this);

        if(actionLockCounter==60) {
            Random random = new Random();
            int i = random.nextInt(2) + 1;
            if (i <= 25) {
                direction = "up";
                moving = true;
            } else if (i <= 50) {
                direction = "down";
                moving = true;
            } else if (i <= 75) {
                direction = "right";
                moving = true;
            } else {
                direction = "left";
                moving = true;
            }
            actionLockCounter=0;
            spriteNum++;
        }
        if(actionLockCounter==30) {
            if(spriteNum>1) spriteNum=0;
        }
    }

    public void update() {
        setAction();
        collision = false;
        gp.collisionChecker.checkTitle(this);
        if (moving) {
            switch (direction) {
                case "up":
                    worldY -= (speed);
                    break;
                case "down":
                    worldY += (speed);
                    break;
                case "left":
                    worldX -= (speed);
                    break;
                case "right":
                    worldX += (speed);
                    break;
            }
            pixelCounter += speed;
            if (pixelCounter == 64) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (spriteNum == 0) {
            image = EnemyImage[1];
        }
        if (spriteNum == 1) {
            image = EnemyImage[2];
        }
        if (spriteNum == 2) {
            image = EnemyImage[0];
        }
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
            g2.drawImage(image, screenX, screenY, null);
            //g2.setColor(Color.red);
            //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            //Animation animation = new Animation(10, image);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(image, screenX, screenY, null);
        }

    }

}
