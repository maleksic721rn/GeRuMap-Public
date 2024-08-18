package raf.dsw.gerumap.app.gui.swing.view;

import raf.dsw.gerumap.app.gui.swing.controller.ControllerUtils;

import javax.swing.*;
import java.awt.*;

public class InfoDialog extends JDialog {

    private JSplitPane splitPane;

    public InfoDialog() {
        super(MainFrame.getInstance(), "Info", true);
        initGUI();
    }

    private void initGUI() {
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(320, 165));
        setResizable(false);
        JPanel leftPanel = GuiUtils.newBoxedJPanel(BoxLayout.Y_AXIS, 8);
        JPanel rightPanel = GuiUtils.newBoxedJPanel(BoxLayout.Y_AXIS, 8);

        leftPanel.add(GuiUtils.newCenteredLabel(ControllerUtils.loadIcon(getClass(), "/images/info_image_2.png")));
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(GuiUtils.newCenteredLabel("Marko Aleksić RN7/2021"));

        rightPanel.add(GuiUtils.newCenteredLabel(ControllerUtils.loadIcon(getClass(), "/images/info_image_1.png")));
        rightPanel.add(Box.createVerticalStrut(12));
        rightPanel.add(GuiUtils.newCenteredLabel("Lazar Kukolj RN26/2021"));

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(0);

        add(splitPane);
    }
}
