package raf.dsw.gerumap.app.mapRepository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import raf.dsw.gerumap.app.mapRepository.commands.CommandManager;
import raf.dsw.gerumap.app.observer.Publisher;
import raf.dsw.gerumap.app.observer.Subscriber;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotificationType;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeVisitor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class MindMap extends MapNodeComposite<Element<?>, Project> implements Publisher<MindMap, MapNodeNotification> {
    private boolean template;
    private CommandManager commandManager = new CommandManager();

    @Override
    public void setName(String name) {
        super.setName(name);
        update(new MapNodeNotification(getName(), MapNodeNotificationType.MIND_MAP_RENAMED, this));
    }

    @Override
    public void addChild(Element<?> child) {
        super.addChild(child);
        update(new MapNodeNotification(child, MapNodeNotificationType.ELEMENT_ADDED, this));
    }

    @Override
    public void removeChild(Element<?> child) {
        super.removeChild(child);
        update(new MapNodeNotification(child, MapNodeNotificationType.ELEMENT_REMOVED, this));
    }

    public void setTemplate(boolean template) {
        this.template = template;
        update(new MapNodeNotification(template,
                MapNodeNotificationType.MIND_MAP_TEMPLATE_STATUS_CHANGED,
                this));
    }

    private transient ArrayList<Subscriber<MindMap, MapNodeNotification>> subscribers = new ArrayList<>();
    @Override
    public void subscribe(Subscriber<MindMap, MapNodeNotification> object) {
        subscribers.add(object);
    }

    @Override
    public void unsubscribe(Subscriber<MindMap, MapNodeNotification> object) {
        subscribers.remove(object);
    }

    @Override
    public void update(MapNodeNotification notification) {
        if(getParent() != null)
            getParent().update(notification);
    
        for (var s : subscribers) {
            s.update(this, notification);
        }
    }

    @Override
    public void accept(MapNodeVisitor visitor) {
        visitor.visit(this);
    }

    public void prepareForSerialization() {
        for (int i = 0; i < this.getChildren().size(); i++)
            if (this.getChildren().get(i) instanceof Term)
                ((Term) this.getChildren().get(i)).setId(i);
    }

    public void fixAfterDeserialization() {
        for (Element<?> e : getChildren()) {
            e.setParent(this);
            if (e instanceof Link l) {
                l.setFrom((Term) getChildren().get(l.getFrom().getId()));
                l.setTo((Term) getChildren().get(l.getTo().getId()));
            }
        }
    }

    public Term getCentralTerm() {
        for (Element<?> t : children)
            if (t instanceof Term && ((Term) t).isCentral())
                return (Term) t;
        return null;
    }
}
