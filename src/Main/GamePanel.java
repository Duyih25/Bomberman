package Main;

import Controller.KeyHandler;
import Controller.ObjectManagement;
import Entities.Player;
import Graphics.Font;
import Graphics.Sprite;
import Tile.TileManagement;
import Object.AssetSetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public TileManagement tileManagement = new TileManagement(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH, tileManagement);
    private Font font;
    Timer timer;
    public int second, minute;
    String ddSecond, ddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");
    public AssetSetter aSetter = new AssetSetter(this);
    ObjectManagement objectManagement = new ObjectManagement(this, keyH);

    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        font = new Font("../../Res/font.png", 10, 10);



    }

    public void setUpGame() {
        aSetter.setObject();
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
        timer.start();

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
        player.update();
        objectManagement.update();
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

        objectManagement.render(g2);

        player.draw(g2);

        //debug
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            //Sprite.drawArray(g2, font, "Draw Time: " + passed, 2 * tileSize, 32 , 32, 32, 32, 0);

            //System.out.println("Draw Time: " + passed);
        }

        Sprite.drawArray(g2, font, ddMinute + ":" + ddSecond , (maxScreenCol - 3) * tileSize, 32 , 32, 32, 32, 0);

        g2.dispose();
    }


}
