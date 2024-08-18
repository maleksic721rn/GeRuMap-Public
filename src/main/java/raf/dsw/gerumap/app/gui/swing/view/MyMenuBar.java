package raf.dsw.gerumap.app.gui.swing.view;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(MainFrame.getInstance().getActionManager().getExitAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewProjectAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewMindMapAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getRenameProjectAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getChangeProjectAuthorAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getDeleteProjectAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getRenameMindMapAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getDeleteMindMapAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getOpenAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAsAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAsTemplateAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getExportAction());

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(MainFrame.getInstance().getActionManager().getUndoAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getRedoAction());

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.add(MainFrame.getInstance().getActionManager().getInfoAction());

        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
    }
}