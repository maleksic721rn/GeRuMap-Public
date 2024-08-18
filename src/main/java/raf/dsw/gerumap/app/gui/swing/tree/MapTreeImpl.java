package raf.dsw.gerumap.app.gui.swing.tree;

import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.observer.Subscriber;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.tree.view.MapTreeView;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.*;

public class MapTreeImpl implements MapTree, Subscriber<ProjectExplorer, MapNodeNotification> {

    private MapTreeView treeView;
    private DefaultTreeModel treeModel;
    @Override
    public MapTreeView generateTree(ProjectExplorer projectExplorer) {
        MapTreeItem root = new MapTreeItem(projectExplorer);
        projectExplorer.subscribe(this);
        treeModel = new DefaultTreeModel(root);
        treeView = new MapTreeView(treeModel);
        return treeView;
    }

    @Override
    public MapTreeItem getSelectedNode() {
        return (MapTreeItem) treeView.getLastSelectedPathComponent();
    }

    @Override
    public MapTreeItem getRootNode() {
        return (MapTreeItem) treeModel.getRoot();
    }

    private <ChildType extends MapNode<ParentType>, ParentType extends MapNodeComposite<ChildType, ?>> void addChild(MapTreeItem parent, ChildType child) {
        parent.add(new MapTreeItem(child));
        LinkedList<TreeNode> l = new LinkedList<>();
        TreeNode n = parent.getLastChild();
        while (n != null) {
            l.addFirst(n);
            n = n.getParent();
        }
        TreePath p = new TreePath(l.toArray());
        treeView.scrollPathToVisible(p);
        treeView.setSelectionPath(p);
    }

    @Override
    public void update(ProjectExplorer object, MapNodeNotification notification) {
        if (notification != null)
            switch (notification.getType()) {
                case PROJECT_ADDED ->
                    addChild(getRootNode(), AppCore.getInstance().getMapRepository().getProjectExplorer()
                            .getChildByName((String) notification.getProperty()));
                case PROJECT_REMOVED -> {
                    MapTreeItem m = (MapTreeItem) getRootNode().getChildAt((Integer) notification.getProperty());
                    m.removeFromParent();
                }
                case MIND_MAP_ADDED ->
                    addChild(getProjectTreeItem(notification.getSource()), ((Project) notification.getSource()).getChildByName((String) notification.getProperty()));
                case MIND_MAP_REMOVED -> {
                    MapTreeItem m = (MapTreeItem) getProjectTreeItem(notification.getSource()).getChildAt((Integer) notification.getProperty());
                    m.removeFromParent();
                }
            }
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    private MapTreeItem getProjectTreeItem(MapNode<?> project) {
        for (Iterator<TreeNode> i = getRootNode().children().asIterator(); i.hasNext();) {
            TreeNode t = i.next();
            if (!(t instanceof MapTreeItem))
                throw new RuntimeException();
            MapTreeItem m = (MapTreeItem) t;
            if (m.getModel() == project)
                return m;
        }
        return null;
    }
}
