package main.java.epiccrawl.designer.designerComponent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.epiccrawl.database.Database;

@SuppressWarnings("serial")
public class PortalInputPanel extends JFrame{
	private JPanel optionPanel;
	@SuppressWarnings("rawtypes")
	private JComboBox levelNameField;
	private JTextField xField, yField;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PortalInputPanel(){
		levelNameField = new JComboBox(Database.getInstance().getSavedLevelNames().toArray());
		levelNameField.setEditable(true);
		xField = new JTextField(3);
		yField = new JTextField(3);
		
		optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));
		optionPanel.add(new JLabel("Level Name To Load:"));
		optionPanel.add(levelNameField);
		optionPanel.add(Box.createHorizontalStrut(15)); // a spacer
		optionPanel.add(new JLabel("X Coordinate:"));
		optionPanel.add(xField);
		optionPanel.add(Box.createHorizontalStrut(15)); // a spacer
		optionPanel.add(new JLabel("Y Coordinate:"));
		optionPanel.add(yField);
		optionPanel.setVisible(true);
		
		this.setDefaultCloseOperation( EXIT_ON_CLOSE );
		this.setVisible(false);
	}
	
	public int showOptionPanel(){
		this.pack();
        
      return JOptionPane.showOptionDialog(this, optionPanel, "Enter Portal Information",
                JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateLevelList(){
		levelNameField.setModel(new DefaultComboBoxModel(Database.getInstance().getSavedLevelNames().toArray()));
	}
	
	public void setX(String x){	xField.setText(x);}
	
	public void setY(String y){ yField.setText(y);}
	
	public void setLevel(String level){	levelNameField.setSelectedItem(level);}
	
	public String getLevelName(){ return (String)(levelNameField.getSelectedItem());}
	
	public int getX(){ 
		try{ return Integer.parseInt(xField.getText());}
		catch(Exception e){ return -1;}
	}
	
	public int getY(){ 
		try{ return Integer.parseInt(yField.getText());}
		catch(Exception e){ return -1;}
	}
	
	public boolean isValid(){
		try{
			int x = Integer.parseInt(xField.getText());
			int y = Integer.parseInt(yField.getText());
			
			if(x < 0 || y < 0) return false; // TODO: Check upper bound
		}
		catch(Exception e){
			// TODO: Possibly warn the user of bad input...
			return false;
		}
		
		if(levelNameField.getSelectedItem().toString().equals("")) return false;
		
		return true;
	}
}
