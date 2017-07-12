package epiccrawl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Database {
	private static Database instance;
	private Connection connection;
	private Statement statement; // TODO: Share this?
	
	public static final String databaseName  = "epiccrawl",
                               itemMetaTable = "itemMeta",
                               levelsTable   = "levels";

	private Database() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + Database.databaseName + ".db");
		statement = connection.createStatement();
		statement.setQueryTimeout(1);
	}
	
	public static Database getInstance(){
		if(instance == null){
			try{
				instance = new Database();
			}
			catch(Exception e){
				System.err.println("Failed to initialize database: " + e);
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		return instance;
	}
	
	public List<String> getSavedLevelNames(){
		List<String> levelNames = new ArrayList<String>();
		
		try{
			ResultSet resultSet = statement.executeQuery("SELECT name FROM " + Database.levelsTable);
			
			while(resultSet.next())
				levelNames.add(resultSet.getString("name"));
		}
		catch(SQLException e){
			System.err.println("Failed to get saved level names: " + e);
			e.printStackTrace();
		}
		
		return levelNames;
	}
	
	public boolean doesLevelNameExist(String levelName){
		try{
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM " + Database.levelsTable + " WHERE name = '" + levelName + "'");

			if(resultSet.getInt("total") == 0)
				return false;
		}
		catch(SQLException e){
			System.err.println("Failed to lookup if levelName already exists: " + e);
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void createLevel(String name, String data){
		try{
			statement.executeUpdate("INSERT INTO " + Database.levelsTable + " (name, data) VALUES('" + name + "', '" + data + "')");
		}
		catch(SQLException e){
			System.err.println("Failed to create level: " + e);
			e.printStackTrace();
		}
	}
	
	public void overWriteLevelData(String name, String levelData){
		try{
			statement.executeUpdate("UPDATE " + Database.levelsTable + " SET data = '" + levelData + "' WHERE name = '" + name + "'");
		}
		catch(SQLException e){
			System.err.println("Failed to get level data: " + e);
			e.printStackTrace();
		}
	}
	
	public void deleteLevel(String name){
		try{
			statement.executeUpdate("DELETE FROM " + Database.levelsTable + " WHERE name = '" + name + "'");
		}
		catch(SQLException e){
			System.err.println("Failed to get level data: " + e);
			e.printStackTrace();
		}
	}
	
	public String getLevelData(String name){
		String levelData = null;
		try{
			ResultSet rs = statement.executeQuery("SELECT data FROM " + Database.levelsTable + " WHERE name = '" + name + "'");
			
			if(rs.next())
				levelData = rs.getString("data");
		}
		catch(SQLException e){
			System.err.println("Failed to get level data: " + e);
			e.printStackTrace();
		}
		
		return levelData;
	}
	
	// TODO: Create new MetaItem in database
	
	public Map<Integer, MetaItem> createMetaItems(){
		Map<Integer, MetaItem> metaItemMap = new HashMap<Integer, MetaItem>();
		
		try{
			ResultSet resultSet = statement.executeQuery("SELECT * from " + Database.itemMetaTable);
		
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				List<String> images = convertDBImageListToActualImageList(resultSet.getString("images"));
				List<Type> types = convertDBTypesListToActualTypesList(resultSet.getString("types"));
				Layer layer = Layer.valueOf(resultSet.getString("layer"));

				MetaItem metaItem = new MetaItem(id, name, images, types, layer);
				metaItemMap.put(metaItem.getID(), metaItem);
			}
		}
		catch(Exception e){
			System.err.println("Error while creating Meta Items: " + e);
			e.printStackTrace();
			System.exit(-1);
		}

		return metaItemMap;
	}
	
	private List<String> convertDBImageListToActualImageList(String images){
		return new ArrayList<String>(Arrays.asList(images.split(",")));
	}
	
	private List<Type> convertDBTypesListToActualTypesList(String types){
		List<String> tempTypes = new ArrayList<>(Arrays.asList(types.split(",")));
		List<Type> actualTypes = new ArrayList<>();
		
		for(String tempType: tempTypes)
			actualTypes.add(Type.valueOf(tempType));
		
		return actualTypes;
	}
}
