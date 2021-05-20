import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class DeleteFromDB {
	
	private JPanel panel, DeletePanel, SearchPanel;
	private JTable DataTable;
	private TableRowSorter<TableModel> sorter;
	private JTextField SearchField;
	private JComboBox<String> TableList;
	private TableModel model;
	private String[] columns = {""};
	private Object[][] Rows;
	private JButton deleteButton;
	private String table, SearchString = "";
	private JLabel ResponseText, searchLabel;
	private JScrollPane ScrollPanel;
	public DeleteFromDB() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
	    
		GridBagConstraints c = new GridBagConstraints();
        
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 0.2;
		c.fill = GridBagConstraints.BOTH;
		panel.add(SearchPanel());
	
		c.gridy = 0;
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
		ScrollPanel = new JScrollPane(DataTable); 
		ScrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);

		return ScrollPanel;
	}
	
	//Creates the right panel with the choice of column to sort by
	JPanel DeletePanel() {
		DeletePanel = new JPanel();
		DeletePanel.setLayout(new FlowLayout(FlowLayout.LEADING) );
				
		//A listener to initiate deleting the table. It gets the path from the fileChooser and adds a filename 
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 //JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected data?", "Warning Message",  JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE); 
				boolean failed = false;
				List<String> passed = new ArrayList<String>();
				
				for (int i = 0; i < DataTable.getRowCount(); i++) {
					
					boolean cell = (boolean)DataTable.getValueAt(i, 0);
					if (cell) {
						
						String key = (String)DataTable.getValueAt(i, 1);
						int response = 0;
						
						if (table.equals("orderdetails")) {
							response = DBConnection.delete(table, (String)columns[1], key + "' AND productCode = '" + (String)DataTable.getValueAt(i, 2));
						} else {
							
							response = DBConnection.delete(table, (String)columns[1], key);
						}
						
						String responseText = "";
						switch(response) {
							case 0:
								passed.add(key);
								break;
							case 1:
								
								responseText = "Foreign key constraint error at id: " + key;
								failed = true;
								break;
							case 2:
								
								responseText = "Something went wrong";
								failed = true;
								break;
						}
						
						if (failed && passed.size() > 0) {
							
							String outPut = "";
							
							for(String n : passed) {
								
								outPut += n + ", ";
							}
							outPut += "were deleted successfully. ";
							
							ResponseText.setText(outPut + responseText);
							UpdateTable();
							break;
						} else if (failed && passed.size() == 0) {
							
							ResponseText.setText(responseText);
							UpdateTable();
							break;
						}
						
					}
				}
				
				if (!failed) {
					
					ResponseText.setText("All rows deleted");
					UpdateTable();
				}
			}
		});
		
		ResponseText = new JLabel();
		
		DeletePanel.add(ResponseText);
		
		return DeletePanel;
	}
	
	//a function to update the sorter when the displayed table changes
	void UpdateSorter() {
		sorter = new TableRowSorter<TableModel>(model);
		DataTable.setRowSorter(sorter);
	}
	
	//Generates the top panel
	JPanel SearchPanel() {
		SearchPanel = new JPanel();
		SearchPanel.setLayout(new FlowLayout(FlowLayout.LEADING) );
		
		
		deleteButton = new JButton("Delete data");
		deleteButton.setBackground(Color.WHITE);
		Icon iconD = UIManager.getIcon("FileView.floppyDriveIcon");
		deleteButton.setIcon(iconD);
		
		

		searchLabel = new JLabel("Search: ");
		SearchField = new JTextField();
		SearchField.setPreferredSize(new Dimension(100, 25));
		SearchField.setBackground(Color.WHITE);
		SearchField.setToolTipText("Search in the first column of all tables");
		
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
		
		//a rather lengthy listener to add a onKeyUp function to the searchBar
		SearchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                SearchString = textField.getText();
                
                //Checks what is selected
                if (SearchString.length() == 0) {
                	
                	sorter.setRowFilter(null);
                } else {
                	
                	try {
                		
                		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + SearchString, 1));
                	} catch (PatternSyntaxException pse) {
                		
                		System.out.println("Failed to search");
                		System.out.println(pse);
                	}
                }
            }
        });
		
		SearchPanel.add(searchLabel);
		SearchPanel.add(SearchField);
		SearchPanel.add(TableList);
		SearchPanel.add(deleteButton);

		return SearchPanel;
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
		
		UpdateSorter();

	}
	
	//Again, the warning should go away during compilation
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


