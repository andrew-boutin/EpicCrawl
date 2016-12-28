package main.java.epiccrawl.database;

import java.awt.Image;
import java.util.List;
import java.util.Map;

import main.java.epiccrawl.GameUtility;

public final class MetaItem {
	private static Database database = Database.getInstance();
	private static Map<Integer, MetaItem> metaItemMap = database.createMetaItems();
	private final static int voidID = 1;
	
	private final int id;
	private String name;
	private List<String> images;
	private List<Type> types;
	private Layer layer;
	
	private Image img;
	
	// TODO: images may need to be 2D array
	public MetaItem(int id, String name, List<String> images, List<Type> types, Layer layer){
		this.images = images;
		this.types = types;
		
		this.id = id;
		this.name = name;
		this.layer = layer;
		
		if(images != null)
			img = GameUtility.makeImage(this.images.get(0));
	}
	
	@Override
    public String toString(){ return name;}
	
	public int getID(){return id;}
	
	public String getName(){return name;}
	
	public List<String> getImages(){return images;}
	
	public List<Type> getTypes(){return types;}
	
	public Layer getLayer(){return layer;}
	
	public Image getImage(){ return img;}
	
	public static MetaItem getVoidMetaItem(){
		return metaItemMap.get(voidID);
	}
	
	public static MetaItem getMetaItemByID(int id){
		return metaItemMap.get(id);
	}
	
    public static Map<Integer, MetaItem> getMetaItemMap(){return metaItemMap;}
	
	//private String extraInfo; // Optional additional info
	//public String getClassName(){ return className;}
	//public String getExtraInfo(){ return extraInfo;}
	//public void setExtraInfo(String info){ extraInfo = info;}
	//public boolean hasExtraInfo(){ return (!extraInfo.equals(""));}
}
