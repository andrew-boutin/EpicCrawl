package epiccrawl;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import epiccrawl.designer.DesignerGrid;
import epiccrawl.designer.OptionsPanel;
import epiccrawl.designer.designerComponent.AdminListener;
import epiccrawl.designer.designerComponent.OptionListener;

@SuppressWarnings("serial")
public class DesignerPanel extends JPanel implements AdminListener{
	private OptionsPanel optionsPanel;
	private DesignerGrid designerGrid;
	
	public DesignerPanel(JButton homeButton){
		makePanel();
		
		designerGrid = new DesignerGrid();
		optionsPanel = new OptionsPanel((AdminListener)this, (OptionListener)designerGrid);
		optionsPanel.addButton(homeButton);
		
		this.add(designerGrid, BorderLayout.CENTER);
		this.add(optionsPanel, BorderLayout.SOUTH);
	}
	
	private void makePanel(){
		this.setLayout(new BorderLayout());
	    Dimension dim = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
	                                  (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60);
	    this.setPreferredSize(dim);
	}
	
	private void showInfoMessage(){
		String infoMessage;
		try {
			infoMessage = GameUtility.readFile("Text/DesignerInfo.txt");
		} catch (IOException e) {
			infoMessage = "Error reading level designer info message file!";
		}
		
		JOptionPane.showMessageDialog(null, infoMessage, "Designer Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void buttonPressed(int id) {
		switch(id){
		case 0:
			System.out.println("Main Menu");
			break;
		case 1:
			LevelParser.saveLevel(this, designerGrid.getGridObjects());
			break;
		case 2:
			LevelParser.loadLevelToDesigner(this, designerGrid.getGridObjects());
			repaint();
			break;
		case 3:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			designerGrid.clearGrid();
			this.setCursor(Cursor.getDefaultCursor());
			repaint();
			break;
		case 4:
			showInfoMessage();
			break;
		default:
			System.out.println("Error in buttonPressed");
		}
	}	
	
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Designer");
		
		DesignerPanel d = new DesignerPanel(new JButton("Main Screen"));
		mainFrame.add(d);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
		int width = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		mainFrame.setSize(width - 200, height - 200); // This makes the restore down button not make the frame very tiny
		mainFrame.setMinimumSize(new Dimension(800, 500));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
