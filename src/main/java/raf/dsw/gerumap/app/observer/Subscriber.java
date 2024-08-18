package raf.dsw.gerumap.app.observer;

public interface Subscriber<Type extends Publisher<Type, NotificationType>, NotificationType> {
    void update(Type object, NotificationType notification);
}
