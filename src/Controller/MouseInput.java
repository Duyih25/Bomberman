package Controller;


import Main.GamePanel;
import Main.SpriteSheet;

import java.awt.event.MouseAdapter;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Camera camera;
    private GamePanel gamePanel;
    private SpriteSheet ss;

    public MouseInput(Handler handler, Camera camera, GamePanel gamePanel, SpriteSheet ss) {
        this.handler = handler;
        this.camera = camera;
        this.gamePanel = gamePanel;
        this.ss = ss;
    }


}
