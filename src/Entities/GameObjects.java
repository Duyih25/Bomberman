package Entities;

import Main.ID;
import Main.SpriteSheet;

import java.awt.*;

public abstract class GameObjects {
    public SpriteSheet ss;
    protected int x,y;
    protected float velX=0, velY = 0;
    protected ID id;
    protected boolean moving = false;


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }


    public GameObjects(int x, int y, ID id, SpriteSheet ss) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.ss = ss;
    }
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }


}
