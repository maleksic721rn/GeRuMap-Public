package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;

public abstract class NodeFactory {
    protected abstract MapNode<?> createNode();

    public <NodeType extends MapNode<?>> NodeType getNode(MapNodeComposite<?, ?> parentNode, String name) {
        NodeType node = (NodeType) createNode();
        MapNodeComposite<NodeType, ?> parent = (MapNodeComposite<NodeType, ?>) parentNode;
        node.setName(name);
        if (parent != null) {
            parent.addChild(node);
        }
        return node;
    }
}
