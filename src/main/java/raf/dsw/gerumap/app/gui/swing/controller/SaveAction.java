package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAction extends AbstractGerumapAction {
    public SaveAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/save_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/save_icon_l.png"));
        putValue(NAME, "Save Project");
        putValue(SHORT_DESCRIPTION, "Save Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noProjectSelected())
            return;

        Project p = MainFrame.getInstance().getProjectView().getCurrentProject();
        if (p.getDirectory() == null) {
            MainFrame.getInstance().getActionManager().getSaveAsAction().actionPerformed(e);
            return;
        }
        for (MindMap m : p.getChildren())
            m.prepareForSerialization();
        AppCore.getInstance().getSerializer().serialize(p.getDirectory(), p);
    }
}
