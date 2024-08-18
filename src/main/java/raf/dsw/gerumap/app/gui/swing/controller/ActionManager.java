package raf.dsw.gerumap.app.gui.swing.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionManager {
    private ExitAction exitAction;
    private NewProjectAction newProjectAction;
    private InfoAction infoAction;
    private NewMindMapAction newMindMapAction;
    private RenameProjectAction renameProjectAction;
    private ChangeProjectAuthorAction changeProjectAuthorAction;
    private RenameMindMapAction renameMindMapAction;
    private DeleteMindMapAction deleteMindMapAction;
    private DeleteProjectAction deleteProjectAction;
    private NewTermAction newTermAction;
    private NewLinkAction newLinkAction;
    private SelectSingleAction selectSingleAction;
    private SelectLassoAction selectLassoAction;
    private MoveElementAction moveElementAction;
    private DeleteElementAction deleteElementAction;
    private ChangeStyleAction changeStyleAction;
    private RenameTermAction renameTermAction;
    private NavigateAction navigateAction;
    private EditLinkAction editLinkAction;
    private OpenAction openAction;
    private SaveAction saveAction;
    private SaveAsAction saveAsAction;
    private SaveAsTemplateAction saveAsTemplateAction;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private ExportAction exportAction;
    private SetCentralTermAction setCentralTermAction;

    public ActionManager() {
        initActions();
    }

    private void initActions() {
        exitAction = new ExitAction();
        newProjectAction = new NewProjectAction();
        newMindMapAction = new NewMindMapAction();
        infoAction = new InfoAction();
        renameProjectAction = new RenameProjectAction();
        changeProjectAuthorAction = new ChangeProjectAuthorAction();
        deleteProjectAction = new DeleteProjectAction();
        renameMindMapAction = new RenameMindMapAction();
        deleteMindMapAction = new DeleteMindMapAction();
        newTermAction = new NewTermAction();
        newLinkAction = new NewLinkAction();
        selectSingleAction = new SelectSingleAction();
        selectLassoAction = new SelectLassoAction();
        moveElementAction = new MoveElementAction();
        deleteElementAction = new DeleteElementAction();
        changeStyleAction = new ChangeStyleAction();
        renameTermAction = new RenameTermAction();
        navigateAction = new NavigateAction();
        editLinkAction = new EditLinkAction();
        openAction = new OpenAction();
        saveAction = new SaveAction();
        saveAsAction = new SaveAsAction();
        saveAsTemplateAction = new SaveAsTemplateAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        exportAction = new ExportAction();
        setCentralTermAction = new SetCentralTermAction();
    }
}