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
                                entity.worldY == gp.objectManagement.obj[i].worldY + 32 &&
                                !gp.objectManagement.obj[i].name.equals("Crate")) {
                                entity.collision = true;
                            if (player) {
                                index = i;
                            }
                            }
                       // if(entity.solidArea.intersects(gp.objectManagement.obj[i].solidArea)) {

                        //}

                        break;
                    case "down":
                        if (entity.worldX == gp.objectManagement.obj[i].worldX &&
                                entity.worldY == gp.objectManagement.obj[i].worldY - 96 && !gp.objectManagement.obj[i].name.equals("Crate")) {
                            entity.collision = true;
                            if (player) {
                                index = i;
                            }
                        }
                        //if(entity.solidArea.intersects(gp.objectManagement.obj[i].solidArea))System.out.println("down");

                        break;
                    case "left":
                        if (entity.worldX == gp.objectManagement.obj[i].worldX + 64 &&
                                entity.worldY == gp.objectManagement.obj[i].worldY - 32 && !gp.objectManagement.obj[i].name.equals("Crate")) {
                            entity.collision = true;
                            if (player) {
                                index = i;
                            }
                        }
                       // if(entity.solidArea.intersects(gp.objectManagement.obj[i].solidArea))System.out.println("left");

                        break;
                    case "right":
                        if (entity.worldX == gp.objectManagement.obj[i].worldX - 64 &&
                                entity.worldY == gp.objectManagement.obj[i].worldY - 32 && !gp.objectManagement.obj[i].name.equals("Crate")) {
                            entity.collision = true;
                            if (player) {
                                index = i;
                            }
                        }
                       // if(entity.solidArea.intersects(gp.objectManagement.obj[i].solidArea))System.out.println("right");


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
