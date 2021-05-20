import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
public class MainFrame extends JFrame implements ActionListener {
	
	  //Header Elements
		private JMenuBar menuBar;
	    private JMenu systemMenu, actionMenu, dateMenu, statusMenu;
	    private JMenuItem addItem, displayItem, deleteItem, updateItem, importItem, exitItem;
	    private JButton homeBtn, backBtn;
	    
	  //Body Elements
	    private JPanel panel = new JPanel();
	    private JLabel welcomeLabel = new JLabel("Welcome to the Management Application");
	    private JButton addBtn, displayBtn, deleteBtn, updateBtn, importBtn;

	  //Extra Elements for Body
	    private DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd : HH:mm");  
	    private LocalDateTime now = LocalDateTime.now();  
	    
	  //Previous panel
	    private JPanel PreviousPanel, StartPanel;

    MainFrame() {
    	
		// Defining application size, layout and close operation.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 550);
		this.setLayout(new FlowLayout());
		
		///
		//Start of Header / Menu Bar
		///
		
		// Defining application MenuBar.
		Image appIcon = Toolkit.getDefaultToolkit().getImage("src/assets/manager.png");
		this.setIconImage(appIcon);  
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        
        homeBtn = new JButton("Home");
        homeBtn.setToolTipText("There is no place like 127.0.0.1");
        homeBtn.setFocusable(false);
        homeBtn.setBackground(Color.WHITE);
        homeBtn.setFont(new Font(null, Font.BOLD,15));
        homeBtn.setForeground(new Color(0x203c56));
        homeBtn.setBorder(null);
        homeBtn.setBorder(new EmptyBorder(0, 10, 0, 10));

        actionMenu = new JMenu("Actions");
        actionMenu.setFont(new Font(null, Font.BOLD,15));
        actionMenu.setForeground(new Color(0x203c56));

        dateMenu = new JMenu(date.format(now)); 
        dateMenu.setFont(new Font(null, Font.BOLD,15));
        dateMenu.setEnabled(false);
        
        systemMenu = new JMenu("System");
        systemMenu.setFont(new Font(null, Font.BOLD,15));
        systemMenu.setForeground(new Color(0x203c56));

    	statusMenu = new JMenu("Connecting to DB...");
    	

		// Defining the MenuItems under each JMenu.
        addItem = new JMenuItem("Add Customer");
        addItem.setBackground(Color.WHITE);

        displayItem = new JMenuItem("Export / Display Tables");
        displayItem.setBackground(Color.WHITE);

        deleteItem = new JMenuItem("Delete Data from Database");
        deleteItem.setBackground(Color.WHITE);
        
        updateItem = new JMenuItem("Update Data in Database");
        updateItem.setBackground(Color.WHITE);
        
        importItem = new JMenuItem("Import from File");
        importItem.setBackground(Color.WHITE);
       
        exitItem = new JMenuItem("Exit Application");
        exitItem.setBackground(Color.WHITE);
        
        backBtn = new JButton("Back");
        backBtn.setFocusable(false);
        backBtn.setBackground(Color.WHITE);
        backBtn.setFont(new Font(null, Font.BOLD,15));
        backBtn.setForeground(new Color(0x203c56));
        backBtn.setBorder(null);
        backBtn.setBorder(new EmptyBorder(0, 10, 0, 10));
		backBtn.setVisible(false);

        
        //Adding shortcuts to each JMenu for accessibility.
        //These can be used by pressing 'alt + key', the key will be underlined.
        KeyStroke keyStrokeHomeBtn = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_DOWN_MASK);
        homeBtn.setMnemonic(KeyEvent.VK_H); 
        systemMenu.setMnemonic(KeyEvent.VK_S); 
        actionMenu.setMnemonic(KeyEvent.VK_A); 
        
        //Adding shortcuts/KeyStrokes to each JMenuItem for accessibility.
        //These can be used by pressing 'ctrl + key', the key will be underlined in the application.
        KeyStroke keyStrokeAddItem = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokedisplayItem = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokedeleteItem = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeupdateItem = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeImportItem = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeExitItem = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);

        addItem.setMnemonic(KeyEvent.VK_A); //key
        addItem.setAccelerator(keyStrokeAddItem);

        displayItem.setMnemonic(KeyEvent.VK_E); //key
        displayItem.setAccelerator(keyStrokedisplayItem);
        
        deleteItem.setMnemonic(KeyEvent.VK_D); //key
        deleteItem.setAccelerator(keyStrokedeleteItem);

        updateItem.setMnemonic(KeyEvent.VK_U); //key
        updateItem.setAccelerator(keyStrokeupdateItem);

        importItem.setMnemonic(KeyEvent.VK_I); //key 
        importItem.setAccelerator(keyStrokeImportItem);
        
        exitItem.setMnemonic(KeyEvent.VK_W); //key
        exitItem.setAccelerator(keyStrokeExitItem);

        //Adding each Item to its JMenu Parent
        actionMenu.add(addItem);
        actionMenu.add(displayItem);
        actionMenu.add(deleteItem);
        actionMenu.add(updateItem);
        actionMenu.add(importItem);
        systemMenu.add(exitItem);
        
        
        homeBtn.addActionListener(this);
        addItem.addActionListener(this);
        displayItem.addActionListener(this);
        deleteItem.addActionListener(this);
        updateItem.addActionListener(this);
        importItem.addActionListener(this);
        exitItem.addActionListener(this);
        backBtn.addActionListener(this);

        menuBar.add(homeBtn);
        menuBar.add(systemMenu);
        menuBar.add(actionMenu);
        menuBar.add(dateMenu);
        menuBar.add(backBtn);
		menuBar.add(Box.createHorizontalGlue()); 
    	menuBar.add(statusMenu);

        menuBar.setFocusable(false);
        
        
        ///
        //Start of panel / Main Part of Program
        ///
        GridLayout layout = new GridLayout(6, 1);//create grid layout frame
        layout.setHgap(10);
        layout.setVgap(10);
        panel.setLayout(layout);
        welcomeLabel.setFont(new Font(null,Font.BOLD,20));
        welcomeLabel.setForeground(new Color(0x203c56));

        addBtn = new JButton("Add Customer");
		addBtn.setToolTipText("Add a customer to the database.");
        addBtn.setFocusable(false);
        addBtn.setBackground(Color.WHITE);
        addBtn.setFont(new Font(null, Font.BOLD,15));
        addBtn.setForeground(new Color(0x102533));
        addBtn.addActionListener(this);
        
        displayBtn = new JButton("Export / Display Tables");
		displayBtn.setToolTipText("Display all tables and exports to a file");
        displayBtn.setFocusable(false);
        displayBtn.setBackground(Color.WHITE);
        displayBtn.setFont(new Font(null, Font.BOLD,15));
        displayBtn.addActionListener(this);

        deleteBtn = new JButton("Delete Data from Database");
        deleteBtn.setToolTipText("Delete data from selected table");
        deleteBtn.setFocusable(false);
        deleteBtn.setBackground(Color.WHITE);
        deleteBtn.setFont(new Font(null, Font.BOLD,15));
        deleteBtn.addActionListener(this);
        
        updateBtn = new JButton("Update data in Database DB");        
		updateBtn.setToolTipText("Updates data and saves to database  ");
        updateBtn.setFocusable(false);
        updateBtn.setBackground(Color.WHITE);
        updateBtn.setFont(new Font(null, Font.BOLD,15));
        updateBtn.addActionListener(this);
        
        importBtn = new JButton("Bulk Import from file");
        importBtn.setToolTipText("Imports data from a file and - to a table");
        importBtn.setFocusable(false);
        importBtn.setBackground(Color.WHITE);
        importBtn.setFont(new Font(null, Font.BOLD,15));
        importBtn.addActionListener(this);
        
        panel.add(welcomeLabel);
        panel.add(addBtn);
        panel.add(displayBtn);
        panel.add(deleteBtn);
        panel.add(updateBtn);
        panel.add(importBtn);
        
        //Adding MenuBar to Application and setting it visible.
        this.setJMenuBar(menuBar);
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
        this.setTitle("Management Application - Home");
        this.setPreferredSize(new Dimension(800,550));
        this.pack();
		this.setLocationRelativeTo(null);
		
		StartPanel = (JPanel)this.getContentPane();
		dbStatus();
        }

    
	//Defining the actions of the MenuBar
	@Override
	public void actionPerformed(ActionEvent e) {
		dbStatus();
		if(e.getSource() == homeBtn) {
	        this.setTitle("Management Application - Home");
			PreviousPanel = (JPanel)this.getContentPane();
			this.setContentPane(StartPanel);
            this.repaint();
			this.revalidate();
			this.pack();
			backBtn.setVisible(true);
		}
		if(e.getSource() == addItem || e.getSource() == addBtn) {
			PreviousPanel = (JPanel)this.getContentPane();
	        this.setTitle("Management Application - Add Customer");
			AddCustomerView panel = new AddCustomerView();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
			this.pack();
			backBtn.setVisible(true);
		} 
		if(e.getSource() == displayItem || e.getSource() == displayBtn) {
			PreviousPanel = (JPanel)this.getContentPane();
	        this.setTitle("Management Application - Export Table Data");
			DisplayTable panel = new DisplayTable();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();
	        backBtn.setVisible(true);
		} 
		
		 if(e.getSource() == deleteItem || e.getSource() == deleteBtn) {
			PreviousPanel = (JPanel)this.getContentPane();
	        this.setTitle("Management Application - Delete Data from DB");
			DeleteFromDB panel = new DeleteFromDB();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();
	        backBtn.setVisible(true);

		}
		 if(e.getSource() == updateItem || e.getSource() == updateBtn) {
				PreviousPanel = (JPanel)this.getContentPane();
		        this.setTitle("Management Application - Update Data");
				EditTablesPanel panel = new EditTablesPanel();
				this.setContentPane(panel.getPanel());
				this.revalidate();
				this.repaint();
		        this.pack();
				backBtn.setVisible(true);
			}
		 
		if(e.getSource() == importItem || e.getSource() == importBtn) {
			PreviousPanel = (JPanel)this.getContentPane();
	        this.setTitle("Management Application - Import from File");
			ImportFromFile panel = new ImportFromFile();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();
			backBtn.setVisible(true);
		} 
			
		if (e.getSource() == backBtn) {
			this.setTitle("Management Application");
			this.setContentPane(PreviousPanel);
			this.revalidate();
			this.repaint();
			this.pack();
			backBtn.setVisible(false);
		}
	
		//Defining what the Exit button should do.
		if(e.getSource()==exitItem) {
			 System.out.println("Closing Application...");
			 try {
                 Thread.sleep(200);
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
	public void dbStatus() {
		boolean status = DBConnection.tryConnection();
        if(!status) {
        	statusMenu.setText("No connection to database");
        	statusMenu.setForeground(Color.RED);

        } else if (status) {
        	statusMenu.setText("Connected to DB!");
        	statusMenu.setForeground(new Color(0x11780f));
        }
	}
	public JPanel getPanel() {
		return panel;
	}
	
}

