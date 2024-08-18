package raf.dsw.gerumap.app.mapRepository.commands.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import raf.dsw.gerumap.app.mapRepository.commands.AbstractCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.Element;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;

@RequiredArgsConstructor
public class AddElementCommand extends AbstractCommand {
    @NonNull private MindMap map;
    @NonNull private Element<?> element;

    @Override
    public void redo() {
        map.addChild(element);
    }

    @Override
    public void undo() {
        map.removeChild(element);
    }
}