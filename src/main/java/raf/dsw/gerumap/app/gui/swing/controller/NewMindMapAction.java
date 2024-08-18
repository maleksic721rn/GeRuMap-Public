package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.factory.MindMapFactory;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public class NewMindMapAction extends AbstractGerumapAction {

    public NewMindMapAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/new_map_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/new_map_icon_l.png"));
        putValue(NAME, "New Mind Map");
        putValue(SHORT_DESCRIPTION, "New Mind Map");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noProjectSelected())
            return;

        Project currentProject = MainFrame.getInstance().getProjectView().getCurrentProject();

        String mapName = GuiUtils.getUserInputWithDialog("New mind map", "Enter mind map name", "NewMindMap");
        if(mapName == null)
            return;

        if(currentProject.getChildByName(mapName) != null) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_ALREADY_USED, "A mind map with the name \"" + mapName + "\" already exists"));
            return;
        }

        if(mapName.length() == 0) {
            AppCore.getInstance().getMessageGenerator()
                    .sendMessage(new Message(MessageSeverity.ERROR, MessageType.NAME_CANNOT_BE_EMPTY, "Mind map name cannot be empty"));
            return;
        }

        int r = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
                "Do you want to initialize the mind map with a template?",
                "Use template?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (r == JOptionPane.NO_OPTION) {
            MindMapFactory mindMapFactory = new MindMapFactory();
            mindMapFactory.getNode(currentProject, mapName);
        }
        else if (r == JOptionPane.YES_OPTION) {
            File f = GuiUtils.getJsonFileWithDialog(true, Objects.requireNonNull(GuiUtils.class.getResource("/templates")).getPath());
            if (f == null)
                return;
            MindMap m = AppCore.getInstance().getSerializer().deserialize(f, MindMap.class);
            if (m == null)
                return;
            m.setTemplate(false);
            m.setName(mapName);
            m.setParent(currentProject);
            currentProject.addChild(m);
            m.fixAfterDeserialization();
        }
    }
}
