// TODO: Will need to store actual objects in the grid as they're placed instead of MetaItems. Objects will refer to Meta Items.
// This will allow objects to take in extra user input when placed, know how to save/load themselves.

package main.java.epiccrawl.designer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.epiccrawl.database.Layer;
import main.java.epiccrawl.database.MetaItem;
import main.java.epiccrawl.database.Type;
import main.java.epiccrawl.designer.designerComponent.PortalInputPanel;

@SuppressWarnings("serial")
public class GridObject extends JPanel{
	private ArrayList<MetaItem> metaItems;
	private PortalInputPanel portalInputPanel;

	public GridObject(PortalInputPanel portalInputPanel){
		this.portalInputPanel = portalInputPanel;
		
		metaItems = new ArrayList<MetaItem>();
		metaItems.add(MetaItem.getVoidMetaItem());

		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setVisible(true);
	}
	
	public void handleClick(MetaItem metaItem){
		Layer layer = metaItem.getLayer();
		
		if(layer == Layer.VOID || layer == Layer.GROUND || layer == Layer.WALL){
			metaItems.clear();
			metaItems.add(metaItem);
		}
		else if(layer == Layer.ONGROUND){ // TODO: separate Layer.ONGROUND from Layer.ONWALL
			if(!hasValidBase()) return; // Nothing underneath
			
			if(metaItem.getTypes().contains(Type.PORTAL)) // Portal
				metaItem = handlePortal(metaItem.getID());
			
			if(metaItem == null) return;
			if(metaItems.size() > 1) metaItems.remove(1); // Replacing
			
			metaItems.add(metaItem);
		}
		else
			System.err.println("Layer " + layer.toString() + " not handled yet!");
				
		repaint();
	}
	
	public void handleEdit(){
		// TODO: change this to better handle multiple "editable" items in the future
		if(getTopObject().getTypes().contains(Type.PORTAL)){
			/* TODO:
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
			
			handlePortal(getTopObject().getID());
			*/
		}
	}
	
	private MetaItem handlePortal(int id){
		portalInputPanel.updateLevelList();
		
		int result = portalInputPanel.showOptionPanel();
		
		if(result != JOptionPane.OK_OPTION || !portalInputPanel.isValid()) return null; // Cancel
		
		//String levelName = portalInputPanel.getLevelName();
		//int x = portalInputPanel.getX(), y = portalInputPanel.getY();
		
		MetaItem metaItem = MetaItem.getMetaItemByID(id);
		//metaItem.setExtraInfo("{" + levelName + "," + x + "," + y + "}"); // TODO:
		
		return metaItem;
	}
	
	public boolean hasValidBase(){
		if(metaItems.size() == 0) return false;
		
		Layer curLayer = metaItems.get(0).getLayer();
		
		if(curLayer == Layer.WALL || curLayer == Layer.VOID) return false; // Can't place anything on top // TODO:
		
		return true;
	}
	
	public MetaItem getTopObject(){
		return metaItems.get(metaItems.size() - 1);
	}
	
	public void addObjectToTop(MetaItem metaItem){
		metaItems.add(metaItem);
	}
	
	public boolean sameObjectMapItems(ArrayList<MetaItem> objs){
		if(objs.size() != metaItems.size()) return false;
		
		for(int i = 0; i < metaItems.size(); i++)
			if(metaItems.get(i).getID() != objs.get(i).getID()) return false;
		
		return true;
	}
	
	public ArrayList<MetaItem> deepCopyObjectMapItems(){
		ArrayList<MetaItem> newMetaItems = new ArrayList<MetaItem>();
		
		for(MetaItem curMetaItem: metaItems){
			MetaItem newMetaItem = MetaItem.getMetaItemByID(curMetaItem.getID());
			newMetaItems.add(newMetaItem);
		}
		
		return newMetaItems;
	}
	
	public ArrayList<MetaItem> getObjects(){ return metaItems;}
	
	public void clearObjects(){ metaItems.clear();}
	
	public void removeTopObject(){
		if(metaItems.size() > 0) // Remove the top object if there is something to remove
			metaItems.remove(metaItems.size() - 1);
		
		if(metaItems.size() == 0) // If nothing is left then replace with void
			metaItems.add(MetaItem.getVoidMetaItem());
		
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g); // Important to call super class method
		g.clearRect(0, 0, getWidth(), getHeight()); // Clear the panel

		for(MetaItem curMetaItem: metaItems){
			if(curMetaItem == null)
				System.out.println("Meta item null");
			else if(curMetaItem.getImage() == null)
				System.out.println("Meta item image null");
			else
				g.drawImage(curMetaItem.getImage(), 0, 0, getWidth(), getHeight(), null);
		}
	}
}
