import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		
		String[] tableNames = dbConnection.getTableNames();		
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
		List<List<String>> dataList = dbConnection.getTable(selectTable.getSelectedItem().toString());
		String[] columnNames = dbConnection.getColumnNames(selectTable.getSelectedItem().toString());
		
		String[][] dataArray = new String[dataList.size()][dataList.get(0).size()];
		for (int i = 0; i < dataList.size(); i++) { //Transforms the 2d arraylist into a 2d array to use with JTable
			for(int j = 0;j< dataList.get(i).size();j++) {
				dataArray [i][j] = dataList.get(i).get(j);
			}		

		}
		table = new JTable(dataArray, columnNames);
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
		
		validation = new JLabel("");
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
			}catch(Exception e) {
				data[i] = null;
			}
		}
		return data;
	}
	
	
	public JPanel getPanel() {
		return panel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == selectTable) { //User has selected a table in the combobox
			if(displayTable != null) {
				panel.remove(displayTable);
			}
			
			panel.add(displayTable(), gbc);
			panel.revalidate();
			panel.repaint();
		}
		
		if (e.getSource() == submit) {
			String message = "";
			
			for(int i = 0;i<row.size();i++) {
				int index = row.get(i);
				String[] data = getDataFromTable(index);
				message = dbConnection.updateTable(selectTable.getSelectedItem().toString(), data, dbConnection.getColumnNames(selectTable.getSelectedItem().toString()));
			}
			validation.setText(message);
		}
		
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		row.add(e.getFirstRow()); // Stores which rows has been changed	
	}

}
