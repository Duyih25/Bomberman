package Main;

import Entities.Enemy;
import Entities.Entity;
import Entities.Player;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTitle(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX =  entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol =  (entityLeftWorldX / gp.tileSize);
        int entityRightCol=  (entityRightWorldX / gp.tileSize);
        int entityTopRow =  (entityTopWorldY / gp.tileSize);
        int entityBottomRow =  (entityBottomWorldY / gp.tileSize);

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (int) ((entityTopWorldY - entity.speed) / gp.tileSize);
                if(gp.tileManagement.tilesMap[entityLeftCol][entityTopRow].collision ||
                        gp.tileManagement.tilesMap[entityRightCol][entityTopRow].collision) {
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (int) ((entityBottomWorldY + entity.speed) / gp.tileSize);

                if(gp.tileManagement.tilesMap[entityLeftCol][entityBottomRow].collision ||
                        gp.tileManagement.tilesMap[entityRightCol][entityBottomRow].collision) {
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (int) ((entityLeftWorldX - entity.speed) / gp.tileSize);
                if(gp.tileManagement.tilesMap[entityLeftCol][entityTopRow].collision ||
                        gp.tileManagement.tilesMap[entityLeftCol][entityBottomRow].collision) {
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (int) ((entityRightWorldX + entity.speed) / gp.tileSize);
                if(gp.tileManagement.tilesMap[entityRightCol][entityTopRow].collision ||
                        gp.tileManagement.tilesMap[entityRightCol][entityBottomRow].collision) {
                    entity.collision = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {

        int index = 999;
        for (int i = 0; i < gp.objectManagement.obj.length; i++) {
            if (gp.objectManagement.obj[i] != null) {


                switch (entity.direction) {
                    case "up":
                        if (entity.worldX == gp.objectManagement.obj[i].worldX &&
                                entity.worldY == gp.objectManagement.obj[i].worldY + 32) {
                            if(!gp.objectManagement.obj[i].name.equals("Bullet"))entity.collision = true;
                            else {
                                if(entity.getClass().equals("Enemy")) {
                                    return i;
                                }
                            }
                            if(gp.objectManagement.obj[i].name.equals("Crate")) {
                                gp.objectManagement.currentBullets+=2;
                            }
                            if (player) {
                                index = i;
                            }
                            }


                        break;
                    case "down":
                        if (entity.worldX == gp.objectManagement.obj[i].worldX &&
                                entity.worldY == gp.objectManagement.obj[i].worldY - 96) {
                            if(!gp.objectManagement.obj[i].name.equals("Bullet")) entity.collision = true;
                            else {
                                if(entity.getClass().equals("Enemy")) {
                                    return i;
                                }
                            }
                            if(gp.objectManagement.obj[i].name.equals("Crate")) {
                                gp.objectManagement.currentBullets+=2;
                            }
                            if (player) {
                                index = i;
                            }
                        }


                        break;
                    case "left":
                        if (entity.worldX == gp.objectManagement.obj[i].worldX + 64 &&
                                entity.worldY == gp.objectManagement.obj[i].worldY - 32) {
                            if(!gp.objectManagement.obj[i].name.equals("Bullet")) entity.collision = true;
                            else {
                                if(entity.getClass().equals("Enemy")) {
                                    return i;
                                }
                            }
                            if(gp.objectManagement.obj[i].name.equals("Crate")) {
                                gp.objectManagement.currentBullets+=2;
                            }
                            if (player) {
                                index = i;
                            }
                        }


                        break;
                    case "right":
                        if (entity.worldX == gp.objectManagement.obj[i].worldX - 64 &&
                                entity.worldY == gp.objectManagement.obj[i].worldY - 32) {
                            if(!gp.objectManagement.obj[i].name.equals("Bullet")) entity.collision = true;
                            else {
                                if(entity.getClass().equals("Enemy")) {
                                    return i;
                                }
                            }
                            if(gp.objectManagement.obj[i].name.equals("Crate")) {
                                gp.objectManagement.currentBullets+=2;
                            }
                            if (player) {
                                index = i;
                            }
                        }



                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objectManagement.obj[i].solidArea.x = gp.objectManagement.obj[i].solidAreaDefaultX;
                gp.objectManagement.obj[i].solidArea.y = gp.objectManagement.obj[i].solidAreaDefaultY;

            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Enemy target) {
        int index = 999;

                switch (entity.direction) {
                    case "up":
                        if (entity.getBound().intersects(target.getBound())) {
                            //if(!gp.objectManagement.obj[i].name.equals("bullet"))entity.collision = true;
                            index = 0;
                        }


                        break;
                    case "down":
                        if (entity.getBound().intersects(target.getBound())) {
                            //System.out.println("hehe");
                            index = 0;
                        }


                        break;
                    case "left":
                        if (entity.getBound().intersects(target.getBound())) {
                            //System.out.println("hehe");
                            index =0;
                        }


                        break;
                    case "right":
                        if (entity.getBound().intersects(target.getBound())) {
                            //System.out.println("hehe");
                            index = 0;
                        }

                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
               target.solidArea.x = target.solidAreaDefaultX;
                target.solidArea.y = target.solidAreaDefaultY;



        return index;
    }

    public int checkObjForEnemy(Enemy enemy) {
        int index = 999;
        for (int i = 0; i < gp.objectManagement.obj.length; i++) {
            if (gp.objectManagement.obj[i] != null) {
                switch (enemy.direction) {
                    case "up":
                        if (gp.objectManagement.obj[i].name.equals("Bullet") && enemy.getBound().intersects(gp.objectManagement.obj[i].getBound())) {
                            //if(!gp.objectManagement.obj[i].name.equals("bullet"))entity.collision = true;
                            for(int j=0;j<gp.npc.length;j++) {
                                if(enemy==gp.npc[j]) gp.npc[j] = null;

                            }
                            index = i;
                        }


                        break;
                    case "down":
                        if (gp.objectManagement.obj[i].name.equals("Bullet") && enemy.getBound().intersects(gp.objectManagement.obj[i].getBound())) {
                            index = i;
                            for(int j=0;j<gp.npc.length;j++) {
                                if(enemy==gp.npc[j]) gp.npc[j] = null;

                            }
                        }


                        break;
                    case "left":
                        if (gp.objectManagement.obj[i].name.equals("Bullet") && enemy.getBound().intersects(gp.objectManagement.obj[i].getBound())) {
                            index =i;
                            for(int j=0;j<gp.npc.length;j++) {
                                if(enemy==gp.npc[j]) gp.npc[j] = null;

                            }

                        }


                        break;
                    case "right":
                        if (gp.objectManagement.obj[i].name.equals("Bullet") && enemy.getBound().intersects(gp.objectManagement.obj[i].getBound())) {
                            index = i;
                            for(int j=0;j<gp.npc.length;j++) {
                                if(enemy==gp.npc[j]) gp.npc[j] = null;

                            }

                        }

                        break;
                }
                //enemy.solidArea.x = enemy.solidAreaDefaultX;
                //enemy.solidArea.y = enemy.solidAreaDefaultY;
                gp.objectManagement.obj[i].solidArea.x = gp.objectManagement.obj[i].solidAreaDefaultX;
                gp.objectManagement.obj[i].solidArea.y = gp.objectManagement.obj[i].solidAreaDefaultY;

            }
        }

        return index;
    }
}
