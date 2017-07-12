/**
 * Database utility functionality that won't be used by the running game.
 * Manual admin capabilities.
 */

package epiccrawl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteException;

public class ManualUtility{
	private static final String makeItemMetaTable = "CREATE TABLE IF NOT EXISTS " + Database.itemMetaTable + 
			                                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, " +
			                                        "images TEXT, types TEXT, layer TEXT)",
			                    makeLevelsTable   = "CREATE TABLE IF NOT EXISTS " + Database.levelsTable + 
			                                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, " +
			                    		            "data BLOB)";

	// ID, NAME, IMAGES, TYPE, LAYER
	// TODO: Check for ones that should have multiple types
	// TODO: Add in ONWALL later on & separate out the WALL items
	// TODO: Could have all doors be portals - and a flag on the portal on whether or not it's just a 'decorative' door
	private static final String[][] itemMeta = {
		{"Void", "void.png", "REGULAR", "VOID"}, // Void should be first to assure ID 0
		{"Bar Keep", "barkeep.png", "NPC", "ONGROUND"},
		{"Single Barrel", "barrel1.png", "CONTAINER", "ONGROUND"},
		{"Double Barrel", "barrel2.png", "CONTAINER", "ONGROUND"},
		{"Triple Barrel", "barrel3.png", "CONTAINER", "ONGROUND"},
		{"Bed", "bedLeft.png,bedRight.png", "SPAN", "ONGROUND"},
		{"Cage", "cage.png", "REGULAR", "ONGROUND"},
		{"Chair Facing Left", "chairLeftFacing.png", "REGULAR", "ONGROUND"}, 
		{"Chair Facing Right", "chairRightFacing.png", "REGULAR", "ONGROUND"}, 
		{"Chest", "chest.png,chestopen.png", "CONTAINER,STATE", "ONGROUND"},
		{"Ornate Chest", "chestornate.png,chestornateopen.png", "CONTAINER,STATE", "ONGROUND"}, 
		{"Single Crate", "crate1.png", "CONTAINER", "ONGROUND"}, 
		{"Double Crate", "crate2.png", "CONTAINER", "ONGROUND"}, 
		{"Double Crate & Single Barrel", "crate2barrel.png", "CONTAINER", "ONGROUND"}, 
		{"Dead Guy 1", "deadguy1.png", "CONTAINER", "ONGROUND"}, 
		{"Dead Guy 2", "deadguy2.png", "CONTAINER", "ONGROUND"}, 
		{"Dirt", "dirt.png", "REGULAR", "GROUND"},
		{"Door Plain", "doorInsideToInside.png", "REGULAR", "WALL"}, // WALL vs GROUND...?
		{"Door Inside To Outside", "doorInsideToOutside.png", "PORTAL", "WALL"},
		{"Door Wood Sides", "doorOutsideToInside.png", "PORTAL", "WALL"}, 
		{"Door Stone Sides", "dungeonwooddoor.png", "REGULAR", "WALL"},
		{"Bull", "enemyBull.png", "ENEMY", "ONGROUND"},
		{"Rat", "enemyRat.png", "REGULAR", "ONGROUND"},
		{"Skeleton", "enemyskeleton.png", "REGULAR", "ONGROUND"},
		{"Flaming Brazier 1", "flamingBrazier1.png,flamingBrazier1a.png", "ANIMATED", "ONGROUND"},
		{"Flaming Brazier 2", "flamingBrazier2.png,flamingBrazier2a.png", "ANIMATED", "ONGROUND"},
		{"Stone Floor", "floorStone.png", "REGULAR", "GROUND"},
		{"Light Female In Dress", "girl.png", "NPC", "ONGROUND"},
		{"Dark Female In Dress", "girl0.png", "NPC", "ONGROUND"},
		{"Light Female In Skirt", "girl1.png", "NPC", "ONGROUND"},
		{"Dark Female In Skirt", "girl2.png", "NPC", "ONGROUND"},
		{"Grass", "grass.png", "REGULAR", "GROUND"},
		{"House", "houser0c0.png,houser0c1.png,houser0c2.png,houser0c3.png,houser0c4.png,houser1c0.png,houser1c1.png,houser1c2.png," + 
		          "houser1c3.png,houser1c4.png,houser2c0.png,houser2c1.png,houser2c2.png,houser2c3.png,houser2c4.png", "SPAN", "ONGROUND"},
		{"Tree With Bushed On The Left", "ltree1.png,ltree2.png,ltree3.png", "ANIMATED", "ONGROUND"},
		{"Portal", "portal1.png,portal2.png", "PORTAL,ANIMATED", "ONGROUND"},
		{"Rock", "rock.png", "REGULAR", "ONGROUND"},
		{"Tree With Bushes On The Right", "rtree1.png,rtree2.png,rtree3.png", "ANIMATED", "ONGROUND"},
		{"Sewer Grate", "sewergrate.png", "REGULAR", "WALL"}, // TODO: 
		{"Sign", "sign.png", "REGULAR", "ONGROUND"}, // TODO: Let signs have text w/ popups
		{"Table", "table.png", "REGULAR", "ONGROUND"},
		{"Table With Food", "tableWithFood.png", "REGULAR", "ONGROUND"},
		{"Tree With Bushes", "tbtree1.png,tbtree2.png,tbtree3.png", "ANIMATED", "ONGROUND"},
		{"Tree", "tree1.png,tree2.png,tree3.png", "ANIMATED", "ONGROUND"},
		{"Male Farmer", "villager1.png", "NPC", "ONGROUND"},
		{"Stone Wall", "wallStone.png", "REGULAR", "WALL"},
		{"Stone Wall With Torch", "wallStoneTorch1.png,wallStoneTorch2.png", "ANIMATED", "WALL"},
		{"Wood Wall", "wallwood.png", "REGULAR", "WALL"},
		{"Dark Wood Floor", "woodFloorDark.png", "REGULAR", "GROUND"},
		{"Light Wood Floor", "woodFloorLight.png", "REGULAR", "GROUND"},
		{"Wood Floor", "woodFloorMedium.png", "REGULAR", "GROUND"},
		{"Red Wood Floor", "woodFloorRed.png", "REGULAR", "GROUND"}};

	//{"Armored Hero", "characterArmor.png"},
	//{"Hero", "characterNoArmor.png"},
	//{"Armed Hero", "characterSwordAndShield.png"},
	//{"heroDown.png"},
	//{"heroine.png"},
	//{"heroineweapon.png"},
	//{"heroLeft.png"},
	//{"heroRight.png"},
	//{"heroUp.png"},
	//{"epicCrawlerIcon.png"},
	//{"epicCrawlTitle.png"},
	//{"deadhero.png", "CONTAINER", "ONGROUND"}, 
	
	// TODO: Function that tests every ITEM META to make sure it's a valid object.
	// Check ID, NAME, TYPE, & LAYER values - see if an object can be created
	// Check if images exist

	private static void createTables(Statement statement) throws SQLException{
		statement.executeUpdate(makeItemMetaTable);
		statement.executeUpdate(makeLevelsTable);
	}

	private static void populateItemMetaTable(Statement statement) throws SQLException{
		for(String[] row: itemMeta){
			try{
				statement.executeUpdate("INSERT INTO " + Database.itemMetaTable + " (name, images, types, layer) VALUES('" + 
			                            row[0] + "', '" + row[1] + "', '" + row[2] + "', '" + row[3] + "')");
			}
			catch(SQLiteException e){
				if(e.toString().contains("SQLITE_CONSTRAINT_UNIQUE"))
					continue;
				else
					throw e;
			}
		}
	}
	
	private static void printItemMetaTable(Statement statement) throws SQLException{
		System.out.println("Item Meta Table");
		
		ResultSet resultSet = statement.executeQuery("SELECT * from " + Database.itemMetaTable);
		
		while(resultSet.next()){
			System.out.println("id " +  resultSet.getInt("id") + ", name " + resultSet.getString("name"));
		}
	}
	
	private static void printLevelsTable(Statement statement) throws SQLException{
		System.out.println("Levels Table");
		
		ResultSet resultSet = statement.executeQuery("SELECT * from " + Database.levelsTable);
		
		while(resultSet.next()){
			System.out.println("id " +  resultSet.getInt("id") + ", name " + resultSet.getString("name"));
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//Database database = Database.getInstance();
		
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + Database.databaseName + ".db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(1);
			
			createTables(statement);
			populateItemMetaTable(statement);
			printItemMetaTable(statement);
			printLevelsTable(statement);
		}
		catch (SQLException e){
			throw e;
		} 
		finally{
			if(connection != null)
				connection.close();
		}
	}
}
