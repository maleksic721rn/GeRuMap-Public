package raf.dsw.gerumap.app.mapRepository.commands;

import raf.dsw.gerumap.app.AppCore;

import java.util.Stack;

public class CommandManager {
    private Stack<AbstractCommand> doneCommands = new Stack<>();
    private Stack<AbstractCommand> undoneCommands = new Stack<>();
    public void newCommand(AbstractCommand command, boolean performAction) {
        if(performAction) {
            undoneCommands.clear();
            undoneCommands.push(command);
            redoCommand();
        } else {
            doneCommands.push(command);
            checkCommandAvailability();
        }
    }

    public void redoCommand() {
        doneCommands.push(undoneCommands.pop()).redo();
        checkCommandAvailability();
    }

    public void undoCommand() {
        undoneCommands.push(doneCommands.pop()).undo();
        checkCommandAvailability();
    }

    public void checkCommandAvailability() {
        AppCore.getInstance().getGui().setUndoEnabled(false);
        AppCore.getInstance().getGui().setRedoEnabled(false);

        if(doneCommands.size() > 0)
            AppCore.getInstance().getGui().setUndoEnabled(true);
        if(undoneCommands.size() > 0)
            AppCore.getInstance().getGui().setRedoEnabled(true);
    }
}
