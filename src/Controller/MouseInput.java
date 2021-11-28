package Controller;


import Entities.Bullet;
import Entities.GameObjects;
import Main.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Camera camera;
    private Game game;
    private SpriteSheet ss;

    public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss) {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) ( e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i <handler.objects.size();i++) {
            GameObjects tmp = handler.objects.get(i);

            if(tmp.getId() == ID.Player && game.ammo >=1) {
                handler.addObject(new Bullet(tmp.getX()+16, tmp.getY()+24, ID.Bullet, handler,mx,my,ss));
                game.ammo--;
            }
        }
    }
}
