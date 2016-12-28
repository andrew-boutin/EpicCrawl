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
	
	@SuppressWarnings("unchecked")
	@Override
	public void addItem(Object anObject) {
        int size = ((DefaultComboBoxModel) dataModel).getSize();
        Object obj;
        boolean added = false;
        for (int i = 0; i < size; i++) {
            obj = dataModel.getElementAt(i);
            int compare = anObject.toString().compareToIgnoreCase(obj.toString());
            if (compare <= 0) { // if anObject less than or equal obj
                super.insertItemAt(anObject, i);
                added = true;
                break;
            }
        }
 
        if (!added) {
            super.addItem(anObject);
        }
    }
}
