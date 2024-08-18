package raf.dsw.gerumap.app.mapRepository.implementation;

import flexjson.JSON;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotificationType;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeVisitor;
import raf.dsw.gerumap.app.serializer.PointFactory;
import raf.dsw.gerumap.app.serializer.PointTransformer;

import java.awt.*;

@Getter
public class Link extends Element<Link> implements Comparable<Link> {
    private Term from, to;
    @JSON(transformer = PointTransformer.class, objectFactory = PointFactory.class)
    @Setter private Point controlPoint1Offset = new Point(0, 0);
    @JSON(transformer = PointTransformer.class, objectFactory = PointFactory.class)
    @Setter private Point controlPoint2Offset = new Point(0, 0);

    public void setFrom(Term from) {
        this.from = from;
        update(new MapNodeNotification(getName(), MapNodeNotificationType.LINK_FROM_CHANGED, this));
    }

    public void setTo(Term to) {
        this.to = to;
        update(new MapNodeNotification(getName(), MapNodeNotificationType.LINK_TO_CHANGED, this));
    }

    @Override
    public void accept(MapNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int compareTo(Link link) {
        if (equals(link))
            return 0;
        int c = getFrom().compareTo(link.getFrom());
        if (c == 0)
            return getTo().compareTo(link.getTo());
        return c;
    }
}
