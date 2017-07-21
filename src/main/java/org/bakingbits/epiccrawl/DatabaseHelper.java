package org.bakingbits.epiccrawl;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by aboutin on 7/20/17.
 */
public final class DatabaseHelper {

    private DatabaseHelper() {

    }

    // TODO: Class as ENUM ?
    public static void populateDefaultAssets(DatabaseConnector databaseConnector) {
        ArrayList<Object[]> assets = new ArrayList<>();
        assets.add(new Object[]{"Bar Keep", null, "npc"});
        assets.add(new Object[]{"Wall Wood", null, "regular"});
        assets.add(new Object[]{"Floor Grass", null, "regular"});
        assets.add(new Object[]{"Rock", null, "regular"});

        // TODO: http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/

        for(Object[] asset: assets) {
            try {
                databaseConnector.createAsset((String)asset[0], (byte[])asset[1], (String)asset[2]);
            }
            catch(SQLException e) {
                e.printStackTrace();
                return;
            }
        }
    }

//    /**
//     * Read the file and returns the byte array
//     * @param file
//     * @return the bytes of the file
//     */
//    private byte[] readFile(String file) {
//        ByteArrayOutputStream bos = null;
//        try {
//            File f = new File(file);
//            FileInputStream fis = new FileInputStream(f);
//            byte[] buffer = new byte[1024];
//            bos = new ByteArrayOutputStream();
//            for (int len; (len = fis.read(buffer)) != -1;) {
//                bos.write(buffer, 0, len);
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println(e.getMessage());
//        } catch (IOException e2) {
//            System.err.println(e2.getMessage());
//        }
//        return bos != null ? bos.toByteArray() : null;
//    }

    public static void main(String[] args) throws SQLException {
        DatabaseConnector connector = DatabaseConnector.getInstance();

        connector.printAssetTable();
    }
}
