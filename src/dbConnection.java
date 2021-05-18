import java.sql.*;

public class dbConnection {
	private Statement statement;
    private Connection conn;
    private ResultSet rs;
    private String iq;

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
    // VIEW USERS FROM DB
    //
    public void viewEmployees() throws SQLException {
        try {
            open();
            iq = " SELECT * FROM `employees` ";
            rs = statement.executeQuery(iq);
            while (rs.next()) {
                System.out.println(rs.getString(2)); //here you can get data, the '1' indicates column number based on your query
            }
            close();
        } catch (SQLException ex) {
            System.out.println("Error in getData: " + ex);
        }
    }
}
