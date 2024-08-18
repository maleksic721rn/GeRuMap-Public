package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditLinkAction extends AbstractGerumapAction {
    public EditLinkAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/edit_link_icon.png"));
        putValue(NAME, "Change Link Control Points");
        putValue(SHORT_DESCRIPTION, "Change Link Control Points");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MainFrame.getInstance().getProjectView().startEditLinkState();
    }
}
