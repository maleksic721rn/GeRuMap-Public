package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseController extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        MainFrame.getInstance().getProjectView().sendMousePress(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MainFrame.getInstance().getProjectView().sendMouseRelease(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        MainFrame.getInstance().getProjectView().sendMouseMove(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MainFrame.getInstance().getProjectView().sendMouseMove(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { MainFrame.getInstance().getProjectView().sendMouseWheel(e); }
}
