package Controller;

import Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed = false;
    public boolean facingLeft, facingRight, facingUp, facingDown = false;//Duy
    public boolean bombPressed = false;
    public boolean bulletPressed = false;
    public boolean restartPressed = false;
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

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                gp.playStop(0);
                gp.playSE(1);
                gp.playMusic(2);
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }

        if (gp.gameState == gp.playState) {
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
            if (code == KeyEvent.VK_P) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                    gp.timer.stop();
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                    gp.timer.start();
                }
            }
        }

        if (gp.gameState == gp.pauseState) {

            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    restartPressed = true;
                }
                if (gp.ui.commandNum == 2) {
                    gp.gameState = gp.titleState;
                }
            }

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
