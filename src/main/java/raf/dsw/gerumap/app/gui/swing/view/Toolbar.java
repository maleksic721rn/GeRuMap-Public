package raf.dsw.gerumap.app.gui.swing.view;

import javax.swing.*;

public class Toolbar extends JToolBar {
    public Toolbar() {
        super(HORIZONTAL);
        setFloatable(false);

        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getExitAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getOpenAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getSaveAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getSaveAsAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getNewProjectAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getNewMindMapAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getRenameProjectAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getChangeProjectAuthorAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getDeleteProjectAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getRenameMindMapAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getDeleteMindMapAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getSaveAsTemplateAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getExportAction()));
        addSeparator();
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getUndoAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getRedoAction()));
        addSeparator();
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getInfoAction()));
    }
}