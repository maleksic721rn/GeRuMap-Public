package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.gui.swing.view.*;
import raf.dsw.gerumap.app.mapRepository.commands.implementation.AddElementCommand;
import raf.dsw.gerumap.app.mapRepository.factory.LinkFactory;
import raf.dsw.gerumap.app.mapRepository.implementation.*;

import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;

public class NewLinkState extends State {

    private LinkFactory lf = new LinkFactory();
    private Term from;
    private ElementStyle es = ElementStyle.getDefaultStyle();

    @RequiredArgsConstructor
    private static class LinkStatePainter implements StatePainter {
        @NonNull private Point from, to;
        @NonNull private MapView mapView;

        public void setTo(Point point) {
            boolean r = to.equals(point);
            to = point;
            if (!r)
                mapView.repaint();
        }
        @Override
        public void paint(Graphics2D g) {
            Line2D.Double line = new Line2D.Double(from, to);

            g.setStroke(new BasicStroke((float) (ElementStyle.PREVIEW_WIDTH * (1 / mapView.getZoom()))));
            g.setPaint(ElementStyle.PREVIEW_COLOR);
            g.draw(line);
        }

        @Override
        public boolean topLayer() {
            return false;
        }
    }

    private LinkStatePainter sp;

    @Override
    public void mousePress(MouseEvent e) {

    }

    @Override
    public void mouseRelease(MouseEvent e) {
        MapView mv = MainFrame.getInstance().getProjectView().getCurrentMapView();
        MindMap m = mv.getMap();
        if(from == null) {
            for (ElementPainter ep : MainFrame.getInstance().getProjectView().getCurrentMapView().getElementPainters()) {
                if((ep instanceof TermPainter) && ep.elementAt(e.getPoint())) {
                    from = ((TermPainter) ep).getElement();
                    sp = new LinkStatePainter(from.getCenter(), ControllerUtils.realPointInverse(e.getPoint()), mv);
                    mv.setStatePainter(sp);
                    break;
                }
            }
        } else {
            for (ElementPainter ep : MainFrame.getInstance().getProjectView().getCurrentMapView().getElementPainters()) {
                if((ep instanceof TermPainter) && ep.elementAt(e.getPoint())) {
                    Term to = ((TermPainter) ep).getElement();
                    if(!(to.equals(from)) && m.getChildren().stream().noneMatch(el -> (el instanceof Link) &&
                            ((((Link) el).getFrom().equals(from) && ((Link) el).getTo().equals(to)) ||
                             (((Link) el).getTo().equals(from) && ((Link) el).getFrom().equals(to))))) {
                        Link link = lf.getNode(null, from.getName() + "--" + to.getName());
                        link.setFrom(from);
                        link.setTo(to);
                        link.setStyle(es);

                        AppCore.getInstance().getMapRepository().newCommand(m, new AddElementCommand(m, link));
                        break;
                    }
                }
            }
            resetFromTerm();
        }
    }

    @Override
    public void mouseMove(MouseEvent e) {
        if (sp != null)
            sp.setTo(ControllerUtils.realPointInverse(e.getPoint()));
        ControllerUtils.highlightTerm(e);
    }

    @Override
    public void mouseWheelMove(MouseWheelEvent e) {

    }

    @Override
    public void mapViewChanged(ChangeEvent e) {
        resetFromTerm();
    }

    @Override
    public void start() {
        ElementStyle s = new StyleDialog().showDialog();
        if (s != null)
            es = s;
    }

    @Override
    public void stop() {
        resetFromTerm();
        ControllerUtils.unhighlightElements();
    }

    private void resetFromTerm() {
        MainFrame.getInstance().getProjectView().getCurrentMapView()
                .setStatePainter(null);
        sp = null;
        from = null;
    }
}
