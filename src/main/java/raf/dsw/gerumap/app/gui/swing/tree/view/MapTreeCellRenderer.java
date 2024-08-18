package raf.dsw.gerumap.app.gui.swing.tree.view;

import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeVisitor;
import raf.dsw.gerumap.app.mapRepository.implementation.*;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class MapTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        MapNode<?> nodeModel = ((MapTreeItem) value).getModel();
        final String[] gIconPath = {null};
        nodeModel.accept(new MapNodeVisitor() {
            @Override
            public void visit(Term object) {

            }

            @Override
            public void visit(Link object) {

            }

            @Override
            public void visit(MindMap object) {
                gIconPath[0] = "/images/map_icon_s.png";
            }

            @Override
            public void visit(Project object) {
                gIconPath[0] = "/images/explorer_icon_s.png";
            }

            @Override
            public void visit(ProjectExplorer object) {
                gIconPath[0] = "/images/explorer_icon_s.png";
            }
        });
        if (gIconPath[0] != null)
            setIcon(ControllerUtils.loadIcon(getClass(), gIconPath[0]));
        return this;
    }
}
