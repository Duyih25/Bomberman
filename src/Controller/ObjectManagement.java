package Controller;

import Main.GamePanel;
import Object.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManagement {
    GamePanel gp;
    KeyHandler keyH;

    public ArrayList<SuperObject> obj = new ArrayList<>();
    public int currentBomb = 0;
    public int maxBombNum = 3;
    public int maxBombRadius = 1;
    public int currentBullets = 10;
    public boolean getSpeedItem = false;
    public Bomb previousBomb = null;
    public ArrayList<Item> waitingItem = new ArrayList<>();
    public ArrayList<Block> blockList = new ArrayList<>();

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
        for (int i = 0; i < waitingItem.size(); i++) {
            if (waitingItem.get(i).waitingTime > 0) waitingItem.get(i).waitingTime--;
            else {
                obj.add(waitingItem.get(i));
                waitingItem.remove(i);
            }
        }

        for (int i = 0; i < obj.size(); i++) {
            int index = gp.collisionChecker.checkObject(obj.get(i));
            if (obj.get(i).name.equals("Item")) {
                //System.out.println("I" + index);
                Item check = (Item) obj.get(i);
                if (index != 999) {
                    obj.remove(i);
                }

            }
        }

        //ListBlock
        for (int i = 0; i < blockList.size(); i++) {
            Block check = blockList.get(i);
            int index = gp.collisionChecker.checkObject(blockList.get(i));
            collideObj(index);
            blockList.get(i).update();
            if (check.destroyed) {
                if (check.destroyingTime > 0) {
                    check.destroyingTime--;
                } else blockList.remove(i);
            }
            if (index != 999 && !check.destroyed) {
                System.out.println("B" + index);
                if (blockList.get(i).portal) {
                    waitingItem.add(new Portal(gp, blockList.get(i).worldX, blockList.get(i).worldY)); //them portal
                } else {
                    Random random = new Random();
                    int func;
                    if (!getSpeedItem) func = random.nextInt(4) + 1;
                    else func = random.nextInt(3) + 1;
                    if (func == 1) {
                        waitingItem.add(new BombItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                    } else if (func == 2) {
                        waitingItem.add(new FlameItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                    } else if (func == 3){
                        waitingItem.add(new CrateItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                    } else if (func == 4) {
                        waitingItem.add(new SpeedItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                        gp.objectManagement.getSpeedItem = true;
                    }
                }
                blockList.remove(i);
                check.destroyed = true;
                check.spriteNum = 3;

            }

        }
    }
    public void collideObj(int index) {
        if(index != 999) {
            String objName = gp.objectManagement.obj.get(index).name;
            if(objName.equals("Bullet")) {
                gp.objectManagement.obj.remove(index);
            }
        }
    }

    private void updateBullet() {
        if(keyH.bulletPressed && currentBullets >0) {
            obj.add(new Bullet(gp));
            Bullet k = (Bullet) obj.get(obj.size() - 1);
            if(keyH.facingUp) k.up=true;
            if(keyH.facingDown) k.down = true;    //Duy
            if(keyH.facingRight) k.right = true;
            if(keyH.facingLeft) k.left = true;

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
            if (previousBomb == null || previousBomb.worldX != (gp.player.worldX + 32) / 64 * 64 ||
                    previousBomb.worldY != (gp.player.worldY + 32) / 64 * 64) {
                //SET BOMB TO PLAYER POSITION
                obj.add(new Bomb(gp));
                obj.get(obj.size() - 1).worldX = (gp.player.worldX + 32) / 64 * 64;
                obj.get(obj.size() - 1).worldY = (gp.player.worldY + 32) / 64 * 64;
                //obj[currentObj].mapPosition = bombTileNum;
                //obj[currentObj].collision = true;
                currentBomb++;
                previousBomb = (Bomb) obj.get(obj.size() - 1);
                //gp.tileManagement.tiles[bombTileNum].available = false;
                //keyH.bombPressed = false;

                System.out.println(previousBomb.worldX);
                System.out.println(previousBomb.worldY);
            }
        }

        if (keyH.bombPressed) keyH.bombPressed = false;
        //System.out.println(currentBomb);
    }

    private void updateExistingBombs() {
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).name.equals("Bomb")) {
                Bomb check = (Bomb) obj.get(i);
                check.update();
                if (check.isExploded() && check.explosionTime == 120) {
                    //gp.tileManagement.tiles[obj[i].mapPosition].available = true;
                    obj.remove(i);
                    for (Flame flame : check.flames) {
                        obj.add(flame);
                    }
                    currentBomb--;
                    if (currentBomb == 0) previousBomb = null;

                }
            } else if (obj.get(i).name.equals("Flame")) {
                Flame flame = (Flame) obj.get(i);
                flame.update();
                for (FlameSegment fs : flame.flameSegments) {
                    if (gp.player.getBound().intersects(fs.getBound())) {
                        if (gp.player.playerLives > 0) {
                            if (gp.player.relievingTime == 70) {
                                gp.player.playerLives--;
                                gp.player.relievingTime--;
                            }
                        } else gp.lose = true;
                        System.out.println(gp.player.playerLives);
                    }
                }
                if (flame.explosionTime <= 0) {
                    obj.remove(i);
                    if (currentBomb == 0) previousBomb = null;
                }
            }
        }
    }

    public boolean checkNearBomb(int x, int y) {
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).name.equals("Bomb") &&
                    obj.get(i).worldX == x && obj.get(i).worldY == y) {
                Bomb check = (Bomb) obj.get(i);
                check.countdown = 0;
                return true;
            }
        }
        return false;
    }

    public void render(Graphics2D g2) {
        for (int i = 0; i < blockList.size(); i++) {
            blockList.get(i).draw(g2);
        }

        for (int i = 0; i < obj.size(); i++) {
            if (!obj.get(i).name.equals("Item"))
                obj.get(i).draw(g2);
        }
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).name.equals("Item"))
                obj.get(i).draw(g2);
        }

    }
}
