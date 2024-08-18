package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;

import javax.swing.event.ChangeEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class SelectSingleState extends State {
    @Override
    public void mousePress(MouseEvent e) {

    }

    @Override
    public void mouseRelease(MouseEvent e) {
        MapView m = MainFrame.getInstance()
                .getProjectView().getCurrentMapView();
        ElementPainter ep = m.elementAt(e.getPoint());
        if (ep != null)
            m.toggleSelection(ep);
    }

    @Override
    public void mouseMove(MouseEvent e) {
        ControllerUtils.highlightElement(e);
    }

    @Override
    public void mouseWheelMove(MouseWheelEvent e) {

    }

    @Override
    public void mapViewChanged(ChangeEvent e) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        ControllerUtils.unhighlightElements();
    }
}
