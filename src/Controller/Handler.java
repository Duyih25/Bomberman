package Controller;

import Entities.GameObjects;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    public ArrayList<GameObjects> objects = new ArrayList<>();

    private boolean up = false, down = false, right = false, left = false;

    public ArrayList<GameObjects> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<GameObjects> objects) {
        this.objects = objects;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void tick() {
        for(int i=0;i<objects.size();i++) {
            GameObjects tmp = objects.get(i);
            tmp.tick();
        }
    }

    public void render(Graphics g) {
        for(int i=0;i<objects.size();i++) {
            GameObjects tmp = objects.get(i);
            tmp.render(g);
        }
    }

    public void addObject(GameObjects tmp) {
        objects.add(tmp);
    }
    public void removeObject(GameObjects tmp) {
        objects.remove(tmp);
    }
}
