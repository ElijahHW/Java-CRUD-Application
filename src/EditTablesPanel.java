import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class EditTablesPanel implements ActionListener, TableModelListener {
	JPanel panel, displayTable;
	JComboBox<String> selectTable;
	JList<String> tableData;
	JLabel validation;
	JButton submit;
	JTable table;
	private GridBagConstraints gbc = new GridBagConstraints();
	ArrayList<Integer> row = new ArrayList<Integer>();
	ArrayList<Integer> linesDone = new ArrayList<Integer>();
	ArrayList<Integer> linesNotDone = new ArrayList<Integer>();
	
	public EditTablesPanel() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(comboBox(),gbc);
		gbc.fill = GridBagConstraints.BOTH; // these constraints will be added to a JPanel later
		gbc.weightx =  1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
	}
	
	
	//Method for the combobox 
	private JPanel comboBox() {
		JPanel comboBoxPanel = new JPanel();
		JLabel comboBoxLabel = new JLabel("Choose table to edit");
		
		String[] tableNames = DBConnection.getTableNames();		
		selectTable = new JComboBox<String>(tableNames);
		selectTable.addActionListener(this);
		selectTable.setBackground(Color.WHITE);
		
		comboBoxPanel.add(comboBoxLabel);
		comboBoxPanel.add(selectTable);
		return comboBoxPanel;
	}
	
	
	//Method for the JTable
	private JPanel displayTable() {
		displayTable = new JPanel();
		List<List<String>> dataList = DBConnection.getTable(selectTable.getSelectedItem().toString());
		String[] columnNames = DBConnection.getColumnNames(selectTable.getSelectedItem().toString());
		
		String[][] dataArray = new String[dataList.size()][dataList.get(0).size()];
		for (int i = 0; i < dataList.size(); i++) { //Transforms the 2d arraylist into a 2d array to use with JTable
			for(int j = 0;j< dataList.get(i).size();j++) {
				dataArray [i][j] = dataList.get(i).get(j);
			}	
		}
		
		table = new JTable(dataArray, columnNames){
			   public boolean isCellEditable(int row, int column){ 
			        return column != 0; // Disables editing on column with index 0, as this should be the primary key
			   }
		};
		
		table.getTableHeader().setReorderingAllowed(false);
		table.getModel().addTableModelListener(this);
		JScrollPane scrollPane = new JScrollPane(table);
		
		displayTable.setLayout(new BorderLayout());
		displayTable.add(scrollPane);
		displayTable.add(scrollPane, BorderLayout.CENTER);
		displayTable.add(submitTable(), BorderLayout.PAGE_END);
		return displayTable;
	}
	
	
	//Method for the submit button and jlabel to display validation messages
	private JPanel submitTable() {
		JPanel submitButton = new JPanel();
		submitButton.setLayout(new BoxLayout(submitButton, BoxLayout.PAGE_AXIS));
		
		validation = new JLabel("",SwingConstants.CENTER);
		validation.setAlignmentX(validation.CENTER_ALIGNMENT);
		submit = new JButton("Save changes");
		submit.setFocusable(false);
		submit.setBackground(Color.WHITE);
		submit.addActionListener(this);
		submit.setAlignmentX(submit.CENTER_ALIGNMENT);
		
		submitButton.add(validation);
		submitButton.add(submit);
		return submitButton;
	}
	
	
	//Gets the data from a row that has been changed
	private String[] getDataFromTable(int index) {
		String[] data = new String[table.getColumnCount()];
		
		for(int i = 0;i<table.getColumnCount();i++) {
			try {
				String temp = table.getValueAt(index, i).toString();
				data[i] = temp;
			}catch(Exception e) { //Cell in table is null so we set it to null in the array
				data[i] = null;
			}
		}
		return data;
	}
	
	
	//Loops through all the changed rows and checks for correct datatype int/double/string
	private boolean validateData() { 
		boolean isValid = true;
		String error = "";
		String data;
		String[] dataTypes = DBConnection.getColumnDataType(selectTable.getSelectedItem().toString()); //Gets the datatypes for each column in the table
		
		outerloop:
		for(int i=0;i<dataTypes.length;i++) {
			innerloop:
			for(int j=0;j<row.size();j++) {
					if(table.getValueAt(j, i) == null || table.getValueAt(j, i).toString().isEmpty()) {break innerloop;} // Skip iteration if the cell is empty
					try {
						data = table.getValueAt(j, i).toString();
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
					validation.setText(error);
					validation.setForeground(Color.red);
					break outerloop; //Break loops after exception so the error message can be displayed to the user
				}
			}
		}
		return isValid;
	}
	
	
	//Method to loop through all the changed row and send it to the database method for insertion in dbConnection class
	public String callDb() { 
		String result = "Data updated";
		
		outerloop:
			for(int i = 0;i<row.size();i++) {
				int index = row.get(i);
				String[] data = getDataFromTable(index);
				
				result = DBConnection.updateTable(selectTable.getSelectedItem().toString(), data);
					
				if(result.equals("Data updated")) {
					linesDone.add(row.get(i)+1); // The row got executed to the database so we add that row number to our linesDone list
					System.out.println(row.get(i)+1);
					row.remove(i);
					i--;
				}else { //an error happened, STOP!
					for(int j = 0;j<row.size();j++) {
						linesNotDone.add(row.get(j)+1); // Gets the lines that was not executed
					}
					break outerloop;
				}
			}
		return result;
	}
	
	
	public JPanel getPanel() {
		return panel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		//execeutes when the user has selected a table
		if (e.getSource() == selectTable) { 
			if(displayTable != null) { //Removes old table when a new is selected
				panel.remove(displayTable);
			}
			
			panel.add(displayTable(), gbc);
			panel.revalidate();
			panel.repaint();
		}
		
		//Executes when the submit button is clicked
		if (e.getSource() == submit) {
			boolean validData = validateData(); //Datatype validation
			if(validData) {
				Collections.sort(row); //Sorts the arraylist and remove duplicate rows
				Set<Integer> set = new HashSet<Integer>(row); 
				row.clear();
				row.addAll(set);
			
				String message = callDb();
						
				if(message.equals("Data updated")) {
					validation.setText(message);
					validation.setForeground(Color.green); 			
				}else { //Display error message
					validation.setText("<html>Error: " + message + "<br/> row " + linesDone.toString() + " got executed. row " + linesNotDone.toString() + " was not executed</html>");
					validation.setForeground(Color.red);	
				}
				linesDone.clear();			}
				linesNotDone.clear();
		}
		
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		row.add(e.getFirstRow()); // Stores which rows has been changed	
	}
	
	
	

}
