package Controller;

import Main.GamePanel;
import Object.*;

import java.awt.*;

public class ObjectManagement {
    GamePanel gp;
    KeyHandler keyH;

    public SuperObject[] obj = new SuperObject[50];
    public int currentObj = 1;
    public int currentBomb = 0;
    public int maxBombNum = 3;
    public int currentBullets = 5;

    public ObjectManagement(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    public void update() {
        updateBomb();
        updateItem();
        updateBullet();
    }

    private void updateItem() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                if (obj[i].name.equals("Crate")) {
                    if (gp.collisionChecker.checkObject(obj[i]) == 0) {
                        obj[i] = null;
                    }
                }
            }
        }
    }

    private void updateBullet() {
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
    private void updateBomb() {
        updateExistingBombs();
        //DROP BOMB
        if (keyH.bombPressed && currentBomb < maxBombNum) {
            int playerLeftWorldX = gp.player.worldX + gp.player.solidArea.x;
            int playerTopWorldY = gp.player.worldY + gp.player.solidArea.y;

            int bombTileCol = playerLeftWorldX / gp.tileSize;
            int bombTileRow = playerTopWorldY / gp.tileSize;
            //System.out.println(bombTileCol + " " + bombTileRow);

            int bombTileNum = gp.tileManagement.mapTileNum[bombTileCol][bombTileRow];
            if (gp.tileManagement.tiles[bombTileNum].available) {
                //SET BOMB TO PLAYER POSITION
                obj[currentObj] = new Bomb(gp);
                obj[currentObj].worldX = (gp.player.worldX + 32) / 64 * 64;
                obj[currentObj].worldY = (gp.player.worldY + 32) / 64 * 64;
                obj[currentObj].mapPosition = bombTileNum;
                currentObj++;
                currentBomb++;
                //gp.tileManagement.tiles[bombTileNum].available = false;
                //keyH.bombPressed = false;
            }
            //keyH.bombPressed = false;
        }

        if (keyH.bombPressed) keyH.bombPressed = false;
        //System.out.println(currentBomb);
    }

    private void updateExistingBombs() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                if (obj[i].name.equals("Bomb")) {
                    Bomb check = (Bomb) obj[i];
                    check.update();
                    if (check.isExploded() && check.explosionTime == 120) {
                        //gp.tileManagement.tiles[obj[i].mapPosition].available = true;
                        obj[i] = null;
                        for (Flame flame : check.flames) {
                            obj[currentObj++] = flame;
                        }
                        currentBomb--;
                    }
                } else if (obj[i].name.equals("Flame")) {
                    Flame flame = (Flame) obj[i];
                    flame.update();
                    if (flame.explosionTime <= 0) {
                        obj[i] = null;
                    }
                }
            }
        }
    }

    public boolean checkNearBomb(int x, int y) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                if (obj[i].name.equals("Bomb") && obj[i].worldX == x && obj[i].worldY == y) {
                    Bomb check = (Bomb) obj[i];
                    check.countdown = 0;
                    return true;
                }
            }
        }
        return false;
    }

    public void render(Graphics2D g2) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null)
                obj[i].draw(g2);
        }
    }
}
