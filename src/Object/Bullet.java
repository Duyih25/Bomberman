package Object;

import Main.GamePanel;

import java.awt.*;
//Duy
public class Bullet extends SuperObject {

    GamePanel gp;
    public boolean down,up,right,left=false;

    public Bullet(GamePanel gp) {
        worldX = gp.player.worldX + gp.player.solidAreaDefaultX;
        worldY = gp.player.worldY + gp.player.solidAreaDefaultX;
        name = "bullet";
        this.gp = gp;
    }


    public void draw(Graphics2D g2, GamePanel gp) {
        if(right) worldX = worldX +1;
        if(left) worldX = worldX -1;
        if(up) worldY -= 1;
        if(down) worldY +=1;
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

            g2.setColor(Color.yellow);
            g2.fillOval(screenX, screenY+48, 16, 16);
            //Animation animation = new Animation(10, image);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.setColor(Color.yellow);
            g2.fillOval(screenX, screenY+48, 16, 16);
            //Animation animation = new Animation(10, image);
        }
    }
}