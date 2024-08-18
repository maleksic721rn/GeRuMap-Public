package raf.dsw.gerumap.app.gui.swing.view;

import raf.dsw.gerumap.app.mapRepository.implementation.ElementStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StyleDialog extends JDialog implements ActionListener {

    private ElementStyle style;
    private JSpinner strokeWidthSpinner;
    private JLabel fillColorLabel;
    private JLabel strokeColorLabel;
    private JLabel textColorLabel;
    private JButton okButton;

    private static int HOR_SPACING = 2;
    private static int VERT_SPACING = 4;

    public StyleDialog(ElementStyle es) {
        super(MainFrame.getInstance(), "Set element style", true);
        style = es;
        initGUI();
        setStyleParameters();
    }

    public StyleDialog() {
        this(ElementStyle.getDefaultStyle());
    }

    private void setStyleParameters() {
        fillColorLabel.setText("Fill color: " + style.getFillColor());
        strokeColorLabel.setText("Stroke color: " + style.getStrokeColor());
        textColorLabel.setText("Text color: " + style.getTextColor());
        pack();
    }

    private void initGUI() {
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel contentPanel = GuiUtils.newBoxedJPanel(BoxLayout.Y_AXIS, 8);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        strokeWidthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 0.1));
        fillColorLabel = new JLabel();
        JButton fillColorButton = new JButton("...");
        fillColorButton.addActionListener(e -> {
            Color fillCol = JColorChooser.showDialog(this, "Select a color", style.getFillColor());
            if(fillCol != null) {
                style.setFillColor(fillCol);
                setStyleParameters();
            }
        });
        strokeColorLabel = new JLabel();
        JButton strokeColorButton = new JButton("...");
        strokeColorButton.addActionListener(e -> {
            Color strokeCol = JColorChooser.showDialog(this, "Select a color", style.getStrokeColor());
            if(strokeCol != null) {
                style.setStrokeColor(strokeCol);
                setStyleParameters();
            }
        });
        textColorLabel = new JLabel();
        JButton textColorButton = new JButton("...");
        textColorButton.addActionListener(e -> {
            Color textCol = JColorChooser.showDialog(this, "Select a color", style.getTextColor());
            if(textCol != null) {
                style.setTextColor(textCol);
                setStyleParameters();
            }
        });

        JPanel strokeWidthPanel = GuiUtils.newBoxedJPanel(BoxLayout.X_AXIS, HOR_SPACING, new JLabel("Stroke width: "), strokeWidthSpinner);
        contentPanel.add(strokeWidthPanel);
        contentPanel.add(Box.createVerticalStrut(VERT_SPACING));

        JPanel fillColPanel = GuiUtils.newBoxedJPanel(BoxLayout.X_AXIS, HOR_SPACING, fillColorLabel, fillColorButton);
        contentPanel.add(fillColPanel);
        contentPanel.add(Box.createVerticalStrut(VERT_SPACING));

        JPanel strokeColPanel = GuiUtils.newBoxedJPanel(BoxLayout.X_AXIS, HOR_SPACING, strokeColorLabel, strokeColorButton);
        contentPanel.add(strokeColPanel);
        contentPanel.add(Box.createVerticalStrut(VERT_SPACING));

        JPanel textColPanel = GuiUtils.newBoxedJPanel(BoxLayout.X_AXIS, HOR_SPACING, textColorLabel, textColorButton);
        contentPanel.add(textColPanel);
        contentPanel.add(Box.createVerticalStrut(VERT_SPACING));

        okButton = new JButton("Ok");
        okButton.addActionListener(this);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        JPanel optionsPanel = GuiUtils.newBoxedJPanel(BoxLayout.X_AXIS, HOR_SPACING, okButton, cancelButton);
        contentPanel.add(optionsPanel);

        add(contentPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okButton) {
            style.setStrokeWidth(((Double) strokeWidthSpinner.getValue()).floatValue());
        } else {
            style = null;
        }
        dispose();
    }

    public ElementStyle showDialog() {
        setVisible(true);
        return style;
    }
}