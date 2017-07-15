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
public class DesignerGrid extends JPanel { // implements Scrollable {
    private Level curLevel;
    private DesignerPanel designerPanel; // TODO: Change up to limited interface

    // TODO: Keep these above 0 when allowing the user to define them
    private int rows = 50;
    private int cols = 50;

    private GridLocation curHoverGridLocation = new GridLocation(0, 0);

    // Related to zoom
    private final double maxZoom = 2.0;
    private final double minZoom = 1.0;
    private final double zoomStep = 0.2;
    private double zoom = minZoom;

    public DesignerGrid(DesignerPanel designerPanel) {
        updateCurCoordsDisplay(curHoverGridLocation);

        this.designerPanel = designerPanel;
        this.setBackground(Color.BLACK);

        addMouseListeners();
    }

    public void setLevel(Level curLevel) {
        this.curLevel = curLevel;
    }

    private GridLocation convertPointToGridLocation(Point clickPoint) {
        Dimension cellDimension = getCellDimension();

        int cellWidth = (int)cellDimension.getWidth();
        int cellHeight = (int)cellDimension.getHeight();

        int col = (int)((clickPoint.getX() + 0) / cellWidth); // / zoom / cellWidth);
        int row = (int)((clickPoint.getY() + 0) / cellHeight); // / zoom / cellHeight);

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
                double temp = (zoom - (notches * zoomStep));
                temp = Math.max(temp, minZoom);
                temp = Math.min(temp, maxZoom);

                if(temp != zoom) {
                    zoom = temp;

                    Dimension jScrollPaneSize = designerPanel.getScrollPaneViewPortSize();
                    Dimension dimension = new Dimension((int)(jScrollPaneSize.getWidth() * zoom), (int)(jScrollPaneSize.getHeight() * zoom));

                    DesignerGrid.this.setPreferredSize(dimension);
                    DesignerGrid.this.revalidate();
                    DesignerGrid.this.repaint();
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

    private Dimension getCellDimension() {
        return new Dimension(getWidth() / cols, getHeight() / rows);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension cellDimension = getCellDimension();

        int cellWidth = (int)cellDimension.getWidth();
        int cellHeight = (int)cellDimension.getHeight();

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

        // Handle displaying with the zoom factor
        Graphics2D g2 = (Graphics2D)g;
        int w = getWidth();
        int h = getHeight();
        g2.translate(w / 2, h / 2);
        g2.scale(zoom, zoom);
        g2.translate(-(w / 2), -(h / 2));
    }

//    @Override
//    public Dimension getPreferredScrollableViewportSize() {
//        return new Dimension(10000, 10000);
//    }
//
//    //visibleRect - The view area visible within the viewport
//    //orientation - Either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
//    //direction - Less than zero to scroll up/left, greater than zero for down/right.
//
//    @Override
//    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
//        // TODO: Clicking in space outside of current scroll on scroll bar - moves the scroll bar a whole block
//        return 1;
//    }
//
//    @Override
//    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
//        // TODO: Moving the scroll bar one increment - move one row / column
//        return 1;
//    }
//
//    @Override
//    public boolean getScrollableTracksViewportHeight() {
//        return false;
//    }
//
//    @Override
//    public boolean getScrollableTracksViewportWidth() {
//        return false;
//    }
}
