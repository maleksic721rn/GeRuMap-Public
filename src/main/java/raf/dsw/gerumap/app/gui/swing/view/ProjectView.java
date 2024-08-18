package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.observer.Subscriber;
import raf.dsw.gerumap.app.gui.swing.controller.state.StateManager;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeNotification;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

@Getter
public class ProjectView extends JPanel implements Subscriber<Project, MapNodeNotification> {
    private Project currentProject;

    private JLabel nameLabel;
    private JLabel authorLabel;
    private JPanel projectInfoPanel;

    private JTabbedPane tabbedPane;

    private StateManager stateManager = new StateManager();

    public ProjectView() {
        super();

        nameLabel = new JLabel();
        authorLabel = new JLabel();
        projectInfoPanel = GuiUtils.newBoxedJPanel(BoxLayout.Y_AXIS);
        projectInfoPanel.add(nameLabel);
        projectInfoPanel.add(authorLabel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(projectInfoPanel);
        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(e -> {
            AppCore.getInstance().getGui().setUndoEnabled(false);
            AppCore.getInstance().getGui().setRedoEnabled(false);

            if(getCurrentMapView() != null) {
                getCurrentMapView().setStatePainter(null);
                sendMapViewChanged(e);
                getCurrentMapView().repaint();
                getCurrentMapView().getMap().getCommandManager().checkCommandAvailability();
            }
        });
        add(tabbedPane);
    }

    public MapView getMapView(MindMap map) {
        int n = tabbedPane.getTabCount();
        for (int i = 0; i < n; i++) {
            MapView mapView = (MapView) tabbedPane.getComponentAt(i);
            if (mapView.getMap() == map)
                return mapView;
        }
        return null;
    }

    public MapView getCurrentMapView() {
        return (MapView) tabbedPane.getSelectedComponent();
    }

    public void clear() {
        tabbedPane.removeAll();
        if (currentProject != null)
            currentProject.unsubscribe(this);
        currentProject = null;
        updateInformation();
    }

    private int findMismatch() {
        if(currentProject.getChildren().size() == 0 && currentProject.getChildren().size() == tabbedPane.getTabCount())
            return -1;

        int n = Math.max(currentProject.getChildren().size(), tabbedPane.getTabCount());

        for (int i = 0; i < n; i++) {
            if(i >= currentProject.getChildren().size() || i >= tabbedPane.getTabCount())
                return i;

            if (getTabbedPane().getTitleAt(i).compareTo(currentProject.getChildren().get(i).getName()) != 0)
                return i;
        }
        return -1;
    }

    private boolean tabsNeedUpdate() {
        return findMismatch() != -1;
    }

    private void updateInformation() {
        String name = "";
        String author = "";

        if(currentProject != null) {
            name = currentProject.getName();
            author = currentProject.getAuthor();
        }

        nameLabel.setText(name);
        authorLabel.setText(author);
    }

    private void updateNoClear(MapNodeNotification notification) {
        updateInformation();

        if(notification != null) {
            String propString = notification.getProperty().toString();
            int idx = currentProject.getChildIndexByName(propString);
            switch(notification.getType()) {
                case MIND_MAP_ADDED:
                    tabbedPane.insertTab(propString, null, new MapView(currentProject.getChildren().get(idx)), null, idx);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                    break;
                case MIND_MAP_REMOVED:
                    idx = Integer.parseInt(propString);
                    tabbedPane.removeTabAt(idx);
                    break;
                case MIND_MAP_RENAMED:
                    tabbedPane.setTitleAt(idx, propString);
                    break;
                default:
                    break;
            }
        }
    }

    public void updateWithClear() {
        updateInformation();
        if (tabsNeedUpdate()) {
            tabbedPane.removeAll();
            for (MindMap map : currentProject.getChildren()) {
                tabbedPane.add(map.getName(), new MapView(map));
            }
        }
    }

    public void setCurrentProject(Project p) {
        clear();
        currentProject = p;
        p.subscribe(this);
        updateWithClear();
    }

    @Override
    public void update(Project object, MapNodeNotification notification) {
        updateNoClear(notification);
    }

    public void sendMousePress(MouseEvent e) {
        stateManager.getCurrentState().mousePress(e);
    }

    public void sendMouseRelease(MouseEvent e) {
        stateManager.getCurrentState().mouseRelease(e);
    }

    public void sendMouseMove(MouseEvent e) {
        stateManager.getCurrentState().mouseMove(e);
    }

    public void sendMouseWheel(MouseWheelEvent e) {
        stateManager.getCurrentState().mouseWheelMove(e);
    }

    public void sendMapViewChanged(ChangeEvent e) {
        stateManager.getCurrentState().mapViewChanged(e);
    }

    public void startDeleteState() {
        stateManager.setDeleteState();
    }

    public void startNewLinkState() {
        stateManager.setNewLinkState();
    }

    public void startNewTermState() {
        stateManager.setNewTermState();
    }

    public void startSelectLassoState() { stateManager.setSelectLassoState(); }

    public void startSelectSingleState() {
        stateManager.setSelectSingleState();
    }

    public void startMoveState() {
        stateManager.setMoveState();
    }

    public void startNavigationState() {
        stateManager.setNavigationState();
    }

    public void startEditLinkState() { stateManager.setEditLinkState(); }
}
