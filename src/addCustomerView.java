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
	
	private JTextField customerNumberInput, customerNameInput, contactLastNameInput, contactFirstNameInput, phoneInput, addressLine1Input,
						addressLine2Input, cityInput, stateInput, postalCodeInput, countryInput, salesRepEmployeeNumberInput, creditLimitInput;
	
    private JComboBox<Integer> salesRepNumberList;
    
	private JLabel responseText;
	private JPanel panel;
	
	private JButton addToDbButton;
	private JButton resetButton;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public addCustomerView() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

		responseText = new JLabel("");
		responseText.setBounds(25, 450, 200, 25);
		
		gbc.fill = GridBagConstraints.BOTH; // these constraints will be added to a JPanel later
		gbc.weightx = 55;
		gbc.weighty = 55;
		panel.add((panelCustomerNumber()), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add((panelCustomerName()), gbc);
		
		gbc.gridy = 2;
		panel.add((panelFirstName()), gbc);
		
		gbc.gridy = 3;
		panel.add((panelLastName()), gbc);
		
		gbc.gridy = 4;
		panel.add((panelPhone()), gbc);
		
		gbc.gridy = 5;
		panel.add((panelAdressOne()), gbc);
		
		gbc.gridy = 6;
		panel.add((panelAdressTwo()), gbc);
		
		gbc.gridy = 7;
		panel.add((panelCity()), gbc);
		
		gbc.gridy = 8;
		panel.add((panelState()), gbc);
		
		gbc.gridy = 9;
		panel.add((panelPostCode()), gbc);
		
		gbc.gridy = 10;
		panel.add((panelCountry()), gbc);
		
		gbc.gridy = 11;
		panel.add((panelSREmployee()), gbc);
		
		gbc.gridy = 12;
		panel.add((panelCreditLimit()), gbc);
		
		gbc.gridy = 13;
		panel.add((panelButtons()), gbc);
		
		gbc.gridy = 14;
		panel.add(responseText, gbc);		
		
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
			responseText.setText("Something went wrong");
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
	
	private JPanel panelCustomerNumber() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Customer number");
		customerNumberInput = new JTextField(20);
		pane.add(label);
		pane.add(customerNumberInput);
		
		return pane;
	}
	
	private JPanel panelCustomerName() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Customer name");
		customerNameInput = new JTextField(20);
		pane.add(label);
		pane.add(customerNameInput);
		
		return pane;
	}
	
	private JPanel panelFirstName() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Contacts first name");
		contactFirstNameInput = new JTextField(20);
		pane.add(label);
		pane.add(contactFirstNameInput);
		
		return pane;
	}
	
	private JPanel panelLastName() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Contacts last name");
		contactLastNameInput = new JTextField(20);
		pane.add(label);
		pane.add(contactLastNameInput);
		
		return pane;
	}
	
	private JPanel panelPhone() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Phone");
		phoneInput = new JTextField(20);
		pane.add(label); 
		pane.add(phoneInput); 
		
		return pane;
	}
	
	private JPanel panelAdressOne() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Address line 1");		
		addressLine1Input = new JTextField(20);
		pane.add(label);
		pane.add(addressLine1Input);
		
		return pane;
	}
	
	private JPanel panelAdressTwo() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Address line 2");
		addressLine2Input = new JTextField(20);
		pane.add(label);
		pane.add(addressLine2Input);
		
		return pane;
	}
	
	private JPanel panelCity() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("City");
		cityInput = new JTextField(20);
		pane.add(label);
		pane.add(cityInput);
		
		return pane;
	}
	
	private JPanel panelState() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("State");
		stateInput = new JTextField(20);
		pane.add(label); 
		pane.add(stateInput); 
		
		return pane;
	}
	
	private JPanel panelPostCode() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Postcode");		
		postalCodeInput = new JTextField(20);
		pane.add(label);
		pane.add(postalCodeInput);
		
		return pane;
	}
	
	private JPanel panelCountry() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Country");		
		countryInput = new JTextField(20);
		pane.add(label);
		pane.add(countryInput);
		
		return pane;
	}
	
	private JPanel panelSREmployee() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Employee Nr.");
	    salesRepNumberList = new JComboBox<Integer>(); updateCombobox();
	    pane.add(label); 
	    pane.add(salesRepNumberList); 
		
		return pane;
	}
	
	private JPanel panelCreditLimit() {
		
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Credit limit");
		creditLimitInput = new JTextField(20);
		pane.add(label); 
		pane.add(creditLimitInput); 
		
		return pane;
	}
	
	private JPanel panelButtons() {
		
		JPanel pane = new JPanel();
		
		addToDbButton = new JButton("Add customer to DB");
		addToDbButton.addActionListener(this);
		addToDbButton.setBackground(Color.WHITE);
	
		resetButton = new JButton("Reset");
		resetButton.setBounds(200, 420, 80, 25);
		resetButton.addActionListener(this);
		resetButton.setBackground(Color.WHITE);
			
		pane.add(addToDbButton);
		pane.add(resetButton);
		
		return pane;
	}
	
	public JPanel getPanel() {
		
		return panel;
	}
}

