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
	    private JMenuItem addItem, listItem, retrieveItem, editItem, importItem, copyItem, exitItem;
	    private JButton homeBtn, backBtn;
	    
	  //Body Elements
	    private JPanel panel = new JPanel();
	    private JLabel welcomeLabel = new JLabel("Welcome to the Management Application");
	    private JButton addBtn, listBtn, retrieveBtn, editBtn, importBtn;
	    
	  //Extra Elements for Body
	    DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd : HH:mm");  
	    LocalDateTime now = LocalDateTime.now();  
	    
	  //Previous panel
	    JPanel PreviousPanel, StartPanel;
	    
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

        listItem = new JMenuItem("List Orders");
        listItem.setBackground(Color.WHITE);

        retrieveItem = new JMenuItem("Retrieve Employees");
        retrieveItem.setBackground(Color.WHITE);
        
        editItem = new JMenuItem("Edit Data in DB");
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
        KeyStroke keyStrokeHomeBtn = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
        homeBtn.setMnemonic(KeyEvent.VK_H); 
        fileMenu.setMnemonic(KeyEvent.VK_F); 
        actionMenu.setMnemonic(KeyEvent.VK_A); 
        
        //Adding shortcuts/KeyStrokes to each JMenuItem for accessibility.
        //These can be used by pressing 'ctrl + key', the key will be underlined in the application.
        KeyStroke keyStrokeAddItem = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeListItem = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeRetrieveItem = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeEditItem = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeImportItem = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeCopyItem = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke keyStrokeExitItem = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);

        addItem.setMnemonic(KeyEvent.VK_A); //key
        addItem.setAccelerator(keyStrokeAddItem);

        listItem.setMnemonic(KeyEvent.VK_L); //key
        listItem.setAccelerator(keyStrokeListItem);
        
        retrieveItem.setMnemonic(KeyEvent.VK_R); //key
        retrieveItem.setAccelerator(keyStrokeRetrieveItem);

        editItem.setMnemonic(KeyEvent.VK_E); //key
        editItem.setAccelerator(keyStrokeEditItem);

        importItem.setMnemonic(KeyEvent.VK_I); //key 
        importItem.setAccelerator(keyStrokeImportItem);

        copyItem.setMnemonic(KeyEvent.VK_W); //key
        copyItem.setAccelerator(keyStrokeCopyItem);
        
        exitItem.setMnemonic(KeyEvent.VK_W); //key
        exitItem.setAccelerator(keyStrokeExitItem);

        //Adding each Item to its JMenu Parent
        actionMenu.add(addItem);
        actionMenu.add(listItem);
        actionMenu.add(retrieveItem);
        actionMenu.add(editItem);
        actionMenu.add(importItem);
        fileMenu.add(copyItem);
        fileMenu.add(exitItem);
        
        
        homeBtn.addActionListener(this);
        addItem.addActionListener(this);
        listItem.addActionListener(this);
        retrieveItem.addActionListener(this);
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
        addBtn.setFocusable(false);
        addBtn.setBackground(Color.WHITE);
        addBtn.setFont(new Font(null, Font.BOLD,15));
        addBtn.setForeground(new Color(0x102533));
        addBtn.addActionListener(this);
        
        listBtn = new JButton("List Orders");
        listBtn.setFocusable(false);
        listBtn.setBackground(Color.WHITE);
        listBtn.setFont(new Font(null, Font.BOLD,15));
        listBtn.addActionListener(this);

        retrieveBtn = new JButton("Retrieve Employees");        
        retrieveBtn.setFocusable(false);
        retrieveBtn.setBackground(Color.WHITE);
        retrieveBtn.setFont(new Font(null, Font.BOLD,15));
        retrieveBtn.addActionListener(this);
        
        editBtn = new JButton("Edit Data in DB");        
        editBtn.setFocusable(false);
        editBtn.setBackground(Color.WHITE);
        editBtn.setFont(new Font(null, Font.BOLD,15));
        editBtn.addActionListener(this);
        
        importBtn = new JButton("Bulk Import from file");
        importBtn.setFocusable(false);
        importBtn.setBackground(Color.WHITE);
        importBtn.setFont(new Font(null, Font.BOLD,15));
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
			//Backbutton won't work on home as everything is reset
			backBtn.setVisible(true);
		}
		if(e.getSource() == addItem || e.getSource() == addBtn) {
			PreviousPanel = (JPanel)this.getContentPane();
	        this.setTitle("Management Application - Add Customer");
			addCustomerView panel = new addCustomerView();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
			this.pack();
			backBtn.setVisible(true);
		} 
		if(e.getSource() == listItem || e.getSource() == listBtn) {
			PreviousPanel = (JPanel)this.getContentPane();
	        this.setTitle("Management Application - List Orders");
			DisplayTable panel = new DisplayTable();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();
	        backBtn.setVisible(true);
		} 
		
		 if(e.getSource() == retrieveItem || e.getSource() == retrieveBtn) {
			PreviousPanel = (JPanel)this.getContentPane();
	        this.setTitle("Management Application - Retrieve Employees");
			getEmployees panel = new getEmployees();
			this.setContentPane(panel.getPanel());
			this.revalidate();
			this.repaint();
	        this.pack();
	        backBtn.setVisible(true);

		}
		 if(e.getSource() == editItem || e.getSource() == editBtn) {
				PreviousPanel = (JPanel)this.getContentPane();
		        this.setTitle("Management Application - Edit Tables");
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
	
		if(e.getSource() == copyItem ) {
		///hmmmm
		}
		
		if (e.getSource() == backBtn) {
			
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

