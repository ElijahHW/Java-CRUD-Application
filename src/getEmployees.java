import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class getEmployees {
	
	String SearchString = "";
	JPanel panel;
	JTable DataTable;
	TableRowSorter<TableModel> sorter;
	JTextField SearchField;
	
	public getEmployees() {
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 0.2;
		c.fill = GridBagConstraints.BOTH;
		panel.add(FilterSearchPanel(), c);
		
		c.gridx = 90;
		c.gridy = 0;
		c.gridheight = 100;
		c.weightx = 0; 
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		
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
	Object[][] GetEmployees() {
		
		List<List<String>> ListTable = dbConnection.getTable("employees");
		Object[][] data = new Object[ListTable.size()][7];
		
		for (int i = 0; i < ListTable.size(); i++) {
			
			Object[] row = new Object[ListTable.get(i).size()];
			
			for (int r = 0; r < row.length; r++) {
			
				
				row[r] = ListTable.get(i).get(r);
			}
			
			data[i] = row;
		}
		
		return data;
	}
	
	
	//Creates the center panel
	JScrollPane ScrollPanel() {
		
		//Provides the Column names for the main table
		Object columns[] ={ "EmployeeNumber", 
				"lastName", 
				"firstName", 
				"extension", 
				"email", 
				"officeCode", 
				"reportsTo",
				"jobTitle"};
		//sets a 2d object array to a 2d array generated from the database
		Object rows[][] = GetEmployees();
		
		//Creates a model for the table. The potential warning should disappear during compilation as far as I could understand
		TableModel model = new DefaultTableModel(rows, columns) {
			
			//an override needed to stop the table from being editable
			@Override
			public boolean isCellEditable(int row, int column) {
				
				return false;
			}
		};
		DataTable = new JTable(model);
		DataTable.setAutoCreateRowSorter(true);
		
		//defines a sorter to allow the search function to work properly
		sorter = new TableRowSorter<TableModel>(model);
		DataTable.setRowSorter(sorter);
		
		//puts the entire table in a scrollpane to allow for the scrollbar as the table itself is rather large
		JScrollPane ScrollPanel = new JScrollPane(DataTable); 
		ScrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);

		return ScrollPanel;
	}
	
	
	
	//Generates the top panel
	JPanel FilterSearchPanel() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		SearchField = new JTextField();
		SearchField.setPreferredSize(new Dimension(300, 30));
		SearchField.setBackground(Color.WHITE);

		JLabel FilterLabel = new JLabel("Search: ");

		panel.add(FilterLabel);
		panel.add(SearchField);
		
		//a rather lengthy listener to add a onKeyUp function to the searchbar
				SearchField.addKeyListener(new KeyAdapter() {
		            public void keyReleased(KeyEvent e) {
		                JTextField textField = (JTextField) e.getSource();
		                SearchString = textField.getText();
		                
		                //Checks what is selected
		                if (SearchString.length() == 0) {
		                	
		                	sorter.setRowFilter(null);
		                } else {
		                	
		                	try {
		                		
		                		
		                		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + SearchString, 7));
		                	} catch (PatternSyntaxException pse) {
		                		
		                		System.out.println("Failed to search");
		                		System.out.println(pse);
		                	}
		                }
		            }
		        });
		
		return panel;
	}
	
	//Return the main panel
	public JPanel getPanel() {
		return panel;
	}

}