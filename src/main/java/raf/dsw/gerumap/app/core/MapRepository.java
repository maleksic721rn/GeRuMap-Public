package raf.dsw.gerumap.app.core;

import raf.dsw.gerumap.app.mapRepository.commands.AbstractCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

public interface MapRepository {
    ProjectExplorer getProjectExplorer();
    void undoCommand(MindMap map);
    void redoCommand(MindMap map);
    void newCommand(MindMap map, AbstractCommand cmd);
    void newCommand(MindMap map, AbstractCommand cmd, boolean perform);
}
