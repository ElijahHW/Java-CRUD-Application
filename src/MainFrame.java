import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
public class MainFrame extends JFrame implements ActionListener {
	 	
	
	//Header Elements
		private JMenuBar menuBar;
	    private JMenu fileMenu, actionMenu, dateMenu;
	    private JMenuItem addItem, listItem, retrieveItem, editItem, importItem, exitItem;
	    private JButton homeBtn;
	    
	  //Body Elements
	    private JPanel panel = new JPanel();
	    private JLabel welcomeLabel = new JLabel("Welcome to the Management Application");
	    private JButton addBtn, listBtn, retrieveBtn, editBtn, importBtn;
	    
	  //Extra Elements for Body
	    DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd : HH:mm");  
	    LocalDateTime now = LocalDateTime.now();  
	
	    
    MainFrame() {
		// Defining application size, layout and close operation.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 550);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		///
		//Start of Header / Menu Bar
		///
		
		// Defining application MenuBar.
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        
        homeBtn = new JButton("Home");
        homeBtn.setFocusable(false);
        homeBtn.setBackground(Color.WHITE);
        homeBtn.setBorder(null);
        homeBtn.setBorder(new EmptyBorder(0, 10, 0, 10));

        actionMenu = new JMenu("Actions");
        dateMenu = new JMenu(date.format(now)); 
        dateMenu.setEnabled(false);
        
        fileMenu = new JMenu("File");
        

		// Defining the MenuItems under each JMenu.
        addItem = new JMenuItem("Add Customer");
        addItem.setBackground(Color.WHITE);

        listItem = new JMenuItem("List Orders");
        listItem.setBackground(Color.WHITE);

        retrieveItem = new JMenuItem("Retrieve Employees");
        retrieveItem.setBackground(Color.WHITE);
        
        editItem = new JMenuItem("Edit Data in DB");
        editItem.setBackground(Color.WHITE);
        
        importItem = new JMenuItem("Import from File");
        importItem.setBackground(Color.WHITE);

        exitItem = new JMenuItem("Exit Application");
        exitItem.setBackground(Color.WHITE);

        
        //Adding shortcuts to each JMenu for accessibility.
        //These can be used by pressing 'alt + key', the key will be underlined.
        fileMenu.setMnemonic(KeyEvent.VK_F); 
        actionMenu.setMnemonic(KeyEvent.VK_A); 
        
        //Adding shortcuts to each JMenu for accessibility.
        //These can be used by pressing 'key', the key will be underlined.
        addItem.setMnemonic(KeyEvent.VK_A); //key
        listItem.setMnemonic(KeyEvent.VK_L); //key
        retrieveItem.setMnemonic(KeyEvent.VK_R); //key
        editItem.setMnemonic(KeyEvent.VK_E); //key
        importItem.setMnemonic(KeyEvent.VK_I); //key 
        exitItem.setMnemonic(KeyEvent.VK_E); //key
        
        //Adding each Item to its JMenu Parent
        actionMenu.add(addItem);
        actionMenu.add(listItem);
        actionMenu.add(retrieveItem);
        actionMenu.add(editItem);
        actionMenu.add(importItem);
        fileMenu.add(exitItem);
        
        homeBtn.addActionListener(this);
        addItem.addActionListener(this);
        listItem.addActionListener(this);
        retrieveItem.addActionListener(this);
        editItem.addActionListener(this);
        importItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        menuBar.add(homeBtn);
        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
        menuBar.add(dateMenu);
        menuBar.setFocusable(false);
        
        
        ///
        //Start of panel / Main Part of Program
        ///
        GridLayout layout = new GridLayout(6, 1);//create grid layout frame
        layout.setHgap(10);
        layout.setVgap(10);
        panel.setLayout(layout);
        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font(null,Font.BOLD,15));
        
        addBtn = new JButton("Add Customer");
        addBtn.setFocusable(false);
        addBtn.setBackground(Color.WHITE);
        addBtn.addActionListener(this);
        
        listBtn = new JButton("List Orders");
        listBtn.setFocusable(false);
        listBtn.setBackground(Color.WHITE);
        listBtn.addActionListener(this);

        retrieveBtn = new JButton("Retrieve Employees");        
        retrieveBtn.setFocusable(false);
        retrieveBtn.setBackground(Color.WHITE);
        retrieveBtn.addActionListener(this);
        
        editBtn = new JButton("Edit Data in DB");        
        editBtn.setFocusable(false);
        editBtn.setBackground(Color.WHITE);
        editBtn.addActionListener(this);
        
        importBtn = new JButton("Bulk Import from file");
        importBtn.setFocusable(false);
        importBtn.setBackground(Color.WHITE);
        importBtn.addActionListener(this);
        
        panel.add(welcomeLabel);
        panel.add(addBtn);
        panel.add(listBtn);
        panel.add(retrieveBtn);
        panel.add(editBtn);
        panel.add(importBtn);
        
        //Adding MenuBar to Application and setting it visible.
        this.setJMenuBar(menuBar);
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
        this.setTitle("Management Application - Home");
        this.setPreferredSize(new Dimension(800,550));
        this.pack();
        }
	
	//Defining the actions of the MenuBar
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == homeBtn) {
			this.getContentPane().removeAll();
            this.add(panel);
            this.repaint();
			this.revalidate();
			this.pack();
		}
		if(e.getSource() == addItem || e.getSource() == addBtn) {
	        this.setTitle("Management Application - Add Customer");
			addCustomerView panel = new addCustomerView();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
			this.pack();
			
		} 
		if(e.getSource() == listItem || e.getSource() == listBtn) {
	        this.setTitle("Management Application - List Orders");
			ListOrders panel = new ListOrders();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();

		} 
		
		 if(e.getSource() == retrieveItem || e.getSource() == retrieveBtn) {
	        this.setTitle("Management Application - Retrieve Employees");
			getEmployees panel = new getEmployees();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();

		}
		 if(e.getSource() == editItem || e.getSource() == editBtn) {
		        this.setTitle("Management Application - Edit Tables");
				//editTables panel = new EditTables();
				//this.setContentPane(panel.getPanel());
				this.revalidate();
				this.repaint();
		        this.pack();

			}
		if(e.getSource() == importItem || e.getSource() == importBtn) {
	        this.setTitle("Management Application - Import from File");
			ImportFromFile panel = new ImportFromFile();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();

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

