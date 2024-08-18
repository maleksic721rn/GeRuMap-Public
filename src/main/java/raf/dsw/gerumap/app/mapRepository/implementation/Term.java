package raf.dsw.gerumap.app.mapRepository.implementation;

import flexjson.JSON;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotificationType;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeVisitor;
import raf.dsw.gerumap.app.serializer.*;

import java.awt.*;
@Getter
@Setter
public class Term extends Element<Term> implements Comparable<Term> {
    @JSON(transformer = PointTransformer.class, objectFactory = PointFactory.class)
    private Point position;
    @JSON(transformer = DimensionTransformer.class, objectFactory = DimensionFactory.class)
    private Dimension dimension;
    @JSON(transformer = InsetsTransformer.class, objectFactory = InsetsFactory.class)
    private Insets padding = new Insets(8, 8, 8, 8);
    private int id;
    private boolean central;
    @Override
    public void accept(MapNodeVisitor visitor) {
        visitor.visit(this);
    }

    public Point getCenter() {
        return  new Point((int) (getPosition().getX() + getDimension().getWidth() * 0.5),
                (int) (getPosition().getY() + getDimension().getHeight() * 0.5));
    }
    
    public void setPosition(Point position) {
        this.position = position;
        update(new MapNodeNotification(this, MapNodeNotificationType.TERM_MOVED, this));
    }

    public void setCentral(boolean central) {
        this.central = central;
        update(new MapNodeNotification(this, MapNodeNotificationType.TERM_CENTRAL_CHANGED, this));
    }

    @Override
    public int compareTo(Term term) {
        if (equals(term))
            return 0;
        Point p1 = getPosition();
        Point p2 = term.getPosition();
        int c = Double.compare(p1.getX(), p2.getX());
        if (c == 0)
            return Double.compare(p1.getY(), p2.getY());
        return c;
    }

    public void setPosition(int x, int y)
    {
        setPosition(new Point(x, y));
    }
}