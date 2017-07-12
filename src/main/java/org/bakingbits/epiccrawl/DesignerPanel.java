package org.bakingbits.epiccrawl;

import javax.swing.*;
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

    // TODO: Can make an interface for DesignerController, give reference to the controls (buttons etc.) so they can kick off events that can
    // effect the designer grid

    public DesignerPanel(JButton mainScreenButton) {
        curLevel = new Level();
        designerGrid = new DesignerGrid(this);
        designerGrid.setLevel(curLevel);

        this.setLayout(new BorderLayout());
        Dimension dimension = new Dimension(EpicCrawl.screenWidth, EpicCrawl.screenHeight - 60);
        this.setPreferredSize(dimension);
        this.setBackground(Color.BLUE);

        this.add(designerGrid, BorderLayout.CENTER);
//        this.add(optionsPanel, BorderLayout.SOUTH);
        //this.add(mainScreenButton);
    }

    public void changeImage(GridLocation gridLocation) {
        curLevel.updateImage(gridLocation);
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
