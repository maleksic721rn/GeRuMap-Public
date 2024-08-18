package raf.dsw.gerumap.app.mapRepository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import raf.dsw.gerumap.app.observer.Publisher;
import raf.dsw.gerumap.app.observer.Subscriber;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotificationType;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeVisitor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class ProjectExplorer extends MapNodeComposite<Project, ProjectExplorer> implements Publisher<ProjectExplorer, MapNodeNotification> {
    @Override
    public ProjectExplorer getParent() {
        throw new RuntimeException();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        update(null);
    }

    @Override
    public void addChild(Project child) {
        super.addChild(child);
        update(new MapNodeNotification(child.getName(), MapNodeNotificationType.PROJECT_ADDED, this));
    }

    @Override
    public void removeChild(Project child) {
        int idx = getChildIndexByName(child.getName());
        super.removeChild(child);
        update(new MapNodeNotification(idx, MapNodeNotificationType.PROJECT_REMOVED, this));
    }

    @Override
    public <PType extends MapNodeComposite<?, ?>> void setParent(PType parent) {
        throw new RuntimeException();
    }

    private transient ArrayList<Subscriber<ProjectExplorer, MapNodeNotification>> subscribers = new ArrayList<>();
    @Override
    public void subscribe(Subscriber<ProjectExplorer, MapNodeNotification> object) {
        subscribers.add(object);
    }

    @Override
    public void unsubscribe(Subscriber<ProjectExplorer, MapNodeNotification> object) {
        subscribers.remove(object);
    }

    @Override
    public void update(MapNodeNotification notification) {
        for (var s : subscribers) {
            s.update(this, notification);
        }
    }

    @Override
    public void accept(MapNodeVisitor visitor) {
        visitor.visit(this);
    }
}
