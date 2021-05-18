import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {
	JFrame frame = new JFrame("Application");

	public static void main(String[] args) {
		System.out.println("test");
		List<List<String>> loginList = dbConnection.getTable("employees");
		for(List<String> item : loginList) {
			System.out.println(item);
		}
		
	}

}
