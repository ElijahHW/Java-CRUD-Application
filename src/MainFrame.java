import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import javax.swing.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
public class MainFrame extends JFrame implements ActionListener {
	 	
	//Header Elements
		private JMenuBar menuBar;
	    private JMenu homeMenu, fileMenu, actionMenu, helpMenu, dateMenu;
	    private JMenuItem addItem, listItem, retrieveItem, importItem, storeItem, exitItem;
	    
	  //Body Elements
	    private JPanel bodyPanel = new JPanel();
	    private JLabel welcomeLabel = new JLabel("Welcome to the Management Application");
	    private JButton addBtn, listBtn, retrieveBtn, importBtn, storeBtn;
	    
	   //Extra Elements for Body
	    DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd : HH:mm");  
	    LocalDateTime now = LocalDateTime.now();  
	
	    
    MainFrame() {
		// Defining application size, layout and close operation.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		///
		//Start of Header / Menu Bar
		///
		
		// Defining application MenuBar.
        menuBar = new JMenuBar();
        menuBar.setForeground(Color.BLACK);
        menuBar.setBackground(Color.WHITE);
        homeMenu = new JMenu("Home");
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Actions");
        helpMenu = new JMenu("Help");
        dateMenu = new JMenu(date.format(now)); 
        dateMenu.setEnabled(false);

		// Defining the MenuItems under each JMenu.
        addItem = new JMenuItem("Add Customer");
        listItem = new JMenuItem("List Orders");
        retrieveItem = new JMenuItem("Retrieve Employees");
        importItem = new JMenuItem("Import from File");
        storeItem = new JMenuItem("Store to file");
        exitItem = new JMenuItem("Exit Application");
        
        //Adding shortcuts to each JMenu for accessibility.
        //These can be used by pressing 'alt + key', the key will be underlined.
        homeMenu.setMnemonic(KeyEvent.VK_H); 
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
        importItem.addActionListener(this);
        storeItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        menuBar.add(homeMenu);
        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
        menuBar.add(helpMenu);
        menuBar.add(dateMenu);
        menuBar.setFocusable(false);
        
        ///
        //Start of BodyPanel / Main Part of Program
        ///
        GridLayout bodyLayout = new GridLayout(6,3);
        bodyLayout.setHgap(10);
        bodyLayout.setVgap(10);
        bodyPanel.setLayout(bodyLayout);
        welcomeLabel.setFont(new Font(null,Font.BOLD,15));
        
        addBtn = new JButton("Add Customer");
        addBtn.setFocusable(false);
        addBtn.setForeground(Color.BLACK);
        addBtn.setBackground(Color.WHITE);
        listBtn = new JButton("List Orders");
        listBtn.setFocusable(false);
        listBtn.setForeground(Color.BLACK);
        listBtn.setBackground(Color.WHITE);
        retrieveBtn = new JButton("Retrieve Employees");
        retrieveBtn.setFocusable(false);
        retrieveBtn.setForeground(Color.BLACK);
        retrieveBtn.setBackground(Color.WHITE);
        importBtn = new JButton("Bulk Import from file");
        importBtn.setFocusable(false);
        importBtn.setForeground(Color.BLACK);
        importBtn.setBackground(Color.WHITE);
        storeBtn = new JButton("Store Results to file");
        storeBtn.setFocusable(false);
        storeBtn.setForeground(Color.BLACK);
        storeBtn.setBackground(Color.WHITE);
        
        bodyPanel.add(welcomeLabel);
        bodyPanel.add(addBtn);
        bodyPanel.add(listBtn);
        bodyPanel.add(retrieveBtn);
        bodyPanel.add(importBtn);
        bodyPanel.add(storeBtn);
        
        //Adding MenuBar to Application and setting it visible.
        this.setJMenuBar(menuBar);
        this.add(bodyPanel);
        this.setVisible(true);
        this.setTitle("Application");
        }
	
	//Defining the actions of the MenuBar
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==homeMenu) {
			
		}
		if(e.getSource()==addItem) {
			
		}
		if(e.getSource()==listItem) {
			ListOrders panel = new ListOrders();
			this.setContentPane(panel.GetPanel());
			this.revalidate();
			this.repaint();
		}
		if(e.getSource()==retrieveItem) {
			//getEmployees getemployees = new getEmployees();
			
		}		
		if(e.getSource()==importItem) {
			ImportFromFile panel = new ImportFromFile();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
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

