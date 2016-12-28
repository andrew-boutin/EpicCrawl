package main.java.epiccrawl.designer.designerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Button extends JButton{
	private AdminListener listener;
	private int identifier;

	public Button(String label, int id, AdminListener bl){
		this.setText(label);
		listener = bl;
		identifier = id;

		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				if(listener != null)
					listener.buttonPressed(identifier);
				else
					System.out.println("No Listener: mouseReleased");
			}
		});
	}
}
