package Main;

import Entities.Enemy;
import Entities.Entity;
import Object.*;

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
        for (Block block: gp.objectManagement.blockList) {
            switch (entity.direction) {
                case "up":
                    if (entity.worldX == block.worldX && entity.worldY == block.worldY + 32) {
                        entity.collision = true;
                        //System.out.println("collide block up");
                    }
                    break;
                case "down":
                    if (entity.worldX == block.worldX && entity.worldY == block.worldY - 96) {
                        entity.collision = true;
                        //System.out.println("collide block down");
                    }
                    break;
                case "left":
                    if (entity.worldX == block.worldX + 64 && entity.worldY == block.worldY - 32) {
                        entity.collision = true;
                        //System.out.println("collide block left");
                    }
                    break;
                case "right":
                    if (entity.worldX == block.worldX - 64 && entity.worldY == block.worldY - 32) {
                        entity.collision = true;
                        //System.out.println("collide block right");
                    }
                    break;
            }
        }

    }

    public int checkObject(Entity entity) {
        int index = 999;
        for (int i = 0; i < gp.objectManagement.obj.size(); i++) {
            if (entity.name.equals("Player")) {
                if (gp.objectManagement.obj.get(i).name.equals("Item")) {
                    switch (entity.direction) {
                        case "up":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY + 32) {

                                Item item = (Item) gp.objectManagement.obj.get(i);
                                if (item instanceof BombItem) {
                                    gp.objectManagement.maxBombNum++;
                                } else if (item instanceof FlameItem) {
                                    gp.objectManagement.maxBombRadius++;
                                } else if (item instanceof SpeedItem) {
                                    gp.player.speed += 4;
                                } else if(item instanceof CrateItem) {
                                    gp.objectManagement.currentBullets+=2;
                                } else if(item instanceof Portal) {
                                    gp.win = true;
                                    gp.currentLevel++;
                                    System.out.println("true");
                                }
                                return i;
                            }
                            break;
                        case "down":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY - 96) {

                                Item item = (Item) gp.objectManagement.obj.get(i);
                                if (item instanceof BombItem) {
                                    gp.objectManagement.maxBombNum++;
                                } else if (item instanceof FlameItem) {
                                    gp.objectManagement.maxBombRadius++;
                                } else if (item instanceof SpeedItem) {
                                    gp.player.speed += 4;
                                }
                                else if(item instanceof CrateItem) {
                                    gp.objectManagement.currentBullets+=2;
                                }
                            return i;
                            }
                            break;
                        case "left":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX + 64 &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {

                                Item item = (Item) gp.objectManagement.obj.get(i);
                                if (item instanceof BombItem) {
                                    gp.objectManagement.maxBombNum++;
                                } else if (item instanceof FlameItem) {
                                    gp.objectManagement.maxBombRadius++;
                                } else if (item instanceof SpeedItem) {
                                    gp.player.speed += 4;
                                }
                                else if(item instanceof CrateItem) {
                                    gp.objectManagement.currentBullets+=2;
                                }
                                return i;
                            }
                            break;
                        case "right":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX - 64 &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {

                                Item item = (Item) gp.objectManagement.obj.get(i);
                                if (item instanceof BombItem) {
                                    gp.objectManagement.maxBombNum++;
                                } else if (item instanceof FlameItem) {
                                    gp.objectManagement.maxBombRadius++;
                                } else if (item instanceof SpeedItem) {
                                    gp.player.speed += 4;
                                }
                                else if(item instanceof CrateItem) {
                                    gp.objectManagement.currentBullets+=2;
                                }
                                return i;
                            }
                            break;

                        }

//                    } else if (gp.objectManagement.obj.get(i).name.equals("Flame")) {
//                        Flame check = (Flame) gp.objectManagement.obj.get(i);
//                        for (FlameSegment fs : check.flameSegments) {
//                            if (entity.getBound().intersects(fs.getBound())) {
//                                gp.lose = true;
//                                return -1;
//                            }
//
//                        }
                    } else if (gp.objectManagement.obj.get(i).name.equals("Bomb")) {
                        switch (entity.direction) {
                            case "up":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY + 32) {
                                    entity.collision = true;
                                }
                                break;
                            case "down":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY - 96) {
                                    entity.collision = true;
                                }
                                break;
                            case "left":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX + 64 &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {
                                    entity.collision = true;
                                }
                                break;
                            case "right":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX - 64 &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {
                                    entity.collision = true;
                                }
                                break;
                        }
                    }
            } else if (entity.name.equals("Item")) {
                if (gp.objectManagement.obj.get(i).name.equals("Flame")) {
                    Flame check = (Flame) gp.objectManagement.obj.get(i);
                    for (FlameSegment fs : check.flameSegments) {
                        if (entity.getBound().intersects(fs.getBound())) {
                            return i;
                        }
                    }
                }
            } else if (entity.name.equals("Block")) {
                if (gp.objectManagement.obj.get(i).name.equals("Flame")) {
                    Flame check = (Flame) gp.objectManagement.obj.get(i);
                    for (FlameSegment fs : check.flameSegments) {
                        if (entity.getBound().intersects(fs.getBound())) {
                           
                            return i;
                        }
                    }
                }
                if(gp.objectManagement.obj.get(i).name.equals("Bullet")
                        && gp.objectManagement.obj.get(i).getBound().intersects(entity.getBound())){
                    return i;
                }
            } else if (entity instanceof Enemy) {
                if (gp.objectManagement.obj.get(i).name.equals("Bomb") ||
                        gp.objectManagement.obj.get(i).name.equals("Block")) {
                    switch (entity.direction) {
                        case "up":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY + 64) {
                                entity.collision = true;
                            }
                            break;
                            case "down":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY - 64) {
                                    entity.collision = true;
                                }
                                break;
                            case "left":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX + 64 &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY) {
                                    entity.collision = true;
                                }
                                break;
                            case "right":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX - 64 &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY) {
                                    entity.collision = true;
                                }
                                break;
                        }
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objectManagement.obj.get(i).setSolidAreaDefault();
                //gp.objectManagement.obj.get(i).solidArea.x = gp.objectManagement.get(i).solidAreaDefaultX;
                //gp.objectManagement.obj[i].solidArea.y = gp.objectManagement.obj[i].solidAreaDefaultY;

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
                            index = 0;
                        }


                        break;
                    case "right":
                        if (entity.getBound().intersects(target.getBound())) {
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
        for (int i = 0; i < gp.objectManagement.obj.size(); i++) {
            if (gp.objectManagement.obj.get(i).name.equals("Bullet") &&
                    enemy.getBound().intersects(gp.objectManagement.obj.get(i).getBound())) {
                //if(!gp.objectManagement.obj[i].name.equals("bullet"))entity.collision = true;
                for (int j = 0; j < gp.npc.length; j++) {
                    if (enemy == gp.npc[j]) gp.npc[j] = null;
                }
                index = i;
            } else if (gp.objectManagement.obj.get(i).name.equals("Flame")) {
                Flame check = (Flame) gp.objectManagement.obj.get(i);
                for (FlameSegment fs: check.flameSegments) {
                    if (enemy.getBound().intersects(fs.getBound())) {
                        for (int j = 0; j < gp.npc.length; j++) {
                            if (enemy == gp.npc[j]) {
                                gp.npc[j] = null;
                                return index;
                            }
                        }
                    }
                }
            }
            //enemy.solidArea.x = enemy.solidAreaDefaultX;
            //enemy.solidArea.y = enemy.solidAreaDefaultY;
            gp.objectManagement.obj.get(i).setSolidAreaDefault();
//            gp.objectManagement.obj[i].solidArea.x = gp.objectManagement.obj[i].solidAreaDefaultX;
//            gp.objectManagement.obj[i].solidArea.y = gp.objectManagement.obj[i].solidAreaDefaultY;
        }
        return index;
    }

    public void checkBlock(Entity entity) {
        for (int i = 0; i < gp.objectManagement.blockList.size(); i++) {
            if (entity.name.equals("Flame")) {
                if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                        entity.worldY == gp.objectManagement.blockList.get(i).worldY) {
                    entity.collision = true;
                }
            }
        if (entity.name.equals("Player")) {
            switch (entity.direction) {
                case "up":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY + 32) {
                        entity.collision = true;
                    }
                    break;
                case "down":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY - 96) {
                        entity.collision = true;
                    }
                    break;
                case "left":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX + 64 &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY - 32) {
                        entity.collision = true;
                    }
                    break;
                case "right":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX - 64 &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY - 32) {
                        entity.collision = true;
                    }
                    break;
                }
            }
            if (entity instanceof Enemy) {
                switch (entity.direction) {
                    case "up":
                        if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                                entity.worldY == gp.objectManagement.blockList.get(i).worldY + 64) {
                            entity.collision = true;
                        }
                        break;
                    case "down":
                        if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                                entity.worldY == gp.objectManagement.blockList.get(i).worldY - 64) {
                            entity.collision = true;
                        }
                        break;
                    case "left":
                        if (entity.worldX == gp.objectManagement.blockList.get(i).worldX + 64 &&
                                entity.worldY == gp.objectManagement.blockList.get(i).worldY ) {
                            entity.collision = true;
                        }
                        break;
                    case "right":
                        if (entity.worldX == gp.objectManagement.blockList.get(i).worldX - 64 &&
                                entity.worldY == gp.objectManagement.blockList.get(i).worldY ) {
                            entity.collision = true;
                        }
                        break;
                }
            }
        }
    }
}
