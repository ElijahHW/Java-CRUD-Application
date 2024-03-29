import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static String connection= "jdbc:mysql://localhost:3306/classicmodels";
    private static String username = "student";
    private static String password = "student";

  
    //Method to test connection to database
    
    public static boolean tryConnection() {
    	boolean status = true;
    	try {
    		Connection con = DriverManager.getConnection(connection, username, password);
    		Statement stm = con.createStatement();
    		stm.executeQuery("SELECT now()");
    		stm.close();
    	}
    	catch(Exception e){
    		status = false;
    		System.out.println(e);
    	}
    	return status;
    }
        
    
    
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
    // Returns all column names from a given table
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
    // Returns data type for all columns in a given table
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
    
    
    
    //
    // Inserts a new row into the customer table
    //    
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

        
         
    /*
    Dynamical method to insert a new row into database
    table = name of table to insert into
    parameterCount = amount of parameters needed
    input = array of all values that is to be inserted
    */
    public static String insertIntoTable(String table, int parameterCount, String[] input) {
    	String parameters = "";
    	
    	for(int i = 0;i<parameterCount;i++) { //Creates a string with the correct amount of parameters
    		parameters += "?,";
    	}	
    	
    	parameters = parameters.substring(0, parameters.length() - 1); // an extra comma is removed here
    	
    	try {	
    		Connection con = DriverManager.getConnection(connection, username, password);
    		String query = "INSERT INTO " + table + " VALUES ( " + parameters + ")";
    		PreparedStatement sql = con.prepareStatement(query);
        
    		for(int i=0;i<input.length;i++) { // Sets the parameters
    			if(input[i].length() == 0) {
    				sql.setString((1+i), null);
    			}else {
    				sql.setString((1+i), input[i]);
    			}
    		}
    		
    		sql.executeUpdate();
            con.close();
            return "Data inserted";
    	}catch(Exception e) {
    		System.out.println(e);
    		if(e.getMessage().contains("foreign key")) {
    			return ("One of your foreign key constraints failed. Check your foreign keys!");
    		}
    		return e.getMessage().toString(); 
    	}        		
   }
    
    
    
    /*
    Dynamical method to update a row in the database
    table = name of table to insert into
    input = array of all values that is to be inserted
    */   
    public static String updateTable(String table, String[] input) {
    	String[] columns = getColumnNames(table);
    	String primarykey = columns[0]; // Gets the name of the first column
		String changes = " SET";
    	
		for(int i = 1;i<columns.length;i++) { //Creates a string with the correct amount of parameters, skips over the first index, as this should be the primary key
    		changes += " " + columns[i] + "=?,"; 
    	}	
		changes = changes.substring(0, changes.length() - 1);  
		
    	try {	
    		Connection con = DriverManager.getConnection(connection, username, password);
    		String query = "UPDATE " + table + changes + " WHERE " + primarykey + "=" + input[0];
    		PreparedStatement sql = con.prepareStatement(query);
        
    		for(int i=1;i<input.length;i++) { // Sets the parameters, Skips over the first index
    			if(input[i] == null || input[i].isEmpty()) {
    				sql.setString((i), null);
    			}else {
    				sql.setString((i), input[i]);
    			}
    		}
    		
    		sql.executeUpdate();
            con.close();
            return "Data updated";
            
    	}catch(Exception e) {
    		System.out.println(e);
    		if(e.getMessage().contains("foreign key")) {
    			return ("One of your foreign key constraints failed. Check your foreign keys!");
    		}
    		return e.getMessage().toString(); 
    	}        		
   }
    public static int delete(String table, String PrimaryColumn, String id)
    {
        //SQL STMT
        String sql="DELETE FROM " + table + " WHERE " + PrimaryColumn +" ='"+id+"'";
        try
        {
            //GET COONECTION
            Connection con=DriverManager.getConnection(connection, username, password);
            //STATEMENT
            Statement s=con.prepareStatement(sql);
            //EXECUTE
            s.execute(sql);
            return 0;

        }catch(Exception ex)
        {
            //ex.printStackTrace();
    		if(ex.getMessage().contains("foreign key")) {
    			return 1;
    		} else {
    			
                return 2;
    		}
        }
    }
        
    
}