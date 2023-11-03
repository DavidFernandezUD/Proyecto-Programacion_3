package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    private boolean leftClick;
    private boolean rightClick;

    public boolean isAttackPressed() {
        return leftClick;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
        if(e.getButton() == MouseEvent.BUTTON2) {
            rightClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
        if(e.getButton() == MouseEvent.BUTTON2) {
            rightClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
