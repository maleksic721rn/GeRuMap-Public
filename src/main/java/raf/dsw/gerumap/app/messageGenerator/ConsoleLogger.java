package raf.dsw.gerumap.app.messageGenerator;

public class ConsoleLogger extends StringLogger {
    public ConsoleLogger(MessageGeneratorImplementation messageGenerator) {
        super(messageGenerator);
    }

    @Override
    protected void output(String message) {
        System.out.println(message);
    }
}
