package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;
import raf.dsw.gerumap.app.mapRepository.commands.implementation.MoveTermsCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;
import raf.dsw.gerumap.app.gui.swing.view.TermPainter;

import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class MoveState extends State {
    private HashSet<Term> terms = new HashSet<>();
    private Point from;
    private double totalDeltaX = 0;
    private double totalDeltaY = 0;

    @Override
    public void mousePress(MouseEvent e) {
        MapView mv = MainFrame.getInstance().getProjectView().getCurrentMapView();
        ElementPainter ep = mv.elementAt(e.getPoint());
        if (mv.isSelected(ep)) {
            for (ElementPainter p : mv.getSelectedPainters())
                if (p instanceof TermPainter)
                    terms.add(((TermPainter) p).getElement());
        }
        else if (ep instanceof TermPainter)
            terms.add(((TermPainter) ep).getElement());
        if (!terms.isEmpty()) {
            from = e.getPoint();
            totalDeltaX = 0;
            totalDeltaY = 0;
        }
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        endMove();
    }

    @Override
    public void mouseMove(MouseEvent e) {
        if (terms.isEmpty()) {
            ControllerUtils.highlightElement(e);
            return;
        }
        ControllerUtils.unhighlightElements();
        MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();

        double deltaX = (e.getX() - from.getX()) * (1 / cm.getZoom());
        double deltaY = (e.getY() - from.getY()) * (1 / cm.getZoom());
        for (Term t : terms) {
            Point old = t.getPosition();
            t.setPosition((int) Math.round(old.getX() + deltaX), (int) Math.round(old.getY() + deltaY));
        }
        totalDeltaX += deltaX;
        totalDeltaY += deltaY;
        from = e.getPoint();
    }

    @Override
    public void mouseWheelMove(MouseWheelEvent e) {

    }

    @Override
    public void mapViewChanged(ChangeEvent e) {
        endMove();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        endMove();
        ControllerUtils.unhighlightElements();
    }

    private void endMove() {
        if(terms.size() > 0 && totalDeltaX != 0 && totalDeltaY != 0) {
            List<Term> movedTerms = new ArrayList<>(terms);
            MindMap m = movedTerms.get(0).getParent();
            AppCore.getInstance().getMapRepository().newCommand(m, new MoveTermsCommand(movedTerms, totalDeltaX, totalDeltaY), false);
        }
        terms.clear();
        from = null;
    }
}
