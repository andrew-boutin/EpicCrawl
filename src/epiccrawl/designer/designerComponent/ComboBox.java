package epiccrawl.designer.designerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import epiccrawl.database.MetaItem;

// Each combo box will have an id
// Items in the combo box will be from the GameInfo map for game objects
// 		Text displayed will be the display text
//		Will also remember the index into the map
//		Will be put into different combo boxes based off of class
//
// When a value is selected - the class will be used for placement logic
// Image name will be used to display to the screen
// Indexes will be used when saving/loading the levels
@SuppressWarnings({ "serial", "rawtypes" })
public class ComboBox extends JComboBox{
	private OptionListener listener;
	private int identifier;

	public ComboBox(int id, OptionListener cbl){
		listener = cbl;
		identifier = id;

		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				if(listener != null)
					listener.comboBoxItemSelected(identifier, (MetaItem)getSelectedItem());
				else
					System.out.println("No Listener: mouseReleased");
			}
		});
	}
	
	/**
	 * Custom insertion order logic for ComboBox that holds the items to choose from in the level designer.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addItem(Object newObj) {
		MetaItem newMetaItem = (MetaItem)newObj;
        MetaItem curMetaItem;
        
        for(int i = 0; i < ((DefaultComboBoxModel)dataModel).getSize(); i++) {
        	curMetaItem = (MetaItem)dataModel.getElementAt(i);
        	
        	int layerCompare = curMetaItem.getLayer().compareTo(newMetaItem.getLayer());
            
        	// Same Layer - start checking further ordering logic
            if(layerCompare == 0) { // if anObject less than or equal obj
            	// TODO: Also group by TYPE: ENEMY, NPC, REGULAR, ANIMATED, PORTAL, STATE (chest open/closed), SPAN (house), CONTAINER (chest)
            	int nameCompare = curMetaItem.toString().compareToIgnoreCase(newMetaItem.toString());
            	
            	if(nameCompare >= 0){
            		super.insertItemAt(newMetaItem, i);
                    return;
            	}
            }
            else if(layerCompare > 0){
            	super.insertItemAt(newMetaItem, i);
                return;
            }
        }

        super.addItem(newMetaItem);
    }
}
