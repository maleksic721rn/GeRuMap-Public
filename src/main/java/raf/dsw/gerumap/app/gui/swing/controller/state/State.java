package raf.dsw.gerumap.app.gui.swing.controller.state;

import javax.swing.event.ChangeEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class State {
    public abstract void mousePress(MouseEvent e);
    public abstract void mouseRelease(MouseEvent e);
    public abstract void mouseMove(MouseEvent e);
    public abstract void mouseWheelMove(MouseWheelEvent e);
    public abstract void mapViewChanged(ChangeEvent e);
    public abstract void start();
    public abstract void stop();
}
