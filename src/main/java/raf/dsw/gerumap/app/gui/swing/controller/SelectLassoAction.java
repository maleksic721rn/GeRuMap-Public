package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SelectLassoAction extends AbstractGerumapAction {
    public SelectLassoAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/select_multi_icon.png"));
        putValue(NAME, "Select Elements Grouped");
        putValue(SHORT_DESCRIPTION, "Select Elements Grouped");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MainFrame.getInstance().getProjectView().startSelectLassoState();
    }
}
