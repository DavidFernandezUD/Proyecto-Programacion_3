package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed,
                   attackUpPressed, attackDownPressed, attackLeftPressed, attackRightPressed;

    public boolean escPressed;
    public boolean escToggled; 

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if(code == KeyEvent.VK_UP) {
            attackUpPressed = true;
        }
        if(code == KeyEvent.VK_LEFT) {
            attackLeftPressed = true;
        }
        if(code == KeyEvent.VK_DOWN) {
            attackDownPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT) {
            attackRightPressed = true;
        }

        if(code == KeyEvent.VK_ESCAPE) {
            if(!escPressed) {
                if(!escToggled) {
                    escToggled = true;
                } else {
                    escToggled = false;
                }
            }
            escPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S) {
                downPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if(code == KeyEvent.VK_UP) {
            attackUpPressed = false;
        }
        if(code == KeyEvent.VK_LEFT) {
            attackLeftPressed = false;
        }
        if(code == KeyEvent.VK_DOWN) {
            attackDownPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT) {
            attackRightPressed = false;
        }

        if(code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }
    }
}
