package raf.dsw.gerumap.app.mapRepository.composite;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public abstract class MapNode<ParentType extends MapNodeComposite<?, ?>> implements Serializable {
    @Getter
    @Setter
    protected String name;
    
    @Getter
    protected transient ParentType parent;

    public <PType extends MapNodeComposite<?, ?>> void setParent(PType parent) {
        this.parent = (ParentType) parent;
    }

    public abstract void accept(MapNodeVisitor visitor);
}