import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		System.out.println("hello world");
		dbConnection conn = new dbConnection();
		try {
			conn.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
