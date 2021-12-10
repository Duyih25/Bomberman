package Entities;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import Graphics.Sprite;


//Duy
public class Enemy extends Entity{

    BufferedImage EnemyImage[] = new BufferedImage[3];
    public int actionLockCounter=0;
    boolean moving = false;
    int pixelCounter = 0;
    int screenX,screenY;
    public Enemy(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        setEnemyImage();
        collision = true;
        solidArea = new Rectangle(1, 1, 62, 62);
    }

    public void setEnemyImage() {

    }
    public void setAction() {

    }

    public void update() {

    }

    public void collideObj(int index) {
        if(index != 999) {
            String objName = gp.objectManagement.obj.get(index).name;
            if(objName.equals("Bullet")) {
                gp.objectManagement.obj.remove(index);
            }
        }
    }
    public void collidePlayer(Player player) {
        System.out.println("OK");

    }
    public void draw(Graphics2D g2) {

    }
    public Rectangle getBound() {
        return new Rectangle(screenX + solidArea.x, screenY + solidArea.y, 64, 64);
    }


}
