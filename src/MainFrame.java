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
	    private JPanel panel = new JPanel();
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
        menuBar.setBackground(Color.WHITE);
        homeMenu = new JMenu("Home");
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Actions");
        helpMenu = new JMenu("Help");
        dateMenu = new JMenu(date.format(now)); 
        dateMenu.setEnabled(false);

		// Defining the MenuItems under each JMenu.
        addItem = new JMenuItem("Add Customer");
        addItem.setBackground(Color.WHITE);

        listItem = new JMenuItem("List Orders");
        listItem.setBackground(Color.WHITE);

        retrieveItem = new JMenuItem("Retrieve Employees");
        retrieveItem.setBackground(Color.WHITE);
        
        importItem = new JMenuItem("Import from File");
        retrieveItem.setBackground(Color.WHITE);

        storeItem = new JMenuItem("Store to file");
        storeItem.setBackground(Color.WHITE);

        exitItem = new JMenuItem("Exit Application");
        exitItem.setBackground(Color.WHITE);

        
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
        
        homeMenu.addActionListener(this);
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
        
        ButtonGroup bodyBtns = new ButtonGroup();
        bodyBtns.add(addBtn);
        bodyBtns.add(listBtn);
        bodyBtns.add(retrieveBtn);
        bodyBtns.add(importBtn);
        bodyBtns.add(storeBtn);
        ///
        //Start of panel / Main Part of Program
        ///
        GridLayout layout = new GridLayout(6,3);
        layout.setHgap(10);
        layout.setVgap(10);
        panel.setLayout(layout);
        welcomeLabel.setFont(new Font(null,Font.BOLD,15));
        
        addBtn = new JButton("Add Customer");
        addBtn.setFocusable(false);
        addBtn.setBackground(Color.WHITE);
        listBtn = new JButton("List Orders");
        listBtn.setFocusable(false);
        listBtn.setBackground(Color.WHITE);
        retrieveBtn = new JButton("Retrieve Employees");
        retrieveBtn.setFocusable(false);
        retrieveBtn.setBackground(Color.WHITE);
        importBtn = new JButton("Bulk Import from file");
        importBtn.setFocusable(false);
        importBtn.setBackground(Color.WHITE);
        storeBtn = new JButton("Store Results to file");
        storeBtn.setFocusable(false);
        storeBtn.setBackground(Color.WHITE);
        
        panel.add(welcomeLabel);
        panel.add(addBtn);
        panel.add(listBtn);
        panel.add(retrieveBtn);
        panel.add(importBtn);
        panel.add(storeBtn);
        
        //Adding MenuBar to Application and setting it visible.
        this.setJMenuBar(menuBar);
        this.add(panel);
        this.setVisible(true);
        this.setTitle("Application");
        }
	
	//Defining the actions of the MenuBar
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==homeMenu) {
			MainFrame panel = new MainFrame();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
		}
		if(e.getSource()==addItem) {
			addCustomerView panel = new addCustomerView();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
		}
		if(e.getSource()==listItem) {
			ListOrders panel = new ListOrders();
			this.setContentPane(panel.GetPanel());
			this.revalidate();
			this.repaint();
		}
		if(e.getSource()==retrieveItem) {
			//getEmployees getEmployees = new getEmployees();
			//this.setContentPane(panel.getPanel());
			//this.revalidate();
			//this.repaint();
			
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
                 Thread.sleep(500);
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
	public JPanel getPanel() {
		return panel;
	}
}

