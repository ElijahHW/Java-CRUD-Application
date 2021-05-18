import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
		
		panel.add(FilterSearchPanel(), BorderLayout.NORTH);
		panel.add(SorterChoicePanel(), BorderLayout.EAST);
		panel.add(ScrollPanel(), BorderLayout.CENTER); 
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
	
	JPanel SorterChoicePanel() {
		
		JLabel FilterButtonLabel = new JLabel("Filter by: ");
		
		JRadioButton OrderNumberButton = new JRadioButton("Order Number", true);
		JRadioButton OrderDateButton = new JRadioButton("Order Date");
		JRadioButton RequiredDateButton = new JRadioButton("Required Date");
		JRadioButton ShippedDateButton = new JRadioButton("Shipped Date");
		JRadioButton StatusButton = new JRadioButton("Status");
		JRadioButton CommentsButton = new JRadioButton("Comments");
		JRadioButton CustomerNumberButton = new JRadioButton("Customer Number");
		
		ButtonGroup SearchButtons = new ButtonGroup();
		
		//adds all the radio buttons to a buttonset so that only one will be active at a time
		SearchButtons.add(OrderNumberButton);
		SearchButtons.add(OrderDateButton);
		SearchButtons.add(RequiredDateButton);
		SearchButtons.add(ShippedDateButton);
		SearchButtons.add(StatusButton);
		SearchButtons.add(CommentsButton);
		SearchButtons.add(CustomerNumberButton);
		
		JButton ExportButton = new JButton("Export to File");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(9, 1));
		
		//adds all the buttons to a panel as well to display them
		panel.add(FilterButtonLabel);
		panel.add(OrderNumberButton);
		panel.add(OrderDateButton);
		panel.add(RequiredDateButton);
		panel.add(ShippedDateButton);
		panel.add(StatusButton);
		panel.add(CommentsButton);
		panel.add(CustomerNumberButton);
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
                
                //Checks what radio button is selected
                if (SearchString.length() == 0) {
                	
                	sorter.setRowFilter(null);
                } else {
                	
                	try {
                		
                		int SelectedSortButton = 0;
                		
                		if (OrderNumberButton.isSelected()) {
                			
                			SelectedSortButton = 0;
                		} else if (OrderDateButton.isSelected()) {
                			
                			SelectedSortButton = 1;
                		} else if (RequiredDateButton.isSelected()) {
                			
                			SelectedSortButton = 2;
                		} else if (ShippedDateButton.isSelected()) {
                			
                			SelectedSortButton = 3;
                		} else if (StatusButton.isSelected()) {
                			
                			SelectedSortButton = 4;
                		} else if (CommentsButton.isSelected()) {
                			
                			SelectedSortButton = 5;
                		} else {
                			
                			SelectedSortButton = 6;
                		}
                		
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
	
	public JPanel GetPanel() {
		
		return panel;
	}

}
