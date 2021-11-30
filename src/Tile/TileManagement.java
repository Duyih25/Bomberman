package Tile;

import Controller.BufferedImageLoader;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileManagement {

    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManagement(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage image = loader.loadImage("../../Res/wizard.png");

        int w = image.getWidth();
        int h = image.getHeight();


        getTileImage();
        loadMap(image);
    }

    public void getTileImage() {

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/sprite_sheet.png");

        tiles[0] = new Tile();
        tiles[0].image = sprite.getSubimage(96, 64, 32, 32);
        tiles[0].collision = false;

        //tuong
        tiles[1] = new Tile();
        tiles[1].image = sprite.getSubimage(128, 58, 32, 38);
        tiles[1].collision = true;

        tiles[2] = new Tile();
        tiles[2].image = sprite.getSubimage(160, 64, 32, 32);

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
            else
                mapTileNum[x][y] = 0;

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

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            //camera
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            double screenX = worldX - gp.player.worldX + gp.player.screenX;
            double screenY = worldY - gp.player.worldY + gp.player.screenY;


            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                if (tileNum == 1) {
                    g2.drawImage(tiles[tileNum].image, (int) screenX, (int) screenY , gp.tileSize, gp.tileSize, null);
                } else g2.drawImage(tiles[tileNum].image, (int) screenX, (int) screenY  , gp.tileSize, gp.tileSize, null);


            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }
        }
    }
}
