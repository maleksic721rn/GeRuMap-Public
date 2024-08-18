package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewTermAction extends AbstractGerumapAction {
    public NewTermAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/new_elem_icon.png"));
        putValue(NAME, "New Term");
        putValue(SHORT_DESCRIPTION, "New Term");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MainFrame.getInstance().getProjectView().startNewTermState();
    }
}
