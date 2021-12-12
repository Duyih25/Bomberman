package Main;

import Controller.BufferedImageLoader;
import Controller.KeyHandler;
import Controller.ObjectManagement;
import Entities.Enemy;
import Entities.Player;
import Object.AssetSetter;
import Tile.TileManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;


public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int orginalTileSize = 32; //32 x 32 tile
    final int scale = 2;

    public int tileSize = orginalTileSize * scale; // 64 x 64 tile
    public int maxScreenCol = 10;
    public int maxScreenRow = 10;

    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    //World setting
    public int maxWorldCol;
    public int maxWorldRow;
    public final int worldWidth  = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //GameState
    public int gameState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int titleState = 0;
    public final int loseState = 3;
    public final int winState = 4;

    //Level
    public int currentLevel = 1;

    //Game component
    BufferedImageLoader loader = new BufferedImageLoader();
    BufferedImage mapLevel1 = loader.loadImage("../../Res/level1/level1.png");
    BufferedImage mapLevel2 = loader.loadImage("../../Res/level2/level2.png");
    BufferedImage playerS = loader.loadImage("../../Res/bomber_sprite.png");

    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public TileManagement tileManagement = new TileManagement(this, mapLevel1);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH, tileManagement, playerS);
    public Timer timer;
    public int second, minute;
    String ddSecond, ddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");
    public AssetSetter aSetter = new AssetSetter(this);
    public ObjectManagement objectManagement = new ObjectManagement(this, keyH);
    public UI ui = new UI (this);

    public Enemy npc[] = new Enemy[10];

    int FPS = 60;
    public Graphics2D g2d;
    public boolean lose = false;
    public boolean win = false;
    Sound sound = new Sound();


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setUpGame() {
        aSetter.setObject(mapLevel1);
        aSetter.setNPC();
        gameState = titleState;
        playMusic(0);
    }

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
        second = 0;
        minute = 0;
        ddSecond = "00";
        ddMinute = "00";
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                second++;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                if (second == 60) {
                    second = 0;
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                    minute++;
                }
            }
        });


        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;



            if (delta >= 1) {


                update();
                //System.out.println(second);
                repaint();
                delta--;
            }

        }

    }


    public void update() {
        if(gameState == playState) {
            player.update();
            objectManagement.update();

            for(int i=0;i<npc.length;i++) {
                if(npc[i]!=null) {
                    npc[i].update();
                }
            }
            if(lose) {
                gameState = loseState;
                timer.stop();
            }
            if(win) {
                currentLevel +=1;
                gameState = winState;
                timer.stop();
            }
        }
        if (gameState == pauseState) {

        }
        if (gameState == loseState) {

        }
        if (gameState == winState) {

        }
    }

    public void paintComponent(Graphics g) {


        //debug
        long drawStart = 0;

        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2d = g2;

        //Tiltle Screen
        if (gameState == titleState || gameState == loseState || gameState == winState) {
            ui.draw(g2);
        } //OTHERS
        else {
            tileManagement.draw(g2);

            objectManagement.render(g2);

            player.draw(g2);

            //Obj
//            for(int i=0;i<objectManagement.obj.size();i++) {
//                    objectManagement.obj.get(i).draw(g2);
//            }

            //npc
            for(int i=0;i<npc.length;i++) {
                if(npc[i]!=null) {
                    npc[i].draw(g2);
                }
            }

            ui.draw(g2);
        }



        //debug
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            //Sprite.drawArray(g2, font, "Draw Time: " + passed, 16, tileSize * 9 , 24, 24, 24, 0);

            //System.out.println("Draw Time: " + passed);
        }

        //Sprite.drawArray(g2, font, ddMinute + ":" + ddSecond , (maxScreenCol - 3) * tileSize, 32 , 32, 32, 32, 0);

//        if(lose) {
//            Sprite.drawArray(g2, font,"You" , tileSize, 5*tileSize , 32, 32, 32, 0);
//            Sprite.drawArray(g2, font,"lose" , tileSize*3, 5*tileSize , 32, 32, 32, 0);
//        }

        g2.dispose();
    }
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }


    public void playStop(int i){
        sound.stop();
    }

    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }

    public void restartGame(int currentLevel) {
        objectManagement.obj.clear();
        objectManagement.blockList.clear();
        npc = new Enemy[10];
        aSetter.setNPC();
        switch (currentLevel) {
            case 1:
                aSetter.setObject(mapLevel1);
                tileManagement = new TileManagement(this, mapLevel1);
                break;
            case 2:
                aSetter.setObject(mapLevel2);
                tileManagement = new TileManagement(this, mapLevel2);
                break;
        }
        objectManagement.maxBombNum = 3;
        objectManagement.maxBombRadius = 1;
        player.setDefautValue();
        gameState = playState;
        keyH.restartPressed = false;
        second = 0;
        minute = 0;
        timer.start();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
