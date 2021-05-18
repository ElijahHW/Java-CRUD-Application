import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class ImportFromFile implements ActionListener {
	private JPanel panel;
	private JButton openFile = new JButton();
	private JFileChooser fc;
	private String filePath;
	private JLabel currentFile;
	private JComboBox<String> tableComboBox;
	private ArrayList<ArrayList<String>> dataFromFile;
	
	public ImportFromFile() {
		panel = new JPanel();
		panel.add(fileChooser());
		panel.add(selectTable());
		dbConnection.getColumnNames("employees");
		dbConnection.getTableNames();
	}
	
	private JPanel fileChooser() { //Method for the filechooser
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		JPanel fileChooser = new JPanel();
		openFile = new JButton("Find a file");
		openFile.addActionListener(this);
		
		currentFile = new JLabel("");
		
		fileChooser.add(openFile);
		fileChooser.add(currentFile);
		
		return fileChooser;
	}
	
	private JPanel selectTable() { //Method for the user to select table to add data
		JPanel selectTable = new JPanel();
		
		JLabel dbTableLabel = new JLabel("Choose table to add data");
		String[] tableNames = dbConnection.getTableNames();
		tableComboBox = new JComboBox<String>(tableNames);
		tableComboBox.addActionListener(this);
		
		selectTable.add(dbTableLabel);
		selectTable.add(tableComboBox);
		
		return selectTable;	
	}
	
	/*private JPanel dataPreview(String[] columns, ArrayList<ArrayList<String>> data) { //Preview of the data in table format
		JPanel dataPreview = new JPanel();
		for (int i = 0; i < employeeList.size(); i++) {
			employeeArray[i][0] = employeeList.get(i).get(0);
			employeeArray[i][1] = employeeList.get(i).get(1);
			employeeArray[i][2] = employeeList.get(i).get(2);
		}
		JTable preview = new JTable(columns, data.toArray());
		
		return dataPreview;
	}
	*/
	
	
	
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openFile) { //Opens the file chooser when the button is clicked
			int returnVal = fc.showOpenDialog(panel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();
				filePath = file.getPath();

				dataFromFile = new ArrayList<ArrayList<String>>();
				int index = 0;
				try {
					File myObj = new File(filePath);
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNextLine()) { //Reads the current line
						dataFromFile.add(new ArrayList<String>());
						String textFromLine = myReader.nextLine(); 
					    String[] parts = textFromLine.split(","); //Splits the string into an array based on the delimeter
						for(int i = 0;i<parts.length;i++)
						dataFromFile.get(index).add(parts[i]);
						index++;
					}
					myReader.close();
				} catch (FileNotFoundException fe) {
					System.out.println("An error occurred.");
					fe.printStackTrace();
				}
				currentFile.setText("Current file: " + file.getName());		
			}
		}	
		
		if (e.getSource() == tableComboBox) {
			String[] columnNames = dbConnection.getColumnNames(tableComboBox.getSelectedItem().toString());
			dataPreview(columnNames, dataFromFile);
		}
	}		
}


