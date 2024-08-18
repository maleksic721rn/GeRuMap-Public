package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;
import raf.dsw.gerumap.app.mapRepository.commands.implementation.RemoveElementsCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.Element;
import raf.dsw.gerumap.app.gui.swing.view.LinkPainter;
import raf.dsw.gerumap.app.mapRepository.implementation.Link;

import javax.swing.event.ChangeEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashSet;

public class DeleteState extends State {
    @Override
    public void mousePress(MouseEvent e) {

    }

    @Override
    public void mouseRelease(MouseEvent e) {
        MapView m = MainFrame.getInstance()
                .getProjectView().getCurrentMapView();
        ElementPainter ep = m.elementAt(e.getPoint());
        if (ep == null)
            return;
        HashSet<Element<?>> s = new HashSet<>();
        if (m.isSelected(ep)) {
            for (ElementPainter p : m.getSelectedPainters()) {
                s.add(p.getElement());
            }
        } else {
            s.add(ep.getElement());
        }
        for (ElementPainter p : m.getElementPainters())
            if (p instanceof LinkPainter) {
                Link l = ((LinkPainter) p).getElement();
                if (s.contains(l.getFrom()) || s.contains(l.getTo()))
                    s.add(l);
            }

        AppCore.getInstance().getMapRepository().newCommand(m.getMap(), new RemoveElementsCommand(m.getMap(), s.stream().toList()));
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
