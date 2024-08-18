package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import raf.dsw.gerumap.app.mapRepository.implementation.Element;

import java.awt.*;

public abstract class ElementPainter {
    @Getter protected Element<?> element;
    public ElementPainter(Element<?> element) {
        this.element = element;
    }
    public abstract void paint(Graphics2D g);
    public abstract boolean elementAt(Point pos);

    public abstract boolean elementIn(Shape shape);
}