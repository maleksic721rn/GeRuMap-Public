package raf.dsw.gerumap.app.gui.swing.tree.controller;

import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.GuiUtils;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class MapTreeSelectionListener implements TreeSelectionListener {

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        MapTreeItem selected = (MapTreeItem) e.getPath().getLastPathComponent();
        String nodeName = selected.getModel().getName();
        if (selected.getModel() instanceof Project)
            MainFrame.getInstance().getProjectView().setCurrentProject((Project) selected.getModel());
        else if(selected.getModel() instanceof MindMap) {
            if(selected.getModel().getParent() != MainFrame.getInstance().getProjectView().getCurrentProject())
                MainFrame.getInstance().getProjectView().setCurrentProject((Project) selected.getModel().getParent());
            MainFrame.getInstance().getProjectView().getTabbedPane().setSelectedIndex(GuiUtils.tabByName(MainFrame.getInstance().getProjectView().getTabbedPane(), nodeName));
        }
    }
}
