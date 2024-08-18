package raf.dsw.gerumap.app.observer;

public interface Publisher<Type extends Publisher<Type, NotificationType>, NotificationType> {
    void subscribe(Subscriber<Type, NotificationType> object);
    void unsubscribe(Subscriber<Type, NotificationType> object);
    void update(NotificationType notification);
}
