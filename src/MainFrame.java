import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
public class MainFrame extends JFrame implements ActionListener {
	
	//Header Elements
		private JMenuBar menuBar;
	    private JMenu fileMenu, actionMenu, dateMenu;
	    private JMenuItem addItem, listItem, deleteItem, editItem, importItem, copyItem, exitItem, backBtn2;
	    private JButton homeBtn, backBtn;
	    
	  //Body Elements
	    private JPanel panel = new JPanel();
	    private JLabel welcomeLabel = new JLabel("Welcome to the Management Application");
	    private JButton addBtn, listBtn, deleteBtn, editBtn, importBtn;
	    
	  //Extra Elements for Body
	    private DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd : HH:mm");  
	    private LocalDateTime now = LocalDateTime.now();  
	    
	  //Previous panel
	    private JPanel PreviousPanel, StartPanel;
	    
    MainFrame() {
    	boolean status = DBConnection.tryConnection(); // Checks the connection with the database
    	
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
        homeBtn.setToolTipText("Click this button to disable the middle button.");
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
        
        fileMenu = new JMenu("File");
        fileMenu.setFont(new Font(null, Font.BOLD,15));
        fileMenu.setForeground(new Color(0x203c56));


		// Defining the MenuItems under each JMenu.
        addItem = new JMenuItem("Add Customer");
        addItem.setBackground(Color.WHITE);

        listItem = new JMenuItem("Display Tables / Export");
        listItem.setBackground(Color.WHITE);

        deleteItem = new JMenuItem("Delete Data from Database");
        deleteItem.setBackground(Color.WHITE);
        
        editItem = new JMenuItem("Update Data in Database");
        editItem.setBackground(Color.WHITE);
        
        importItem = new JMenuItem("Import from File");
        importItem.setBackground(Color.WHITE);

        copyItem = new JMenuItem("Copy");
        copyItem.setBackground(Color.WHITE);
        
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
        fileMenu.setMnemonic(KeyEvent.VK_F); 
        actionMenu.setMnemonic(KeyEvent.VK_A); 
        
        //Adding shortcuts/KeyStrokes to each JMenuItem for accessibility.
        //These can be used by pressing 'ctrl + key', the key will be underlined in the application.
        KeyStroke keyStrokeAddItem = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeListItem = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokedeleteItem = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeEditItem = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeImportItem = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeCopyItem = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeExitItem = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);

        addItem.setMnemonic(KeyEvent.VK_A); //key
        addItem.setAccelerator(keyStrokeAddItem);

        listItem.setMnemonic(KeyEvent.VK_L); //key
        listItem.setAccelerator(keyStrokeListItem);
        
        deleteItem.setMnemonic(KeyEvent.VK_D); //key
        deleteItem.setAccelerator(keyStrokedeleteItem);

        editItem.setMnemonic(KeyEvent.VK_E); //key
        editItem.setAccelerator(keyStrokeEditItem);

        importItem.setMnemonic(KeyEvent.VK_I); //key 
        importItem.setAccelerator(keyStrokeImportItem);

        copyItem.setMnemonic(KeyEvent.VK_C); //key
        copyItem.setAccelerator(keyStrokeCopyItem);
        
        exitItem.setMnemonic(KeyEvent.VK_W); //key
        exitItem.setAccelerator(keyStrokeExitItem);

        //Adding each Item to its JMenu Parent
        actionMenu.add(addItem);
        actionMenu.add(listItem);
        actionMenu.add(deleteItem);
        actionMenu.add(editItem);
        actionMenu.add(importItem);
        fileMenu.add(copyItem);
        fileMenu.add(exitItem);
        
        
        homeBtn.addActionListener(this);
        addItem.addActionListener(this);
        listItem.addActionListener(this);
        deleteItem.addActionListener(this);
        editItem.addActionListener(this);
        importItem.addActionListener(this);
        copyItem.addActionListener(this);
        exitItem.addActionListener(this);
        backBtn.addActionListener(this);

        menuBar.add(homeBtn);
        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
        menuBar.add(dateMenu);
        menuBar.add(backBtn);
        if(!status) {
        	JLabel statusLabel  = new JLabel("No connection to database", SwingConstants.RIGHT);
        	statusLabel.setForeground(Color.RED);	
        	menuBar.add(statusLabel);
        }
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
        
        listBtn = new JButton("Display Tables / Export");
		listBtn.setToolTipText("Display all tables and exports to a file");
        listBtn.setFocusable(false);
        listBtn.setBackground(Color.WHITE);
        listBtn.setFont(new Font(null, Font.BOLD,15));
        listBtn.addActionListener(this);

        deleteBtn = new JButton("Delete Data from Database");        
        deleteBtn.setFocusable(false);
        deleteBtn.setBackground(Color.WHITE);
        deleteBtn.setFont(new Font(null, Font.BOLD,15));
        deleteBtn.addActionListener(this);
        
        editBtn = new JButton("Update data in Database DB");        
		editBtn.setToolTipText("Updates data and saves to database  ");
        editBtn.setFocusable(false);
        editBtn.setBackground(Color.WHITE);
        editBtn.setFont(new Font(null, Font.BOLD,15));
        editBtn.addActionListener(this);
        
        importBtn = new JButton("Bulk Import from file");
        importBtn.setToolTipText("Imports data from a file and - to a table");
        importBtn.setFocusable(false);
        importBtn.setBackground(Color.WHITE);
        importBtn.setFont(new Font(null, Font.BOLD,15));
        importBtn.addActionListener(this);
        
        panel.add(welcomeLabel);
        panel.add(addBtn);
        panel.add(listBtn);
        panel.add(deleteBtn);
        panel.add(editBtn);
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

        }

    
	//Defining the actions of the MenuBar
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == homeBtn) {
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
		if(e.getSource() == listItem || e.getSource() == listBtn) {
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
		 if(e.getSource() == editItem || e.getSource() == editBtn) {
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

