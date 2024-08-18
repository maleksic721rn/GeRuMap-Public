package raf.dsw.gerumap.app.mapRepository.composite;

import flexjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public abstract class MapNodeComposite<ChildType extends MapNode<?>, ParentType extends MapNodeComposite<?, ?>> extends MapNode<ParentType> {
    @Getter
    @Setter
    @JSON(include = true)
    protected ArrayList<ChildType> children = new ArrayList<>();

    public void addChild(ChildType child) {
        if(!children.contains(child)) {
            child.setParent(this);
            children.add(child);
        }
    }

    public void removeChild(ChildType child) {
        if(children.contains(child)) {
            child.setParent(null);
            children.remove(child);
        }
    }

    public ChildType getChildByName(String name) {
        for (ChildType c : getChildren()) {
            if(name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public int getChildIndexByName(String name) {
        for (int i = 0; i < getChildren().size(); i++) {
            if(name.equals(getChildren().get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
