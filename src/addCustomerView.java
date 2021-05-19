import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

public class addCustomerView implements ActionListener {
	
	private JTextField customerNumberInput, customerNameInput, contactLastNameInput, contactFirstNameInput, phoneInput, addressLine1Input,
						addressLine2Input, cityInput, stateInput, postalCodeInput, countryInput, creditLimitInput;
	
    private JComboBox<Integer> salesRepNumberList;
    
	private JLabel responseText, label;
	private JPanel panel;
	
	private JButton addToDbButton;
	private JButton resetButton;
	private GridBagConstraints gbc = new GridBagConstraints();
	private GridBagConstraints LeftCon, RightCon;
	private Dimension PreferedDimension= new Dimension(100, 20);
	
	private boolean UniqueName = false, ValidCredit = true;
	private double CreditNumber = 0f;
	
	public addCustomerView() {
		
		UpdateConstraints();
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		label = new JLabel();
		label.setFont(new Font(null,Font.BOLD,20));
		label.setForeground(new Color(0x203c56));
		label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		label.setText("Add a customer to the Database");
		
		responseText = new JLabel("");
		
		gbc.fill = GridBagConstraints.BOTH; 
		gbc.weightx = 55;
		gbc.weighty = 55;
		
		gbc.gridx = 0;
		gbc.gridy = 1;
        panel.add(label);
		panel.add((panelCustomerNumber()), gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add((panelCustomerName()), gbc);
		
		gbc.gridy = 3;
		panel.add((panelFirstName()), gbc);
		
		gbc.gridy = 4;
		panel.add((panelLastName()), gbc);
		
		gbc.gridy = 5;
		panel.add((panelPhone()), gbc);
		
		gbc.gridy = 6;
		panel.add((panelAdressOne()), gbc);
		
		gbc.gridy = 7;
		panel.add((panelAdressTwo()), gbc);
		
		gbc.gridy = 8;
		panel.add((panelCity()), gbc);
		
		gbc.gridy = 9;
		panel.add((panelState()), gbc);
		
		gbc.gridy = 10;
		panel.add((panelPostCode()), gbc);
		
		gbc.gridy = 11;
		panel.add((panelCountry()), gbc);
		
		gbc.gridy = 12;
		panel.add((panelSREmployee()), gbc);
		
		gbc.gridy = 13;
		panel.add((panelCreditLimit()), gbc);
		
		gbc.gridy = 14;
		panel.add((panelButtons()), gbc);
		
		gbc.gridy = 15;
		panel.add(responseText, gbc);		
		
		panel.revalidate();
		panel.repaint();	
		panel.setPreferredSize(new Dimension(800,550));
		
	}

	
	protected void UpdateConstraints() {
		
		LeftCon = new GridBagConstraints();
		
		LeftCon.fill = GridBagConstraints.HORIZONTAL;
		LeftCon.weightx = 0.10;
		LeftCon.gridx = 0;
		LeftCon.gridy = 0;
		LeftCon.gridwidth = 3;
		
		RightCon = new GridBagConstraints();
		
		RightCon.fill = GridBagConstraints.HORIZONTAL;
		RightCon.weightx = 0.90;
		RightCon.gridx = 3;
		RightCon.gridy = 0;
		RightCon.gridwidth = 6;
	}
	
	//Action listeners
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//The add customer button is pressed
		if(e.getSource()==addToDbButton) {
			
			//Makes sure that the usernumber does not exist already
			if (UniqueName) {
				
				//makes sure that the credit input is valid
				if (ValidCredit) {
					
					//tries to insert the values added to the database
					try {
						String respons = dbConnection.addCustomer(
								customerNumberInput.getText(), 
								customerNameInput.getText(),
								contactLastNameInput.getText(), 
								contactFirstNameInput.getText(), 
								phoneInput.getText(), 
								addressLine1Input.getText(), 
								addressLine2Input.getText(), 
								cityInput.getText(), 
								stateInput.getText(), 
								postalCodeInput.getText(), 
								countryInput.getText(),
								salesRepNumberList.getSelectedItem().toString(),
								CreditNumber + "");
						
						responseText.setText(respons);
								
					} catch (Exception e1) {
						System.out.println(e1);
						responseText.setText("Something went wrong");
					}
				} else {
					
					responseText.setText("Credit number not valid");
				}
				
			} else {
				
				responseText.setText("Customer number not valid");
			}
		
		//The Resetbutton is pressed
		} else if (e.getSource()==resetButton) {
			customerNumberInput.setText(null);
			customerNameInput.setText(null);
			contactLastNameInput.setText(null);
			contactFirstNameInput.setText(null);
			phoneInput.setText(null);
			addressLine1Input.setText(null);
			addressLine2Input.setText(null);
			cityInput.setText(null);
			stateInput.setText(null);
			postalCodeInput.setText(null);
			countryInput.setText(null);
			creditLimitInput.setText(null);
			
			responseText.setText("All fields reset.");
		} else if (e.getSource()==customerNumberInput) {
			
			
		}
			
	}
	
	//A simple function to populate the listbox with valid sales personell
	private void updateCombobox()
	{
			List<List<String>> list = dbConnection.getTable("employees");
			for (List<String> row : list) {
				if (row.get(7).equals("Sales Rep")) {
					salesRepNumberList.addItem(Integer.parseInt(row.get(0)));
	        }
	    }
		      
	}
	
	private JPanel panelCustomerNumber() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Customer number");
		label.setPreferredSize(PreferedDimension);
		customerNumberInput = new JTextField(20);
		customerNumberInput.addKeyListener(new KeyAdapter() {
			
			//If a key is pressed in the customer number input
			public void keyReleased(KeyEvent e) {
				
				JTextField textField = (JTextField) e.getSource();
                String SearchString = textField.getText();
                
                //Checks the length of the input string
                if (SearchString.length() == 0) {
                	
                	UniqueName = false;
                } else {
                	
                	//tries to parse the input to see if it contains non integers
                	try {
                		Integer.parseInt(SearchString);
                	} catch (NumberFormatException nfe) {
                		
                		//if the input contains non integers, removes non-integers
                		textField.setText(SearchString.replaceAll("[^\\d]", ""));
                	}
                	
                	//checks if the number entered is unique. A rather reseource heavy task sadly
                	List<List<String>> list = dbConnection.getTable("customers");
                	for (List<String> row : list) {
                		
                		if (row.get(0).equals(SearchString)) {
                			
                			UniqueName = false;
                			break;
                		} else {
                			
                			UniqueName = true;
                		}
                	}
                	
                	if (!UniqueName) {
                		
                		responseText.setText("Customer Number already exists");
                	} else {
                		
                		responseText.setText(null);
                	}
                }
			}
		});
		
		pane.setLayout(new GridBagLayout());

		pane.add(label, LeftCon);
		pane.add(customerNumberInput, RightCon);
		
		return pane;
	}
	
	private JPanel panelCustomerName() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Customer name");
		label.setPreferredSize(PreferedDimension);
		customerNameInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(customerNameInput, RightCon);
		
		return pane;
	}
	
	//A bunch of functions creating JPanels containing the label/input pairs
	private JPanel panelFirstName() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Contacts first name");
		label.setPreferredSize(PreferedDimension);
		contactFirstNameInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(contactFirstNameInput, RightCon);
		
		return pane;
	}
	
	private JPanel panelLastName() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Contacts last name");
		label.setPreferredSize(PreferedDimension);
		contactLastNameInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(contactLastNameInput, RightCon);
		
		return pane;
	}
	
	private JPanel panelPhone() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Phone");
		label.setPreferredSize(PreferedDimension);
		phoneInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon); 
		pane.add(phoneInput, RightCon); 
		
		return pane;
	}
	
	private JPanel panelAdressOne() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Address line 1");	
		label.setPreferredSize(PreferedDimension);
		addressLine1Input = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(addressLine1Input, RightCon);
		
		return pane;
	}
	
	private JPanel panelAdressTwo() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Address line 2");
		label.setPreferredSize(PreferedDimension);
		addressLine2Input = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(addressLine2Input, RightCon);
		
		return pane;
	}
	
	private JPanel panelCity() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("City");
		label.setPreferredSize(PreferedDimension);
		cityInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(cityInput, RightCon);
		
		return pane;
	}
	
	private JPanel panelState() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("State");
		label.setPreferredSize(PreferedDimension);
		stateInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon); 
		pane.add(stateInput, RightCon); 
		
		return pane;
	}
	
	private JPanel panelPostCode() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Postcode");	
		label.setPreferredSize(PreferedDimension);
		postalCodeInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(postalCodeInput, RightCon);
		
		return pane;
	}
	
	private JPanel panelCountry() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Country");	
		label.setPreferredSize(PreferedDimension);
		countryInput = new JTextField(20);
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon);
		pane.add(countryInput, RightCon);
		
		return pane;
	}
	
	private JPanel panelSREmployee() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Sales Rep Nr.");
		label.setPreferredSize(PreferedDimension);
	    salesRepNumberList = new JComboBox<Integer>(); updateCombobox();
	    
	    pane.setLayout(new GridBagLayout());
	    
	    pane.add(label, LeftCon); 
	    pane.add(salesRepNumberList, RightCon); 
		
		return pane;
	}
	
	private JPanel panelCreditLimit() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Credit limit");
		label.setPreferredSize(PreferedDimension);
		creditLimitInput = new JTextField(20);
		creditLimitInput.addKeyListener(new KeyAdapter() {
			
			//Checks that the credits field only has 10 digits and 2 decimals
			public void keyReleased(KeyEvent e) {
				
				JTextField textField = (JTextField) e.getSource();
                String SearchString = textField.getText();
                
                //checks if the searchstring actually has anything written in it
                if (SearchString.length() > 0) {
                	
                    textField.setText(SearchString.replaceAll("[^\\d.]", ""));
                    double tempNumber = Double.parseDouble(SearchString);
                    tempNumber = Math.round(tempNumber * 100d)/100d;
                    
                    //checks if the rounded number is longer than 10 digits
                    if (String.valueOf(Math.round(tempNumber)).length() > 10) {
                    	responseText.setText("The credit number must be less than 10000000000");
                    	ValidCredit = false;
                    } else {
                    	responseText.setText(null);
                    	CreditNumber = tempNumber;
                    	ValidCredit = true;
                    }
                                      
                } else {
                	CreditNumber = 0f;
                	ValidCredit = true;
                }
			}
		});
		
		pane.setLayout(new GridBagLayout());
		
		pane.add(label, LeftCon); 
		pane.add(creditLimitInput, RightCon); 
		
		return pane;
	}
	
	private JPanel panelButtons() {
		
		JPanel pane = new JPanel();
		
		addToDbButton = new JButton("Add customer to DB");
		addToDbButton.addActionListener(this);
		addToDbButton.setBackground(Color.WHITE);
		Icon iconD = UIManager.getIcon("FileView.hardDriveIcon");
		addToDbButton.setIcon(iconD);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		resetButton.setBackground(Color.WHITE);
			
		pane.setLayout(new GridLayout(2, 1));
		
		pane.add(addToDbButton);
		pane.add(resetButton);
		
		return pane;
	}
	
	public JPanel getPanel() {
		
		return panel;
	}
}

