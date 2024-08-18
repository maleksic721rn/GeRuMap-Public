package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteMindMapAction extends AbstractGerumapAction {
    public DeleteMindMapAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/delete_map_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/delete_map_icon_l.png"));
        putValue(NAME, "Delete Mind Map");
        putValue(SHORT_DESCRIPTION, "Delete Mind Map");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MindMap currMap = MainFrame.getInstance().getProjectView().getCurrentMapView().getMap();
        currMap.getParent().removeChild(currMap);
    }
}
