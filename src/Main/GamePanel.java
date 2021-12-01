package Main;

import Controller.KeyHandler;
import Entities.Player;
import Graphics.TileManagement;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int orginalTileSize = 32; //32 x 32 tile
    final int scale = 2;

    public int tileSize = orginalTileSize * scale; // 64 x 64 tile
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    //World setting
    public int maxWorldCol;
    public int maxWorldRow;
    public final int worldWidth  = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    TileManagement tileManagement = new TileManagement(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH, tileManagement);


    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        startGameThread();

    }

//    public void zoomInOut(int i) {
//        int oldWorldWidth = tileSize * maxWorldCol;
//        tileSize += i;
//        int newWorldWidth = tileSize * maxWorldCol;
//
//        double multiplier = (double) newWorldWidth / oldWorldWidth;
//
//        double newPlayerWorldX = player.worldX * multiplier;
//        double newPlayerWorldY = player.worldY * multiplier;
//
//        player.worldX = newPlayerWorldX;
//        player.worldY = newPlayerWorldY;
//
//        double newPlayerAreaX = player.solidArea.width * multiplier;
//        double newPlayerAreaY = player.solidArea.height * multiplier;
//
//        player.solidArea.width = (int) newPlayerAreaX;
//        player.solidArea.height = (int) newPlayerAreaY;
//    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {


                update();

                repaint();
                delta--;
            }
        }

    }


    public void update() {

        player.update();
    }

    public void paintComponent(Graphics g) {


        //debug
        long drawStart = 0;

        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileManagement.draw(g2);

        player.draw(g2);

        //debug
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }


        g2.dispose();
    }


}
