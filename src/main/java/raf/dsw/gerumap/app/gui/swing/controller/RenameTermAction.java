package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.ElementPainter;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Element;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RenameTermAction   extends AbstractGerumapAction {
    public RenameTermAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        putValue(LARGE_ICON_KEY, loadActionIcon("/images/rename_elem_icon.png"));
        putValue(NAME, "Change Name of Selected Terms");
        putValue(SHORT_DESCRIPTION, "Change Name of Selected Terms");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ControllerUtils.ErrorHandling.noSelectedElements())
            return;

        for (ElementPainter p : MainFrame.getInstance().getProjectView().getCurrentMapView().getSelectedPainters()) {
            Element<?> el = p.getElement();
            if (!(el instanceof Term))
                continue;
            String n = GuiUtils.getUserInputWithDialog("Rename Term",
                    "New name:", el.getName());
            if (n == null)
                continue;
            el.setName(n);
            ControllerUtils.resizeTermToFitName((Term) el);
        }
    }
}
