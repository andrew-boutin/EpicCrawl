package epiccrawl.designer;

import java.awt.Image;

import database.Type;
import database.Layer;
import epiccrawl.GameUtility;

public class ObjectMapItem {
	private int key;
	private Type type;
	private Layer layer;
	private String className, imageName, desc;
	private Image img;
	private String extraInfo; // Optional additional info
	
	public ObjectMapItem(int key, String className, String imageName, String desc, 
													Layer layer, Type type){
		this.key = key;
		this.className = className;
		this.imageName = imageName;
		this.desc = desc;
		this.layer = layer;
		this.type = type;
		this.extraInfo = "";
		
		setUp();
	}
	
	// TODO: Make images somewhere else and have a key stored here.
	private void setUp(){ img = GameUtility.makeImage(imageName);}
	
	public int getKey(){ return key;}
	
	public String getClassName(){ return className;}
	
	public String getImageName(){ return imageName;}

	public String getDesc(){ return desc;}
	
	public Layer getLayer(){ return layer;}
	
	public Type getType(){ return type;}
	
	public String getExtraInfo(){ return extraInfo;}
	
	public void setExtraInfo(String info){ extraInfo = info;}
	
	public boolean hasExtraInfo(){ return (!extraInfo.equals(""));}
	
	@Override
    public String toString(){ return desc;}
	
	public Image getImage(){ return img;}
}