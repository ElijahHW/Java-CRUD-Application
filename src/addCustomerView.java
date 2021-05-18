import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class addCustomerView implements ActionListener {

	private JLabel customerNumberLabel;
	private JLabel customerNameLabel;
	private JLabel contactLastNameLabel;
	private JLabel contactFirstNameLabel;
	private JLabel phoneLabel;
	private JLabel addressLine1Label;
	private JLabel addressLine2Label;
	private JLabel cityLabel;
	private JLabel stateLabel;
	private JLabel postalCodeLabel;
	private JLabel countryLabel;
	private JLabel salesRepEmployeeNumberLabel;
	private JLabel creditLimitLabel;
	
	private JTextField customerNumberInput; 
	private JTextField customerNameInput; 
	private JTextField contactLastNameInput; 
	private JTextField contactFirstNameInput; 
	private JTextField phoneInput; 
	private JTextField addressLine1Input; 
	private JTextField addressLine2Input; 
	private JTextField cityInput; 
	private JTextField stateInput; 
	private JTextField postalCodeInput; 
	private JTextField countryInput;
	private JTextField salesRepEmployeeNumberInput;
	private JTextField creditLimitInput;
	
	
	private JLabel responseText;
	private JFrame frame;
	private JPanel panel;
	private JButton addToDbButton;
//	private JButton resetButton;
	
	
	public addCustomerView() {
		
		 frame = new JFrame ("Add customer");
		 panel = new JPanel();
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(null);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Add Customer");
		frame.pack();
		frame.setVisible(true);
		
		customerNumberLabel = new JLabel("Customer number");
		customerNumberLabel.setBounds(10, 20, 120, 25);
		panel.add(customerNumberLabel);
		
		customerNumberInput = new JTextField(20);
		customerNumberInput.setBounds(150, 20, 165, 25);
		panel.add(customerNumberInput);
		
		customerNameLabel = new JLabel("Customer name");
		customerNameLabel.setBounds(10, 50, 120, 25);
		panel.add(customerNameLabel);
		
		customerNameInput = new JTextField(20);
		customerNameInput.setBounds(150, 50, 165, 25);
		panel.add(customerNameInput);
		
		contactLastNameLabel = new JLabel("Contacts last name");
		contactLastNameLabel.setBounds(10, 80, 120, 25);
		panel.add(contactLastNameLabel);
		
		contactLastNameInput = new JTextField(20);
		contactLastNameInput.setBounds(150, 80, 165, 25);
		panel.add(contactLastNameInput);
		
		contactFirstNameLabel = new JLabel("Contacts first name");
		contactFirstNameLabel.setBounds(10, 110, 120, 25);
		panel.add(contactFirstNameLabel);
		
		contactFirstNameInput = new JTextField(20);
		contactFirstNameInput.setBounds(150, 110, 165, 25);
		panel.add(contactFirstNameInput);
		
		phoneLabel = new JLabel("Phone");
		phoneLabel.setBounds(10, 140, 120, 25);
		panel.add(phoneLabel);
		
		phoneInput = new JTextField(20);
		phoneInput.setBounds(150, 140, 165, 25);
		panel.add(phoneInput);
		
		addressLine1Label = new JLabel("Address line 1");
		addressLine1Label.setBounds(10, 170, 120, 25);
		panel.add(addressLine1Label);
		
		addressLine1Input = new JTextField(20);
		addressLine1Input.setBounds(150, 170, 165, 25);
		panel.add(addressLine1Input);
		
		addressLine2Label = new JLabel("Address line 2");
		addressLine2Label.setBounds(10, 200, 120, 25);
		panel.add(addressLine2Label);
		
		addressLine2Input = new JTextField(20);
		addressLine2Input.setBounds(150, 200, 165, 25);
		panel.add(addressLine2Input);
		
		cityLabel = new JLabel("City");
		cityLabel.setBounds(10, 230, 120, 25);
		panel.add(cityLabel);
		
		cityInput = new JTextField(20);
		cityInput.setBounds(150, 230, 165, 25);
		panel.add(cityInput);

		stateLabel = new JLabel("State");
		stateLabel.setBounds(10, 260, 120, 25);
		panel.add(stateLabel);
		
		stateInput = new JTextField(20);
		stateInput.setBounds(150, 260, 165, 25);
		panel.add(stateInput);
		
		postalCodeLabel = new JLabel("Postcode");
		postalCodeLabel.setBounds(10, 290, 120, 25);
		panel.add(postalCodeLabel);
		
		postalCodeInput = new JTextField(20);
		postalCodeInput.setBounds(150, 290, 165, 25);
		panel.add(postalCodeInput);
		
		countryLabel = new JLabel("Country");
		countryLabel.setBounds(10, 320, 120, 25);
		panel.add(countryLabel);
		
		countryInput = new JTextField(20);
		countryInput.setBounds(150, 320, 165, 25);
		panel.add(countryInput);
		
		salesRepEmployeeNumberLabel = new JLabel("Sales rep employee number");
		salesRepEmployeeNumberLabel.setBounds(10, 350, 120, 25);
		panel.add(salesRepEmployeeNumberLabel);
		
		salesRepEmployeeNumberInput = new JTextField(20);
		salesRepEmployeeNumberInput.setBounds(150, 350, 165, 25);
		panel.add(salesRepEmployeeNumberInput);
		
		creditLimitLabel = new JLabel("Credit limit");
		creditLimitLabel.setBounds(10, 380, 120, 25);
		panel.add(creditLimitLabel);
		
		creditLimitInput = new JTextField(20);
		creditLimitInput.setBounds(150, 380, 165, 25);
		panel.add(creditLimitInput);
		
		

		addToDbButton = new JButton("Add customer to DB");
		addToDbButton.setBounds(25, 420, 150, 25);
		addToDbButton.addActionListener(this);
		panel.add(addToDbButton);
		
		
		/*
		resetButton = new JButton("Reset");
		resetButton.setBounds(200, 420, 80, 25);
		resetButton.addActionListener(this);
		panel.add(resetButton);
		*/
			
		responseText = new JLabel("");
		responseText.setBounds(10, 110, 300, 25);
		panel.add(responseText);
		
	}

	/*@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
		dbConnection.addCustomer(Integer.parseInt(customerNumberInput.getText()), customerNameInput.getText(),
				contactLastNameInput.getText(), contactFirstNameInput.getText(), 
				phoneInput.getText(), addressLine1Input.getText(), addressLine2Input.getText(), 
				cityInput.getText(), stateInput.getText(), postalCodeInput.getText(), 
				countryInput.getText(), Integer.parseInt(salesRepEmployeeNumberInput.getText()), 
				Double.parseDouble(creditLimitInput.getText()));
		} catch (Exception e1) {
			System.out.println("noe gikk galt");
		}
		
		
	}*/
}
