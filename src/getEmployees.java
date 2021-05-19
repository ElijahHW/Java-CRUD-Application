import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class getEmployees {
	
	private String SearchString = "";
	private JPanel panel;
	private JTable DataTable;
	private TableRowSorter<TableModel> sorter;
	private JTextField searchField;
	private JButton ExportButton;
	private JFileChooser PathChooser;
	private JLabel headerLabel, filterLabel;
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
	JScrollPane ScrollPanel() 
	{
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
		TableModel model = new DefaultTableModel(rows, columns) 
		{
		//an override needed to stop the table from being editable
		@Override public boolean isCellEditable(int row, int column) {return false;}
		};
		DataTable = new JTable(model);
		DataTable.setAutoCreateRowSorter(true);
		DataTable.setCellSelectionEnabled(true);

		//defines a sorter to allow the search function to work properly
		sorter = new TableRowSorter<TableModel>(model);
		DataTable.setRowSorter(sorter);
		
		//puts the entire table in a scrollPane to allow for the scrollBar as the table itself is rather large
		JScrollPane ScrollPanel = new JScrollPane(DataTable); 
		ScrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);
		return ScrollPanel;
	}
	
		//Generates the top panel
		JPanel FilterSearchPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(300, 30));
		searchField.setBackground(Color.WHITE);

		headerLabel = new JLabel();
		headerLabel.setFont(new Font(null,Font.BOLD,20));
		headerLabel.setForeground(new Color(0x203c56));
		headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		headerLabel.setText("Table of all Employee Data");
		
		filterLabel = new JLabel("Search: ");
		filterLabel.setFont(new Font(null, Font.BOLD,15));
		
		ExportButton = new JButton("Export to File");
		ExportButton.setBackground(Color.WHITE);
		Icon iconD = UIManager.getIcon("FileView.floppyDriveIcon");
		ExportButton.setIcon(iconD);

		PathChooser = new JFileChooser();
		PathChooser.setDialogTitle("Export to...");
		PathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		PathChooser.setBackground(Color.WHITE);
		
		panel.add(headerLabel);
		panel.add(filterLabel);
		panel.add(searchField);
		panel.add(ExportButton);
		
		//A listener to initiate exporting the table. It gets the path from the filechooser and adds a filename 
		ExportButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				int ReturnValue = PathChooser.showSaveDialog(null);
				if (ReturnValue == JFileChooser.APPROVE_OPTION) {
				ExportTable(DataTable, PathChooser.getSelectedFile().getAbsolutePath() + "//EmployeesData.txt");
				}
			}
		});
		
		//a rather lengthy listener to add a onKeyUp function to the searchbar
		searchField.addKeyListener(new KeyAdapter() 
		{
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
		
		
	//Return the main panel
	public JPanel getPanel() {
		return panel;
	}

}