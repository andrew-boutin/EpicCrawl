package main.java.epiccrawl;

import javax.swing.JPanel;

public class ModeManager {
	private JPanel designerPanel, homePanel;
	private Main main;
	//private GameMode homeMode, designerMode, gameMode;
	
	public ModeManager(Main main, JPanel home, JPanel designer){
		designerPanel = designer;
		homePanel = home;
		this.main = main;
	}
	
	public void startDesigner(){
		main.setPanel(designerPanel);
	}
	
	public void startHome(){
		main.setPanel(homePanel);
	}
	
	public void startGame(){
		
	}
}
