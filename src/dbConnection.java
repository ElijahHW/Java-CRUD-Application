import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbConnection {
	private static Statement statement;
    private static Connection conn;
    private ResultSet rs;
    private String iq;
    private static String connection= "jdbc:mysql://localhost:3306/classicmodels";
    private static String username = "root";
    private static String password = "";

  
    //
    // Get data from a table
    //
    
    public static List<List<String>> getTable(String table) {
        
        List<List<String>> res = new ArrayList<List<String>>();
        
        try {

            Connection con = DriverManager.getConnection(connection, username, password);
            
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM " + table + ";");
            
            while (rs.next()) {

                List<String> row = new ArrayList<String>();
                
                row.clear();
                
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    
                    row.add(rs.getString(i));
                }
                
                
                res.add(row);
            }
            
            stm.close();
            return res;
        } catch (Exception e) {
            
            return new ArrayList<List<String>>();
        }
    }
    
    //
    // Get table names
    //
    
    public static String[] getTableNames() { //Returns the name of all tables in the classicmodels database
		ArrayList<String> tablesList = new ArrayList<String>();

		try {
			Connection MyCon = DriverManager.getConnection(connection, username, password);
			Statement stmt = MyCon.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'classicmodels'");
			while (rs.next()) {
				tablesList.add(rs.getString("table_name"));
			}
			MyCon.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		String[] tables = tablesList.toArray(new String[tablesList.size()]);
		return tables;
	}
    
    //
    // Get column names
    //
    
    public static String[] getColumnNames(String table) { //Returns all column names from a table
		ArrayList<String> tablesList = new ArrayList<String>();

		try {
			Connection MyCon = DriverManager.getConnection(connection, username, password);
			Statement stmt = MyCon.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT COLUMN_NAME  FROM `INFORMATION_SCHEMA`.`COLUMNS`  WHERE `TABLE_SCHEMA`='classicmodels' AND `TABLE_NAME`=' " + table + "';");
			while (rs.next()) {
				tablesList.add(rs.getString("table_name"));
			}
			MyCon.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		String[] tables = tablesList.toArray(new String[tablesList.size()]);
		return tables;
	}
    
    
}