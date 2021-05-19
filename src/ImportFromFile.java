import java.awt.BorderLayout;
import java.awt.Color;
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
	private JLabel currentFile, validationMessage;
	private JComboBox<String> tableComboBox;
	private ArrayList<ArrayList<String>> dataFromFile;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JTable preview;
	
	
	
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
		openFile.addActionListener(this);
		openFile.setBackground(Color.WHITE);
		
		currentFile = new JLabel(""); // Label for the file that is currently opened
		
		fileChooser.add(openFile);
		fileChooser.add(currentFile);
		fileChooser.setLayout(new BoxLayout(fileChooser, BoxLayout.PAGE_AXIS));
		return fileChooser;
	}
	
	
	//Method for the user to select to which table to the add data
	private JPanel selectTable() { 
		JPanel selectTable = new JPanel();
		
		JLabel dbTableLabel = new JLabel("Choose table to add data");
		String[] tableNames = dbConnection.getTableNames();
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
		preview.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(preview);
		
		validationMessage = new JLabel("");
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
				lineError.add(i+1);
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
		String[] dataTypes = dbConnection.getColumnDataType(table);
		int rows = preview.getRowCount();
		
		outerloop:
		for(int i=0;i<dataTypes.length;i++) {
			innerloop:
			for(int j=0;j<rows;j++) {
					data = preview.getValueAt(j, i).toString();
					if(data.isEmpty()) {break innerloop;} // Skip iteration if the cell is empty
					try {
					switch(dataTypes[i]) {	
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
					break outerloop; //Break loops after exception to display error message for user
				}
			}
		}
		return isValid;
	}
	
	
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
					while (myReader.hasNextLine()) {
						dataFromFile.add(new ArrayList<String>());
						String textFromLine = myReader.nextLine(); 
					    String[] parts = textFromLine.split(";"); //Splits the string into an array based on the delimeter
					    
						for(int i = 0;i<parts.length;i++) {
							dataFromFile.get(index).add(parts[i]);
						}
						index++;
					}
					myReader.close();
				} catch (FileNotFoundException fe) {
					System.out.println("An error occurred.");
					fe.printStackTrace();
				}
				currentFile.setText("Current file: " + file.getName());	//Shows the user the name of current open file	
			}
		}	
		
		if (e.getSource() == tableComboBox && dataFromFile != null) { //Execute if user has selected table and file
			String[] columnNames = dbConnection.getColumnNames(tableComboBox.getSelectedItem().toString());
			
			ArrayList<Integer> errors = checkIfDataMatches(columnNames, dataFromFile); //Checks for errors in the text file format
			
			if(errors.size() <= 0) { //No errors
			panel.add(dataPreview(columnNames, dataFromFile), gbc); // the constraints from the constructor is added here
			panel.revalidate();
			panel.repaint();
			}else { //Tells the user about the errors
				JOptionPane.showMessageDialog(panel, "The expected number of columns for the " + tableComboBox.getSelectedItem() + " table is " + columnNames.length + ". Your text file is not matching this on line " + errors.toString()
				+ "\n\n Each column value in the text file should be seperated by ; and rows by a newline");
			}
		}
		
		if (e.getSource() == addToTable) {
			boolean validData = validateData(); //Datatype validation
			if(validData) {
				for (int i = 0;i<preview.getRowCount();i++) {
					for(int j = 0;j<preview.getColumnCount();j++) {
						String test = preview.getValueAt(i, j).toString();
						System.out.print(test + " ");
					}
					System.out.println();
				}
			}
		}
	}		
}


