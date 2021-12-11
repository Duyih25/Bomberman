package Entities;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class YellowDevil extends Enemy {

    int extraSpeed = 0;
    public YellowDevil(GamePanel gp) {
        super(gp);
    }

    public void setEnemyImage() {
        name = "YellowDevil";
        UtilityTool uTool = new UtilityTool();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/sprite_sheet.png");
        for (int i = 0; i < 3; i++)
            EnemyImage[i] = uTool.scaleImage(sprite.getSubimage(96 + i * 32, 0, 32, 32), 64, 64);
    }

    public void setAction() {
        actionLockCounter++;
        collision = false;

        if (worldX > gp.player.worldX) {
            direction = "left";
            moving = true;
        }
        else if (worldX < gp.player.worldX) {
            direction = "right";
            moving = true;
        }


        if (worldY -31< gp.player.worldY) {
            direction = "down";
            moving = true;
        }
        if (worldY -31> gp.player.worldY) {
            direction = "up";
            moving = true;
        }
        if(Math.abs((gp.player.worldX * gp.player.worldX + gp.player.worldY* gp.player.worldY) - worldX*worldX - (worldY) * (worldY)) < 64*64*50){
            if(worldY%2==0) extraSpeed =2;
            else extraSpeed = 1;
        }
        else extraSpeed = 0;

        //kiem tra va cham
        //collision = false;
        //check object collision
        int objIndex = gp.collisionChecker.checkObject(this);


        if (actionLockCounter % 30==0) {
            if (spriteNum > 1) spriteNum = 0;
            spriteNum++;
        }
    }
    public void update() {
        setAction();
        collision = false;
        int objIndex = gp.collisionChecker.checkObject(this);

        if (moving && collision == false) {
            switch (direction) {
                case "up":
                    worldY -= (speed + extraSpeed);

                    break;
                case "down":
                    worldY += (speed + extraSpeed);

                    break;
                case "left":
                    worldX -= (speed + extraSpeed);

                    break;
                case "right":
                    worldX += (speed + extraSpeed);

                    break;
            }

            pixelCounter += (speed+extraSpeed);
            if (pixelCounter == 64) {
                moving = false;
                pixelCounter = 0;
            }
            objIndex = gp.collisionChecker.checkObjForEnemy(this);
            collideObj(objIndex);

            if(gp.collisionChecker.checkEntity(gp.player, this) == 0){
                gp.lose = true;
                collidePlayer(gp.player);
            }
        }


    }
    public void collideObj(int index) {
        if(index != 999) {
            String objName = gp.objectManagement.obj.get(index).name;
            if(objName.equals("Bullet")) {
                gp.objectManagement.obj.remove(index);
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
            g2.drawImage(image, screenX, screenY, null);
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            //Animation animation = new Animation(10, image);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(image, screenX, screenY, null);
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

}

