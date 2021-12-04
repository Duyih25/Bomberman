package Controller;

import Main.GamePanel;
import Object.*;

import java.awt.*;

public class ObjectManagement {
    GamePanel gp;
    KeyHandler keyH;

    public SuperObject[] obj = new SuperObject[50];
    public int currentObj = 1;
    int currentBomb = 0;
    int maxBombNum = 30;
    public int currentBullets = 5;

    public ObjectManagement(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    public void update() {
        updateBomb();
        updateBullet();
    }
    public void updateBullet() {
        if(keyH.bulletPressed && currentBullets >0) {
            obj[currentObj] = new Bullet(gp);
            Bullet k = (Bullet) obj[currentObj];
            if(keyH.facingUp) k.up=true;
            if(keyH.facingDown) k.down = true;    //Duy
            if(keyH.facingRight) k.right = true;
            if(keyH.facingLeft) k.left = true;

            currentObj++;
            currentBullets--;
            keyH.bulletPressed = false;
        }
    }
    public void updateBomb() {
        //DROP BOMB
        if (keyH.bombPressed && currentBomb <= maxBombNum) {
            int playerLeftWorldX = gp.player.worldX + gp.player.solidArea.x;
            int playerTopWorldY = gp.player.worldY + gp.player.solidArea.y;

            int bombTileCol = playerLeftWorldX / gp.tileSize;
            int bombTileRow = playerTopWorldY / gp.tileSize;
            //System.out.println(bombTileCol + " " + bombTileRow);

            int bombTileNum = gp.tileManagement.mapTileNum[bombTileCol][bombTileCol];
            if (gp.tileManagement.tiles[bombTileNum].available) {
                //SET BOMB TO PLAYER POSITION
                obj[currentObj] = new Bomb();
                obj[currentObj].worldX = (gp.player.worldX + 32) / 64 * 64;
                obj[currentObj].worldY = (gp.player.worldY + 32) / 64 * 64;

                currentObj++;
                currentBomb++;
                keyH.bombPressed = false;
            }
        }
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                //System.out.println(obj[i].solidArea.width);
            }
        }
        //System.out.println(currentBomb);
    }

    public void render(Graphics2D g2) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null)
                obj[i].draw(g2, gp);
        }
    }
}
