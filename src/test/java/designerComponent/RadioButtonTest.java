package designerComponent;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import epiccrawl.database.MetaItem;
import epiccrawl.designer.designerComponent.OptionListener;
import epiccrawl.designer.designerComponent.RadioButton;

public class RadioButtonTest{
    final String correctLabel = "rb";
    final int correctID = 1;
    final boolean correctSelected = false;
    
    String label;
    int id;
    boolean selected;
    
    @Before
    public void setUp(){
        label = correctLabel;
        id = correctID;
        selected = correctSelected;
    }
    
    @Test
    public void radioButtonSignalsOnClickTest(){
        // Create an option listener to verify that the radio button signals back on an event
        OptionListener ol = new OptionListener(){
            @Override
            public void radioButtonPressed(int inputID){
                // Verify that the correct id is passed when the radio button signals
                if(id != inputID)
                    fail();
                
                // Increment so we can verify that this was reached
                id++;
            }
            
            @Override
            public void comboBoxItemSelected(int comboBoxID, MetaItem metaItem){}
        };
        
        // Verify the radio button signals to the listener with the correct id
        assertEquals(id, correctID);
        RadioButton rb = new RadioButton(label, selected, id, ol);
        rb.doClick();
        assertEquals(id, (correctID + 1));
    }
}
