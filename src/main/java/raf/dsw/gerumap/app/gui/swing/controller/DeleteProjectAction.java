package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteProjectAction extends AbstractGerumapAction {
    public DeleteProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/delete_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/delete_icon_l.png"));
        putValue(NAME, "Delete Project");
        putValue(SHORT_DESCRIPTION, "Delete Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noProjectSelected())
            return;

        Project currProj = MainFrame.getInstance().getProjectView().getCurrentProject();
        while(currProj.getChildren().size() > 0)
            MainFrame.getInstance().getActionManager().getDeleteMindMapAction().actionPerformed(e);
        MainFrame.getInstance().getProjectView().clear();
        AppCore.getInstance().getMapRepository().getProjectExplorer().removeChild(currProj);
    }
}