package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.mapRepository.implementation.ElementStyle;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;
import raf.dsw.gerumap.app.gui.swing.view.StatePainter;
import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;

import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class SelectLassoState extends State {
    private Rectangle currRect = null;
    private Point startPoint = null;

    @RequiredArgsConstructor
    private static class SelectLassoStatePainter implements StatePainter {
        @NonNull
        private Rectangle rect;
        @NonNull private MapView mapView;

        public void repaint() {
            mapView.repaint();
        }
        @Override
        public void paint(Graphics2D g) {
            g.setStroke(new BasicStroke((float) (ElementStyle.PREVIEW_WIDTH * (1 / mapView.getZoom()))));
            g.setPaint(ElementStyle.PREVIEW_COLOR);
            g.draw(ControllerUtils.realShapeInverse(rect));
        }

        @Override
        public boolean topLayer() {
            return true;
        }
    }

    private SelectLassoStatePainter sp = null;
    @Override
    public void mousePress(MouseEvent e) {
        startPoint = e.getPoint();
        currRect = new Rectangle(e.getX(), e.getY(), 1, 1);
        sp = new SelectLassoStatePainter(currRect, MainFrame.getInstance().getProjectView().getCurrentMapView());
        MainFrame.getInstance().getProjectView().getCurrentMapView().setStatePainter(sp);
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();
        cm.clearSelection();
        cm.elementsIn(currRect).forEach(ep -> cm.toggleSelection(ep));
        resetSelection();
    }

    @Override
    public void mouseMove(MouseEvent e) {
        if(startPoint != null) {
            currRect.setBounds(
                    (int) Math.min(startPoint.getX(), e.getX()),
                    (int) Math.min(startPoint.getY(), e.getY()),
                    (int) Math.max(1, Math.abs(startPoint.getX() - e.getX())),
                    (int) Math.max(1, Math.abs(startPoint.getY() - e.getY()))
            );
            MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();
            cm.setHighlightedPainters(cm.elementsIn(currRect).toList().toArray(new ElementPainter[0]));
            if (sp != null)
                sp.repaint();
        }
    }

    @Override
    public void mouseWheelMove(MouseWheelEvent e) {

    }

    @Override
    public void mapViewChanged(ChangeEvent e) {
        resetSelection();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        resetSelection();
    }

    private void resetSelection() {
        currRect = null;
        startPoint = null;
        MainFrame.getInstance().getProjectView().getCurrentMapView()
                .setStatePainter(null);
        ControllerUtils.unhighlightElements();
    }
}
