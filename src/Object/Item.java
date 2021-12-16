package Object;

import Main.GamePanel;

import java.awt.*;

public class Item extends SuperObject {
    public int waitingTime = 22;

    public Item(GamePanel gp, int x, int y) {
        super(gp);
        this.setWorldX(x);
        this.setWorldY(y);
        this.setName("Item");
    }



    @Override
    public void draw(Graphics2D g2) {
        screenX = getWorldX() - gp.player.getWorldX() + gp.player.screenX;
        screenY = getWorldY() - gp.player.getWorldY() + gp.player.screenY;

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
        if (bottomOffset > (gp.maxWorldRow * gp.tileSize) - gp.player.getWorldY()) {
            screenY = gp.screenHeight - ((gp.maxWorldRow * gp.tileSize) - getWorldY());
        }

        if(getWorldX() + gp.tileSize > gp.player.getWorldX() - gp.player.screenX &&
                getWorldX() - gp.tileSize < gp.player.getWorldX() + gp.player.screenX &&
                getWorldY() + gp.tileSize > gp.player.getWorldY() - gp.player.screenY &&
                getWorldY() - gp.tileSize < gp.player.getWorldY() + gp.player.screenY) {

            g2.drawImage(image[0], screenX, screenY , null);
        } else if(gp.player.screenX > gp.player.getWorldX() ||
                gp.player.screenY > gp.player.getWorldY() ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.getWorldX() ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.getWorldY() ) {
            g2.drawImage(image[0], screenX, screenY, null);
        }
        //g2.setColor(Color.white);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        //Animation animation = new Animation(10, image);
    }

    public Rectangle getBound() {
        //return null;
        return new Rectangle(getWorldX(), getWorldY(), solidArea.width, solidArea.height);
    }
}
