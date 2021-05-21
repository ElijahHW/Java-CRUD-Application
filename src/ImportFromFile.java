import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;

public class ImportFromFile implements ActionListener {
	private JPanel panel, dataPreview;
	private JButton openFile, addToTable;
	private JFileChooser fc;
	private String filePath;
	private JLabel currentFile;
	private JLabel validationMessage = new JLabel("");
	private JComboBox<String> tableComboBox;
	private ArrayList<ArrayList<String>> dataFromFile;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JTable preview;
	private int comboBoxIndex;
		
	public ImportFromFile() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(fileChooser(), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(selectTable(), gbc);
		
		gbc.fill = GridBagConstraints.BOTH; // these constraints will be added to a JPanel later
		gbc.weightx =  1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
	}
	
	
	//Method for the filechooser
	private JPanel fileChooser() { 
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		JPanel fileChooser = new JPanel();
		openFile = new JButton("Find a file");
		Icon iconD = UIManager.getIcon("FileChooser.upFolderIcon");
		openFile.setIcon(iconD);
		openFile.addActionListener(this);
		openFile.setBackground(Color.WHITE);
		
		currentFile = new JLabel(""); // Label for the file that is currently opened
		
		fileChooser.add(openFile);
		fileChooser.add(currentFile);
		fileChooser.setLayout(new BoxLayout(fileChooser, BoxLayout.PAGE_AXIS));
		return fileChooser;
	}
	
	
	//Method for the combobox, where the user can select tables
	private JPanel selectTable() { 
		JPanel selectTable = new JPanel();
		JLabel dbTableLabel = new JLabel("Choose table to add data");
		String[] tableNames = DBConnection.getTableNames();
		
		tableComboBox = new JComboBox<String>(tableNames);
		tableComboBox.addActionListener(this);
		tableComboBox.setBackground(Color.WHITE);
		
		selectTable.add(dbTableLabel);
		selectTable.add(tableComboBox);
		return selectTable;	
	}
	

	
	//Method to preview the data in the text file before it is inserted	
	private JPanel dataPreview(String[] columns, ArrayList<ArrayList<String>> data) { 
		dataPreview = new JPanel();
		
		String[][] tableArray = new String[data.size()][data.get(0).size()];
		for (int i = 0; i < data.size(); i++) { //Transforms the 2d arraylist into a 2d array to use with JTable
			for(int j = 0;j< data.get(i).size();j++) {
				tableArray [i][j] = data.get(i).get(j);
			}		
		}
			
		preview = new JTable(tableArray, columns);
		preview.setEnabled(false); //disable editing
		preview.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(preview);
	
		validationMessage.setFont(new Font(null, Font.BOLD,15));
		validationMessage.setAlignmentX(addToTable.CENTER_ALIGNMENT);
		
		addToTable = new JButton("Add to table");
		addToTable.setAlignmentX(addToTable.CENTER_ALIGNMENT);
		addToTable.addActionListener(this);	
		addToTable.setBackground(Color.WHITE);
		
		JPanel validationPanel = new JPanel();
		validationPanel.setLayout(new BoxLayout(validationPanel, BoxLayout.PAGE_AXIS));
		validationPanel.add(validationMessage);
		validationPanel.add(addToTable);
		
		dataPreview.setLayout(new BorderLayout());
		dataPreview.add(scrollPane, BorderLayout.CENTER);
		dataPreview.add(validationPanel, BorderLayout.PAGE_END);
		return dataPreview;
	}
	
	
	//Compares the amount of columns in the text file to the amount of columns in the selected database table.
	private ArrayList<Integer> checkIfDataMatches(String[] columns, ArrayList<ArrayList<String>> data) { 
		ArrayList<Integer> lineError = new ArrayList<Integer>();
		for(int i = 0;i<data.size();i++) {
			if(columns.length != data.get(i).size()) {
				lineError.add(i+1); //Stores which line the error(s) is on
			}else {
				comboBoxIndex = tableComboBox.getSelectedIndex(); //Stores the index of the table
			}
		}
		return lineError;
	}
	
	
	//Method to validate the data before it is inserted into the database table. Checks for int and double
	private boolean validateData() { 
		boolean isValid = true;
		String error = "";
		String data;
		String table = tableComboBox.getSelectedItem().toString();
		String[] dataTypes = DBConnection.getColumnDataType(table); //Gets the datatypes for each column in the table
		int rows = preview.getRowCount();
		
		outerloop:
		for(int i=0;i<dataTypes.length;i++) {
			innerloop:
			for(int j=0;j<rows;j++) {
					data = preview.getValueAt(j, i).toString();
					if(data.isEmpty()) {break innerloop;} // Skip iteration if the cell is empty
					try {
					switch(dataTypes[i]) {	//Checks for different data types
						case "decimal":
							Double.parseDouble(data);
							break;
						case "int":
							Integer.parseInt(data);
							break;								
					}	
				}catch(Exception e) {	
					isValid = false; //Data is not valid and cant be entered to database
					error = "Error: Expected " + dataTypes[i] + " at row " + (j+1) + " column " + (i+1);
					validationMessage.setText(error);
					validationMessage.setForeground(Color.red);
					break outerloop; //Break loops after exception so the error message can be displayed to the user
				}
			}
		}
		return isValid;
	}
	
	
	public String callDb() { //Method to loop through all the data and send it to the insertIntoTable method in DBConnection class
		String result = "Data inserted";
		int rowCount = 0;
		outerloop:
		for (int i = 0;i<preview.getRowCount();i++) {
			String[] row = new String[preview.getColumnCount()];
					
			for(int j=0;j<preview.getColumnCount();j++) {
				row[j] = preview.getValueAt(i, j).toString();
			};
			if(result.equals("Data inserted")) { //Continue if result didnt change
				result = DBConnection.insertIntoTable(tableComboBox.getSelectedItem().toString(), preview.getColumnCount(), row);
			}else { //an error happened, STOP!
				result += " Stopped on row " + String.valueOf(rowCount);
				break outerloop;
			}
			rowCount++;
		}
		return result;
	}
	
	
	public JPanel getPanel() {
		return panel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Opens the file chooser when the button is clicked
		if (e.getSource() == openFile) { 
			int returnVal = fc.showOpenDialog(panel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();
				filePath = file.getPath();
				
				dataFromFile = new ArrayList<ArrayList<String>>(); // Array to store all the data in the file
				int index = 0; // Used to track the row in the textfile
				
				try {
					File myObj = new File(filePath);
					Scanner myReader = new Scanner(myObj);
			
					if(file.length() == 0) { //File is empty
						JOptionPane.showMessageDialog(panel, "File is empty, please choose another file");
					}else {
						while (myReader.hasNextLine()) {
							dataFromFile.add(new ArrayList<String>());
							String textFromLine = myReader.nextLine(); 
							String[] parts = textFromLine.split("~"); //Splits the row into an array based on the delimeter
					    
							for(int i = 0;i<parts.length;i++) {
								dataFromFile.get(index).add(parts[i]);
							}
						
							if(dataPreview != null) { //Remove old table when a new file is selected
								panel.remove(dataPreview);
								panel.revalidate();
								panel.repaint();
							
							}
							index++;
						}
						myReader.close();
						currentFile.setText("Current file: " + file.getName());	//Shows the user the name of the current open file
				}
				} catch (FileNotFoundException fe) {
					fe.printStackTrace();
				}
					
			}
		}	
		
		//Execute if user has selected table and file
		if (e.getSource() == tableComboBox && dataFromFile != null) { 
			String[] columnNames = DBConnection.getColumnNames(tableComboBox.getSelectedItem().toString());
			
			ArrayList<Integer> errors = checkIfDataMatches(columnNames, dataFromFile); //Checks for datatype errors in the text file format
			
			if(errors.size() <= 0) { //No errors
				
				panel.add(dataPreview(columnNames, dataFromFile), gbc); // the gridbag constraints from the constructor is added here
				panel.revalidate();
				panel.repaint();
			}else { //Tells the user about the errors
				
				if(errors.size() < 10) { // Under 10 errors, displays the row for the errors
					UIManager.put("Button.background", Color.white);
					JOptionPane.showMessageDialog(panel, "The expected number of columns for the " + tableComboBox.getSelectedItem() + " table is " + columnNames.length + ". Your text file is not matching this on line " + errors.toString()
					+ "\n\n Each column value in the text file should be seperated by ~ and rows by a new line");
				}
				else { // 10 or more errors, displays a more generic error message
					UIManager.put("Button.background", Color.white);
					JOptionPane.showMessageDialog(panel, "The expected number of columns for the " + tableComboBox.getSelectedItem() + " table is " + columnNames.length + ". You have " + errors.size() + " rows where this doesn't match. Are you sure you are choosing the right table?"
							+ "\nEach column value in the text file should be seperated by ~ and rows by a new line");
				}
				tableComboBox.setSelectedIndex(comboBoxIndex); //resets the combobox value to the working table
			}
		}
		
		
		//Executes when to addToTable button is clicked
		if (e.getSource() == addToTable) {
			boolean validData = validateData(); //Datatype validation
			if(validData) {	//Data is valid
				String message = callDb();
				
				if(message.equals("Data inserted")) { //No errors in data insertion
					validationMessage.setText(message);
					validationMessage.setForeground(new Color(0x11780f));			
				}else { //Error occured when data was inserted
					validationMessage.setText("Error: " + message);
					validationMessage.setForeground(Color.RED);	
				}
			}
		}
		
	}		
}


