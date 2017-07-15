package org.bakingbits.epiccrawl;

import java.sql.*;

/**
 * Created by aboutin on 7/11/17.
 */
public class DatabaseConnector {
    private static DatabaseConnector instance;

    private Connection connection;
    private Statement statement;

    private final String databaseName = "epiccrawl",
                         levelsTableName = "levels";

    private DatabaseConnector() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
        statement = connection.createStatement();
        statement.setQueryTimeout(1);

        createTablesAndDataIfNotExists();
    }

    public static DatabaseConnector getInstance() {
        if(instance == null) {
            try{
                instance = new DatabaseConnector();
            }
            catch(Exception e){
                System.err.println("Failed to initialize database: " + e);
                e.printStackTrace();
                System.exit(-1);
            }
        }

        return instance;
    }

    private final String createLevelsTableQuery = "CREATE TABLE IF NOT EXISTS " + levelsTableName +
                                                  " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, data BLOB)";

    private void createTablesAndDataIfNotExists() throws SQLException {
        if(isDatabaseEmpty()) {
            PreparedStatement preparedStatement = connection.prepareStatement(createLevelsTableQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // TODO: Load the tables with default data if it doesn't already exist
        }
    }

    private final String countTablesQuery = "SELECT COUNT(name) AS total FROM sqlite_master WHERE type='table'";

    private boolean isDatabaseEmpty() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(countTablesQuery);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        boolean emptyDatabase = rs.getInt("total") == 0;
        preparedStatement.close();
        return emptyDatabase;
    }
}
