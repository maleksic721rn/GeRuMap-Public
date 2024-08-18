package raf.dsw.gerumap.app.mapRepository.commands.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import raf.dsw.gerumap.app.mapRepository.commands.AbstractCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.Element;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;

import java.util.List;

@RequiredArgsConstructor
public class RemoveElementsCommand extends AbstractCommand {
    @NonNull private MindMap map;
    @NonNull private List<Element<?>> elements;
    @Override
    public void redo() {
        for(Element<?> e : elements)
            map.removeChild(e);
    }

    @Override
    public void undo() {
        for(Element<?> e : elements)
            map.addChild(e);
    }
}
