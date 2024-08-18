package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.mapRepository.implementation.ElementStyle;
import raf.dsw.gerumap.app.mapRepository.implementation.Link;

import java.awt.*;
import java.awt.geom.CubicCurve2D;

@Getter
@Setter
public class LinkPainter extends ElementPainter {

    public static final double INTERSECTION_TOLERANCE = 1;
    public LinkPainter(Link element) {
        super(element);
    }

    public Link getElement() {
        return (Link) element;
    }

    public CubicCurve2D makeCurve() {
        CubicCurve2D c = new CubicCurve2D.Double();

        c.setCurve(getElement().getFrom().getCenter(),
                new Point((int) (getElement().getFrom().getCenter().getX() + getElement().getControlPoint1Offset().getX()),
                        (int) (getElement().getFrom().getCenter().getY() + getElement().getControlPoint1Offset().getY())),
                new Point((int) (getElement().getTo().getCenter().getX() + getElement().getControlPoint2Offset().getX()),
                        (int) (getElement().getTo().getCenter().getY() + getElement().getControlPoint2Offset().getY())),
                getElement().getTo().getCenter());
        return c;
    }

    @Override
    public void paint(Graphics2D g) {
        CubicCurve2D curve = makeCurve();
        MapView m = MainFrame.getInstance().getProjectView().getCurrentMapView();

        float h = getElement().getStyle().getStrokeWidth();
        if (m.isSelected(this))
            h += 10;
        else if (m.getHighlightedPainters().contains(this))
            h += 5;

        if (h > getElement().getStyle().getStrokeWidth()) {
            g.setStroke(new BasicStroke(h));
            g.setPaint(ElementStyle.HIGHLIGHT_COLOR);
            g.draw(curve);
        }

        g.setStroke(new BasicStroke(getElement().getStyle().getStrokeWidth()));
        g.setPaint(getElement().getStyle().getStrokeColor());
        g.draw(curve);
    }

    @Override
    public boolean elementAt(Point pos) {
        Point mPoint = ControllerUtils.realPointInverse(pos);
        return makeCurve().intersects(mPoint.getX() - INTERSECTION_TOLERANCE, mPoint.getY() - INTERSECTION_TOLERANCE,
                2 * INTERSECTION_TOLERANCE, 2 * INTERSECTION_TOLERANCE);
    }

    @Override
    public boolean elementIn(Shape shape) {
        return makeCurve().intersects(ControllerUtils.realShapeInverse(shape).getBounds());
    }

    public boolean CP1In(Point pos) {
        Point mPoint = ControllerUtils.realPointInverse(pos);
        double tol = 4 * INTERSECTION_TOLERANCE;
        return new Rectangle(new Point((int) (mPoint.getX() - tol), (int) (mPoint.getY() - tol)),
                new Dimension((int) (2 * tol), (int) (2 * tol)))
                .contains(
                    new Point((int) (getElement().getFrom().getCenter().getX() + getElement().getControlPoint1Offset().getX()),
                        (int) (getElement().getFrom().getCenter().getY() + getElement().getControlPoint1Offset().getY()))
                );
    }

    public boolean CP2In(Point pos) {
        Point mPoint = ControllerUtils.realPointInverse(pos);
        double tol = 4 * INTERSECTION_TOLERANCE;
        return new Rectangle(new Point((int) (mPoint.getX() - tol), (int) (mPoint.getY() - tol)),
                new Dimension((int) (2 * tol), (int) (2 * tol)))
                .contains(
                    new Point((int) (getElement().getTo().getCenter().getX() + getElement().getControlPoint2Offset().getX()),
                        (int) (getElement().getTo().getCenter().getY() + getElement().getControlPoint2Offset().getY()))
                );
    }
}
