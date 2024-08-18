package raf.dsw.gerumap.app.gui.swing.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public final class GuiUtils {
    private GuiUtils() { }
    public static void removeButtonBorders(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    public static JLabel newCenteredLabel(String text) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        return l;
    }

    public static JLabel newCenteredLabel(Icon icon) {
        JLabel l = new JLabel(icon);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        return l;
    }

    public static JPanel newBoxedJPanel(int layout, int padding) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, layout));
        panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        return panel;
    }

    public static JPanel newBoxedJPanel(int layout) {
        return newBoxedJPanel(layout, 0);
    }

    public static JPanel newBoxedJPanel(int layout, int padding, Component ... components) {
        JPanel panel = newBoxedJPanel(layout, padding);
        boolean padded = false;
        for (Component c : components) {
            if(padded) {
                if(layout == BoxLayout.X_AXIS)
                    panel.add(Box.createHorizontalStrut(padding));
                else
                    panel.add(Box.createVerticalStrut(padding));
            }
            panel.add(c);
            padded = true;
        }
        return panel;
    }

    public static String getUserInputWithDialog(String title, String msg, String defaultText) {
        return (String) JOptionPane.showInputDialog(
                MainFrame.getInstance(),
                msg,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                defaultText
        );
    }

    public static int tabByName(JTabbedPane pane, String title) {
        for (int i = 0; i < pane.getTabCount(); i++) {
            if(pane.getTitleAt(i).equals(title)) return i;
        }
        return -1;
    }

    public static Dimension getStringSize(Graphics g, String str, Font fnt, float x, float y) {
       return fnt.createGlyphVector(g.getFontMetrics().getFontRenderContext(), str).getPixelBounds(null, x, y).getSize();
    }

    public static File getFileWithDialog(boolean open, String fileDescription, String fileExtension, String path) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileNameExtensionFilter(fileDescription, fileExtension));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if(path != null)
            chooser.setCurrentDirectory(new File(path));
        int chosenOption = open ? chooser.showOpenDialog(MainFrame.getInstance()) : chooser.showSaveDialog(MainFrame.getInstance());
        if(chosenOption != JFileChooser.APPROVE_OPTION)
            return null;

        File selectedFile = chooser.getSelectedFile();
        if(!open && !selectedFile.getPath().toLowerCase().endsWith("." + fileExtension.toLowerCase()))
            selectedFile = new File(selectedFile.getAbsolutePath() + "." + fileExtension);
        return selectedFile;
    }

    public static File getFileWithDialog(boolean open, String fileDescription, String fileExtension) {
        return getFileWithDialog(open, fileDescription, fileExtension, null);
    }

    public static File getJsonFileWithDialog(boolean open, String path) {
        return getFileWithDialog(open, "JSON file", "json", path);
    }

    public static File getJsonFileWithDialog(boolean open) {
        return getJsonFileWithDialog(open, null);
    }
}