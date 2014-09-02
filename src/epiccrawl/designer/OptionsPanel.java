package epiccrawl.designer;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

import epiccrawl.GameInfo;
import epiccrawl.designer.designerComponent.AdminListener;
import epiccrawl.designer.designerComponent.Button;
import epiccrawl.designer.designerComponent.ComboBox;
import epiccrawl.designer.designerComponent.OptionListener;
import epiccrawl.designer.designerComponent.RadioButton;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel{
	private  AdminListener adminClient;
	private OptionListener optionClient;
	private ComboBox tilesComboBox;
	private Button save, load, clear, info;
	private ButtonGroup radioButtonGroup;
	private JPanel buttonPanel;
	
	public OptionsPanel(AdminListener adminListener, OptionListener optionListener){
		adminClient = adminListener;
		optionClient = optionListener;
		
		this.setLayout(new FlowLayout());
		
		makeRadioButton();
		makeComboBoxes();
		makeButtons();		
	}
	
	private void makeRadioButton(){
		RadioButton single   = new RadioButton("Single", true, 0, optionClient);
		RadioButton fill    = new RadioButton("Fill", false, 1, optionClient);
		RadioButton edit = new RadioButton("Edit", false, 2, optionClient);

		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(single);
		radioButtonGroup.add(fill);
		radioButtonGroup.add(edit);

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(1, 3));
		radioPanel.add(single);
		radioPanel.add(fill);
		radioPanel.add(edit);

		radioPanel.setBorder(BorderFactory.createTitledBorder(
		           BorderFactory.createEtchedBorder(), "Options"));
		
		this.add(radioPanel);
	}
	
	private void makeButtons(){
		save = new Button("Save", 1, adminClient);
		save.setVisible(true);
		
		load = new Button("Load", 2, adminClient);
		load.setVisible(true);
		
		clear = new Button("Clear", 3, adminClient);
		clear.setVisible(true);
		
		info = new Button("Info", 4, adminClient);
		info.setVisible(true);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 4));
		buttonPanel.add(save);
		buttonPanel.add(load);
		buttonPanel.add(clear);
		buttonPanel.add(info);

		buttonPanel.setBorder(BorderFactory.createTitledBorder(
		           BorderFactory.createEtchedBorder(), "Admin"));
		
		this.add(buttonPanel);
	}
	
	public void addButton(JButton button){
		buttonPanel.add(button);
	}

	private void makeComboBoxes(){
		tilesComboBox = new ComboBox(0, optionClient);
		tilesComboBox.setMaximumRowCount(40);
		tilesComboBox.setBorder(BorderFactory.createTitledBorder(
		           BorderFactory.createEtchedBorder(), "Tiles"));
		
		for (Integer key : GameInfo.objectMap.keySet())
			tilesComboBox.addItem(GameInfo.makeObjectMapItem(key));
		
		this.add(tilesComboBox);
	}
}
