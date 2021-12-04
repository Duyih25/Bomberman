package Object;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject {

    public BufferedImage[] image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(16, 16, 32, 32);
    public int solidAreaDefaultX = 16;
    public int solidAreaDefaultY = 16;


    public abstract void draw(Graphics2D g2, GamePanel gp);
    public abstract Rectangle getBound();
}

