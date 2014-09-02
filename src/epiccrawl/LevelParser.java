package epiccrawl;

import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import epiccrawl.designer.GridObject;
import epiccrawl.designer.ObjectMapItem;

public final class LevelParser {
	private static String curLevelName = "";
	
	private LevelParser(){}
	
	// save to file
	public static void saveLevel(JPanel panel, GridObject[][] gridObjs){
		String levelName = (String)JOptionPane.showInputDialog(panel, 
				"Enter level name.", "Save level", JOptionPane.INFORMATION_MESSAGE, null, null, curLevelName);
	
		if(levelName == null || levelName.equals("null")) return;
		
		curLevelName = levelName;
		
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		levelName += ".txt";
		String levelString = designerToString(gridObjs); // Make level into a string
		
		boolean alreadyExists = false;
		
		// Need to check if level name already exists in the file
		try{
			FileReader fr = new FileReader("Levels/LevelsList.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;  
			while((line = br.readLine()) != null) {  
			      if(line.equals(levelName)){
			    	  alreadyExists = true;
			    	  break;
			      }
			}
			fr.close();
			br.close();
		}catch(Exception e2){}
		
		if(!alreadyExists){
			try{
				FileWriter fw = new FileWriter("Levels/LevelsList.txt", true);
				BufferedWriter bufWriter = new BufferedWriter(fw);
				bufWriter.write(levelName);
				bufWriter.newLine();
				bufWriter.flush();
				bufWriter.close();
				fw.close();
			}
			catch(IOException e){System.out.println("Error saving level to LevelsList.txt");}
		}
		
		try{
			PrintWriter p = new PrintWriter(new File("Levels/" + levelName));
			p.println(levelString);
			p.close();   
		} catch (FileNotFoundException e1){}
		panel.setCursor(Cursor.getDefaultCursor());
	}
	
	private static String designerToString(GridObject[][] gridObjs){
		StringBuilder strBuilder = new StringBuilder("");
		
		for(int row = 0; row < gridObjs.length; row++){
			for(int col = 0; col < gridObjs[row].length; col++){
				for(ObjectMapItem objMapItem: gridObjs[row][col].getObjects()){
					strBuilder.append(objMapItem.getKey()).append(",");
				}
				strBuilder.append(" ");
			}
			strBuilder.append("\n");
		}
		
		return strBuilder.toString();
	}
	
	// load to designer
	public static void loadLevelToDesigner(JPanel panel, GridObject[][] gridObjs){
		String[] savedLevels = GameUtility.getSavedLevelNames();
		
		if(savedLevels == null) return; // "";

		String levelFileName = (String)JOptionPane.showInputDialog(null,
				"Select level to load", "Load Level", JOptionPane.INFORMATION_MESSAGE, null, savedLevels, curLevelName);

		if(levelFileName == null) return; // "";
		curLevelName = levelFileName; //.replace(".txt", "");
		levelFileName += ".txt";

		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		FileReader fileReader = null;
		try{
			fileReader = new FileReader("Levels/" + levelFileName);
		} 
		catch(FileNotFoundException e1){System.out.println("file not found");}

		Scanner lineScanner = new Scanner(fileReader);
		Scanner wordScanner = null;
		int curRow = 0, curCol = 0;
		String curString = "";

		while(lineScanner.hasNextLine()){
			wordScanner = new Scanner(lineScanner.nextLine());

			while(wordScanner.hasNext()){
				gridObjs[curRow][curCol].clearObjects();
				curString = wordScanner.next();
				
				ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(curString.split(",")));

				for(String cur: wordList) // TODO: Portals ... etc.
					gridObjs[curRow][curCol].addObjectToTop(GameInfo.makeObjectMapItem(Integer.parseInt(cur)));
					//gridObjs[curRow][curCol].handleClick(GameInfo.makeObjectMapItem(Integer.parseInt(cur)));
				

				++curCol;
			}

			curCol = 0;
			++curRow;
		}

		try{ fileReader.close();}
		catch (IOException e){}
		
		lineScanner.close();
		wordScanner.close();
		
		panel.setCursor(Cursor.getDefaultCursor());
	}
	
	
	
	// load to game
}
