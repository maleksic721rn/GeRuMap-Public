package raf.dsw.gerumap.app.mapRepository.commands.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import raf.dsw.gerumap.app.mapRepository.commands.AbstractCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;

import java.util.List;

@RequiredArgsConstructor
public class MoveTermsCommand extends AbstractCommand {
    @NonNull private List<Term> terms;
    @NonNull private double deltaX;
    @NonNull private double deltaY;
    @Override
    public void redo() {
        for (Term t : terms) {
            t.setPosition((int) Math.round(t.getPosition().getX() + deltaX), (int) Math.round(t.getPosition().getY() + deltaY));
        }
    }

    @Override
    public void undo() {
        for (Term t : terms) {
            t.setPosition((int) Math.round(t.getPosition().getX() - deltaX), (int) Math.round(t.getPosition().getY() - deltaY));
        }
    }
}
