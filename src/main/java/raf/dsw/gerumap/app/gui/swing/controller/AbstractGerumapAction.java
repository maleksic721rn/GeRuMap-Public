package raf.dsw.gerumap.app.gui.swing.controller;

import javax.swing.*;

public abstract class AbstractGerumapAction extends AbstractAction {
    public Icon loadActionIcon(String fromFile) {
        return ControllerUtils.loadIcon(getClass(), fromFile);
    }
}