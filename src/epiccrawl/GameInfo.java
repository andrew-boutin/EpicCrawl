package epiccrawl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import epiccrawl.designer.ObjectMapItem;

// Contains important relationships of information for the game
public final class GameInfo { // Final so it can't be extended
	public static int rows = 50, cols = 50;
    
    private GameInfo(){} // Can't instantiate
    
    public static ObjectMapItem makeObjectMapItem(int key){
	    Object[] value = objectMap.get(key);
	    return(new ObjectMapItem(key, (String)value[0], (String)value[1], (String)value[2], 
	    													(Layer)value[3], (Type)value[4]));
    }
}