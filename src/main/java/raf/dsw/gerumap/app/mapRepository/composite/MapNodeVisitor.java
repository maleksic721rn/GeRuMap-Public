package raf.dsw.gerumap.app.mapRepository.composite;

import raf.dsw.gerumap.app.mapRepository.implementation.*;

public interface MapNodeVisitor {
    void visit(Term object);
    void visit(Link object);
    void visit(MindMap object);
    void visit(Project object);
    void visit(ProjectExplorer object);
}
