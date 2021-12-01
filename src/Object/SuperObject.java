package Object;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject {

    public BufferedImage[] image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(1, 1, 62, 62);
    public int solidAreaDefaultX = 1;
    public int solidAreaDefaultY = 1;


    public abstract void draw(Graphics2D g2, GamePanel gp);

}

