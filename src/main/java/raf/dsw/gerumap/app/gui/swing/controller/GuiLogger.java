package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.Logger;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.messageGenerator.Message;
import raf.dsw.gerumap.app.messageGenerator.MessageGeneratorImplementation;
import raf.dsw.gerumap.app.messageGenerator.MessageSeverity;
import raf.dsw.gerumap.app.observer.Subscriber;

import javax.swing.*;

public class GuiLogger implements Logger, Subscriber<MessageGeneratorImplementation, Message> {
    public GuiLogger(MessageGeneratorImplementation messageGeneratorImplementation) {
        messageGeneratorImplementation.subscribe(this);
    }

    private int messageSeverity(MessageSeverity messageSeverity) {
        switch (messageSeverity) {

            case INFO -> {
                return JOptionPane.INFORMATION_MESSAGE;
            }
            case WARNING -> {
                return JOptionPane.WARNING_MESSAGE;
            }
            case ERROR -> {
                return JOptionPane.ERROR_MESSAGE;
            }
        }
        return JOptionPane.PLAIN_MESSAGE;
    }

    @Override
    public void log(Message message) {
        JOptionPane.showMessageDialog(
                MainFrame.getInstance(),
                message.getText(),
                message.getType().toString(),
                messageSeverity(message.getSeverity())
        );
    }

    @Override
    public void update(MessageGeneratorImplementation object, Message notification) {
        log(notification);
    }
}
