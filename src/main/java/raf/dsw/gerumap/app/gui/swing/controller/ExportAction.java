package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MapView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class ExportAction extends AbstractGerumapAction {

    public ExportAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadActionIcon("/images/export_icon_s.png"));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/export_icon_l.png"));
        putValue(NAME, "Export Mind Map as Image");
        putValue(SHORT_DESCRIPTION, "Export Mind Map as Image");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noValidMindMap())
            return;

        MapView currMapView = MainFrame.getInstance().getProjectView().getCurrentMapView();
        File f = GuiUtils.getFileWithDialog(false, "PNG images", "png");
        if(f == null)
            return;

        currMapView.exportImage(f);
    }
}
