package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RenameProjectAction extends AbstractGerumapAction {

    public RenameProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/rename_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/rename_icon_l.png"));
        putValue(NAME, "Rename Project");
        putValue(SHORT_DESCRIPTION, "Rename Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noProjectSelected())
            return;

        String newName = GuiUtils.getUserInputWithDialog("Rename", "Enter project name", MainFrame.getInstance().getProjectView().getCurrentProject().getName());
        if(newName == null || newName.compareTo(MainFrame.getInstance().getProjectView().getCurrentProject().getName()) == 0)
            return;

        if(newName.length() == 0) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_CANNOT_BE_EMPTY, "Project name cannot be empty"));
            return;
        }

        if(AppCore.getInstance().getMapRepository().getProjectExplorer().getChildByName(newName) != null) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_ALREADY_USED, "A different project with the name \"" + newName + "\" already exists"));
            return;
        }

        MainFrame.getInstance().getProjectView().getCurrentProject().setName(newName);
    }
}
