package Object;

import Main.GamePanel;

import java.awt.*;
//Duy
public class Bullet extends SuperObject {
    public boolean down,up,right,left=false;

    public Bullet(GamePanel gp) {
        super(gp);
        setWorldX(gp.player.getWorldX() + gp.player.solidAreaDefaultX);
        setWorldY(gp.player.getWorldY() + gp.player.solidAreaDefaultX);
        setName("Bullet");
    }


    public void draw(Graphics2D g2) {
        if(right) setWorldX(getWorldX() + 6);
        if(left) setWorldX(getWorldX() - 6);
        if(up) setWorldY(getWorldY() - 6);
        if(down) setWorldY(getWorldY() + 6);
        screenX = getWorldX() - gp.player.getWorldX() + gp.player.screenX;
        screenY = getWorldX() - gp.player.getWorldY() + gp.player.screenY;

        if (gp.player.screenX > gp.player.getWorldX()) {
            screenX = getWorldX();
        }
        if (gp.player.screenY > gp.player.getWorldY()) {
            screenY = getWorldY();
        }
        int rightOffset = gp.screenWidth - gp.player.screenX;
        if (rightOffset > (gp.maxWorldCol * gp.tileSize) - gp.player.getWorldX()) {
            screenX = gp.screenWidth - ((gp.maxWorldCol * gp.tileSize) - getWorldX());
        }
        int bottomOffset = gp.screenHeight - gp.player.screenY;
        if (bottomOffset > (gp.maxWorldRow * gp.tileSize) - gp.player.getWorldX()) {
            screenY = gp.screenHeight - ((gp.maxWorldRow * gp.tileSize) - getWorldY());
        }

        if(getWorldX() + gp.tileSize > gp.player.getWorldX() - gp.player.screenX &&
                getWorldX() - gp.tileSize < gp.player.getWorldX() + gp.player.screenX &&
                getWorldY() + gp.tileSize > gp.player.getWorldY() - gp.player.screenY &&
                getWorldY() - gp.tileSize < gp.player.getWorldY() + gp.player.screenY) {

            g2.setColor(Color.yellow);
            g2.fillOval(screenX+24, screenY+48, 32, 32);
            //Animation animation = new Animation(10, image);
        } else if(gp.player.screenX > gp.player.getWorldX() ||
                gp.player.screenY > gp.player.getWorldY() ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.getWorldX() ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.getWorldY() ) {
            g2.setColor(Color.yellow);
            g2.fillOval(screenX+24, screenY+48, 32, 32);
            //Animation animation = new Animation(10, image);
        }
    }
    public Rectangle getBound() {
        return new Rectangle(getWorldX() + 24, getWorldY() + 48, 32, 32);
    }
}
