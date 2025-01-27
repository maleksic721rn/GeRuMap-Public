package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RedoAction extends AbstractGerumapAction {
    public RedoAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/redo_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/redo_icon_l.png"));
        putValue(NAME, "Redo");
        putValue(SHORT_DESCRIPTION, "Redo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        AppCore.getInstance().getMapRepository().redoCommand(MainFrame.getInstance().getProjectView().getCurrentMapView().getMap());
    }
}