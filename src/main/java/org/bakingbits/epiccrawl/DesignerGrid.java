package org.bakingbits.epiccrawl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

/**
 * Created by aboutin on 7/10/17.
 *
 * The actual grid that shows what's in the current level. Interactive.
 */
public class DesignerGrid extends JPanel {
    private Level curLevel;
    private DesignerPanel designerPanel; // TODO: Change up to limited interface

    private int rows = 50, cols = 50; // TODO: Keep above 0

    private int cellWidth = getWidth() / cols, cellHeight = getHeight() / rows;

    private GridLocation curHoverGridLocation = new GridLocation(0, 0);

    private double zoom = 1.0;

    private AffineTransform m_zoom;

    public DesignerGrid(DesignerPanel designerPanel) {
        updateCurCoordsDisplay(curHoverGridLocation);

        this.designerPanel = designerPanel;

        this.setLayout(new BorderLayout());
        Dimension dimension = new Dimension(EpicCrawl.screenWidth,EpicCrawl.screenHeight - 60);
        this.setPreferredSize(dimension);
        this.setBackground(Color.BLACK);

        addMouseListeners();
    }

    public void setLevel(Level curLevel) {
        this.curLevel = curLevel;
    }

    // TODO: Now this also has to take the scrolls into consideration
    private GridLocation convertPointToGridLocation(Point clickPoint) {
        int col = (int)(clickPoint.getX() / zoom / cellWidth);
        int row = (int)(clickPoint.getY() / zoom / cellHeight);

        // Cell chunked grid may not take up the whole panel so prevent out of bounds
        if(row >= rows) {
            row = rows - 1;
        }
        if(col >= cols) {
            col = cols - 1;
        }

        return new GridLocation(row, col);
    }

    private void addMouseListeners(){
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me) {
                GridLocation gridLocation = convertPointToGridLocation(me.getPoint());

                if(SwingUtilities.isLeftMouseButton(me)) {
                    designerPanel.changeImage(gridLocation);
                    repaint();
                }
                else if(SwingUtilities.isRightMouseButton(me))
                    return;
            }
        });

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                double temp = zoom - (notches * 0.2);
                temp = Math.max(temp, 1.0);

                temp = Math.min(temp, 2.0);

                if(temp != zoom) {
                    zoom = temp;

                    Dimension dimension = new Dimension((int)(EpicCrawl.screenWidth * zoom), (int)((EpicCrawl.screenHeight - 60) * zoom));
                    designerPanel.setPreferredSize(dimension);

                    // Handles automatically updating the scroll bar sizes
                    designerPanel.revalidate();
                    designerPanel.repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent me) {
                updateHoverLocation(convertPointToGridLocation(me.getPoint()));
            }

            public void mouseMoved(MouseEvent me){
                updateHoverLocation(convertPointToGridLocation(me.getPoint()));
            }
        });
    }

    private void updateHoverLocation(GridLocation newHoverGridLocation) {
        if(!curHoverGridLocation.equals(newHoverGridLocation)) {
            curHoverGridLocation = newHoverGridLocation;
            updateCurCoordsDisplay(newHoverGridLocation);
            repaint();
        }
    }

    private void updateCurCoordsDisplay(GridLocation newGridLocation) {
        this.setToolTipText("(" + newGridLocation.getRow() + ", " + newGridLocation.getCol() + ")");
    }

    @Override
    public void paintComponent(Graphics g) {
        m_zoom = ((Graphics2D) g).getTransform();
        m_zoom.scale(zoom, zoom);
        ((Graphics2D) g).transform(m_zoom);

        super.paintComponent(g);

        cellWidth = getWidth() / cols;
        cellHeight = getHeight() / rows;

        // TODO: Maybe interface to only expose this to here... same for class inside the array...
        // TODO: So would only have access to the 3D array of objects and only be able to get the images out of the objects
        Image[][] gridObjects = curLevel.getGridObjects();

        // Draw images
        for(int curRow = 0; curRow < rows; curRow++) {
            for(int curCol = 0; curCol < cols; curCol++) {
                g.drawImage(gridObjects[curRow][curCol], curCol * cellWidth, curRow * cellHeight, cellWidth, cellHeight, this);

                // Draw the column dividers
                int x1 = curCol * cellWidth;
                int y1 = 0;

                int x2 = curCol * cellWidth;
                int y2 = getHeight();

                g.drawLine(x1, y1, x2, y2);
            }

            // Draw the row dividers
            int x1 = 0;
            int y1 = curRow * cellHeight;

            int x2 = getWidth();
            int y2 = curRow * cellHeight;

            g.drawLine(x1, y1, x2, y2);
        }

        // Draw a different color outline around the currently hovered cell
        g.setColor(Color.YELLOW);

        int y1 = cellHeight * curHoverGridLocation.getRow();
        int y2 = y1 + cellHeight;

        // Draw the two horizontal lines
        for(int curRow = 0; curRow < 2; curRow++) {
            int x = cellWidth * curHoverGridLocation.getCol() + (cellWidth * curRow);
            g.drawLine(x, y1, x, y2);
        }

        int x1 = cellWidth * curHoverGridLocation.getCol();
        int x2 = x1 + cellWidth;

        // Draw the two vertical lines
        for(int curCol = 0; curCol < 2; curCol++) {
            int y = cellHeight * curHoverGridLocation.getRow() + (cellHeight * curCol);
            g.drawLine(x1, y, x2, y);
        }
    }
}
