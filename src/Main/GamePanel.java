package Main;
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
import java.text.DecimalFormat;
public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int orginalTileSize = 32; //32 x 32 tile
    final int scale = 2;
    public int tileSize = orginalTileSize * scale; // 64 x 64 tile
    public int maxScreenCol = 10;
    public int maxScreenRow = 10;
    public int maxBombRadius = 2;
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
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public TileManagement tileManagement = new TileManagement(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH, tileManagement);
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
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setUpGame() {
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
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
            if(lose) {
                //zzzz
                gameThread.stop();
                System.out.println("End game!");
                //gameThread.stop();
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
        }
        if (gameState == pauseState) {
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
        if (gameState == titleState) {
            ui.draw(g2);
        } //OTHERS
        else {
            tileManagement.draw(g2);
            objectManagement.render(g2);
            player.draw(g2);

            //Obj
            for(int i=0;i<objectManagement.obj.length;i++) {
                if(objectManagement.obj[i]!=null) {
                    objectManagement.obj[i].draw(g2);
                }
            }
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
}