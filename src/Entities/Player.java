package Entities;

import Controller.BufferedImageLoader;
import Controller.KeyHandler;
import Controller.UtilityTool;
import Main.GamePanel;
import Graphics.TileManagement;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    TileManagement tileManagement;
    private BufferedImage[] playerImage = new BufferedImage[12];
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH, TileManagement tileManagement) {
        this.gp = gp;
        this.keyH = keyH;
        this.tileManagement = tileManagement;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(1, 33, 62, 62);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefautValue();
        setPlayerImage();
    }

    public void setDefautValue() {

        worldX = 64 * 1;
        worldY = 32 * 1;
        speed = 4;
        direction = "down";
    }

    public void setPlayerImage() {


        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/sprite_sheet.png");
        playerImage[0] = sprite.getSubimage(0,0,32,48);
        playerImage[1] = sprite.getSubimage(32,0,32,48);
        playerImage[2] = sprite.getSubimage(64,0,32,48);
        playerImage[3] = sprite.getSubimage(0,48,32,48);
        playerImage[4] = sprite.getSubimage(32,48,32,48);
        playerImage[5] = sprite.getSubimage(64,48,32,48);
        playerImage[6] = sprite.getSubimage(0,96,32,48);
        playerImage[7] = sprite.getSubimage(32,96,32,48);
        playerImage[8] = sprite.getSubimage(64,96,32,48);
        playerImage[9] = sprite.getSubimage(0,144,32,48);
        playerImage[10] = sprite.getSubimage(32,144,32,48);
        playerImage[11] = sprite.getSubimage(64,144,32,48);
        for (int i =0; i < 12; i++) {
            setUp(i);
        }
    }

    public void setUp(int index) {

        UtilityTool uTool = new UtilityTool();
        playerImage[index] = uTool.scaleImage(playerImage[index], 64, 48*2);
    }

    public void update() {

        if (moving == false) {

            if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {

                if (keyH.upPressed == true) {
                    direction = "up";
                    moving = true;
                } else if (keyH.downPressed == true) {
                    direction = "down";
                    moving = true;

                } else if (keyH.leftPressed == true) {
                    direction = "left";
                    moving = true;

                } else if (keyH.rightPressed == true) {
                    direction = "right";
                    moving = true;

                }

                //kiem tra va cham
                collision = false;
                gp.collisionChecker.checkTitle(this);

                //check object collision
                int objIndex = gp.collisionChecker.checkObject(this, true);


            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 0;
                    standCounter = 0;
                }
            }
        }
        if (moving) {
            //false
            if (collision == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 0) {
                    spriteNum = 1;
                }
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            pixelCounter += speed;
            if (pixelCounter == 64) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {

/*        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.titleSize, gp.titleSize);*/

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 0) {
                    image = playerImage[3];
                }
                if (spriteNum == 1) {
                    image = playerImage[4];
                }
                if (spriteNum == 2) {
                    image = playerImage[5];
                }
                break;
            case "down":
                if (spriteNum == 0) {
                    image = playerImage[0];
                }
                if (spriteNum == 1) {
                    image = playerImage[1];
                }
                if (spriteNum == 2) {
                    image = playerImage[2];
                }
                break;
            case "left":
                if (spriteNum == 0) {
                    image = playerImage[11];
                }
                if (spriteNum == 1) {
                    image = playerImage[10];
                }
                if (spriteNum == 2) {
                    image = playerImage[9];
                }
                break;
            case "right":
                if (spriteNum == 0) {
                    image = playerImage[6];
                }
                if (spriteNum == 1) {
                    image = playerImage[7];
                }
                if (spriteNum == 2) {
                    image = playerImage[8];
                }
                break;
        }

        int x = screenX;
        int y = screenY;
        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > tileManagement.mapCol * gp.tileSize - worldX) {
            x = gp.screenWidth - (tileManagement.mapCol * gp.tileSize - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if (bottomOffset > tileManagement.mapRow * gp.tileSize - worldY) {
            y = gp.screenHeight - (tileManagement.mapRow * gp.tileSize - worldY);
        }

        g2.drawImage(image, x, y, null);
        g2.setColor(Color.red);
        g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);
    }
}
