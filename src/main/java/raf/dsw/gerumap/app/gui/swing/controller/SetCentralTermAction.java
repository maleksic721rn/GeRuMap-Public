package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;
import raf.dsw.gerumap.app.mapRepository.commands.implementation.SetCentralTermCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.Element;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Set;

public class SetCentralTermAction extends AbstractGerumapAction {
    public SetCentralTermAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/set_central_icon.png"));
        putValue(NAME, "Set Selected Term as Central");
        putValue(SHORT_DESCRIPTION, "Set Selected Term as Central");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MapView mv = MainFrame.getInstance().getProjectView().getCurrentMapView();
        Set<ElementPainter> s = mv.getSelectedPainters();
        if (s.isEmpty()) {
            AppCore.getInstance().getMessageGenerator().sendMessage(new Message(
                    MessageSeverity.INFO,
                    MessageType.NOTHING_SELECTED,
                    "No elements are selected."
            ));
            return;
        }
        if (s.size() > 1) {
            AppCore.getInstance().getMessageGenerator().sendMessage(new Message(
                    MessageSeverity.INFO,
                    MessageType.MULTIPLE_SELECTED,
                    "Multiple elements are selected."
            ));
            return;
        }
        Element<?> el = s.stream().findAny().get().getElement();
        if (!(el instanceof Term)){
            AppCore.getInstance().getMessageGenerator().sendMessage(new Message(
                    MessageSeverity.INFO,
                    MessageType.NO_TERMS_SELECTED,
                    "No terms are selected."
            ));
            return;
        }

        if(mv.getMap().getCentralTerm() == null || !mv.getMap().getCentralTerm().equals(el)) {
            AppCore.getInstance().getMapRepository().newCommand(mv.getMap(), new SetCentralTermCommand(mv.getMap(), (Term) el));
        }
    }
}
