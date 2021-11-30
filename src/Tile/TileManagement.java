package Tile;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileManagement {

    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][];
    public int mapCol;
    public int mapRow;

    public TileManagement(GamePanel gp) {
        this.gp = gp;

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage image = loader.loadImage("../../Res/wizard.png");

        mapCol = image.getWidth();
        mapRow = image.getHeight();

        gp.maxWorldCol = mapCol;
        gp.maxWorldRow = mapRow;

        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol + 1][gp.maxWorldRow + 1];

        getTileImage();
        loadMap(image);
    }

    public void getTileImage() {

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/sprite_sheet.png");

        //co
        setUp(0, false, 96, 64, 32, 32);


        //tuong goc

        setUp(1, true, 208, 64, 32, 32);


        setUp(2, false, 160, 64, 32, 32);

        //chan tuong
        setUp(3, false, 128, 96, 32, 32);

        //giua goc doc
        setUp(4, true, 208, 96, 32, 32);

        //giua goc ngang
        setUp(5, true, 240, 64, 32, 32);

        //it co
        setUp(6, false, 96, 96, 32, 32);
    }

    public void setUp(int index, boolean collision, int x, int y, int w, int h) {

        UtilityTool uTool = new UtilityTool();
        tiles[index] = new Tile();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/sprite_sheet.png");
        tiles[index].image = sprite.getSubimage(x, y, w, h);
        tiles[index].collision = collision;
        tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
    }

    public void loadMap(BufferedImage image) {
        int x = 0;
        int y = 0;
        while (x < image.getWidth() && y < image.getHeight()) {

            int pixel = image.getRGB(x,y);
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;

            if (red == 186)
                mapTileNum[x][y] = 1;
            else if (red == 0)
                mapTileNum[x][y] = 0;
            else if (blue == 177)
                mapTileNum[x][y] = 3;
            else if (red == 134)
                mapTileNum[x][y] = 4;
            else if (red == 215)
                mapTileNum[x][y] = 5;
            else if (red == 143)
                mapTileNum[x][y] = 6;


            x += 1;
            if (x == image.getWidth()) {
                x = 0;
                y++;
            }
        }

    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < mapCol && worldRow < mapRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            //camera
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

//            //stop camera
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > (mapCol * gp.tileSize) - gp.player.worldX) {
                screenX = gp.screenWidth - ((mapCol * gp.tileSize) - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > (mapRow * gp.tileSize) - gp.player.worldY) {
                screenY = gp.screenHeight - ((mapRow * gp.tileSize) - worldY);
            }


            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                    g2.drawImage(tiles[tileNum].image, screenX, screenY , null);
            } else if(gp.player.screenX > gp.player.worldX ||
                      gp.player.screenY > gp.player.worldY ||
                      rightOffset > mapCol * gp.tileSize - gp.player.worldX ||
                      bottomOffset > mapRow * gp.tileSize -gp.player.worldY ) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY , null);

            }

            worldCol++;

            if (worldCol == mapCol) {
                worldCol = 0;

                worldRow++;

            }
        }
    }
}
