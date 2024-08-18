package raf.dsw.gerumap.app.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ApplicationFramework {
    protected Gui gui;
    protected MapRepository mapRepository;
    protected MessageGenerator messageGenerator;
    protected Serializer serializer;
    public abstract void run();

    public ApplicationFramework() { }

    public void init(Gui gui, MapRepository mapRepository, MessageGenerator messageGenerator, Serializer serializer) {
        this.gui = gui;
        this.mapRepository = mapRepository;
        this.messageGenerator = messageGenerator;
        this.serializer = serializer;
    }
}