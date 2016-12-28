package epiccrawl.designer.designerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class RadioButton extends JRadioButton{
	private OptionListener listener;
	private int identifier;

	public RadioButton(String label, boolean isSelected, int id, OptionListener rbl){
		this.setText(label);
		this.setSelected(isSelected);
		listener = rbl;
		identifier = id;

		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				if(listener != null)
					listener.radioButtonPressed(identifier);
				else
					System.out.println("No Listener: mouseReleased");
			}
		});
	}
}
