package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.messageGenerator.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class OpenAction extends AbstractGerumapAction {
    public OpenAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/explorer_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/explorer_icon_l.png"));
        putValue(NAME, "Open Project");
        putValue(SHORT_DESCRIPTION, "Open Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File f = GuiUtils.getJsonFileWithDialog(true);
        if (f == null)
            return;

        Project p = AppCore.getInstance().getSerializer().deserialize(f, Project.class);
        if (p == null)
            return;

        if (AppCore.getInstance().getMapRepository()
                .getProjectExplorer().getChildByName(p.getName()) != null) {
            AppCore.getInstance().getMessageGenerator().sendMessage(
                    new Message(MessageSeverity.ERROR,
                            MessageType.NAME_ALREADY_USED,
                            "A project of name " + p.getName() + " is already open.")
            );
            return;
        }

        p.setDirectory(f);
        AppCore.getInstance().getMapRepository().getProjectExplorer().addChild(p);
        for (MindMap m : p.getChildren()) {
            m.setParent(p);
            p.addChild(m);
            m.fixAfterDeserialization();
        }
        MainFrame.getInstance().getProjectView().updateWithClear();
        MainFrame.getInstance().getProjectView().getTabbedPane().setSelectedIndex(MainFrame.getInstance().getProjectView().getTabbedPane().getTabCount() - 1);
    }
}
