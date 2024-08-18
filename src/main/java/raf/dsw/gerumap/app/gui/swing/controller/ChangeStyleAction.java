package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;
import raf.dsw.gerumap.app.mapRepository.implementation.ElementStyle;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.StyleDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ChangeStyleAction extends AbstractGerumapAction {
    public ChangeStyleAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/style_icon.png"));
        putValue(NAME, "Change Style of Selected Elements");
        putValue(SHORT_DESCRIPTION, "Change Style of Selected Elements");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noSelectedElements())
            return;

        ElementStyle es = new StyleDialog().showDialog();
        if (es == null)
            return;

        for (ElementPainter p : MainFrame.getInstance().getProjectView().getCurrentMapView().getSelectedPainters()) {
            p.getElement().setStyle(es);
        }
    }
}
