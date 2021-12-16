package Entities;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public GamePanel gp;
    public BufferedImage[] image;
    int worldX, worldY;
    int speed;
    String name;
    int x,y;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public boolean collision = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public abstract void draw(Graphics2D g2);

    abstract public Rectangle getBound();
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
