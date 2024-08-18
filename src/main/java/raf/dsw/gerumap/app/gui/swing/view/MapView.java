package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.controller.MouseController;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;
import raf.dsw.gerumap.app.observer.Subscriber;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeVisitor;
import raf.dsw.gerumap.app.mapRepository.implementation.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Getter
public class MapView extends JPanel implements Subscriber<MindMap, MapNodeNotification> {
    private MindMap map;

    private static int IMAGE_PADDING = 8;

    private static class ElementPainterComparator implements Comparator<ElementPainter> {
        @Override
        public int compare(ElementPainter elementPainter, ElementPainter t1) {
            if (elementPainter.equals(t1))
                return 0;
            if (t1 == null)
                return 1;
            if (elementPainter instanceof TermPainter) {
                if (t1 instanceof LinkPainter)
                    return 1;
                else
                    return ((TermPainter) elementPainter).getElement()
                            .compareTo(((TermPainter) t1).getElement());
            }
            else if (t1 instanceof TermPainter)
                return -1;
            else
                return ((LinkPainter) elementPainter).getElement()
                        .compareTo(((LinkPainter) t1).getElement());
        }
    }

    private Set<ElementPainter> elementPainters =
            new TreeSet<>(new ElementPainterComparator());

    private Set<ElementPainter> selectedPainters = new HashSet<>();
    private Set<ElementPainter> highlightedPainters = new HashSet<>();
    private StatePainter statePainter;

    private AffineTransform currentTransform = new AffineTransform();

    public void setHighlightedPainters(ElementPainter ... elementPainters) {
        highlightedPainters.clear();
        if(elementPainters.length > 0 && elementPainters[0] != null) {
            highlightedPainters.addAll(Arrays.asList(elementPainters));
        }
        repaint();
    }

    public void setStatePainter(StatePainter painter) {
        boolean r = painter != statePainter;
        statePainter = painter;
        if (r)
            repaint();
    }

    public boolean isSelected(ElementPainter elementPainter) {
        return selectedPainters.contains(elementPainter);
    }

    public void toggleSelection(ElementPainter elementPainter) {
        if (isSelected(elementPainter))
            selectedPainters.remove(elementPainter);
        else
            selectedPainters.add(elementPainter);
        repaint();
    }

    public void clearSelection() {
        for(ElementPainter ep : getElementPainters()) {
            if(isSelected(ep))
                toggleSelection(ep);
        }
    }

    private MouseController mouseController = new MouseController();

    private class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            if (centered) {
                Term c = map.getCentralTerm();
                if (c != null)
                    panToCentralTerm();
            }
        }
    }
    private ResizeListener resizeListener = new ResizeListener();

    @Setter private boolean centered = true;

    public MapView(MindMap map) {
        this.map = map;
        map.subscribe(this);
        addMouseListener(mouseController);
        addMouseMotionListener(mouseController);
        addMouseWheelListener(mouseController);
        addComponentListener(resizeListener);
        setBackground(Color.white);

        for(Element<?> e : map.getChildren()) {
            if(e instanceof Term)
                getElementPainters().add(new TermPainter((Term) e));
            else
                getElementPainters().add(new LinkPainter((Link) e));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform lastAT = g2.getTransform();
        g2.transform(getCurrentTransform());
        if (statePainter != null && !statePainter.topLayer())
            statePainter.paint(g2);
        for (ElementPainter p : elementPainters) {
            p.paint(g2);
        }
        if (statePainter != null && statePainter.topLayer())
            statePainter.paint(g2);
        g2.setTransform(lastAT);
    }

    @Override
    public void update(MindMap object, MapNodeNotification notification) {
        switch (notification.getType()) {
            case ELEMENT_ADDED -> {
                ElementPainter[] e = new ElementPainter[1];
                MapNodeVisitor v = new MapNodeVisitor() {
                    @Override
                    public void visit(Term object) {
                        e[0] = new TermPainter((Term) notification.getProperty());
                    }

                    @Override
                    public void visit(Link object) {
                        e[0] = new LinkPainter((Link) notification.getProperty());
                    }

                    @Override
                    public void visit(MindMap object) {
                        throw new RuntimeException();
                    }

                    @Override
                    public void visit(Project object) {
                        throw new RuntimeException();
                    }

                    @Override
                    public void visit(ProjectExplorer object) {
                        throw new RuntimeException();
                    }
                };
                ((Element<?>) notification.getProperty()).accept(v);
                elementPainters.add(e[0]);
                repaint();
            }
            case ELEMENT_REMOVED -> {
                Object e = notification.getProperty();
                ElementPainter ep = elementPainters.stream().filter(elementPainter -> elementPainter.getElement() == e).findAny().get();
                elementPainters.remove(ep);
                selectedPainters.remove(ep);
                if (highlightedPainters.contains(ep))
                    highlightedPainters.clear();
                repaint();
            }
            case ELEMENT_RENAMED, ELEMENT_STYLE_CHANGED ->
                repaint();
            case TERM_CENTRAL_CHANGED ->
                panToCentralTerm();
            case TERM_MOVED -> {
                if (centered)
                    panToCentralTerm();
                else
                    repaint();
            }
        }
    }

    public ElementPainter elementAt(Point p) {
        return elementPainters.stream()
                .filter(elementPainter -> elementPainter.elementAt(p))
                .findAny().orElse(null);
    }

    public Stream<ElementPainter> elementsIn(Shape shape) {
        return elementPainters.stream()
                .filter(elementPainter -> elementPainter.elementIn(shape));
    }

    public double getZoom() {
        return getCurrentTransform().getScaleX();
    }

    public Rectangle getTotalBounds() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxStrokeWidth = 0;
        int padding;

        for(ElementPainter ep : getElementPainters()) {
            Rectangle bounds;
            if(ep instanceof TermPainter) {
                bounds = ((TermPainter) ep).makeShape().getBounds();
            } else {
                bounds = ((LinkPainter) ep).makeCurve().getBounds();
            }

            maxStrokeWidth = Math.max(maxStrokeWidth, Math.round(ep.getElement().getStyle().getStrokeWidth()));
            minX = Math.min(minX, (int)bounds.getX());
            minY = Math.min(minY, (int)bounds.getY());
            maxX = Math.max(maxX, (int)(bounds.getX() + bounds.getWidth()));
            maxY = Math.max(maxY, (int)(bounds.getY() + bounds.getHeight()));
        }

        padding = IMAGE_PADDING + maxStrokeWidth;
        if(getMap().getCentralTerm() != null) {
            Point p = getMap().getCentralTerm().getCenter();
            int centerX = (int) p.getX();
            int centerY = (int) p.getY();

            int minXDiff = (centerX - minX);
            int maxXDiff = (maxX - centerX);
            int minYDiff = (centerY - minY);
            int maxYDiff = (maxY - centerY);

            if(maxXDiff > minXDiff)
                minX = centerX - maxXDiff;
            else
                maxX = centerX + minXDiff;

            if(maxYDiff > minYDiff)
                minY = centerY - maxYDiff;
            else
                maxY = centerY + minYDiff;
        }

        return new Rectangle(minX - padding, minY - padding, maxX - minX + 2 * padding, maxY - minY + 2 * padding);
    }

    public void exportImage(File img) {
        Rectangle bounds = getTotalBounds();
        BufferedImage outputImage = new BufferedImage((int)bounds.getWidth(), (int)bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = outputImage.createGraphics();

        AffineTransform lastT = (AffineTransform) getCurrentTransform().clone();
        getCurrentTransform().setToIdentity();
        State lastState = MainFrame.getInstance().getProjectView().getStateManager().getCurrentState();
        Set<ElementPainter> prevSelectedPainters = new HashSet<>(getSelectedPainters());

        MainFrame.getInstance().getProjectView().getStateManager().setCurrentState(MainFrame.getInstance().getProjectView().getStateManager().getNavigationState());
        clearSelection();

        int offsetX, offsetY = (int) bounds.getY();
        try {
            while (offsetY <= bounds.getHeight()) {
                offsetX = (int) bounds.getX();
                while (offsetX <= bounds.getWidth()) {
                    getCurrentTransform().setToTranslation(-offsetX, -offsetY);
                    AffineTransform inv = getCurrentTransform().createInverse();
                    inv.translate(-bounds.getX(), -bounds.getY());
                    g.setTransform(inv);
                    paintAll(g);
                    offsetX += getWidth();
                }
                offsetY += getHeight();
            }
        } catch (NoninvertibleTransformException e) {
            return;
        }

        MainFrame.getInstance().getProjectView().getStateManager().setCurrentState(lastState);
        getCurrentTransform().setTransform(lastT);
        for(ElementPainter ep : prevSelectedPainters)
            toggleSelection(ep);

        try {
            ImageIO.write(outputImage, "png", img);
        } catch (IOException e) {
            AppCore.getInstance().getMessageGenerator().sendMessage(
                    new Message(MessageSeverity.ERROR,
                            MessageType.FAILED_TO_WRITE_FILE,
                            "Failed to write file " + img)
            );
        }

        repaint();
    }

    public void panToCentralTerm() {
        if(getMap().getCentralTerm() != null) {
            Point p = getMap().getCentralTerm().getCenter();
            double z = getZoom();

            getCurrentTransform().setToIdentity();
            getCurrentTransform().translate(getWidth() / 2.0d, getHeight() / 2.0d);
            getCurrentTransform().scale(z, z);
            getCurrentTransform().translate(-p.getX(), -p.getY());
            setCentered(true);
        }
        repaint();
    }
}
