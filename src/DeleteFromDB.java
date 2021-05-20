import javax.swing.*;
import javax.swing.table.AbstractTableModel;
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
import java.util.*;

public class DeleteFromDB {
	
	private JPanel panel;
	private JTable DataTable;
	private TableRowSorter<TableModel> sorter;
	private JTextField SearchField;
	private JComboBox<String> TableList;
	private TableModel   model;
	private String[] columns = {""};
	private Object[][] Rows;
	private JButton deleteButton;
	private String table;
	
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
	Object[][] GetRows() {

		List<List<String>> ListTable = DBConnection.getTable(table);
		Object[][] data = new Object[ListTable.size()][columns.length];
		
		for (int i = 0; i < ListTable.size(); i++) {
			
			Object[] row = new Object[columns.length]; 
			
			row[0] = false;
			
			for (int r = 1; r < row.length; r++) {
				
				if (ListTable.get(i).get(r-1) == null) {
					
					row[r] = "";
				} else {
					
					row[r] = ListTable.get(i).get(r-1);
				}
			} 
			
			data[i] = row;
		}
		
		return data;
	}
	
	
	//Creates the center panel
	JScrollPane ScrollPanel() {
		
		DataTable = new JTable();
		table = "customers";
		UpdateTable();
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
				 //JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected data?", "Warning Message",  JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE); 
				
				for (int i = 0; i < DataTable.getRowCount(); i++) {
					
					boolean cell = (boolean)DataTable.getValueAt(i, 0);
					if (cell) {
						
						DBConnection.delete(table, (String)DataTable.getValueAt(i, 1));
					}
				}
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
				table = TableList.getSelectedItem().toString();
				UpdateTable();
			}
		});
		
		panel.add(searchLabel);
		panel.add(SearchField);
		panel.add(TableList);
		panel.add(deleteButton);

		return panel;
	}
	
	//function to update the table based on a table name
	void UpdateTable() {
		
		String[] tempColumns = DBConnection.getColumnNames(table);
		
		columns = new String[tempColumns.length + 1];
		columns[0] = "Delete";
		for (int i = 1; i < columns.length; i++) {
			
			columns[i] = tempColumns[i-1];
		}
		
		Rows = GetRows();
		model = new model();
		
		DataTable.setModel(model);
		SearchField.setText(null);

	}
	class model extends AbstractTableModel {
		
        String[] columnNames = columns;
        Object[][] data = Rows;
                
        public int getColumnCount() {
        	
            return columnNames.length;
        }

        public int getRowCount() {
        	
            return data.length;
        }

        public String getColumnName(int col) {
        	
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
        	
            return data[row][col];
        }

        //Makes the table render a checkmark instead of a true/false
        public Class getColumnClass(int c) {
        	
            return getValueAt(0, c).getClass();
        }

        //Makes sure only the first column is editable
        public boolean isCellEditable(int row, int col) {
        	
            if (col == 0) {
            	
                return true;
            } else {
            	
                return false;
            }
        }

        public void setValueAt(Object value, int row, int col) {
        	
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }
		
	
	//Return the main panel
	public JPanel getPanel() {
		return panel;
	}
	
}


