package raf.dsw.gerumap.app.gui.swing.tree.model;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
@Setter
public class MapTreeItem extends DefaultMutableTreeNode {
    private MapNode<?> model;

    public MapTreeItem(MapNode<?> model) {
        setModel(model);
    }

    @Override
    public String toString() {
        return getModel().getName();
    }

    public void setName(String name) {
        getModel().setName(name);
    }
}
