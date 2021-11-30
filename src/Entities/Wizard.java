package Entities;

import Controller.Animation;
import Controller.Handler;
import Main.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wizard extends GameObjects {

    Handler handler;
    GamePanel gamePanel;
    boolean moving = false;
    int pixelCounter = 0;
    int speed = 4;

    private BufferedImage[] wizard_image = new BufferedImage[3];
    Animation animation;

    public Wizard(int x, int y, ID id, Handler handler, GamePanel gamePanel, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;
        this.gamePanel = gamePanel;
        this.ss = ss;

        for(int i=1;i<=3;i++)
        wizard_image[i-1] = ss.grabImage(i,1,32,48);

        animation = new Animation(10,wizard_image);
    }

    @Override

    public void tick() {
        x += velX;
        y += velY;


        collision();

        if(handler.isUp()) {
            velY = -speed;
            pixelCounter += speed;
            if (pixelCounter == 32 ) {
                velY = 0;
                pixelCounter = 0;
            }
        }
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()) {
            velY = speed;
            if (pixelCounter == 32 ) {
                velY = 0;
                pixelCounter = 0;
            }
        }
        else if(!handler.isUp()) velY = 0;

        if(handler.isLeft()) {
            velX = -speed;
            if (pixelCounter == 32 ) {
                velX = 0;
                pixelCounter = 0;
            }
        }
        else if(!handler.isRight()) velX = 0;

        if(handler.isRight()) {
            velX = speed;
            if (pixelCounter == 32 ) {
                speed = 0;
                pixelCounter = 0;
            }
        }
        else if(!handler.isLeft()) velX = 0;


        animation.runAnimation();

    }
    private void collision() {
        for(int i=0;i<handler.objects.size();i++) {
            GameObjects tmp = handler.objects.get(i);

            if(tmp.getId() == ID.Block) {
                if(getBounds().intersects(tmp.getBounds())) {
                    x += velX * -1;
                    y += velY * -1;
                }
            }

            if(tmp.getId() == ID.Crate) {
                if(getBounds().intersects(tmp.getBounds())) {
                    //gamePanel.ammo +=10;
                    handler.removeObject(tmp);
                }
            }

            if(tmp.getId() == ID.Black_Devil) {
                if(getBounds().intersects(tmp.getBounds())) {
                    //gamePanel.hp--;
                }
            }

        }
    }

    @Override
    public void render(Graphics g) {
        if(velX==0 && velY==0)
        g.drawImage(wizard_image[0],x,y,null);
        else {
            animation.drawAnimation(g,x,y,0);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y + 16 ,32,32);
    }
}
