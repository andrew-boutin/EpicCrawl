package epiccrawl;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public final class GameUtility {
	
	private GameUtility(){}
	
	public static Image makeImage(String imageName){
		InputStream input = GameUtility.class.getClassLoader().getResourceAsStream("Images/" + imageName);
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
	
	public static String readFile(String path) throws IOException{
		InputStream is = GameUtility.class.getClassLoader().getResourceAsStream(path);

		char[] buf = new char[2048];
		Reader r = new InputStreamReader(is, "UTF-8");
		StringBuilder s = new StringBuilder();
  
		int n;
		while((n = r.read(buf)) >= 0)
			s.append(buf, 0, n);
		
		return s.toString();
	}
	
	public static String[] getSavedLevelNames(){
		ArrayList<String> savedLevelsList = new ArrayList<String>();
		try{
			BufferedReader bufReader = new BufferedReader(new FileReader("Levels/LevelsList.txt"));
			String curLine = "";

			while((curLine = bufReader.readLine()) != null){
				if(!curLine.equals(""))
					savedLevelsList.add(curLine.replace(".txt", ""));
			}

			bufReader.close();
		}
		catch(Exception e){
			System.err.println("Can't open file to load levels, " + e);
			JOptionPane.showMessageDialog(null, "Missing the LevelsList.txt file!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		
		String[] savedLevels = new String[savedLevelsList.size()];
		
		for(int i = 0; i < savedLevelsList.size(); ++i)
			savedLevels[i] = savedLevelsList.get(i);
		
		return savedLevels;
	}
}
