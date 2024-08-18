package raf.dsw.gerumap.app.gui.swing;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.core.Gui;
import raf.dsw.gerumap.app.gui.swing.controller.GuiLogger;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.messageGenerator.MessageGeneratorImplementation;

public class SwingGui implements Gui {

    private GuiLogger guiLogger;
    @Override
    public void start() {
        guiLogger = new GuiLogger((MessageGeneratorImplementation) AppCore.getInstance().getMessageGenerator());
        MainFrame.getInstance().setVisible(true);

        setRedoEnabled(false);
        setUndoEnabled(false);
    }

    @Override
    public void setUndoEnabled(boolean enabled) {
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(enabled);
    }

    @Override
    public void setRedoEnabled(boolean enabled) {
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(enabled);
    }
}
