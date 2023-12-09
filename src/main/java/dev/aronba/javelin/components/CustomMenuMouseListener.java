package dev.aronba.javelin.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomMenuMouseListener implements MouseListener {


    private final Runnable runMain;
    public CustomMenuMouseListener(Runnable runMain) {
    this.runMain = runMain;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        runMain.run();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
