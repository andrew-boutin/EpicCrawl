package epiccrawl;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import epiccrawl.database.Database;
import epiccrawl.database.MetaItem;
import epiccrawl.designer.GridObject;

public final class LevelParser {
	private static LevelParser instance;
	private static String curLevelName = "";
	private Database database;
	
	private LevelParser(){
		database = Database.getInstance();
	}
	
	public static LevelParser getInstance(){
		if(instance == null)
			instance = new LevelParser();
		
		return instance;
	}
	
	public void deleteLevel(JPanel panel){
		List<String> savedLevels = database.getSavedLevelNames();
		
		if(savedLevels.size() == 0) return; // TODO: Warn about no levels

		String levelName = (String)JOptionPane.showInputDialog(null,
				"Select level to delete", "Delete Level", JOptionPane.INFORMATION_MESSAGE, null, savedLevels.toArray(), curLevelName);

		if(levelName == null || levelName.equals("null")) return;
		
		if(levelName.equals(curLevelName))
			curLevelName = "";
		
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		database.deleteLevel(levelName);
		
		panel.setCursor(Cursor.getDefaultCursor());
	}
	
	public void saveLevel(JPanel panel, GridObject[][] gridObjs){
		String levelName = (String)JOptionPane.showInputDialog(panel, 
				"Enter level name.", "Save level", JOptionPane.INFORMATION_MESSAGE, null, null, curLevelName);
	
		if(levelName == null || levelName.equals("null")) return;
		
		curLevelName = levelName;
		
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		String levelString = designerToString(gridObjs); // Make level into a string
		
		// Need to check if level name already exists
		if(database.doesLevelNameExist(curLevelName)){
			int reply = JOptionPane.showConfirmDialog(null, "Overwrite existing level \"" + curLevelName +"\"?", "Overwrite Level", JOptionPane.YES_NO_OPTION);
			
	        if(reply == JOptionPane.YES_OPTION)
	          database.overWriteLevelData(curLevelName, levelString);
		}
		else
			database.createLevel(curLevelName, levelString);

		panel.setCursor(Cursor.getDefaultCursor());
	}
	
	private String designerToString(GridObject[][] gridObjs){
		StringBuilder strBuilder = new StringBuilder("");
		
		for(int row = 0; row < gridObjs.length; row++){
			for(int col = 0; col < gridObjs[row].length; col++){
				for(MetaItem metaItem: gridObjs[row][col].getObjects()){
					strBuilder.append(metaItem.getID()).append(",");
				}
				strBuilder.append(" ");
			}
			strBuilder.append("\n");
		}
		
		return strBuilder.toString();
	}
	
	public void loadLevelToDesigner(JPanel panel, GridObject[][] gridObjs){
		List<String> savedLevels = database.getSavedLevelNames();
		
		if(savedLevels.size() == 0) return; // TODO: Warn about no levels

		String levelFileName = (String)JOptionPane.showInputDialog(null,
				"Select level to load", "Load Level", JOptionPane.INFORMATION_MESSAGE, null, savedLevels.toArray(), curLevelName);

		if(levelFileName == null) return;
		curLevelName = levelFileName;

		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		String levelData = database.getLevelData(curLevelName);

		int curRow = 0, curCol = 0;

		for(String line: levelData.split("\n")){
			for(String word: line.split(" ")){
				gridObjs[curRow][curCol].clearObjects();
			
				ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(word.split(",")));

				for(String cur: wordList) // TODO: Portals ... etc.
					gridObjs[curRow][curCol].addObjectToTop(MetaItem.getMetaItemByID(Integer.parseInt(cur)));

				++curCol;
			}

			curCol = 0;
			++curRow;
		}
		
		panel.setCursor(Cursor.getDefaultCursor());
	}
}
