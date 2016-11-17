package utility;

public final class Database {
	private static Database instance;

	private Database(){
		
	}
	
	public static Database getInstance(){
		if(instance == null)
			instance = new Database();
		
		return instance;
	}
	
	// TODO: Save level
	
	// TODO: Get level
}
