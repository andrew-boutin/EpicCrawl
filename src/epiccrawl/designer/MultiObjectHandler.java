package epiccrawl.designer;

public final class MultiObjectHandler {
	// TODO: Make methods to check xrows by xcols, have house use method to make xrows by xcols, pass in images
	
	public static void handleMultiObjectPlacement(GridObject[][] gridObjs, int row, int col, int objKey){
		// TODO: Should be able to determine this from the input image 2D array
		// Gives the coords offset from click point to check for validity - place the given objects
		
		switch(objKey){
		case 21:
			handleBed(gridObjs, row, col, objKey);
			break;
		case 26: // House
			handleHouse(gridObjs, row, col, objKey);
			break;
		default:
			System.err.println("Error placing multi-object, key: " + objKey);	
		}
	}
	
	private static void handleBed(GridObject[][] gridObjs, int rowStart, int colStart, int objKey){
		System.err.println("SPAN bed placement not implemented yet.");
		/* TODO:
		if(colStart + 1 >= GameInfo.rows) return;
		
		for(int col = colStart; col < colStart + 2; col++)
			if(!gridObjs[rowStart][col].hasValidBase()) return;
		
		// Place all objects
		String[] imageNames = GameInfo.multiImageMap.get(objKey);
		ObjectMapItem p = GameInfo.makeObjectMapItem(objKey);
				
		ObjectMapItem objMapItem = new ObjectMapItem(objKey, p.getClassName(), imageNames[0], p.getDesc(), p.getLayer(), p.getType());
		gridObjs[rowStart][colStart].handleClick(objMapItem);
		
		objMapItem = new ObjectMapItem(objKey, p.getClassName(), imageNames[1], p.getDesc(), p.getLayer(), p.getType());
		gridObjs[rowStart][colStart + 1].handleClick(objMapItem);
		*/
	}
	
	private static void handleHouse(GridObject[][] gridObjs, int rowStart, int colStart, int objKey){
		System.err.println("SPAN house placement not implemented yet.");
		/* TODO:
		// row,col will be top/left of building
		// Have to see if all spots are within valid indexes
		
		if(rowStart + 5 >= GameInfo.rows || colStart + 3 >= GameInfo.cols) return;
		
		// Have to see if all spots are good for placement
		for(int row = rowStart; row < rowStart + 3; row++){
			for(int col = colStart; col < colStart + 5; col++){
				if(!gridObjs[row][col].hasValidBase()) return;
			}
		}
		
		// Place all objects
		String[] imageNames = GameInfo.multiImageMap.get(objKey);
		int i = 0;
		ObjectMapItem p = GameInfo.makeObjectMapItem(objKey);
		
		for(int row = rowStart; row < rowStart + 3; row++){
			for(int col = colStart; col < colStart + 5; col++){
				ObjectMapItem objMapItem = new ObjectMapItem(objKey, p.getClassName(), imageNames[i], p.getDesc(), p.getLayer(), p.getType());
				gridObjs[row][col].handleClick(objMapItem);
				i++;
			}
		}
		*/
	}
}
