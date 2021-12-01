package Controller;

import Main.GamePanel;
import Object.*;

import java.awt.*;

public class ObjectManagement {
    GamePanel gp;
    KeyHandler keyH;

    public SuperObject[] obj = new SuperObject[50];
    int currentObj = 0;
    int currentBomb = 0;
    int maxBombNum = 3;

    public ObjectManagement(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    public void update() {
        if (keyH.bombPressed == true) {
            obj[currentObj] = new Bomb();
            obj[currentObj].worldX = gp.player.worldX;
            obj[currentObj].worldY = gp.player.worldY + 32;
            currentObj++;
            currentBomb++;
            keyH.bombPressed = false;
        }
    }

    public void render(Graphics2D g2) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null)
                obj[i].draw(g2, gp);
        }
    }
}
