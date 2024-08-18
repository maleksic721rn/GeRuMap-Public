package raf.dsw.gerumap.app.messageGenerator;

import raf.dsw.gerumap.app.core.Logger;
import raf.dsw.gerumap.app.observer.Subscriber;

public abstract class StringLogger implements Logger, Subscriber<MessageGeneratorImplementation, Message> {
    public StringLogger(MessageGeneratorImplementation messageGenerator) {
        messageGenerator.subscribe(this);
    }
    @Override
    public void log(Message message) {
        String s = "[" +
                message.getSeverity() +
                "][" +
                message.getTimestamp() +
                "] " +
                message.getText();
        output(s);
    }

    protected abstract void output(String message);

    @Override
    public void update(MessageGeneratorImplementation object, Message notification) {
        log(notification);
    }
}
