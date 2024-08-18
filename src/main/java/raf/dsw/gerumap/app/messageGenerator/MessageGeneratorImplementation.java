package raf.dsw.gerumap.app.messageGenerator;

import raf.dsw.gerumap.app.core.MessageGenerator;
import raf.dsw.gerumap.app.observer.Publisher;
import raf.dsw.gerumap.app.observer.Subscriber;

import java.util.ArrayList;

public class MessageGeneratorImplementation implements MessageGenerator, Publisher<MessageGeneratorImplementation, Message> {
    private ArrayList<Subscriber<MessageGeneratorImplementation, Message>> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber<MessageGeneratorImplementation, Message> object) {
        subscribers.add(object);
    }

    @Override
    public void unsubscribe(Subscriber<MessageGeneratorImplementation, Message> object) {
        subscribers.remove(object);
    }

    @Override
    public void update(Message notification) {
        for (var s : subscribers)
            s.update(this, notification);
    }

    @Override
    public void sendMessage(Message message) {
        update(message);
    }
}
