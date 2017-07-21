package org.bakingbits.epiccrawl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aboutin on 7/10/17.
 */
public class Level {
    private String name;
    private int numRows = 50, numCols = 50;

    private Image[][] gridObjects;

    public Level() {
        gridObjects = new Image[numRows][numCols];

        for(int curRow = 0; curRow < numRows; curRow++) {
            for(int curCol = 0; curCol < numCols; curCol++) {
                gridObjects[curRow][curCol] = makeImage("void.png");
            }
        }
    }

    public Image[][] getGridObjects(){
        return gridObjects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void reSize(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;

        // TODO: Copy over pre existing images..

        gridObjects = new Image[numRows][numCols];
    }

    public void setCell(GridLocation gridLocation){
        gridObjects[gridLocation.getRow()][gridLocation.getCol()] = makeImage("grass.png");
    }

    public void resetCell(GridLocation gridLocation) {
        gridObjects[gridLocation.getRow()][gridLocation.getCol()] = makeImage("void.png");
    }

    private Image makeImage(String imageName){
        InputStream input = DesignerGrid.class.getClassLoader().getResourceAsStream("images/" + imageName);
        Image img = null;

        try{
            img = ImageIO.read(input);
        }
        catch(IOException e){
            System.err.println("Failed to load image " + imageName + " for grid square.");
        }
        catch(Exception e){
            System.err.println("Image not found! " + imageName);
        }

        return img;
    }
}
