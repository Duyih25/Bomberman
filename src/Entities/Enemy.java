package Entities;

import Controller.Handler;
import Controller.Animation;
import Main.ID;
import Main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObjects {

    private Handler handler;
    private BufferedImage [] enemy_image = new BufferedImage[3];

    Random r = new Random();
    int choose =0;
    int hp = 100;
    Animation animation;

    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;

        for(int i=0;i<3;i++)
        enemy_image[i] = ss.grabImage(i+4,1,32,32);

        animation = new Animation(3,enemy_image );
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        choose = r.nextInt(10);

        for(int i=0;i<handler.objects.size();i++) {
            GameObjects tmp = handler.objects.get(i);

            if(tmp.getId() == ID.Block) {
                if(getBoundBig().intersects(tmp.getBounds())) {
                    x += (velX * 5) * -1;
                    y += (velY * 5) * -1;
                    velX *= -1;
                    velY *= -1;
                }
                else if(choose==0) {
                    velX = (r.nextInt(4 - -4) + -4);
                    velY = (r.nextInt(4 - -4) + -4);

                }
            }
            if(tmp.getId() == ID.Bullet) {
                if(getBounds().intersects(tmp.getBounds())) {
                    hp -= 20;
                }
            }

        }
        animation.runAnimation();
        if (hp <= 0) handler.removeObject(this);


    }

    @Override
    public void render(Graphics g) {
        animation.drawAnimation(g,x,y,0);


       /* Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.green);
        g2d.draw(getBoundBig());*/
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    public Rectangle getBoundBig() {
        return new Rectangle (x-16, y-16, 64, 64);
    }
}
