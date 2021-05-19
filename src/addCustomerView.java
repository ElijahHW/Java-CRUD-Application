import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.swing.*;

public class addCustomerView implements ActionListener {
	
	private static Statement statement;
	private static PreparedStatement pst;
    private static Connection conn;
    private ResultSet rs;
	private JLabel customerNumberLabel, customerNameLabel,contactLastNameLabel, contactFirstNameLabel, phoneLabel,
					addressLine1Label, addressLine2Label, cityLabel, stateLabel, postalCodeLabel, countryLabel, 
					salesRepEmployeeNumberLabel, creditLimitLabel;
	
	private JTextField customerNumberInput, customerNameInput, contactLastNameInput, contactFirstNameInput, phoneInput, addressLine1Input,
						addressLine2Input, cityInput, stateInput, postalCodeInput, countryInput, salesRepEmployeeNumberInput, creditLimitInput;
	
    private JComboBox<Integer> salesRepNumberList;
    
	private JLabel responseText;
	private JPanel panel, panelCustomerNumber, panelCustomerName, panelFirstName, panelLastName, panelPhone, 
					panelAdressOne, panelAdressTwo, panelCity, panelState, panelPostCode, panelCountry, 
					panelSREmployee, panelCreditLimit, panelButtons;
	
	private JButton addToDbButton;
	private JButton resetButton;
    private static Connection MyCon;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public addCustomerView() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

		panelCustomerNumber = new JPanel();
		customerNumberLabel = new JLabel("Customer number");
		customerNumberInput = new JTextField(20);
		panelCustomerNumber.add(customerNumberLabel);
		panelCustomerNumber.add(customerNumberInput);
		
		panelCustomerName = new JPanel();
		customerNameLabel = new JLabel("Customer name");
		customerNameInput = new JTextField(20);
		panelCustomerName.add(customerNameLabel);
		panelCustomerName.add(customerNameInput);
		
		panelFirstName = new JPanel();
		contactFirstNameLabel = new JLabel("Contacts first name");
		contactFirstNameInput = new JTextField(20);
		panelFirstName.add(contactFirstNameLabel);
		panelFirstName.add(contactFirstNameInput);
		
		panelLastName = new JPanel();
		contactLastNameLabel = new JLabel("Contacts last name");
		contactLastNameInput = new JTextField(20);
		panelLastName.add(contactLastNameLabel);
		panelLastName.add(contactLastNameInput);

		panelPhone = new JPanel();
		phoneLabel = new JLabel("Phone");
		phoneInput = new JTextField(20);
		panelPhone.add(phoneLabel); 
		panelPhone.add(phoneInput); 

		panelAdressOne = new JPanel();
		addressLine1Label = new JLabel("Address line 1");		
		addressLine1Input = new JTextField(20);
		panelAdressOne.add(addressLine1Label);
		panelAdressOne.add(addressLine1Input);

		panelAdressTwo = new JPanel();
		addressLine2Label = new JLabel("Address line 2");
		addressLine2Input = new JTextField(20);
		panelAdressTwo.add(addressLine2Label);
		panelAdressTwo.add(addressLine2Input);
		
		panelCity = new JPanel();
		cityLabel = new JLabel("City");
		cityInput = new JTextField(20);
		panelCity.add(cityLabel);
		panelCity.add(cityInput);
		
		panelState = new JPanel();
		stateLabel = new JLabel("State");
		stateInput = new JTextField(20);
		panelState.add(stateLabel); 
		panelState.add(stateInput); 

		panelPostCode = new JPanel();
		postalCodeLabel = new JLabel("Postcode");		
		postalCodeInput = new JTextField(20);
		panelPostCode.add(postalCodeLabel);
		panelPostCode.add(postalCodeInput);

		panelCountry = new JPanel();
		countryLabel = new JLabel("Country");		
		countryInput = new JTextField(20);
		panelCountry.add(countryLabel);
		panelCountry.add(countryInput);

		panelSREmployee = new JPanel();
		salesRepEmployeeNumberLabel = new JLabel("Employee Nr.");
	    salesRepNumberList = new JComboBox<Integer>(); updateCombobox();
		panelSREmployee.add(salesRepEmployeeNumberLabel); 
		panelSREmployee.add(salesRepNumberList); 
		
		panelCreditLimit = new JPanel();
		creditLimitLabel = new JLabel("Credit limit");
		creditLimitInput = new JTextField(20);
		panelCreditLimit.add(creditLimitLabel); 
		panelCreditLimit.add(creditLimitInput); 
		
		panelButtons = new JPanel();
		addToDbButton = new JButton("Add customer to DB");
		addToDbButton.addActionListener(this);
		addToDbButton.setBackground(Color.WHITE);
	
		resetButton = new JButton("Reset");
		resetButton.setBounds(200, 420, 80, 25);
		resetButton.addActionListener(this);
		resetButton.setBackground(Color.WHITE);
			
		panelButtons.add(addToDbButton);
		panelButtons.add(resetButton);

		responseText = new JLabel("");
		responseText.setBounds(25, 450, 200, 25);
		
		panel.add(panelCustomerNumber);
		panel.add(panelCustomerName);
		panel.add(panelFirstName);
		panel.add(panelLastName);
		panel.add(panelPhone);
		panel.add(panelAdressOne);
		panel.add(panelAdressTwo);
		panel.add(panelCity);
		panel.add(panelState);
		panel.add(panelPostCode);
		panel.add(panelCountry);
		panel.add(panelSREmployee);
		panel.add(panelCreditLimit);
		panel.add(panelButtons);
		
		gbc.fill = GridBagConstraints.BOTH; // these constraints will be added to a JPanel later
		gbc.weightx = 55;
		gbc.weighty = 55;
		panel.add((panelCustomerNumber), gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add((panelCustomerName), gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add((panelFirstName), gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add((panelLastName), gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add((panelPhone), gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		panel.add((panelAdressOne), gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		panel.add((panelAdressTwo), gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		panel.add((panelCity), gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		panel.add((panelState), gbc);
		gbc.gridx = 0;
		gbc.gridy = 9;
		panel.add((panelPostCode), gbc);
		gbc.gridx = 0;
		gbc.gridy = 10;
		panel.add((panelCountry), gbc);
		gbc.gridx = 0;
		gbc.gridy = 11;
		panel.add((panelSREmployee), gbc);
		gbc.gridx = 0;
		gbc.gridy = 12;
		panel.add((panelCreditLimit), gbc);
		gbc.gridx = 0;
		gbc.gridy = 13;
		panel.add((panelButtons), gbc);
		
		
		
		panel.add(responseText);		
		panel.revalidate();
		panel.repaint();	
		panel.setPreferredSize(new Dimension(800,550));
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addToDbButton) {
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
				salesRepEmployeeNumberInput.getSelectedText(),
				creditLimitInput.getText());
		
		responseText.setText(respons);
				
		} catch (Exception e1) {
			responseText.setText("Something went wrong" + e1);
		}
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
		salesRepEmployeeNumberInput.setText(null);
		creditLimitInput.setText(null);
		
		responseText.setText("All fields reset.");
	}
			
}
	private void updateCombobox()
	{
			List<List<String>> list = dbConnection.getTable("employees");
			for (List<String> row : list) {
				if (row.get(7).equals("Sales Rep")) {
					salesRepNumberList.addItem(Integer.parseInt(row.get(0)));
	        }
	    }
		      
	}
	
	public JPanel getPanel() {
		return panel;
	}
}

