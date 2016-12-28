package epiccrawl;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.imageio.ImageIO;

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
}
