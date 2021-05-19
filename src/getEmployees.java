import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class getEmployees {
	
	String SearchString = "";
	private JPanel panel;
	
	public getEmployees() {
		
		panel = new JPanel();
		panel.setSize(500, 500);
		panel.setVisible(true);
		
		JTextField SearchField = new JTextField();
		SearchField.setBounds(0, 0, 200, 35);
		
		Object columns[] = { "EmployeeNumber", 
				"lastName", 
				"firstName", 
				"extension", 
				"email", 
				"officeCode", 
				"reportsTo",
				"jobTitle"};
		Object rows[][] = GetEmployees();
		
		
		JTable DataTable;
		TableModel model;
		model = new DefaultTableModel(rows, columns);
		DataTable = new JTable(model);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		DataTable.setRowSorter(sorter);
		JScrollPane ScrollPanel = new JScrollPane(DataTable); 
		ScrollPanel.setBounds(40, 40, 200, 300);
		
		panel.add(SearchField);
		panel.add(ScrollPanel); 
	
		
		SearchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                SearchString = textField.getText();
                if (SearchString.length() == 0) {
                	
                	sorter.setRowFilter(null);
                } else {
                	
                	try {
                		
                		sorter.setRowFilter(RowFilter.regexFilter(SearchString));
                	} catch (PatternSyntaxException pse) {
                		
                		System.out.println("Bad regex pattern");
                	}
                }
            }
        });
	}
	
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


	
	//Return the main panel
	public JPanel getPanel() {
		
		return panel;
	}

}