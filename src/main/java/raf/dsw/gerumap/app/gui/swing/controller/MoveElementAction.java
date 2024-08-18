package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MoveElementAction extends AbstractGerumapAction {
    public MoveElementAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/move_elem_icon.png"));
        putValue(NAME, "Move Elements");
        putValue(SHORT_DESCRIPTION, "Move Elements");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MainFrame.getInstance().getProjectView().startMoveState();
    }
}

