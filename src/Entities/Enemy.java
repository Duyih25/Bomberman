package Entities;

import Controller.Handler;
import Controller.Animation;
import Main.ID;
import Main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObjects {

    protected Handler handler;

    Random r = new Random();
    int choose =0;
    int hp = 100;

    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {


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
