package epiccrawl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private JFrame mainFrame; // Frame that holds the entire game
	private JPanel curPanel;
	//private Home home;
	//private ModeManager modeManager;
	
	public Main(){
		mainFrame = new JFrame("Epic Crawl");
		
		final HomePanel home = new HomePanel();
		
		JButton designerToHome = new JButton("Main Screen");
		designerToHome.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				Main.this.setPanel(home);
			}
		});
		
		final DesignerPanel designer = new DesignerPanel(designerToHome);
		setPanel(home);
		
		// create a button, set its action performed, pass it into whatever 
		JButton homeToDesigner = new JButton("Level Designer");
		homeToDesigner.setBackground(Color.RED);
		//homeToDesigner.setForeground(Color.RED);
		homeToDesigner.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent aE){
				Main.this.setPanel(designer);
			}
		});
		
		home.addButton(homeToDesigner);
		
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
	
	public void setPanel(JPanel panel){
		if(curPanel != null){
			curPanel.setVisible(false);
			mainFrame.remove(curPanel);
		}
		
		panel.setVisible(true);
		mainFrame.add(panel);
		curPanel = panel;
		//mainFrame.pack();
		mainFrame.repaint();
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Main main = new Main();
	}
}
