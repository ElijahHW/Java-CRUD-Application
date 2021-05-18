import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {
	static JFrame frame = new JFrame("Application");

	public Main() {		
		new MainFrame(frame);
	}
	
	public static void main(String[] args) {
	
		new MainFrame(frame);
	}

}
