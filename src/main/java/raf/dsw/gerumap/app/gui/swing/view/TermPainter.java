package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.mapRepository.implementation.ElementStyle;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;

import java.awt.*;
@Getter
@Setter
public class TermPainter extends ElementPainter {

    public TermPainter(Term element) {
        super(element);
    }

    public Term getElement() {
        return (Term) element;
    }

    public Shape makeShape() {
        return new Rectangle(new Point((int) (getElement().getPosition().getX() - getElement().getPadding().left),
                (int) (getElement().getPosition().getY() - getElement().getPadding().top)),

                new Dimension((int) (getElement().getDimension().getWidth() + getElement().getPadding().right + getElement().getPadding().left),
                        (int) (getElement().getDimension().getHeight() + getElement().getPadding().bottom + getElement().getPadding().top)));
    }

    @Override
    public void paint(Graphics2D g) {
        Shape shape = makeShape();

        MapView m = MainFrame.getInstance().getProjectView().getCurrentMapView();
        float h = getElement().getStyle().getStrokeWidth();
        if (m.isSelected(this))
            h += 10;
        else if (m.getHighlightedPainters().contains(this))
            h += 5;

        if (h > getElement().getStyle().getStrokeWidth()) {
            g.setStroke(new BasicStroke(h));
            g.setPaint(ElementStyle.HIGHLIGHT_COLOR);
            g.draw(shape);
        }
        g.setPaint(getElement().getStyle().getFillColor());
        g.fill(shape);
        g.setStroke(new BasicStroke(getElement().getStyle().getStrokeWidth()));
        g.setPaint(getElement().getStyle().getStrokeColor());
        g.draw(shape);
        g.setPaint(getElement().getStyle().getTextColor());
        Font f = g.getFont();
        if (getElement().isCentral())
            g.setFont(new Font(f.getName(), Font.BOLD, f.getSize()));
        g.drawString(getElement().getName(), (int)getElement().getPosition().getX(), (int)(getElement().getPosition().getY() + getElement().getDimension().getHeight()));
        if (getElement().isCentral())
            g.setFont(f);
    }

    @Override
    public boolean elementAt(Point pos) {
        return makeShape().contains(ControllerUtils.realPointInverse(pos));
    }

    @Override
    public boolean elementIn(Shape shape) {
        return makeShape().intersects(ControllerUtils.realShapeInverse(shape).getBounds());
    }
}
