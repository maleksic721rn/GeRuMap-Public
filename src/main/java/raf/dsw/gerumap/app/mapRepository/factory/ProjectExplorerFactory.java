package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

public class ProjectExplorerFactory extends NodeFactory{
    @Override
    protected MapNode<?> createNode() {
        return new ProjectExplorer();
    }
}
