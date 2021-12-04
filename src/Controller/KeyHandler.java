package Controller;

import Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed = false;
    public boolean facingLeft, facingRight, facingUp, facingDown = false;//Duy
    public boolean bombPressed = false;
    public boolean bulletPressed = false;
    GamePanel gp;
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
            facingLeft = false; //Duy
            facingRight = false;
            facingDown = false;
            facingUp = true;//
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
            facingLeft = false;
            facingRight = false;
            facingDown = true;
            facingUp = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
            facingLeft = true;
            facingRight = false;
            facingDown = false;
            facingUp = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
            facingLeft = false;
            facingRight = true;
            facingDown = false;
            facingUp = false;
        }
//        if (code == KeyEvent.VK_UP) {
//            gp.zoomInOut(1);
//        }
//        if (code == KeyEvent.VK_DOWN) {
//            gp.zoomInOut(-1);
//        }
        if (code == KeyEvent.VK_T) {
            if (checkDrawTime == false) {
                checkDrawTime = true;
            } else
                checkDrawTime = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            bombPressed = true;
        }

        if (code == KeyEvent.VK_E) {
            bulletPressed = true;
        }

    }

    @Override
    public void keyReleased (KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_E) {
            bulletPressed = false;
        }
    }
}
