package Entities;

import Controller.Handler;
import Main.ID;
import Main.SpriteSheet;

import java.awt.*;

public class Bullet extends GameObjects {

    private Handler handler;

    public Bullet(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;
        int speed = 10;

        double bulletAngle = Math.toDegrees(Math.atan2(my - y, mx - x));
        velX = (float)(Math.cos(Math.toRadians(bulletAngle))*speed);
        velY = (float)(Math.sin(Math.toRadians(bulletAngle ))*speed);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        for (int i=0;i<handler.objects.size();i++) {
            GameObjects tmp = handler.objects.get(i);

            if(tmp.getId() == ID.Block) {
                if(getBounds().intersects(tmp.getBounds())) {
                    handler.removeObject(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(x,y,8,8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,8,8);
    }
}

