package main.java.epiccrawl.designer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.epiccrawl.Main;
import main.java.epiccrawl.database.MetaItem;
import main.java.epiccrawl.database.Type;
import main.java.epiccrawl.designer.designerComponent.OptionListener;
import main.java.epiccrawl.designer.designerComponent.PortalInputPanel;

@SuppressWarnings("serial")
public class DesignerGrid extends JPanel implements OptionListener{
	private int rows, cols, optionsID;
	private GridObject[][] gridObjs;
	
	private MetaItem selectedMetaItem;
	private GridObject hoverGridObj;
	
	private ArrayList<MetaItem> fillObjectMetaItemHolder;
	private boolean[][] alreadyChecked;
	
	public DesignerGrid(){
		rows = Main.rows;
		cols = Main.cols;
		optionsID = 0;
		selectedMetaItem = MetaItem.getVoidMetaItem();
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setColumns(cols);
		gridLayout.setRows(rows);
		
		this.setVisible(true);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(getMaximumSize());
		this.setLayout(gridLayout);

		alreadyChecked = new boolean[rows][cols];
		
		makeGrid();
		
		hoverGridObj = gridObjs[0][0];
		
		addMouseHandlers();
	}
	
	private void makeGrid(){
		gridObjs = new GridObject[rows][cols];
		PortalInputPanel portalInputPanel = new PortalInputPanel();
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				GridObject gridObj = new GridObject(portalInputPanel);
				gridObj.setToolTipText("(" + i +", " + j + ")");
				
				gridObj.addMouseListener(new MouseAdapter (){
				    public void mousePressed ( MouseEvent e ){
				    	DesignerGrid.this.dispatchEvent ( SwingUtilities.convertMouseEvent ( e.getComponent (), e, DesignerGrid.this ) );
				    }
				});
				gridObj.addMouseMotionListener(new MouseAdapter(){
					public void mouseDragged(MouseEvent e){
				    	DesignerGrid.this.dispatchEvent ( SwingUtilities.convertMouseEvent ( e.getComponent (), e, DesignerGrid.this ) );
				    }
				    
				    public void mouseMoved(MouseEvent e){
				    	DesignerGrid.this.dispatchEvent ( SwingUtilities.convertMouseEvent ( e.getComponent (), e, DesignerGrid.this ) );
				    }
				});
				gridObjs[i][j] = gridObj;
				this.add(gridObjs[i][j]);
			}
		}
	}
	
	@Override
	public void radioButtonPressed(int id) {
		optionsID = id; // 0 for single, 1 for fill, 2 for edit
	}
	
	@Override
	public void comboBoxItemSelected(int comboBoxID, MetaItem metaItem) {
		if(metaItem.getID() == -1)
			return;
		
		selectedMetaItem = metaItem;
	}
	
	public void clearGrid(){
		for(int i = 0; i < gridObjs.length; i++){
			for(int j = 0; j < gridObjs[i].length; j++){
				gridObjs[i][j].clearObjects();
				gridObjs[i][j].addObjectToTop(MetaItem.getVoidMetaItem());
			}
		}
	}
	
	private GridObject findGridObjectByPoint(Point p){
		for(int row = 0; row < rows; ++row){
			for(int col = 0; col < cols; ++col){
				if (gridObjs[row][col].getBounds().contains(p))
					return gridObjs[row][col];
			}
		}
		
		return null;
	}
	
	public GridObject[][] getGridObjects(){ return gridObjs;}
	
	// 
	private void handleLeftClick(Point clickPoint){
		// Handle things such as if the selector is on (portal editing
		// could have boolean for selector - pass it down to the grid object
		
		if(findGridObjectByPoint(clickPoint) == null) return;
		
		switch(optionsID){ // 
		case 0: // Single option
			handleSingle(clickPoint);
			break;
		case 1: // Fill option
			handleFill(clickPoint);
			break;
		case 2: // Edit option
			findGridObjectByPoint(clickPoint).handleEdit();
			break;
		}
	}
	
	private void handleRightClick(Point clickPoint){
		if(findGridObjectByPoint(clickPoint) == null) return;
		
		switch(optionsID){
		case 0:
			findGridObjectByPoint(clickPoint).removeTopObject();
			break;
		}
	}
	
	private void handleSingle(Point clickPoint){
		List<Type> types = selectedMetaItem.getTypes();
		if(types.contains(Type.SPAN)){ // Multi-image object
			Point coord = getCoordinateofPoint(clickPoint);
			int col = coord.x, row = coord.y;
			MultiObjectHandler.handleMultiObjectPlacement(gridObjs, row, col, selectedMetaItem.getID());
		}
		else{ // Singular object
			findGridObjectByPoint(clickPoint).handleClick(selectedMetaItem);
		}
	}
	
	private void handleFill(Point clickPoint){
		Point coord = getCoordinateofPoint(clickPoint);
		int col = coord.x, row = coord.y;
		List<Type> types = selectedMetaItem.getTypes();
		
		if(types.contains(Type.SPAN) || types.contains(Type.PORTAL)) return; // Don't fill with multi-tile images or portals
		
		fillObjectMetaItemHolder = gridObjs[row][col].deepCopyObjectMapItems();
		
		for(int i = 0; i < alreadyChecked.length; i++){
			for(int j = 0; j < alreadyChecked[i].length; j++)
				alreadyChecked[i][j] = false;
		}
		
		fillHelper(row, col);
	}
	
	// Original objMapItems, row and col that need to be checked
	private void fillHelper(int row, int col){
		alreadyChecked[row][col] = true;
		
		if(gridObjs[row][col].sameObjectMapItems(fillObjectMetaItemHolder)){
			gridObjs[row][col].handleClick(selectedMetaItem);
			
			if(row + 1 < rows && !alreadyChecked[row + 1][col])
				fillHelper(row + 1, col);
			if(row - 1 >= 0 && !alreadyChecked[row - 1][col])
				fillHelper(row - 1, col);
			if(col + 1 < cols && !alreadyChecked[row][col + 1])
				fillHelper(row, col + 1);
			if(col - 1 >= 0 && !alreadyChecked[row][col - 1])
				fillHelper(row, col - 1);
		}
	}
	
	private Point getCoordinateofPoint(Point clickPoint){
		for(int row = 0; row < rows; ++row){
			for(int col = 0; col < cols; ++col){
				if (gridObjs[row][col].getBounds().contains(clickPoint))
					return new Point(col, row);
			}
		}
		
		return null;
	}
	
	private void handleHover(Point hoverPoint){
		GridObject gridObj = findGridObjectByPoint(hoverPoint);
		
		if(gridObj == null) return;
		
		if(gridObj != hoverGridObj){
			hoverGridObj.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			gridObj.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			hoverGridObj = gridObj;
		}
	}
	
	private void addMouseHandlers(){
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
				if(SwingUtilities.isLeftMouseButton(me))
					handleLeftClick(me.getPoint());
				else if(SwingUtilities.isRightMouseButton(me))
					handleRightClick(me.getPoint());
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent me) {
				handleHover(me.getPoint());
				
				if(SwingUtilities.isLeftMouseButton(me))
					handleLeftClick(me.getPoint());
			} 
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me){
				handleHover(me.getPoint());
		    }
		});
	}
}