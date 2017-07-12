package epiccrawl.designer.designerComponent;

import epiccrawl.database.MetaItem;

// When buttons are pressed they will use this interface to let the listener know they were pressed
//When a combo box has an item selected - it will let the listener know the id of the combo box
//and key of the object in the GameInfo objectMap
public interface OptionListener {
	void comboBoxItemSelected(int comboBoxID, MetaItem metaItem);
	void radioButtonPressed(int id);
}