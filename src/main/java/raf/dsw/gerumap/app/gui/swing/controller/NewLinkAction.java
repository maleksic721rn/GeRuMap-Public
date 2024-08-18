package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewLinkAction extends AbstractGerumapAction {
    public NewLinkAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/new_link_icon.png"));
        putValue(NAME, "New Link");
        putValue(SHORT_DESCRIPTION, "New Link");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MainFrame.getInstance().getProjectView().startNewLinkState();
    }
}
