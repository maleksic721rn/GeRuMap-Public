package raf.dsw.gerumap.app.mapRepository;

import raf.dsw.gerumap.app.core.MapRepository;
import raf.dsw.gerumap.app.mapRepository.commands.AbstractCommand;
import raf.dsw.gerumap.app.mapRepository.factory.ProjectExplorerFactory;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

public class MapRepositoryImpl implements MapRepository {
    private ProjectExplorer projectExplorer;
    @Override
    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }

    @Override
    public void undoCommand(MindMap map) {
        map.getCommandManager().undoCommand();
    }

    @Override
    public void redoCommand(MindMap map) {
        map.getCommandManager().redoCommand();
    }

    @Override
    public void newCommand(MindMap map, AbstractCommand cmd) {
        newCommand(map, cmd, true);
    }

    @Override
    public void newCommand(MindMap map, AbstractCommand cmd, boolean perform) {
        map.getCommandManager().newCommand(cmd, perform);
    }

    public MapRepositoryImpl() {
        init();
    }
    private void init() {
        projectExplorer = new ProjectExplorerFactory().getNode(null, "Project Explorer");
    }
}
