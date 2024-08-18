package raf.dsw.gerumap.app.gui.swing.view;

import java.awt.*;

public interface StatePainter {
    void paint(Graphics2D g);
    boolean topLayer();
}
