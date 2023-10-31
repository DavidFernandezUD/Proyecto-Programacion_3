package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed,
                   upToggled, downToggled, leftToggled, rightToggled,
                   attackUpPressed, attackDownPressed, attackLeftPressed, attackRightPressed,
                   enterPressed, enterToggled,
                   escPressed, escToggled;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // TODO: Fix moving direction priorities
    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        // MOVING
        if(code == KeyEvent.VK_W) {
            if(!upPressed) {
                upToggled = !upToggled;
            }
            upPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            if(!leftPressed) {
                leftToggled = !leftToggled;
            }
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            if(!downPressed) {
                downToggled = !downToggled;
            }
            downPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            if(!rightPressed) {
                rightToggled = !rightToggled;
            }
            rightPressed = true;
        }

        // ATTACKING
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

        // ENTER
        if(code == KeyEvent.VK_ENTER) {
            if(!enterPressed) {
                enterToggled = !enterToggled;
            }
            enterPressed = true;
        }

        // ESCAPE
        if(code == KeyEvent.VK_ESCAPE) {
            if(!escPressed) {
                escToggled = !escToggled;
            }
            escPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        // MOVING
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

        // ATTACKING
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

        // ESCAPE
        if(code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }

        // ENTER
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}
