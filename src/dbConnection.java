import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbConnection {
	private Statement statement;
    private Connection conn;
    private ResultSet rs;
    private String iq;
    static String connection= "jdbc:mysql://localhost:3306/classicmodels";
	static String username = "root";
	static String password = "";

    //
    // OPEN DB CONNECTION
    //
    public void open() throws SQLException {
        try {
            //Establish connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/classicmodels", "root", ""); // HUSKE Å ENDRE TIL STUDENT STUDENT
            //Create statement that will be used for executing SQL queries
            statement = conn.createStatement();
            System.out.print("Database Connected! \n");
        } catch (SQLException ex) {
            System.out.print("Database Not Connected! \n");
            ex.printStackTrace();// More elegant solutions for catching errors exist but they are out of the scope for this course
        }
    }

    //
    // CLOSE DB CONNECTION
    //
    public void close() throws SQLException {
        try {
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

}