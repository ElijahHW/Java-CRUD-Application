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
    // Returns the name of all tables in the classicmodels database
    //
    public static String[] getTableNames() { 
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
    // Returns all column names from a table
    //  
    public static String[] getColumnNames(String table) { 
		ArrayList<String> tablesList = new ArrayList<String>();

		try {
			Connection MyCon = DriverManager.getConnection(connection, username, password);
			Statement stmt = MyCon.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT COLUMN_NAME FROM `INFORMATION_SCHEMA`.`COLUMNS`  WHERE `TABLE_SCHEMA`='classicmodels' AND `TABLE_NAME`='" + table + "';");
			while (rs.next()) {
				tablesList.add(rs.getString("column_name"));
			}
			MyCon.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		String[] tables = tablesList.toArray(new String[tablesList.size()]);
		return tables;
	}
    
    
    //
    // Returns data type for all columns in a table
    //
    public static String[] getColumnDataType(String table) { 
    	ArrayList<String> dataTypeList = new ArrayList<String>();
    	
    	try {
			Connection MyCon = DriverManager.getConnection(connection, username, password);
			Statement stmt = MyCon.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT DATA_TYPE FROM `INFORMATION_SCHEMA`.`COLUMNS`  WHERE `TABLE_SCHEMA`='classicmodels' AND `TABLE_NAME`='" + table + "';");
			while (rs.next()) {
				dataTypeList.add(rs.getString("data_type"));
			}
			MyCon.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		String[] dataType = dataTypeList.toArray(new String[dataTypeList.size()]);
		return dataType;
    }
        
    	public static String addCustomer(String customerNumber, String customerName, 
    		String contactLastName, String contactFirstName, String phone, 
    		String addressLine1, String addressLine2, String city, String state, 
    		String postalCode, String country, String salesRepEmployeeNumber, 
    		String creditLimit){
    	
        try {
        	
            Connection con = DriverManager.getConnection(connection, username, password);
            String query = "INSERT INTO customers "
            		+ "(customerNumber, customerName, contactLastName, contactFirstName, "
            		+ "phone, addressLine1, addressLine2, city, state, postalCode, country, "
            		+ "salesRepEmployeeNumber, creditLimit) "
            		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement sql = con.prepareStatement(query);
            
            sql.setString((1), customerNumber);
            sql.setString((2), customerName);
            sql.setString((3), contactLastName);
            sql.setString((4), contactFirstName);
            sql.setString((5), phone);
            sql.setString((6), addressLine1);
            sql.setString((7), addressLine2);
            sql.setString((8), city);
            sql.setString((9), state);
            sql.setString((10), postalCode);
            sql.setString((11), country);
            sql.setString((12), salesRepEmployeeNumber);
            sql.setString((13), creditLimit);
            
            sql.executeUpdate();
            con.close();
            
            return "Customer was added to DB!";
            
        }catch (Exception e) {
        	
        	System.out.println(e);
        	
            return "Something went wrong.";
        }
  
    }
    
    
    
    
}