package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import epiccrawl.GameInfo.Layer;
import epiccrawl.GameInfo.Type;

public class DataBaseManualUtility {
	private static final String databaseName = "epiccrawl",
                                imageTable   = "image",
                                objectTable  = "object",
                                primaryID    = "id INTEGER PRIMARY KEY AUTOINCREMENT",
                                uniqueName   = "name TEXT UNIQUE";
	
	private static final String makeImageTable = "CREATE TABLE IF NOT EXISTS " + imageTable + " (" + primaryID + ", " + uniqueName + ")",
			                    makeObjectTable = "CREATE TABLE IF NOT EXISTS " + objectTable + " (" + primaryID + ", " + uniqueName + ", FOREIGN KEY(image) REFERENCES image)";
	
	//tmpMap.put(0, new Object[]{"GameObject", "void.png", "Floor - Void", Layer.first, Type.regular});
	
	private static final String[] imageNames = {"barkeep.png", "barrel1.png", "barrel2.png", "barrel3.png", "bedLeft.png", "bedRight.png", "cage.png",
		"chairLeftFacing.png", "chairRightFacing.png", "characterArmor.png", "characterNoArmor.png", "characterSwordAndShield.png", "chest.png", "chestopen.png",
		"chestornate.png", "chestornateopen.png", "crate1.png", "crate2.png", "crate2barrel.png", "deadguy1.png", "deadguy2.png", "deadhero.png", "dirt.png",
		"doorInsideToInside.png", "doorInsideToOutside.png", "doorOutsideToInside.png", "dungeonwooddoor.png", "enemyBull.png", "enemyRat.png", "enemyskeleton.png",
		"epicCrawlerIcon.png", "epicCrawlTitle.png", "flamingBrazier1.png", "flamingBrazier1a.png", "flamingBrazier2.png", "flamingBrazier2a.png", "floorStone.png",
		"girl.png", "girl0.png", "girl1.png", "girl2.png", "grass.png", "heroDown.png", "heroine.png", "heroineweapon.png", "heroLeft.png", "heroRight.png", "heroUp.png",
		"houser0c0.png", "houser0c1.png", "houser0c2.png", "houser0c3.png", "houser0c4.png", "houser1c0.png", "houser1c1.png", "houser1c2.png", "houser1c3.png",
		"houser1c4.png", "houser2c0.png", "houser2c1.png", "houser2c2.png", "houser2c3.png", "houser2c4.png", "ltree1.png", "ltree2.png", "ltree3.png", "portal1.png",
		"portal2.png", "rock.png", "rtree1.png", "rtree2.png", "rtree3.png", "sewergrate.png", "sign.png", "table.png", "tableWithFood.png", "tbtree1.png", "tbtree2.png",
		"tbtree3.png", "tree1.png", "tree2.png", "tree3.png", "villager1.png", "void.png", "wallStone.png", "wallStoneTorch1.png", "wallStoneTorch2.png", "wallwood.png",
		"woodFloorDark.png", "woodFloorLight.png", "woodFloorMedium.png", "woodFloorRed.png"};
	
	private static void populateImageTable(Statement statement) throws SQLException{
		for(String image: imageNames)
			statement.executeUpdate("INSERT INTO " + imageTable + " (name) VALUES('" + image + "')");
	}
	
	private static void printImageTable(Statement statement) throws SQLException{
		ResultSet resultSet = statement.executeQuery("SELECT * from " + imageTable);
		while(resultSet.next()) {
			// iterate & read the result set
			System.out.println("id " +  resultSet.getInt("id") + ", name " + resultSet.getString("name"));
		}
	}
	
	private static void createTables(Statement statement) throws SQLException{
		statement.executeUpdate(makeImageTable);
		statement.executeUpdate(makeObjectTable);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(1);
			
			createTables(statement);
			populateImageTable(statement);
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
