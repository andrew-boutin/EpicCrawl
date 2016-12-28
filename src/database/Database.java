package database;

public final class Database {
	private static Database instance;
	
	public static final String databaseName  = "epiccrawl",
                               itemMetaTable = "itemMeta";

	private Database(){
		
	}
	
	public static Database getInstance(){
		if(instance == null)
			instance = new Database();
		
		return instance;
	}
	
	// TODO: Save level
	// TODO: Get level
	// TODO: Add new meta item
}
