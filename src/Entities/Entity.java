package Entities;

import Main.GamePanel;

import java.awt.*;

public abstract class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public int x,y;

    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public boolean collision = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    abstract public Rectangle getBound();
}
