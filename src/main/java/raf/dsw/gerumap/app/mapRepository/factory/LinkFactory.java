package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.Link;

public class LinkFactory extends ElementFactory<Link> {
    @Override
    protected MapNode<?> createNode() {
        return new Link();
    }
}
