import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class DeleteFromDB {
	
	private JPanel panel;
	private JTable DataTable;
	private TableRowSorter<TableModel> sorter;
	private JTextField SearchField;
	private JComboBox<String> TableList;
	private TableModel model;
	private String[] columns = {""};
	private JButton deleteButton;
	private JCheckBox checkBox = null;
	
	public DeleteFromDB() {
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 0.2;
		c.fill = GridBagConstraints.BOTH;
		panel.add(SearchPanel(), c);
		
		c.gridx = 90;
		c.gridy = 0;
		c.gridheight = 100;
		c.weightx = 0; 
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(DeletePanel(), c);
		
		c.gridx = 0;
		c.gridy = 10;
		c.gridheight = 90;
		c.gridwidth = 90;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		panel.add(ScrollPanel(), c); 
	}
	
	
	//A function to convert the list list from the database into a 2d object array with date value types in the correct places
	String[][] GetRows(String table, String[] columns) {
		List<List<String>> ListTable = DBConnection.getTable(table);
		String[][] data = new String[ListTable.size()][columns.length];
		for (int i = 0; i < ListTable.size(); i++) {
			String[] row = new String[ListTable.get(i).size()];
			for (int r = 0; r < row.length; r++) {
				row[r] = ListTable.get(i).get(r);
				
			}
			
			data[i] = row;
			
		}
		
		return data;
	}
	
	
	//Creates the center panel
	JScrollPane ScrollPanel() {
		
		DataTable = new JTable();
		UpdateTable("customers");
		DataTable.setAutoCreateRowSorter(true);
		DataTable.getTableHeader().setReorderingAllowed(false);
		DataTable.setCellSelectionEnabled(true);

		//defines a sorter to allow the search function to work properly
		sorter = new TableRowSorter<TableModel>(model);
		DataTable.setRowSorter(sorter);
		
		//puts the entire table in a scrollPane to allow for the scrollBar as the table itself is rather large
		JScrollPane ScrollPanel = new JScrollPane(DataTable); 
		ScrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);

		return ScrollPanel;
	}
	
	//Creates the right panel with the choice of column to sort by
	JPanel DeletePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(9, 1));
				
		//A listener to initiate deleting the table. It gets the path from the fileChooser and adds a filename 
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected data?", "Warning Message",  JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE); 
			}
		});
		return panel;
	}
	
	//a function to update the sorter when the displayed table changes
	void UpdateSorter() {
		sorter = new TableRowSorter<TableModel>(model);
		DataTable.setRowSorter(sorter);
	}
	
	//Generates the top panel
	JPanel SearchPanel() {
		deleteButton = new JButton("Delete data");
		deleteButton.setBackground(Color.WHITE);
		Icon iconD = UIManager.getIcon("FileView.floppyDriveIcon");
		deleteButton.setIcon(iconD);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());	

		JLabel searchLabel = new JLabel("Search: ");
		SearchField = new JTextField();
		SearchField.setPreferredSize(new Dimension(300, 30));
		SearchField.setBackground(Color.WHITE);
		
		String[] TableArray = {"customers", 
				"employees",
				"offices",
				"orderdetails",
				"orders",
				"payments",
				"productlines",
				"products"};
		
		TableList = new JComboBox<String>(TableArray);
		TableList.addActionListener(new ActionListener () {
			
		public void actionPerformed(ActionEvent e) {
				UpdateTable(TableList.getSelectedItem().toString());
			}
		});
		
		panel.add(searchLabel);
		panel.add(SearchField);
		panel.add(TableList);
		panel.add(deleteButton);

		return panel;
	}
	
	//function to update the table based on a table name
	void UpdateTable(String tableName) {
		
		columns = DBConnection.getColumnNames(tableName);
		String[][] rows = GetRows(tableName, columns);
		model = new DefaultTableModel(rows, columns) {
		
			//an override needed to stop the table from being editable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};;
		DataTable.setModel(model);
		SearchField.setText(null);

	}
		
	//Return the main panel
	public JPanel getPanel() {
		return panel;
	}

}
