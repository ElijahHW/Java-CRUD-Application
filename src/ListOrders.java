import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class ListOrders {
	
	String SearchString = "";
	JPanel panel;
	JTable DataTable;
	TableRowSorter<TableModel> sorter;
	JTextField SearchField;
	
	public ListOrders() {
		
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
		panel.add(SorterChoicePanel(), c);
		
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
	Object[][] GetOrders() {
		
		List<List<String>> ListTable = dbConnection.getTable("orders");
		Object[][] data = new Object[ListTable.size()][7];
		
		for (int i = 0; i < ListTable.size(); i++) {
			
			Object[] row = new Object[ListTable.get(i).size()];
			
			for (int r = 0; r < row.length; r++) {
				
				if (r >= 1 && r <= 3) {
					
					if (ListTable.get(i).get(r) != null && !ListTable.get(i).get(r).equals("")) {
						
						//if the column is between 1 and 3, the value should be a date
						Date date;
						try {
							
							date = new SimpleDateFormat("yyyy-MM-dd").parse(ListTable.get(i).get(r));
						} catch (ParseException e) {
							
							System.out.println("couldn't convert date at GetOrder()");
							System.out.println(e);
							
							date = null;
						}
						row[r] = date;
					}
				}
				
				row[r] = ListTable.get(i).get(r);
			}
			
			data[i] = row;
			
		}
		
		return data;
	}
	
	
	//A function to export the table into a file at a given path
	void ExportTable(JTable table, String savePath) {
		
		try {
			
			FileWriter TableExport = new FileWriter(savePath);
			BufferedWriter WriterBuffer = new BufferedWriter(TableExport);
			
			for (int i = 0; i < table.getRowCount(); i++) {
				
				for (int r = 0; r < table.getColumnCount(); r++) {
					
					if (table.getValueAt(i, r) != null && !table.getValueAt(i, r).equals("")) {
						
						WriterBuffer.write(table.getValueAt(i, r).toString() + " ");
					} else {
						
						//in case of a null or empty value
						WriterBuffer.write(" - ");
					}
				}
				
				WriterBuffer.newLine();
			}
			
			System.out.println("File Written");
			
			WriterBuffer.close();
			
		} catch (IOException e) {
			
			System.out.println("failed to export file");
			System.out.println(e);
		}
	}
	
	//Creates the center panel
	JScrollPane ScrollPanel() {
		
		//Provides the Column names for the main table
		Object columns[] = { "Order Number", 
				"Order Date", 
				"Required Date", 
				"Shipped Date", 
				"Status", 
				"Comments", 
				"Customer Number" };
		//sets a 2d object array to a 2d array generated from the database
		Object rows[][] = GetOrders();
		
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
		
		return ScrollPanel;
	}
	
	//Creates the right panel with the choice of column to sort by
	JPanel SorterChoicePanel() {
		
		JLabel FilterButtonLabel = new JLabel("Filter by: ");
		
		String[] SearchArray = {"Order Number", 
				"Order Date", 
				"Required Date", 
				"Shipped Date", 
				"Status", 
				"Comments", 
				"Customer Number"};
		
		JComboBox<String> SearchFilterColumns = new JComboBox<String>(SearchArray);
		SearchFilterColumns.setPreferredSize(new Dimension(100, 50));
		SearchFilterColumns.setMaximumSize(new Dimension(200, 50));
		
		JButton ExportButton = new JButton("Export to File");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(9, 1));
		
		//adds all the buttons to a panel as well to display them
		panel.add(FilterButtonLabel);
		panel.add(SearchFilterColumns);
		panel.add(ExportButton);
		
		
		//Creates a filechooser to select a folder
		JFileChooser PathChooser = new JFileChooser();
		PathChooser.setDialogTitle("Export to...");
		PathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		//A listener to initiate exporting the table. It gets the path from the filechooser and adds a filename 
		ExportButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				int ReturnValue = PathChooser.showOpenDialog(null);
				
				if (ReturnValue == JFileChooser.APPROVE_OPTION) {
					
					ExportTable(DataTable, PathChooser.getSelectedFile().getAbsolutePath() + "//OrderTable.txt");
				}
			}
		});
		
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
                		
                		int SelectedSortButton = SearchFilterColumns.getSelectedIndex();
                		
                		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + SearchString, SelectedSortButton));
                	} catch (PatternSyntaxException pse) {
                		
                		System.out.println("Failed to search");
                		System.out.println(pse);
                	}
                }
            }
        });
		
		return panel;
	}
	
	//Generates the top panel
	JPanel FilterSearchPanel() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		SearchField = new JTextField();
		SearchField.setPreferredSize(new Dimension(300, 30));
		
		JLabel FilterLabel = new JLabel("Search: ");

		panel.add(FilterLabel);
		panel.add(SearchField);
		
		return panel;
	}
	
	//Return the main panel
	public JPanel GetPanel() {
		
		return panel;
	}

}
