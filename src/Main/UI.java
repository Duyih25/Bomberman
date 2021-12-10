package Main;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Graphics.Font;
import Graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font font;
    public int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        font = new Font("../../Res/font.png", 10, 10);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        //Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //play state
        if (gp.gameState == gp.playState) {
            Sprite.drawArray(g2, font, gp.ddMinute + ":" + gp.ddSecond , (gp.maxScreenCol - 3) * gp.tileSize, 32 , 32, 32, 32, 0);
        }
        //Pause state
        if (gp.gameState == gp.pauseState) {
                drawPauseScreen();
        }
//        if(gp.lose) {
//            Sprite.drawArray(g2, font,"You" , gp.tileSize, 5*gp.tileSize , 32, 32, 32, 0);
//            Sprite.drawArray(g2, font,"lose" , gp.tileSize*3, 5*gp.tileSize , 32, 32, 32, 0);
//        }
    }

    public void drawPauseScreen() {
        String text = "PAU^E";
        Sprite.drawArray(g2, font, text , 3 * gp.tileSize, 3 * gp.tileSize , 64, 64, 64, 0);

    }

    public void drawTitleScreen() {
        UtilityTool utool = new UtilityTool();

        //Back ground
        g2.setColor(new Color(69, 195, 253));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //title
        BufferedImage image;
        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("../../Res/2.png");
        image = utool.scaleImage(image, gp.screenWidth, gp.screenHeight);
        g2.drawImage(image, 0, 0, null);

        //Menu
        Sprite.drawArray(g2, font, "NEW RAME" , 3 * gp.tileSize, 6 * gp.tileSize , 32, 32, 32, 0);
        if (commandNum == 0) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize - 32, 6 * gp.tileSize, 32, 32, 32, 0);
        }
        Sprite.drawArray(g2, font, "LOAD RAME", 3 * gp.tileSize, 7 * gp.tileSize , 32, 32, 32, 0);
        if (commandNum == 1) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize - 32, 7 * gp.tileSize, 32, 32, 32, 0);
        }

        Sprite.drawArray(g2, font, "QUIT" , 3 * gp.tileSize, 8 * gp.tileSize , 32, 32, 32, 0);

        if (commandNum == 2) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize - 32, 8 * gp.tileSize, 32, 32, 32, 0);
        }


    }
}
