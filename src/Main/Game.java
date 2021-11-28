package Main;

import Controller.*;
import Entities.Block;
import Entities.Crate;
import Entities.Enemy;
import Entities.Wizard;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game  extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;
    private BufferedImage floor = null;

    public int ammo = 100;
    public int hp = 100;

    private BufferedImage level = null;
    private BufferedImage sprite_sheet = null;


    public Game() {
        new Window(1000,563,"Bomberman",this);
        start();
        handler = new Handler();
        camera = new Camera(0,0);
        this.addKeyListener(new KeyInput(handler));


        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("../../Res/wizard.png");
        sprite_sheet = loader.loadImage("../../Res/sprite_sheet.png");

        ss = new SpriteSheet(sprite_sheet);
        this.addMouseListener(new MouseInput(handler,camera,this,ss));

        floor = ss.grabImage(4,2,32,32);
        loaderLevel(level);
       // handler.addObject(new Entities.Wizard(100,100,Main.ID.Player,handler));

    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = Math.pow(10,9)/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis()- timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }
    public void tick() {

        for (int i=0;i<handler.objects.size();i++) {
            if(handler.objects.get(i).getId() == ID.Player) {
                camera.tick(handler.objects.get(i));
            }
        }

        handler.tick();
    }
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        //////////////////////////////////////////


        g2d.translate(-camera.getX(), - camera.getY());

        for(int xi=0;xi<30*72;xi+=32)
            for(int yi=0;yi<30*72;yi+=32) {
                g.drawImage(floor,xi,yi,null);
            }

        handler.render(g);

        g2d.translate(camera.getX(),  camera.getY());

        g.setColor(Color.gray);
        g.fillRect(5,5,200,32);

        g.setColor(Color.green);
        g.fillRect(5,5,hp*2,32);

        g.setColor(Color.black);
        g.drawRect(5,5,200,32);

        g.setColor(Color.white);
        g.drawString("Ammo: " + ammo, 5,50);

        if(hp <= 0) {
            g.setColor(Color.white);
            g.drawString("Oh dear, you are dead!", 400, 281);
        }
        //g.drawRect(5,5,200,32);
        /////////////////////////////////////////

         g.dispose();
         bs.show();
    }

    private void loaderLevel (BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xi = 0; xi< w;xi++) {
            for (int yi=0;yi<h;yi++) {
                int pixel = image.getRGB(xi,yi);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                if (red == 186)
                    handler.addObject(new Block(xi*32,yi*32, ID.Block,ss));
                if (blue == 177)
                    handler.addObject(new Wizard(xi*32,yi*32, ID.Player,handler,this,ss));
                if(red == 255 && blue == 255)
                    handler.addObject(new Enemy(xi*32,yi*32, ID.Enemy,handler,ss));

                if(blue == 255 && red == 0)
                    handler.addObject(new Crate(xi*32,yi*32, ID.Crate,ss));
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }


}
