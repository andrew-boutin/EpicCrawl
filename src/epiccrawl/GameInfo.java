package epiccrawl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import epiccrawl.designer.ObjectMapItem;

// Contains important relationships of information for the game
public final class GameInfo { // Final so it can't be extended
	public static int rows = 50, cols = 50;
	public enum Type{ regular, span, animated, multi, portal}
	public enum Layer{ first, firstBase, second}
	
	public static final Map<Integer, Object[]> objectMap; // Easy way to share info across system parts
    static { // ------------------ Class Name, Image Name, Description, Placement Logic ID
        Map<Integer, Object[]> tmpMap = new HashMap<Integer, Object[]>();
        tmpMap.put(0, new Object[]{"GameObject", "void.png", "Floor - Void", Layer.first, Type.regular});
        tmpMap.put(1, new Object[]{"GameObject", "dirt.png", "Floor - Dirt", Layer.firstBase, Type.regular});
        tmpMap.put(2, new Object[]{"GameObject", "grass.png", "Floor - Grass", Layer.firstBase, Type.regular});
        tmpMap.put(3, new Object[]{"GameObject", "floorStone.png", "Floor - Stone", Layer.firstBase, Type.regular});
        tmpMap.put(4, new Object[]{"GameObject", "woodFloorDark.png", "Floor - Wood, Dark", Layer.firstBase, Type.regular});
        tmpMap.put(5, new Object[]{"GameObject", "woodFloorLight.png", "Floor - Wood, Light", Layer.firstBase, Type.regular});
        tmpMap.put(6, new Object[]{"GameObject", "woodFloorMedium.png", "Floor - Wood, Medium", Layer.firstBase, Type.regular});
        tmpMap.put(7, new Object[]{"GameObject", "woodFloorRed.png", "Floor - Wood, Red", Layer.firstBase, Type.regular});
        tmpMap.put(9, new Object[]{"GameObject", "wallwood.png", "Wall - Wood", Layer.first, Type.regular});
        tmpMap.put(10, new Object[]{"AnimatedObject", "wallStoneTorch1.png", "Wall - Stone w/ Torch", Layer.first, Type.animated}); // Animated, multi image
        tmpMap.put(11, new Object[]{"GameObject", "sewergrate.png", "Wall - Stone w/ Sewer Grate", Layer.first, Type.regular});
        tmpMap.put(12, new Object[]{"GameObject", "wallStone.png", "Wall - Stone", Layer.first, Type.regular});
        tmpMap.put(13, new Object[]{"GameObject", "rock.png", "Misc - Rock", Layer.second, Type.regular});
        tmpMap.put(14, new Object[]{"GameObject", "sign.png", "Misc - Sign Post", Layer.second, Type.regular});
        tmpMap.put(15, new Object[]{"GameObject", "barrel1.png", "Misc - Barrel - 1", Layer.second, Type.regular});
        tmpMap.put(16, new Object[]{"GameObject", "barrel2.png", "Misc - Barrel - 2", Layer.second, Type.regular});
        tmpMap.put(17, new Object[]{"GameObject", "barrel3.png", "Misc - Barrel - 3", Layer.second, Type.regular});
        tmpMap.put(18, new Object[]{"GameObject", "crate1.png", "Misc - Crate - 1", Layer.second, Type.regular});
        tmpMap.put(19, new Object[]{"GameObject", "crate2.png", "Misc - Crate - 2", Layer.second, Type.regular});
        tmpMap.put(20, new Object[]{"GameObject", "crate2barrel.png", "Misc - Crate/Barrel", Layer.second, Type.regular});
        tmpMap.put(21, new Object[]{"GameObject", "bedLeft.png", "Misc - Bed", Layer.second, Type.span}); // Multi tile object
        //tmpMap.put(22, new Object[]{"GameObject", "bedLeftCat.png", "Misc - Bed w/ Cat (Left Half)", 2});
        //tmpMap.put(23, new Object[]{"GameObject", "bedRight.png", "Misc - Bed (Right Half)", 2});
        tmpMap.put(24, new Object[]{"GameObject", "chairLeftFacing.png", "Misc - Chair, Facing Left", Layer.second, Type.regular});
        tmpMap.put(25, new Object[]{"GameObject", "chairRightFacing.png", "Misc - Chair, Facing Right", Layer.second, Type.regular});
        tmpMap.put(26, new Object[]{"GameObject", "houser0c0.png", "Misc - House", Layer.second, Type.span}); // Multi tile object
        tmpMap.put(27, new Object[]{"AnimatedObject", "ltree1.png", "Misc - Tree - Bushes Half on Left", Layer.second, Type.animated}); // Animated, multi image
        tmpMap.put(28, new Object[]{"AnimatedObject", "rtree1.png", "Misc - Tree - Bushes Half on Right", Layer.second, Type.animated}); // Animated, multi image
        tmpMap.put(29, new Object[]{"AnimatedObject", "tbtree1.png", "Misc - Tree - Bush Half on Both", Layer.second, Type.animated}); // Animated, multi image
        tmpMap.put(30, new Object[]{"AnimatedObject", "tree1.png", "Misc - Tree", Layer.second, Type.animated}); // Animated, multi image
        tmpMap.put(31, new Object[]{"GameObject", "table.png", "Misc - Table", Layer.second, Type.regular});
        tmpMap.put(32, new Object[]{"GameObject", "tableWithFood.png", "Misc - Table w/ Food", Layer.second, Type.regular});
        tmpMap.put(33, new Object[]{"GameObject", "cage.png", "Misc - Cage", Layer.second, Type.regular});
        tmpMap.put(34, new Object[]{"GameObject", "deadguy1.png", "Misc - Dead Guy - Bloody", Layer.second, Type.regular});
        tmpMap.put(35, new Object[]{"GameObject", "deadguy2.png", "Misc - Dead Guy - Not Bloody", Layer.second, Type.regular});
        tmpMap.put(36, new Object[]{"AnimatedObject", "flamingBrazier1.png", "Misc - Small Brazier", Layer.second, Type.animated}); // Animated, multi image
        tmpMap.put(37, new Object[]{"AnimatedObject", "flamingBrazier2.png", "Misc - Large Brazier", Layer.second, Type.animated}); // Animated, multi image
        tmpMap.put(38, new Object[]{"Container", "chest.png", "Misc - Chest - Regular", Layer.second, Type.multi});
        tmpMap.put(39, new Object[]{"Container", "chestornate.png", "Misc - Chest - Ornate", Layer.second, Type.multi});
        tmpMap.put(40, new Object[]{"Enemy", "enemyBull.png", "Enemy - Bull", Layer.second, Type.regular});
        tmpMap.put(41, new Object[]{"Enemy", "enemyRat.png", "Enemy - Rat", Layer.second, Type.regular});
        tmpMap.put(42, new Object[]{"Enemy", "enemyskeleton.png", "Enemy - Skeleton", Layer.second, Type.regular});
        tmpMap.put(43, new Object[]{"NPC", "girl.png", "NPC - Girl - Yellow", Layer.second, Type.regular});
        tmpMap.put(44, new Object[]{"NPC", "girl0.png", "NPC - Girl - Green", Layer.second, Type.regular});
        tmpMap.put(45, new Object[]{"NPC", "girl1.png", "NPC - Girl - Red", Layer.second, Type.regular});
        tmpMap.put(46, new Object[]{"NPC", "girl2.png", "NPC - Girl - Blue", Layer.second, Type.regular});
        tmpMap.put(47, new Object[]{"NPC", "barkeep.png", "NPC - Guy - Barkeep", Layer.second, Type.regular});
        tmpMap.put(48, new Object[]{"NPC", "villager1.png", "NPC - Guy - Pitchfork", Layer.second, Type.regular});
        tmpMap.put(49, new Object[]{"Portal", "doorInsideToOutside.png", "Door, to Exterior", Layer.second, Type.portal});
        tmpMap.put(50, new Object[]{"Portal", "doorOutsideToInside.png", "Door, to Interior", Layer.second, Type.portal});
        tmpMap.put(51, new Object[]{"GameObject", "dungeonwooddoor.png", "Door, Inside, Stone Wall", Layer.second, Type.regular});
        tmpMap.put(52, new Object[]{"Portal", "portal1.png", "Door - Portal", Layer.second, Type.portal}); // Animated, multi image
        objectMap = Collections.unmodifiableMap(tmpMap);
    }
    
    public static final Map<Integer, String[]> multiImageMap;
    static {
        Map<Integer, String[]> tmpMap = new HashMap<Integer, String[]>();
        // Placement Logic ID
        tmpMap.put(26, new String[]{"houser0c0.png", "houser0c1.png", "houser0c2.png", "houser0c3.png", "houser0c4.png",
        									  "houser1c0.png", "houser1c1.png", "houser1c2.png", "houser1c3.png", "houser1c4.png",
        									  "houser2c0.png", "houser2c1.png", "houser2c2.png", "houser2c3.png", "houser2c4.png"});
        tmpMap.put(21, new String[]{"bedLeft.png", "bedRight.png"});
        multiImageMap = Collections.unmodifiableMap(tmpMap);
    }
    
    public static final Map<Integer, String[]> animatedImageMap;
    static{
    	Map<Integer, String[]> tmpMap = new HashMap<Integer, String[]>();
    	tmpMap.put(10, new String[]{"wallStoneTorch1.png", "wallStoneTorch2.png"});
    	tmpMap.put(27, new String[]{"ltree1.png", "ltree2.png", "ltree3.png"});
        tmpMap.put(28, new String[]{"rtree1.png", "rtree2.png", "rtree3.png"});
        tmpMap.put(29, new String[]{"tbtree1.png", "tbtree2.png", "tbtree3.png"});
        tmpMap.put(30, new String[]{"tree1.png", "tree2.png", "tree3.png"});
        tmpMap.put(36, new String[]{"flamingBrazier1.png", "flamingBrazier1a.png"});
        tmpMap.put(37, new String[]{"flamingBrazier2.png", "flamingBrazier2a.png"});
        tmpMap.put(52, new String[]{"Portal", "portal1.png", "portal2.png"});
        
    	animatedImageMap = Collections.unmodifiableMap(tmpMap);
    }
    
    private GameInfo(){} // Can't instantiate
    
    public static ObjectMapItem makeObjectMapItem(int key){
	    Object[] value = objectMap.get(key);
	    return(new ObjectMapItem(key, (String)value[0], (String)value[1], (String)value[2], 
	    													(Layer)value[3], (Type)value[4]));
    }
}