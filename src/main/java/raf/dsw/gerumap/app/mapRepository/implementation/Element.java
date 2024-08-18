package raf.dsw.gerumap.app.mapRepository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import raf.dsw.gerumap.app.observer.Publisher;
import raf.dsw.gerumap.app.observer.Subscriber;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeLeaf;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotificationType;

import java.util.ArrayList;

@NoArgsConstructor
public abstract class Element<Type extends Element<Type>> extends MapNodeLeaf<MindMap> implements Publisher<Type, MapNodeNotification> {

    @Getter private ElementStyle style;

    public void setStyle(ElementStyle style) {
        this.style = style;
        update(new MapNodeNotification(getName(), MapNodeNotificationType.ELEMENT_STYLE_CHANGED, this));
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        update(new MapNodeNotification(getName(), MapNodeNotificationType.ELEMENT_RENAMED, this));
    }

    private transient ArrayList<Subscriber<Type, MapNodeNotification>> subscribers = new ArrayList<>();
    @Override
    public void subscribe(Subscriber<Type, MapNodeNotification> object) {
        subscribers.add(object);
    }

    @Override
    public void unsubscribe(Subscriber<Type, MapNodeNotification> object) {
        subscribers.remove(object);
    }

    @Override
    public void update(MapNodeNotification notification) {
        if(getParent() != null)
            getParent().update(notification);
    
        for (var s : subscribers) {
            s.update((Type) this, notification);
        }
    }
}