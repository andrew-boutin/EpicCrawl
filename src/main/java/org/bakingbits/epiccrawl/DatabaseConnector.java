package org.bakingbits.epiccrawl;

import java.sql.*;

/**
 * Created by aboutin on 7/11/17.
 */
public class DatabaseConnector {
    private static DatabaseConnector instance;

    private Connection connection;
    private Statement statement;

    private static final String DATABASE_NAME = "epiccrawl",
                         LEVELS_TABLE_NAME = "levels",
                         ASSETS_TABLE_NAME = "assets";

    private DatabaseConnector() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME + ".db");
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

    private final String createLevelsTableQuery = "CREATE TABLE IF NOT EXISTS " + LEVELS_TABLE_NAME +
                                                  " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, data BLOB)";

    private final String createAssetsTableQuery = "CREATE TABLE IF NOT EXISTS " + ASSETS_TABLE_NAME +
                                                  " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, image BLOB, className TEXT)";

    private void createTablesAndDataIfNotExists() throws SQLException {
        if(isDatabaseEmpty()) {
            PreparedStatement preparedStatement = connection.prepareStatement(createLevelsTableQuery);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(createAssetsTableQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            DatabaseHelper.populateDefaultAssets(this);
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

    private final String createAssetQuery = "INSERT INTO " + ASSETS_TABLE_NAME + " (name, image, className) VALUES(?, ?, ?)";

    public void createAsset(final String name, final byte[] data, final String className) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(createAssetQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setBytes(2, data);
        preparedStatement.setString(3, className);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void printAssetTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + ASSETS_TABLE_NAME);
        ResultSet rs = preparedStatement.executeQuery();

        System.out.println("Assets:");
        while(rs.next()) {
            System.out.println("Name " + rs.getString("name") + ", className " + rs.getString("className"));
        }

        preparedStatement.close();
    }
}
