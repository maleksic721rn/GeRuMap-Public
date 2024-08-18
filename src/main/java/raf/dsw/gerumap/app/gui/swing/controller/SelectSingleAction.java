package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SelectSingleAction extends AbstractGerumapAction {
    public SelectSingleAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/select_single_icon.png"));
        putValue(NAME, "Select Elements Individually");
        putValue(SHORT_DESCRIPTION, "Select Elements Individually");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MainFrame.getInstance().getProjectView().startSelectSingleState();
    }
}
