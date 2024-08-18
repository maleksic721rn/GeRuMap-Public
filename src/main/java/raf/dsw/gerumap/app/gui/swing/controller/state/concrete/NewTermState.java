package raf.dsw.gerumap.app.gui.swing.controller.state.concrete;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.controller.state.State;
import raf.dsw.gerumap.app.mapRepository.implementation.ElementStyle;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.StyleDialog;
import raf.dsw.gerumap.app.mapRepository.commands.implementation.AddElementCommand;
import raf.dsw.gerumap.app.mapRepository.factory.TermFactory;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.event.ChangeEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class NewTermState extends State {
    private TermFactory tf = new TermFactory();
    private ElementStyle es = ElementStyle.getDefaultStyle();

    @Override
    public void mousePress(MouseEvent e) {

    }

    @Override
    public void mouseRelease(MouseEvent e) {
        MindMap m = MainFrame.getInstance().getProjectView().getCurrentMapView().getMap();
        String termName = GuiUtils.getUserInputWithDialog("New Term", "Enter term name:", "");

        if(termName == null)
            return;
        if(termName.length() == 0) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_CANNOT_BE_EMPTY, "Term name cannot be empty"));
            return;
        }

        Term t = tf.getNode(null, termName);

        t.setPosition(ControllerUtils.realPointInverse(e.getPoint()));
        ControllerUtils.resizeTermToFitName(t);
        t.setStyle(es);

        AppCore.getInstance().getMapRepository().newCommand(m, new AddElementCommand(m, t));
    }

    @Override
    public void mouseMove(MouseEvent e) {

    }

    @Override
    public void mouseWheelMove(MouseWheelEvent e) {

    }

    @Override
    public void mapViewChanged(ChangeEvent e) {

    }

    @Override
    public void start() {
        ElementStyle s = new StyleDialog().showDialog();
        if (s != null)
            es = s;
    }

    @Override
    public void stop() {

    }
}
