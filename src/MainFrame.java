import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import javax.swing.*;
public class MainFrame extends JFrame implements ActionListener {
	 	
		private JMenuBar menuBar;
	    private JMenu fileMenu, actionMenu, helpMenu;
	    private JMenuItem addItem, listItem, retrieveItem, importItem, storeItem, exitItem;
	    
	
	MainFrame() {
		// Defining application size, layout and close operation.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setLayout(new FlowLayout());

		// Defining application MenuBar.
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Actions");
        helpMenu = new JMenu("Help");
        
		// Defining the MenuItems under each JMenu.
        addItem = new JMenuItem("Add Customer");
        listItem = new JMenuItem("List Orders");
        retrieveItem = new JMenuItem("Retrieve Employees");
        importItem = new JMenuItem("Import from File");
        storeItem = new JMenuItem("Store to file");
        exitItem = new JMenuItem("Exit Application");
        
        //Adding shortcuts to each JMenu for accessibility.
        //These can be used by pressing 'alt + key', the key will be underlined.
        fileMenu.setMnemonic(KeyEvent.VK_F); 
        actionMenu.setMnemonic(KeyEvent.VK_A); 
        helpMenu.setMnemonic(KeyEvent.VK_H); 
        
        //Adding shortcuts to each JMenu for accessibility.
        //These can be used by pressing 'key', the key will be underlined.
        addItem.setMnemonic(KeyEvent.VK_A); //key
        listItem.setMnemonic(KeyEvent.VK_L); //key
        retrieveItem.setMnemonic(KeyEvent.VK_R); //key
        importItem.setMnemonic(KeyEvent.VK_I); //key 
        storeItem.setMnemonic(KeyEvent.VK_S); //key
        exitItem.setMnemonic(KeyEvent.VK_E); //key
        
        //Adding each Item to its JMenu Parent
        actionMenu.add(addItem);
        actionMenu.add(listItem);
        actionMenu.add(retrieveItem);
        actionMenu.add(importItem);
        actionMenu.add(storeItem);
        fileMenu.add(exitItem);
        
        addItem.addActionListener(this);
        listItem.addActionListener(this);
        retrieveItem.addActionListener(this);
        storeItem.addActionListener(this);
        storeItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
        menuBar.add(helpMenu);
        menuBar.setFocusable(false);
        
        //Adding MenuBar to Application and setting it visible.
        this.setJMenuBar(menuBar);
        this.setVisible(true);
        this.setTitle("Application");
        
	}
	
	//Defining the actions of the MenuBar
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addItem) {
			
		}
		if(e.getSource()==listItem) {
			
		}
		if(e.getSource()==retrieveItem) {
			getEmployees getemployees = new getEmployees();
			
		}		
		if(e.getSource()==importItem) {
			
		}
		if(e.getSource()==storeItem) {
			
		}
		
		if(e.getSource()==exitItem) {
			 String[] exitResponse = {"Yes", "No"};
			 System.out.println("Closing Application...");
			 try {
                 Thread.sleep(1000);
    			 if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Application Manager",
    					 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
    				 		System.exit(0);
    				} else {
    				    // do nothing :)
    				}
             } catch (InterruptedException interruptedException) {
                 interruptedException.printStackTrace();
             }
             
         }
		}
}

