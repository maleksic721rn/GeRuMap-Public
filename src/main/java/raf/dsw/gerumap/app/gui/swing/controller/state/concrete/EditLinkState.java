package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.gui.swing.view.*;
import raf.dsw.gerumap.app.mapRepository.implementation.ElementStyle;
import raf.dsw.gerumap.app.mapRepository.implementation.Link;

import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Ellipse2D;

public class EditLinkState extends State {

    private Link link;
    private boolean cp2 = false;
    private Point from;

    @RequiredArgsConstructor
    private static class EditLinkStatePainter implements StatePainter {
        @NonNull private MapView mapView;

        public static int POINT_RADIUS = 4;

        public void repaint() { mapView.repaint(); }

        @Override
        public void paint(Graphics2D g) {
            for (ElementPainter ep : mapView.getElementPainters()) {
                if((ep instanceof LinkPainter)) {
                    g.setStroke(new BasicStroke((float) (ElementStyle.PREVIEW_WIDTH * (1 / mapView.getZoom()))));
                    g.setPaint(ElementStyle.PREVIEW_COLOR);

                    double scaledSize = POINT_RADIUS * (1 / mapView.getZoom());
                    LinkPainter lp = (LinkPainter) ep;
                    Point ctr1 = lp.getElement().getFrom().getCenter();
                    Point ctr2 = lp.getElement().getTo().getCenter();
                    Point cp1 = lp.getElement().getControlPoint1Offset();
                    Point cp2 = lp.getElement().getControlPoint2Offset();

                    Ellipse2D cp1C = new Ellipse2D.Double();
                    cp1C.setFrame(ctr1.x + cp1.x - scaledSize, ctr1.y + cp1.y - scaledSize, scaledSize * 2, scaledSize * 2);

                    Ellipse2D cp2C = new Ellipse2D.Double();
                    cp2C.setFrame(ctr2.x + cp2.x - scaledSize, ctr2.y + cp2.y - scaledSize, scaledSize * 2, scaledSize * 2);

                    g.drawLine(ctr1.x, ctr1.y, ctr1.x + cp1.x, ctr1.y + cp1.y);
                    g.fill(cp1C);
                    g.drawLine(ctr2.x, ctr2.y, ctr2.x + cp2.x, ctr2.y + cp2.y);
                    g.fill(cp2C);
                }
            }
        }

        @Override
        public boolean topLayer() {
            return true;
        }
    }

    EditLinkStatePainter editLinkStatePainter;

    @Override
    public void mousePress(MouseEvent e) {
        MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();
        for (ElementPainter ep : cm.getElementPainters())
        {
            if (ep instanceof LinkPainter)
            {
                LinkPainter lp = (LinkPainter) ep;
                if(lp.CP1In(e.getPoint()) || lp.CP2In(e.getPoint())) {
                    link = lp.getElement();
                    cp2 = lp.CP2In(e.getPoint());
                    from = e.getPoint();
                    break;
                }
            }
        }
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        endMove();
    }

    @Override
    public void mouseMove(MouseEvent e) {
        if (from != null) {
            MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();

            double deltaX = (e.getX() - from.getX()) * (1 / cm.getZoom());
            double deltaY = (e.getY() - from.getY()) * (1 / cm.getZoom());

            Point curr = cp2 ? link.getControlPoint2Offset() : link.getControlPoint1Offset();
            curr.setLocation(
                    new Point((int) Math.round(curr.getX() + deltaX), (int) Math.round(curr.getY() + deltaY))
            );

            from = e.getPoint();
            editLinkStatePainter.repaint();
        }
    }

    @Override
    public void mouseWheelMove(MouseWheelEvent e) {

    }

    @Override
    public void mapViewChanged(ChangeEvent e) {
        reset();
    }

    @Override
    public void start() {
        MapView cm = MainFrame.getInstance().getProjectView().getCurrentMapView();
        editLinkStatePainter = new EditLinkStatePainter(cm);
        cm.setStatePainter(editLinkStatePainter);
    }

    @Override
    public void stop() {
        endMove();
        MainFrame.getInstance().getProjectView().getCurrentMapView().setStatePainter(null);
    }

    private void endMove() {
        from = null;
    }

    private void reset() { stop(); start(); }
}
