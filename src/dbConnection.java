import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbConnection {
	private static Statement statement;
    private static Connection conn;
    private ResultSet rs;
    private String iq;
    static String connection= "jdbc:mysql://localhost:3306/classicmodels";
	static String username = "root";
	static String password = "";

  
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

}