package org.bakingbits.epiccrawl;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by aboutin on 7/10/17.
 *
 * Sets up the screen for the designer used to create new levels.
 *
 * Contains the grid and controls used to make levels.
 */
public class DesignerPanel extends JPanel {
    private Level curLevel;
    private final DesignerGrid designerGrid;
    private final JScrollPane jScrollPane;

    // TODO: Can make an interface for DesignerController, give reference to the controls (buttons etc.) so they can kick off events that can
    // effect the designer grid

    public DesignerPanel(JButton mainScreenButton) {
        // The designer grid is embedded into a scrollable view to support zooming
        jScrollPane = new JScrollPane();

        curLevel = new Level();
        designerGrid = new DesignerGrid(this);
        designerGrid.setLevel(curLevel);

        jScrollPane.setWheelScrollingEnabled(false);
        jScrollPane.setViewportView(designerGrid);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        JSpinner rowSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 100, 1));
        JSpinner columnSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 100, 1));

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int rows = (int)rowSpinner.getValue();
                int cols = (int)(columnSpinner.getValue());

                curLevel.reSize(rows, cols);
                designerGrid.updateGridSize(rows, cols);
            }
        };

        rowSpinner.addChangeListener(changeListener);
        columnSpinner.addChangeListener(changeListener);

        JPanel rowSpinnerPanel = new JPanel();
        rowSpinnerPanel.add(rowSpinner);
        rowSpinnerPanel.setBorder(BorderFactory.createTitledBorder("Rows"));
        rowSpinnerPanel.setToolTipText("Number of rows in the level (1 - 100).");
        rowSpinner.setToolTipText("Number of rows in the level (1 - 100).");

        JPanel columnSpinnerPanel = new JPanel();
        columnSpinnerPanel.add(columnSpinner);
        columnSpinnerPanel.setBorder(BorderFactory.createTitledBorder("Cols"));
        columnSpinnerPanel.setToolTipText("Number of columns in the level (1 - 100).");
        columnSpinner.setToolTipText("Number of columns in the level (1 - 100).");

        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLUE);

        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(rowSpinnerPanel, BorderLayout.EAST);
        this.add(columnSpinnerPanel, BorderLayout.WEST);
        this.add(new JLabel("Controls"), BorderLayout.NORTH);
    }

    public Dimension getScrollPaneViewPortSize() {
        return jScrollPane.getViewport().getSize();
    }

    public void changeImage(GridLocation gridLocation) {
        curLevel.setCell(gridLocation);
    }

    public void popTopObject(GridLocation gridLocation) {
        curLevel.resetCell(gridLocation);
    }

//    public static void main(String[] args){
//        JFrame mainFrame = new JFrame("Designer");
//
//        DesignerPanel d = new DesignerPanel(new JButton("Main Screen"));
//        mainFrame.add(d);
//
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
//        int width = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//        int height = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//        mainFrame.setSize(width - 200, height - 200); // This makes the restore down button not make the frame very tiny
//        mainFrame.setMinimumSize(new Dimension(800, 500));
//        mainFrame.setLocationRelativeTo(null);
//        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//        mainFrame.pack();
//        mainFrame.setVisible(true);
//    }
}
