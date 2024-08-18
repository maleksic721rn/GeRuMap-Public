package raf.dsw.gerumap.app;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.SwingGui;
import raf.dsw.gerumap.app.mapRepository.MapRepositoryImpl;
import raf.dsw.gerumap.app.messageGenerator.ConsoleLogger;
import raf.dsw.gerumap.app.messageGenerator.FileLogger;
import raf.dsw.gerumap.app.messageGenerator.MessageGeneratorImplementation;
import raf.dsw.gerumap.app.serializer.FlexjsonSerializer;

public class AppCore extends ApplicationFramework {
    private static AppCore instance = null;
    private AppCore() { }

    public static AppCore getInstance() {
        if(instance == null) {
            instance = new AppCore();
        }
        return instance;
    }

    public void run() {
        gui.start();
    }

    private ConsoleLogger consoleLogger;
    private FileLogger fileLogger;

    public static void main(String[] args) {
        MessageGeneratorImplementation messageGenerator = new MessageGeneratorImplementation();
        AppCore.getInstance().consoleLogger = new ConsoleLogger(messageGenerator);
        AppCore.getInstance().fileLogger = new FileLogger(messageGenerator);
        AppCore.getInstance().init(new SwingGui(), new MapRepositoryImpl(), messageGenerator, new FlexjsonSerializer());
        AppCore.getInstance().run();
    }
}