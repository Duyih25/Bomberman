package Main;

import Entities.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTitle(Entity entity) {
        double entityLeftWorldX = entity.worldX + entity.solidArea.x;
        double entityRightWorldX =  entity.worldX + entity.solidArea.x + entity.solidArea.width;
        double entityTopWorldY = entity.worldY + entity.solidArea.y;
        double entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = (int) (entityLeftWorldX / gp.tileSize);
        int entityRightCol= (int) (entityRightWorldX / gp.tileSize);
        int entityTopRow = (int) (entityTopWorldY / gp.tileSize);
        int entityBottomRow = (int) (entityBottomWorldY / gp.tileSize);

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
}
