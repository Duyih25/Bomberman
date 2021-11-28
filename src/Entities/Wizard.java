package Entities;

import Controller.Animation;
import Controller.Handler;
import Main.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wizard extends GameObjects {

    Handler handler;
    Game game;

    private BufferedImage[] wizard_image = new BufferedImage[3];
    Animation animation;

    public Wizard(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;
        this.game = game;
        this.ss = ss;

        for(int i=1;i<=3;i++)
        wizard_image[i-1] = ss.grabImage(i,1,32,48);

        animation = new Animation(3,wizard_image[0],wizard_image[1],wizard_image[2]);
    }

    @Override

    public void tick() {
        x += velX;
        y += velY;

        collision();

        if(handler.isUp()) velY = -5;
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()) velY = 5;
        else if(!handler.isUp()) velY = 0;

        if(handler.isLeft()) velX = -5;
        else if(!handler.isRight()) velX = 0;

        if(handler.isRight()) velX = 5;
        else if(!handler.isLeft()) velX = 0;

        if(game.hp <= 0) {
            handler.removeObject(this);
        }

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
                    game.ammo +=10;
                    handler.removeObject(tmp);
                }
            }

            if(tmp.getId() == ID.Enemy) {
                if(getBounds().intersects(tmp.getBounds())) {
                    game.hp--;
                    //handler.removeObject(tmp);
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
        return new Rectangle(x,y,32,48);
    }
}
