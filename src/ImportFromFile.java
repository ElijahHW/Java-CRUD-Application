import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class ImportFromFile implements ActionListener {
	private JPanel panel, dataPreview;
	private JButton openFile, addToTable;
	private JFileChooser fc;
	private String filePath;
	private JLabel currentFile;
	private JComboBox<String> tableComboBox;
	private ArrayList<ArrayList<String>> dataFromFile;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	
	
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
		
		selectTable.add(dbTableLabel);
		selectTable.add(tableComboBox);
		return selectTable;	
	}
	

	
	//Method to preview the data in the text file	
	private JPanel dataPreview(String[] columns, ArrayList<ArrayList<String>> data) { 
		dataPreview = new JPanel();
		
		String[][] tableArray = new String[data.size()][data.get(0).size()];
		for (int i = 0; i < data.size(); i++) { //Transforms the 2d arraylist into a 2d array to use with JTable
			for(int j = 0;j< data.get(i).size();j++) {
				tableArray [i][j] = data.get(i).get(j);
			}		

		}
			
		JTable preview = new JTable(tableArray, columns);
		preview.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(preview);
		
		addToTable = new JButton("Add to table");
		addToTable.addActionListener(this);				
		
		dataPreview.add(scrollPane);
		return dataPreview;
	}
	
	
	//Compares the text file columns to the amount of columns in the selected database table.
	private ArrayList<Integer> checkIfDataMatches(String[] columns, ArrayList<ArrayList<String>> data) { 
		ArrayList<Integer> lineError = new ArrayList<Integer>();
		for(int i = 0;i<data.size();i++) {
			if(columns.length != data.get(i).size()) {
				lineError.add(i+1);
			}
		}
		return lineError;
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
					    String[] parts = textFromLine.split(","); //Splits the string into an array based on the delimeter
					    
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
		
		if (e.getSource() == tableComboBox && dataFromFile != null) { 
			String[] columnNames = dbConnection.getColumnNames(tableComboBox.getSelectedItem().toString());
			
			ArrayList<Integer> errors = checkIfDataMatches(columnNames, dataFromFile); //Checks for errors in the text file format
			
			if(errors.size() <= 0) { //No errors
			panel.add(dataPreview(columnNames, dataFromFile), gbc); // the constraints from the constructor is added here
			panel.revalidate();
			panel.repaint();
			}else { //Tells the user about the errors
				JOptionPane.showMessageDialog(panel, "The expected number of columns for the " + tableComboBox.getSelectedItem() + " table is " + columnNames.length + ". Your text file is not matching this on line " + errors.toString()
				+ "\n\n Each column value in the text file should be seperated by ,");
			}
		}
		
		if (e.getSource() == addToTable) {
			
		}
	}		
}


