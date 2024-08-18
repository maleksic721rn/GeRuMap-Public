package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ChangeProjectAuthorAction extends AbstractGerumapAction {

    public ChangeProjectAuthorAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/author_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/author_icon_l.png"));
        putValue(NAME, "Change Project Author");
        putValue(SHORT_DESCRIPTION, "Change Project Author");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noProjectSelected())
            return;

        String newAuthor = GuiUtils.getUserInputWithDialog("Change author", "Enter project author", MainFrame.getInstance().getProjectView().getCurrentProject().getAuthor());
        if(newAuthor == null || newAuthor.compareTo(MainFrame.getInstance().getProjectView().getCurrentProject().getAuthor()) == 0)
            return;

        MainFrame.getInstance().getProjectView().getCurrentProject().setAuthor(newAuthor);
    }
}
