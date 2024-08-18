package raf.dsw.gerumap.app.gui.swing.tree.view;

import raf.dsw.gerumap.app.gui.swing.tree.controller.MapTreeSelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class MapTreeView extends JTree {
    public MapTreeView(DefaultTreeModel model) {
        super();
        setModel(model);
        addTreeSelectionListener(new MapTreeSelectionListener());
        setCellRenderer(new MapTreeCellRenderer());
    }
}
