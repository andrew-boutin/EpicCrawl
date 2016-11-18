package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteException;

public class ManualUtility{
	private static final String makeItemMetaTable = "CREATE TABLE IF NOT EXISTS " + Database.itemMetaTable + 
			                                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE," +
			                                        "images TEXT, type TEXT, layer TEXT)";
	
	// ID, NAME, IMAGES, TYPE, LAYER
	// TODO: Check for ones that should have multiple types
	// TODO: Add in ONWALL later on & separate out the WALL items
	private static final String[][] itemMeta = {
		{"Bar Keep", "barkeep.png", "NPC", "ONGROUND"},
		{"Single Barrel", "barrel1.png", "CONTAINER", "ONGROUND"},
		{"Double Barrel", "barrel2.png", "CONTAINER", "ONGROUND"},
		{"Triple Barrel", "barrel3.png", "CONTAINER", "ONGROUND"},
		{"Bed Left", "bedLeft.png", "REGULAR", "ONGROUND"}, 
		{"Bed Right", "bedRight.png", "REGULAR", "ONGROUND"}, 
		{"Cage", "cage.png", "REGULAR", "ONGROUND"},
		{"Chair Facing Left", "chairLeftFacing.png", "REGULAR", "ONGROUND"}, 
		{"Chair Facing Right", "chairRightFacing.png", "REGULAR", "ONGROUND"}, 
		//{"Armored Hero", "characterArmor.png"}, // TODO:
		//{"Hero", "characterNoArmor.png"}, // TODO:
		//{"Armed Hero", "characterSwordAndShield.png"}, // TODO:
		//{"chest.png"},
		//{"chestopen.png"},
		{"Ornate Chest", "{chestornate.png,chestornateopen.png}", "CONTAINER,STATE", "ONGROUND"}, 
		//{"crate1.png", "CONTAINER", "ONGROUND"}, 
		//{"crate2.png", "CONTAINER", "ONGROUND"}, 
		//{"crate2barrel.png", "CONTAINER", "ONGROUND"}, 
		//{"deadguy1.png", "CONTAINER", "ONGROUND"}, 
		//{"deadguy2.png", "CONTAINER", "ONGROUND"}, 
		//{"deadhero.png", "CONTAINER", "ONGROUND"}, 
		//{"dirt.png"},
		//{"doorInsideToInside.png"}, 
		//{"doorInsideToOutside.png"},
		//{"doorOutsideToInside.png"}, 
		//{"dungeonwooddoor.png"},
		{"Bull", "enemyBull.png", "ENEMY", "ONGROUND"},
		//{"enemyRat.png"},
		//{"enemyskeleton.png"},
		//{"epicCrawlerIcon.png"},
		//{"epicCrawlTitle.png"},
		//{"flamingBrazier1.png"},
		//{"flamingBrazier1a.png"},
		//{"flamingBrazier2.png"},
		//{"flamingBrazier2a.png"},
		//{"floorStone.png"},
		//{"girl.png"},
		//{"girl0.png"},
		//{"girl1.png"},
		//{"girl2.png"},
		//{"grass.png"},
		//{"heroDown.png"},
		//{"heroine.png"},
		//{"heroineweapon.png"},
		//{"heroLeft.png"},
		//{"heroRight.png"},
		//{"heroUp.png"},
		//{"houser0c0.png"},
		//{"houser0c1.png"},
		//{"houser0c2.png"},
		//{"houser0c3.png"},
		//{"houser0c4.png"},
		//{"houser1c0.png"},
		//{"houser1c1.png"},
		//{"houser1c2.png"},
		//{"houser1c3.png"},
		//{"houser1c4.png"},
		//{"houser2c0.png"},
		//{"houser2c1.png"},
		//{"houser2c2.png"},
		//{"houser2c3.png"},
		//{"houser2c4.png"},
		//{"ltree1.png"},
		//{"ltree2.png"},
		//{"ltree3.png"},
		{"Portal", "{portal1.png,portal2.png}", "PORTAL,ANIMATED", "ONGROUND"},
		//{"rock.png"},
		{"Tree With Bushes On The Right", "{rtree1.png,rtree2.png,rtree3.png}", "ANIMATED", "ONGROUND"},
		{"Sewer Grate", "sewergrate.png", "REGULAR", "WALL"}, // TODO: 
		//{"sign.png"},
		//{"table.png"},
		//{"tableWithFood.png"},
		{"Tree With Bushes", "{tbtree1.png,tbtree2.png,tbtree3.png}", "ANIMATED", "ONGROUND"},
		{"Tree", "{tree1.png,tree2.png,tree3.png}", "ANIMATED", "ONGROUND"},
		{"Male Farmer", "villager1.png", "NPC", "ONGROUND"},
		{"Void", "void.png", "REGULAR", "VOID"},
		{"Stone Wall", "wallStone.png", "REGULAR", "WALL"},
		{"Stone Wall With Torch", "{wallStoneTorch1.png,wallStoneTorch2.png}", "ANIMATED", "WALL"},
		{"Wood Wall", "wallwood.png", "REGULAR", "WALL"},
		{"Dark Wood Floor", "woodFloorDark.png", "REGULAR", "GROUND"},
		{"Light Wood Floor", "woodFloorLight.png", "REGULAR", "GROUND"},
		{"Wood Floor", "woodFloorMedium.png", "REGULAR", "GROUND"},
		{"Red Wood Floor", "woodFloorRed.png", "REGULAR", "GROUND"}};
	
	// TODO: Function that tests every ITEM META to make sure it's a valid object.
	// Check ID, NAME, TYPE, & LAYER values - see if an object can be created
	// Check if images exist
	
	private static void createItemMetaTable(Statement statement) throws SQLException{
		statement.executeUpdate(makeItemMetaTable);
	}
	
	private static void populateItemMetaTable(Statement statement) throws SQLException{
		for(String[] row: itemMeta){
			try{
				statement.executeUpdate("INSERT INTO " + Database.itemMetaTable + " (name, images, type, layer) VALUES('" + 
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
	
	private static void printImageTable(Statement statement) throws SQLException{
		ResultSet resultSet = statement.executeQuery("SELECT * from " + Database.itemMetaTable);
		
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
			
			createItemMetaTable(statement);
			populateItemMetaTable(statement);
			printImageTable(statement);
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
