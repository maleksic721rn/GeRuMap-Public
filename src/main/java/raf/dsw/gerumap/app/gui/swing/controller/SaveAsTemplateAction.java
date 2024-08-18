package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public class SaveAsTemplateAction extends AbstractGerumapAction {
    public SaveAsTemplateAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/save_map_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/save_map_icon_l.png"));
        putValue(NAME, "Save Mind Map As Template");
        putValue(SHORT_DESCRIPTION, "Save Mind Map As Template");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MindMap m = MainFrame.getInstance().getProjectView().getCurrentMapView().getMap();
        File f = GuiUtils.getJsonFileWithDialog(false, Objects.requireNonNull(GuiUtils.class.getResource("/templates")).getPath());
        if (f == null)
            return;
        m.setTemplate(true);
        m.prepareForSerialization();
        AppCore.getInstance().getSerializer().serialize(f, m);
    }
}
