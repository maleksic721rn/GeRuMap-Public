package raf.dsw.gerumap.app.gui.swing.view;

import javax.swing.*;

public class MapToolbar extends JToolBar {
    public MapToolbar() {
        super(VERTICAL);
        setFloatable(false);

        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getNavigateAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getNewTermAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getNewLinkAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getSelectSingleAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getSelectLassoAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getDeleteElementAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getMoveElementAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getEditLinkAction()));
        addSeparator();
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getChangeStyleAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getRenameTermAction()));
        GuiUtils.removeButtonBorders(add(MainFrame.getInstance().getActionManager().getSetCentralTermAction()));
    }
}
