package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

public class ProjectFactory extends NodeFactory {
    @Override
    protected MapNode<?> createNode() {
        return new Project();
    }
}
