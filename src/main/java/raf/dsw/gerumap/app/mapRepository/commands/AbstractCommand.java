package raf.dsw.gerumap.app.mapRepository.commands;

public abstract class AbstractCommand {
    public abstract void redo();
    public abstract void undo();
}