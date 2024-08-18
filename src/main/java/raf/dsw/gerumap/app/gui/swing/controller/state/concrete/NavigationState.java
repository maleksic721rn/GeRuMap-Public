package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;

import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class NavigationState extends State {

    private Point lastPoint;
    public static double MIN_ZOOM = 0.1;
    public static double MAX_ZOOM = 5;
    @Override
    public void mousePress(MouseEvent e) {
        lastPoint = e.getPoint();
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        resetLastPoint();
    }

    @Override
    public void mouseMove(MouseEvent e) {
        if(lastPoint != null) {
            MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();

            double xDiff = (e.getX() - lastPoint.getX()) * (1 / cm.getZoom());
            double yDiff = (e.getY() - lastPoint.getY()) * (1 / cm.getZoom());
            cm.getCurrentTransform().translate(xDiff, yDiff);
            cm.setCentered(false);

            cm.repaint();
            lastPoint = e.getPoint();
        }
    }

    @Override
    public void mouseWheelMove(MouseWheelEvent e) {
        if(e.isControlDown()) {
            MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();

            double oldZoom = cm.getZoom();
            double amount = Math.pow(1.1, e.getScrollAmount());
            double newScale;
            if (e.getWheelRotation() < 0) {
                newScale = amount;
                if (oldZoom * newScale > MAX_ZOOM) {
                    newScale = MAX_ZOOM / oldZoom;
                }
            } else {
                newScale = 1 / amount;
                if (oldZoom * newScale < MIN_ZOOM) {
                    newScale = MIN_ZOOM / oldZoom;
                }
            }

            Point mp = ControllerUtils.realPointInverse(e.getPoint());

            cm.getCurrentTransform().setToIdentity();
            cm.getCurrentTransform().translate(e.getPoint().getX(), e.getPoint().getY());
            cm.getCurrentTransform().scale(oldZoom * newScale, oldZoom * newScale);
            cm.getCurrentTransform().translate(-mp.getX(), -mp.getY());

            cm.repaint();
        }
    }

    @Override
    public void mapViewChanged(ChangeEvent e) {
        resetLastPoint();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        resetLastPoint();
    }

    private void resetLastPoint() {
        lastPoint = null;
    }
}
