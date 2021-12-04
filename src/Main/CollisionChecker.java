package Main;

import Entities.Enemy;
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
                                entity.worldY == gp.objectManagement.obj[i].worldY + 32) {
                            if(!gp.objectManagement.obj[i].name.equals("bullet"))entity.collision = true;
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
                            if(!gp.objectManagement.obj[i].name.equals("bullet")) entity.collision = true;
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
                            if(!gp.objectManagement.obj[i].name.equals("bullet")) entity.collision = true;
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
                            if(!gp.objectManagement.obj[i].name.equals("bullet")) entity.collision = true;
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

    public int checkEntity(Entity entity, Enemy[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {


                switch (entity.direction) {
                    case "up":
                        if (entity.worldX == target[i].worldX &&
                                entity.worldY == target[i].worldY + 32) {
                            if(!gp.objectManagement.obj[i].name.equals("bullet"))entity.collision = true;
                            index = i;
                        }


                        break;
                    case "down":
                        if (entity.worldX == target[i].worldX &&
                                entity.worldY == target[i].worldY - 96) {
                            index = i;
                        }


                        break;
                    case "left":
                        if (entity.worldX == target[i].worldX + 64 &&
                                entity.worldY == target[i].worldY - 32) {
                            index =i;
                        }


                        break;
                    case "right":
                        if (entity.worldX == target[i].worldX - 64 &&
                                entity.worldY == target[i].worldY - 32) {
                            index = i;
                        }

                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
               target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;

            }
        }

        return index;
    }

    public void checkPlayer(Entity entity) {
        entity.solidArea.x= entity.worldX+entity.solidArea.x;
        entity.solidArea.y= entity.worldY+entity.solidArea.y;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        /**
         *
         *
         *
         *
         *
         *
         *
         *
         * */
    }

}
