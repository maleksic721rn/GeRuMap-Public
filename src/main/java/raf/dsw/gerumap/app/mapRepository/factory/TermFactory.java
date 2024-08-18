package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;

public class TermFactory extends ElementFactory<Term> {

    @Override
    protected MapNode<?> createNode() {
        return new Term();
    }
}
