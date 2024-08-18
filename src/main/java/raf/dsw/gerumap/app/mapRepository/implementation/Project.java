package raf.dsw.gerumap.app.mapRepository.implementation;

import flexjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import raf.dsw.gerumap.app.observer.Publisher;
import raf.dsw.gerumap.app.observer.Subscriber;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotificationType;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeVisitor;

import java.io.File;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class Project extends MapNodeComposite<MindMap, ProjectExplorer> implements Publisher<Project, MapNodeNotification> {
    @JSON(include = false)
    private File directory;
    @NonNull
    private String author;

    @Override
    public void setName(String name) {
        super.setName(name);
        update(new MapNodeNotification(getName(), MapNodeNotificationType.PROJECT_RENAMED, this));
    }

    public void setAuthor(String author) {
        this.author = author;
        update(new MapNodeNotification(getAuthor(), MapNodeNotificationType.PROJECT_AUTHOR_CHANGE, this));
    }

    public void setDirectory(File directory) {
        this.directory = directory;
        update(null);
    }

    @Override
    public void addChild(MindMap child) {
        super.addChild(child);
        update(new MapNodeNotification(child.getName(), MapNodeNotificationType.MIND_MAP_ADDED, this));
    }

    @Override
    public void removeChild(MindMap child) {
        int idx = getChildIndexByName(child.getName());
        super.removeChild(child);
        update(new MapNodeNotification(idx, MapNodeNotificationType.MIND_MAP_REMOVED, this));
    }

    private transient ArrayList<Subscriber<Project, MapNodeNotification>> subscribers = new ArrayList<>();
    @Override
    public void subscribe(Subscriber<Project, MapNodeNotification> object) {
        subscribers.add(object);
    }

    @Override
    public void unsubscribe(Subscriber<Project, MapNodeNotification> object) {
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
}
