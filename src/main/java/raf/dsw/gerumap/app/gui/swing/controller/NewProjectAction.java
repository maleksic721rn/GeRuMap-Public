package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.mapRepository.factory.ProjectFactory;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewProjectAction extends AbstractGerumapAction {

    public NewProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/new_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/new_icon_l.png"));
        putValue(NAME, "New Project");
        putValue(SHORT_DESCRIPTION, "New Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String projName = GuiUtils.getUserInputWithDialog("New project", "Enter project name", "NewProject");
        if(projName == null)
            return;

        if(projName.length() == 0) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_CANNOT_BE_EMPTY, "Project name cannot be empty"));
            return;
        }

        if(AppCore.getInstance().getMapRepository().getProjectExplorer().getChildByName(projName) != null) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_ALREADY_USED, "A project with the name \"" + projName + "\" already exists"));
            return;
        }

        String authorName = GuiUtils.getUserInputWithDialog("New project", "Enter project author", "");
        if(authorName == null)
            return;

        ProjectFactory projectFactory = new ProjectFactory();
        Project p = projectFactory.getNode(AppCore.getInstance().getMapRepository().getProjectExplorer(), projName);
        p.setAuthor(authorName);
    }
}