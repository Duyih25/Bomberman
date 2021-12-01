package Main;

import Entities.Entity;

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
                tileNum1 = gp.tileManagement.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManagement.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileManagement.tiles[tileNum1].collision == true || gp.tileManagement.tiles[tileNum2].collision == true) {
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (int) ((entityBottomWorldY + entity.speed) / gp.tileSize);
                tileNum1 = gp.tileManagement.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManagement.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManagement.tiles[tileNum1].collision == true || gp.tileManagement.tiles[tileNum2].collision == true) {
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (int) ((entityLeftWorldX - entity.speed) / gp.tileSize);
                tileNum1 = gp.tileManagement.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManagement.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileManagement.tiles[tileNum1].collision == true || gp.tileManagement.tiles[tileNum2].collision == true) {
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (int) ((entityRightWorldX + entity.speed) / gp.tileSize);
                tileNum1 = gp.tileManagement.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManagement.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManagement.tiles[tileNum1].collision == true || gp.tileManagement.tiles[tileNum2].collision == true) {
                    entity.collision = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX =  entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int index = 999;
        for (int i = 0; i < gp.objectManagement.obj.length; i++) {
            if (gp.objectManagement.obj[i] != null) {

                //get entity solid Area
//                entity.solidArea.x = entity.worldX + entity.solidArea.x;
//                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //get object solid Area
//                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
//                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        if (entityTopWorldY > gp.objectManagement.obj[i].worldY + gp.objectManagement.obj[i].solidArea.height &&
                        gp.objectManagement.obj[i].worldX < entityRightWorldX &&
                        gp.objectManagement.obj[i].worldX + gp.objectManagement.obj[i].solidArea.width > entityLeftWorldX &&
                        entityTopWorldY - (gp.objectManagement.obj[i].worldY + gp.objectManagement.obj[i].solidArea.height)
                                < 64) {
                            if (gp.objectManagement.obj[i].collision == true) {
                                entity.collision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        if (entityBottomWorldY < gp.objectManagement.obj[i].worldY &&
                        gp.objectManagement.obj[i].worldX < entityRightWorldX &&
                        gp.objectManagement.obj[i].worldX + gp.objectManagement.obj[i].solidArea.width > entityLeftWorldX &&
                        gp.objectManagement.obj[i].worldY - entityTopWorldY < 64) {
                            if (gp.objectManagement.obj[i].collision == true) {
                                entity.collision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        if (entityLeftWorldX > gp.objectManagement.obj[i].worldX + gp.objectManagement.obj[i].solidArea.width &&
                        gp.objectManagement.obj[i].worldY < entityBottomWorldY &&
                        gp.objectManagement.obj[i].worldY + gp.objectManagement.obj[i].solidArea.height > entityTopWorldY &&
                        entityLeftWorldX - (gp.objectManagement.obj[i].worldX + gp.objectManagement.obj[i].solidArea.width) < 64) {
                            if (gp.objectManagement.obj[i].collision == true) {
                                entity.collision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        if (entityRightWorldX < gp.objectManagement.obj[i].worldX &&
                        gp.objectManagement.obj[i].worldY < entityTopWorldY &&
                        gp.objectManagement.obj[i].worldY + gp.objectManagement.obj[i].solidArea.height > entityBottomWorldY &&
                        gp.objectManagement.obj[i].worldX - entityRightWorldX < 64) {
                            if (gp.objectManagement.obj[i].collision == true) {
                                entity.collision = true;
                            }
                            if (player == true) {
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
}
