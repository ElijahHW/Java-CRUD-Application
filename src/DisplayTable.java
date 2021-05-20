import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class DisplayTable {
	
	private String SearchString = "";
	private JPanel panel, ScrollPanel, FilterPanel, FilterPanelSticky, ExportPanel;
	private JTable DataTable;
	private TableRowSorter<TableModel> sorter;
	private JTextField searchField;
	private JComboBox<String> filterTableList, SearchFilterColumns;
	private TableModel model;
	private String[] columns = {""};
	private JLabel searchLabel, filterLabel;
	private JButton exportButton;
	
	public DisplayTable() {
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
        c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		panel.add(FilterPanel(), c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0; 
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(ExportPanel(), c);
		
		c.gridx = 0;
		c.gridy = 10;
		c.gridheight = 80;
		c.gridwidth = 50;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		panel.add(ScrollPanel(), c); 
	
	}
	
	//Generates the top panel
		JPanel FilterPanel() {
			
			FilterPanel = new JPanel();
			FilterPanel.setLayout(new FlowLayout(FlowLayout.LEFT) );

			FilterPanelSticky = new JPanel();
			FilterPanelSticky.setLayout(new FlowLayout(FlowLayout.LEFT) );
			
			filterLabel = new JLabel("Filter by: ");
			filterLabel.setFont(new Font(null, Font.BOLD, 15));
			
			SearchFilterColumns = new JComboBox<String>();
			SearchFilterColumns.setModel(new DefaultComboBoxModel<String>(DBConnection.getColumnNames("customers")));
			SearchFilterColumns.setBackground(Color.WHITE);
			
			searchField = new JTextField();
			searchField.setPreferredSize(new Dimension(100, 25));
			searchField.setBackground(Color.WHITE);

			searchLabel = new JLabel("Search: ");
			searchLabel.setFont(new Font(null, Font.BOLD, 15));

			String[] TableArray = {
					"customers", 
					"employees",
					"offices",
					"orderdetails",
					"orders",
					"payments",
					"productlines",
					"products"};
			filterTableList = new JComboBox<String>(TableArray);
			filterTableList.setPreferredSize(new Dimension(100, 25));
			filterTableList.setBackground(Color.WHITE);

			filterTableList.addActionListener(new ActionListener () {	
			public void actionPerformed(ActionEvent e) {
					UpdateTable(filterTableList.getSelectedItem().toString());
			}
			});
			
			//a rather lengthy listener to add a onKeyUp function to the searchBar
			searchField.addKeyListener(new KeyAdapter() {
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
			FilterPanelSticky.add(filterLabel);
			FilterPanelSticky.add(filterTableList);
			FilterPanel.add(searchLabel);
			FilterPanel.add(searchField);
			FilterPanel.add(FilterPanelSticky);
							
			return FilterPanel;
		}
		
	
	//Creates the right panel with the choice of column to sort by
	JPanel ExportPanel() {
		ExportPanel = new JPanel();
		ExportPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		exportButton = new JButton("Export to File");
		exportButton.setBackground(Color.WHITE);
		Icon iconD = UIManager.getIcon("FileView.floppyDriveIcon");
		exportButton.setIcon(iconD);
		
		
		//Creates a filechooser to select a folder
		JFileChooser PathChooser = new JFileChooser();
		PathChooser.setDialogTitle("Export to...");
		PathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		PathChooser.setBackground(Color.WHITE);

		//A listener to initiate exporting the table. It gets the path from the filechooser and adds a filename 
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ReturnValue = PathChooser.showSaveDialog(null);
				if (ReturnValue == JFileChooser.APPROVE_OPTION) {	
					ExportTable(DataTable, PathChooser.getSelectedFile().getAbsolutePath() + "//" + filterTableList.getSelectedItem().toString() + ".txt");
				}
			}
		});
		
		ExportPanel.add(exportButton);
		return ExportPanel;
	}
	//A function to export the table into a file at a given path
	void ExportTable(JTable table, String savePath) {
		try {
			FileWriter TableExport = new FileWriter(savePath);
			BufferedWriter WriterBuffer = new BufferedWriter(TableExport);
			for (int i = 0; i < table.getRowCount(); i++) {
				for (int r = 0; r < table.getColumnCount(); r++) {
					if (table.getValueAt(i, r) != null && !table.getValueAt(i, r).equals("")) {
						WriterBuffer.write(table.getValueAt(i, r).toString() + ";");
					} else {
						//in case of a null or empty value
						WriterBuffer.write(";");
					}
				}
				WriterBuffer.newLine();
			}
			System.out.println("File Written");
			JOptionPane.showMessageDialog(null, "File saved to your system.", "User Mangement - Export Data", JOptionPane.INFORMATION_MESSAGE);
			WriterBuffer.close();
			
		} catch (IOException e) {
			System.out.println("failed to export file");
			System.out.println(e);
		}
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
		
		//puts the entire table in a scrollpane to allow for the scrollbar as the table itself is rather large
		JScrollPane ScrollPanel = new JScrollPane(DataTable); 
		ScrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);

		return ScrollPanel;
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
	//a function to update the sorter when the displayed table changes
	void UpdateSorter() {
		sorter = new TableRowSorter<TableModel>(model);
		DataTable.setRowSorter(sorter);
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
		searchField.setText(null);

		UpdateFilterBox();
	}
	
	//Function to Update the filterbox
	void UpdateFilterBox() {
		
		SearchFilterColumns.setModel(new DefaultComboBoxModel<String>(columns));
		UpdateSorter();
	}
	
	//Return the main panel
	public JPanel getPanel() {
		return panel;
	}

}
