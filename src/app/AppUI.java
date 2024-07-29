package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import dao.UserDAO;
import dao.UserPostgresDAO;

import models.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppUI {

	private JFrame frame;
	private JTextField signInNameField;
	private JTextField signInEmailField;
	private JTextField signInFieldPassword;
	private UserDAO userDB = new UserPostgresDAO();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppUI window = new AppUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 728, 483);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		signInNameField = new JTextField();
		signInNameField.setBounds(188, 97, 288, 27);
		frame.getContentPane().add(signInNameField);
		signInNameField.setColumns(10);
		
		signInEmailField = new JTextField();
		signInEmailField.setColumns(10);
		signInEmailField.setBounds(188, 151, 288, 27);
		frame.getContentPane().add(signInEmailField);
		
		JLabel signInLabelName = new JLabel("Name");
		signInLabelName.setBounds(188, 82, 46, 14);
		frame.getContentPane().add(signInLabelName);
		
		JLabel signInLabelEmail = new JLabel("Email");
		signInLabelEmail.setBounds(188, 135, 46, 14);
		frame.getContentPane().add(signInLabelEmail);
		
		JSpinner signInAgeField = new JSpinner();
		signInAgeField.setModel(new SpinnerNumberModel(Integer.valueOf(18), Integer.valueOf(18), null, Integer.valueOf(1)));
		signInAgeField.setBounds(203, 280, 46, 27);
		frame.getContentPane().add(signInAgeField);
		
		JSpinner signInBalanceField = new JSpinner();
		signInBalanceField.setModel(new SpinnerNumberModel(Float.valueOf(0), Float.valueOf(0), null, Float.valueOf(10)));
		signInBalanceField.setBounds(392, 283, 93, 24);
		frame.getContentPane().add(signInBalanceField);
		
		JLabel signInLabelAge = new JLabel("Age");
		signInLabelAge.setBounds(203, 264, 46, 14);
		frame.getContentPane().add(signInLabelAge);
		
		JLabel signInLabelBalance = new JLabel("Balance :");
		signInLabelBalance.setBounds(338, 286, 46, 14);
		frame.getContentPane().add(signInLabelBalance);
		
		JButton SignInButton = new JButton("Create Account");
		SignInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try {
					User newUser = new User(
						signInNameField.getText(),
						(int) signInAgeField.getValue(),
						signInEmailField.getText(),
						signInFieldPassword.getText(),
						"user"
					);
					
					userDB.createUser(newUser);
					
					JOptionPane.showMessageDialog(null, "Account Created! \n Welcome " + newUser.getName());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Failed to Create Account!");
					ex.printStackTrace();
				}
				
				
			}
		});
		SignInButton.setBounds(275, 335, 124, 46);
		frame.getContentPane().add(SignInButton);
		
		signInFieldPassword = new JTextField();
		signInFieldPassword.setColumns(10);
		signInFieldPassword.setBounds(188, 205, 288, 27);
		frame.getContentPane().add(signInFieldPassword);
		
		JLabel signInLabelPassword = new JLabel("Password");
		signInLabelPassword.setBounds(188, 189, 46, 14);
		frame.getContentPane().add(signInLabelPassword);
	}
}
