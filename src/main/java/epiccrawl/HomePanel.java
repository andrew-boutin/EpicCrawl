package main.java.epiccrawl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HomePanel extends JPanel{
	private JButton startGameB, optionsB;
	private JPanel optionsPanel;
	
	public HomePanel(){
		makePanel();
		makeLabel();
		makeImageAndOptions();
		setUpButtons();
	}
	
	private void makePanel(){
		this.setLayout(new BorderLayout());
	    Dimension dim = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
	                                  (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60);
	    this.setPreferredSize(dim);
	    this.setBackground(Color.BLACK);
	}
	
	private void makeLabel(){
		JLabel gameLabel = new JLabel("Rigid Link Studios");
	    gameLabel.setFont(new Font("Serif", Font.BOLD, 48));
	    gameLabel.setHorizontalAlignment(JLabel.CENTER);
	    gameLabel.setForeground(Color.RED);
	    this.add(gameLabel, BorderLayout.NORTH);
	}
	
	private void makeImageAndOptions(){
		JPanel mainScreenImageAndOptionsPanel = new JPanel();
	    mainScreenImageAndOptionsPanel.setBackground(Color.BLACK);
	    mainScreenImageAndOptionsPanel.setLayout(new BorderLayout());
	    mainScreenImageAndOptionsPanel.add(makeOptions(), BorderLayout.NORTH);
	    //mainScreenImageAndOptionsPanel.add(mainScreenImageLabel, BorderLayout.CENTER);
	    this.add(mainScreenImageAndOptionsPanel, BorderLayout.CENTER);
	}
	
	private JPanel makeOptions(){
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new FlowLayout());
		optionsPanel.setBackground(Color.BLACK);
		
		startGameB = new JButton("Start Game");
		startGameB.setBackground(Color.RED);
		optionsB = new JButton("Options");
		optionsB.setBackground(Color.RED);
		
		optionsPanel.add(startGameB);
		optionsPanel.add(optionsB);
		optionsPanel.setVisible(true);
		return optionsPanel;
	}
	
	private void setUpButtons(){
		startGameB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
            	JOptionPane.showMessageDialog(null, "Game play coming soon!", "Start Game Info", JOptionPane.INFORMATION_MESSAGE);
            }
		});
		
		optionsB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
            	showInfoMessage();
            }
		});
	}
	
	private void showInfoMessage(){
		String infoMessage;
		try {
			infoMessage = GameUtility.readFile("Text/HomeInfo.txt");
		} catch (IOException e) {
			infoMessage = "Error reading main screen info message file!";
		}
		
		JOptionPane.showMessageDialog(null, infoMessage, "Epic Crawl Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void addButton(JButton button){
		optionsPanel.add(button);
	}
}
