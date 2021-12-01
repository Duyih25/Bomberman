package Entities;

import java.awt.*;

public abstract class Entity {

    public int worldX, worldY;
    public int speed;

    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle solidArea;
    public boolean collision = false;




}
