package raf.dsw.gerumap.app.gui.swing.controller.state;

import lombok.Getter;
import raf.dsw.gerumap.app.gui.swing.controller.state.concrete.*;

@Getter
public class StateManager {
    private State currentState;
    private NavigationState navigationState = new NavigationState();
    private DeleteState deleteState = new DeleteState();
    private NewLinkState newLinkState = new NewLinkState();
    private NewTermState newTermState = new NewTermState();
    private SelectLassoState selectLassoState = new SelectLassoState();
    private SelectSingleState selectSingleState = new SelectSingleState();
    private EditLinkState editLinkState = new EditLinkState();
    private MoveState moveState = new MoveState();

    public StateManager() { setCurrentState(navigationState); }

    public void setCurrentState(State state) {
        if(currentState != null) { currentState.stop(); }
        currentState = state;
        currentState.start();
    }

    public void setDeleteState() {
        setCurrentState(deleteState);
    }

    public void setNewLinkState() {
        setCurrentState(newLinkState);
    }

    public void setNewTermState() {
        setCurrentState(newTermState);
    }

    public void setSelectLassoState() { setCurrentState(selectLassoState); }

    public void setSelectSingleState() {
        setCurrentState(selectSingleState);
    }

    public void setMoveState() {
        setCurrentState(moveState);
    }

    public void setNavigationState() {
        setCurrentState(navigationState);
    }

    public void setEditLinkState() { setCurrentState(editLinkState); }
}
