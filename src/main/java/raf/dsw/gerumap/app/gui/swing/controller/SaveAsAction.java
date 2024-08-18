package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAsAction extends AbstractGerumapAction {
    public SaveAsAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/save_as_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/save_as_icon_l.png"));
        putValue(NAME, "Save Project As");
        putValue(SHORT_DESCRIPTION, "Save Project As");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noProjectSelected())
            return;

        Project p = MainFrame.getInstance().getProjectView().getCurrentProject();
        File f = GuiUtils.getJsonFileWithDialog(false);
        if (f == null)
            return;
        p.setDirectory(f);
        MainFrame.getInstance().getActionManager().getSaveAction().actionPerformed(e);
    }
}
