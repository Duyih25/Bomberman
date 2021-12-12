package Entities;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;


//Duy
public abstract class Enemy extends Entity{

    BufferedImage EnemyImage[] = new BufferedImage[10];
    public int actionLockCounter=0;
    boolean moving = false;
    boolean collidingPlayer = false;
    int pixelCounter = 0;
    int screenX,screenY;
    public Enemy(GamePanel gp) {
        super(gp);
        name = "Enemy";
        direction = "down";
        speed = 1;
        setEnemyImage();
        collision = true;
        solidArea = new Rectangle(16, 16, 48, 48);
    }

    public abstract void setEnemyImage();
    public abstract void setAction();
    public abstract void update();

    public void collideObj(int index) {
        if(index != 999) {
            String objName = gp.objectManagement.obj.get(index).name;
            if(objName.equals("Bullet")) {
                gp.objectManagement.obj.remove(index);
            }
        }
    }
    public void collidePlayer(Player player) {
        if (player.playerLives > 1) {
            if (player.relievingTime == 70) {
                player.playerLives--;
                player.relievingTime--;
            }
        } else gp.lose = true;
        System.out.println(player.playerLives);

    }
    public void draw(Graphics2D g2) {

    }
    public Rectangle getBound() {
        return new Rectangle(worldX + 16 , worldY + 16 , 40, 40);
    }


}
