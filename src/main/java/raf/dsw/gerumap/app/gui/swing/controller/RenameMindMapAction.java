package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RenameMindMapAction extends AbstractGerumapAction {
    public RenameMindMapAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/rename_map_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/rename_map_icon_l.png"));
        putValue(NAME, "Rename Mind Map");
        putValue(SHORT_DESCRIPTION, "Rename Mind Map");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MindMap currMap = MainFrame.getInstance().getProjectView().getCurrentMapView().getMap();

        String newName = GuiUtils.getUserInputWithDialog("Rename", "Enter mind map name", currMap.getName());
        if(newName == null || newName.compareTo(currMap.getName()) == 0)
            return;

        if(newName.length() == 0) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_CANNOT_BE_EMPTY, "Mind map name cannot be empty"));
            return;
        }

        if(MainFrame.getInstance().getProjectView().getCurrentProject().getChildByName(newName) != null) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_ALREADY_USED, "A different mind map with the name \"" + newName + "\" already exists"));
            return;
        }

        currMap.setName(newName);
    }
}
