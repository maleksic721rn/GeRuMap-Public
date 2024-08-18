package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;
import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;
import raf.dsw.gerumap.app.gui.swing.view.TermPainter;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Set;

public final class ControllerUtils {
    private ControllerUtils() { }

    public static Icon loadIcon(Class<?> fromClass, String fromFile) {
        Icon icon = null;

        try {
            icon = new ImageIcon(Objects.requireNonNull(fromClass.getResource(fromFile)));
        } catch(NullPointerException e) {
            System.err.println("Resource error: " + e.getMessage());
        }

        return icon;
    }

    public static void highlightElement(MouseEvent e) {
        MapView m = MainFrame.getInstance().getProjectView().getCurrentMapView();
        m.setHighlightedPainters(m.elementAt(e.getPoint()));
    }

    public static void unhighlightElements() {
        MapView m = MainFrame.getInstance().getProjectView().getCurrentMapView();
        m.setHighlightedPainters((ElementPainter) null);
    }

    public static void highlightTerm(MouseEvent e) {
        MapView m = MainFrame.getInstance().getProjectView().getCurrentMapView();
        m.setHighlightedPainters((ElementPainter) null);
        for (ElementPainter ep : MainFrame.getInstance().getProjectView().getCurrentMapView().getElementPainters())
            if((ep instanceof TermPainter) && ep.elementAt(e.getPoint())) {
                m.setHighlightedPainters(ep);
                break;
            }
    }

    public static class ErrorHandling {
        public static boolean noProjectSelected() {
            if (MainFrame.getInstance().getProjectView().getCurrentProject() == null) {
                AppCore.getInstance().getMessageGenerator().sendMessage(new Message(MessageSeverity.ERROR, MessageType.A_PROJECT_MUST_BE_SELECTED, "You must select a project"));
                return true;
            }
            return false;
        }

        public static boolean noValidMindMap() {
            if(ControllerUtils.ErrorHandling.noProjectSelected())
                return true;

            MapView currView = MainFrame.getInstance().getProjectView().getCurrentMapView();
            if(currView == null) {
                AppCore.getInstance().getMessageGenerator().sendMessage(new Message(MessageSeverity.ERROR, MessageType.NO_VALID_NODE, "Project must have at least one mind map"));
                return true;
            }

            return false;
        }

        public static boolean noSelectedElements() {
            if(ControllerUtils.ErrorHandling.noValidMindMap())
                return true;

            Set<ElementPainter> s = MainFrame.getInstance().getProjectView()
                    .getCurrentMapView().getSelectedPainters();
            if (s.isEmpty()) {
                AppCore.getInstance().getMessageGenerator().sendMessage(new Message(
                        MessageSeverity.INFO,
                        MessageType.NOTHING_SELECTED,
                        "No elements are selected."
                ));
                return true;
            }

            return false;
        }
    }

    public static void resizeTermToFitName(Term t) {
        t.setDimension(GuiUtils.getStringSize(MainFrame.getInstance().getProjectView().getCurrentMapView().getGraphics(),
                t.getName(),
                MainFrame.getInstance().getProjectView().getCurrentMapView().getGraphics().getFont(), // mora malo placeholder
                (float) t.getPosition().getX(),
                (float) t.getPosition().getY()));
    }

    public static Point realPointInverse(Point p) {
        try {
            Point2D rPoint = (Point2D) p.clone();
            return (Point) MainFrame.getInstance().getProjectView().getCurrentMapView().getCurrentTransform().inverseTransform(p, rPoint);
        } catch(NoninvertibleTransformException e) {
            throw new RuntimeException(e);
        }
    }
    public static Shape realShapeInverse(Shape s) {
        try {
            return MainFrame.getInstance().getProjectView().getCurrentMapView().getCurrentTransform().createInverse().createTransformedShape(s);
        } catch (NoninvertibleTransformException e) {
            throw new RuntimeException(e);
        }
    }
}