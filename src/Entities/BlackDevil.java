package Entities;

import Controller.Animation;
import Controller.Handler;
import Main.ID;
import Main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackDevil extends Enemy{
    private BufferedImage[] black_devil = new BufferedImage[3];
    Animation animation;

    public BlackDevil(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, handler, ss);

        for(int i=0;i<3;i++)
            black_devil[i] = ss.grabImage(i+4,1,32,32);

        animation = new Animation(3, black_devil);
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
    }
}
