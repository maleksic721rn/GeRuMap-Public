package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import raf.dsw.gerumap.app.AppCore;
import raf.dsw.gerumap.app.gui.swing.controller.ActionManager;
import raf.dsw.gerumap.app.gui.swing.tree.MapTree;
import raf.dsw.gerumap.app.gui.swing.tree.MapTreeImpl;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static MainFrame instance = null;

    @Getter
    private ActionManager actionManager;
    @Getter
    private MapTree mapTree;

    private JToolBar toolBar;
    private  JMenuBar menuBar;
    private JSplitPane splitPane;
    private JScrollPane projectExplorerPane;
    private JPanel rightPane;
    @Getter
    private ProjectView projectView;
    private JToolBar mapToolBar;

    private MainFrame() {
        super();
    }

    private void init() {
        actionManager = new ActionManager();
        mapTree = new MapTreeImpl();
        initGUI();
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.init();
        }
        return instance;
    }

    private void initGUI() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("GeRuMap");

        menuBar = new MyMenuBar();
        setJMenuBar(menuBar);

        toolBar = new Toolbar();

        add(toolBar, BorderLayout.NORTH);

        projectExplorerPane = new JScrollPane(mapTree.generateTree(AppCore.getInstance().getMapRepository().getProjectExplorer()));
        projectExplorerPane.setBackground(Color.white);
        projectExplorerPane.setPreferredSize(new Dimension(180, projectExplorerPane.getHeight()));
        projectExplorerPane.setBorder(BorderFactory.createEmptyBorder());

        projectView = new ProjectView();

        mapToolBar = new MapToolbar();

        rightPane = GuiUtils.newBoxedJPanel(BoxLayout.X_AXIS);
        rightPane.add(projectView);
        rightPane.add(mapToolBar);
        rightPane.setBorder(BorderFactory.createEmptyBorder());


        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectExplorerPane, rightPane);
        splitPane.setOneTouchExpandable(false);
        splitPane.setEnabled(false);

        getContentPane().add(splitPane);
    }
}