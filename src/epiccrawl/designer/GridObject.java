package epiccrawl.designer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import epiccrawl.GameInfo;
import epiccrawl.GameInfo.Layer;
import epiccrawl.GameInfo.Type;
import epiccrawl.designer.designerComponent.PortalInputPanel;

@SuppressWarnings("serial")
public class GridObject extends JPanel{
	private ArrayList<ObjectMapItem> objMapItems;
	private PortalInputPanel portalInputPanel;

	public GridObject(PortalInputPanel portalInputPanel){
		this.portalInputPanel = portalInputPanel;
		
		objMapItems = new ArrayList<ObjectMapItem>();
		objMapItems.add(GameInfo.makeObjectMapItem(0));

		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setVisible(true);
	}
	
	public void handleClick(ObjectMapItem objMapItem){
		Layer layer = objMapItem.getLayer();
		
		if(layer == Layer.first || layer == Layer.firstBase){
			objMapItems.clear();
			objMapItems.add(objMapItem);
		}
		else if(layer == Layer.second){
			if(!hasValidBase()) return; // Nothing underneath
			
			if(objMapItem.getType() == Type.portal) // Portal
				objMapItem = handlePortal(objMapItem.getKey());
			
			if(objMapItem == null) return;
			if(objMapItems.size() > 1) objMapItems.remove(1); // Replacing
			
			objMapItems.add(objMapItem);
		}
				
		repaint();
	}
	
	public void handleEdit(){
		// TODO: change this to better handle multiple "editable" items in the future
		if(getTopObject().getType() == Type.portal){
			String info = getTopObject().getExtraInfo();
			info = getTopObject().getExtraInfo().replace("{", "").replace("}", "");
			
			int toIndex = info.indexOf(",");
			String levelName = info.substring(0, toIndex);
			
			info = info.substring(toIndex + 1);
			toIndex = info.indexOf(",");
			String x = info.substring(0, toIndex);
			
			info = info.substring(toIndex + 1);
			String y = info;
			
			portalInputPanel.setX(x);
			portalInputPanel.setY(y);
			portalInputPanel.setLevel(levelName);
			
			handlePortal(getTopObject().getKey());
		}
	}
	
	private ObjectMapItem handlePortal(int key){
		portalInputPanel.updateLevelList();
		
		int result = portalInputPanel.showOptionPanel();
		
		if(result != JOptionPane.OK_OPTION || !portalInputPanel.isValid()) return null; // Cancel
		
		String levelName = portalInputPanel.getLevelName();
		int x = portalInputPanel.getX(), y = portalInputPanel.getY();
		
		ObjectMapItem objMapItem = GameInfo.makeObjectMapItem(key);
		objMapItem.setExtraInfo("{" + levelName + "," + x + "," + y + "}");
		
		return objMapItem;
	}
	
	public boolean hasValidBase(){
		if(objMapItems.size() == 0) return false;
		if(objMapItems.get(0).getLayer() == Layer.first) return false; // Can't place anything on top
		
		return true;
	}
	
	public ObjectMapItem getTopObject(){
		return objMapItems.get(objMapItems.size() - 1);
	}
	
	public void addObjectToTop(ObjectMapItem objMapItem){
		objMapItems.add(objMapItem);
	}
	
	public boolean sameObjectMapItems(ArrayList<ObjectMapItem> objs){
		if(objs.size() != objMapItems.size()) return false;
		
		for(int i = 0; i < objMapItems.size(); i++)
			if(objMapItems.get(i).getKey() != objs.get(i).getKey()) return false;
		
		return true;
	}
	
	public ArrayList<ObjectMapItem> deepCopyObjectMapItems(){
		ArrayList<ObjectMapItem> objs = new ArrayList<ObjectMapItem>();
		
		for(ObjectMapItem curObj: objMapItems){
			ObjectMapItem objMapItem = GameInfo.makeObjectMapItem(curObj.getKey());
			objs.add(objMapItem);
		}
		
		return objs;
	}
	
	public ArrayList<ObjectMapItem> getObjects(){ return objMapItems;}
	
	public void clearObjects(){ objMapItems.clear();}
	
	public void removeTopObject(){
		if(objMapItems.size() > 0) // Remove the top object if there is something to remove
			objMapItems.remove(objMapItems.size() - 1);
		
		if(objMapItems.size() == 0) // If nothing is left then replace with void
			objMapItems.add(GameInfo.makeObjectMapItem(0));
		
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g); // Important to call super class method
		g.clearRect(0, 0, getWidth(), getHeight()); // Clear the panel

		for(ObjectMapItem curObjMapItem: objMapItems)
			g.drawImage(curObjMapItem.getImage(), 0, 0, getWidth(), getHeight(), null);
	}
}
