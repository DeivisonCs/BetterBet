package app.profile;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import app.homeUser.HomeUserUI;
import components.ImageUtils;
import components.RoundedButtonComponent;
import components.RoundedPasswordFieldComponent;
import components.RoundedTextFieldComponent;
import models.AdminUser;
import models.CommonUser;
import service.users.UserService;

public class CreateAdminUser {
	private int positionX;
	private int positionY;
	
	private int userId;
	private UserService userService = new UserService();
	private JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CreateAdminUser window = new CreateAdminUser();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public CreateAdminUser(int userId, int positionX, int positionY) {
		this.positionX = positionX;
    	this.positionY = positionY;
    	this.userId = userId;
    	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.setBounds(positionX, positionY, 800, 750);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		// ------------------------- Page Text -------------------------
		JLabel PageTitle = new JLabel("Cadastrar Administrador");
		PageTitle.setForeground(new Color(255, 215, 0));
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 33));
		PageTitle.setBounds(193, 65, 367, 31);
		frame.getContentPane().add(PageTitle);
		
		
		// ------------------------- Return Button -------------------------        
        
        ImageUtils returnButton = new ImageUtils();
        returnButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		frame.dispose();
        	}
        });
        returnButton.setBorderSize(0);
        
        returnButton.setBorder(null);
        returnButton.setImage(new ImageIcon(getClass().getResource("/resources/images/back-arrow.jpg")));
        returnButton.setBounds(24, 24, 30, 30);
        frame.getContentPane().add(returnButton);
        
        // ------------------------- Name Field -------------------------
        JLabel namePlaceholder = new JLabel("Nome");
 		namePlaceholder.setForeground(new Color(156, 156, 156));
 		namePlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
 		namePlaceholder.setBounds(219, 207, 330, 14);
 		frame.getContentPane().add(namePlaceholder);
 		
 		
 		RoundedTextFieldComponent nameField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
 		namePlaceholder.setLabelFor(nameField);
 		nameField.addFocusListener(new FocusAdapter() {
 			
 			// Tira o placeholder quando o usuário clicar no campo
 			@Override
 			public void focusGained(FocusEvent e) {
 				namePlaceholder.setVisible(false);
 			}
 			// Coloca o placeholder quando o usuário sair do campo e não tiver texto
 			@Override
 			public void focusLost(FocusEvent e) {
 				if(nameField.getText().length() == 0) {
 					namePlaceholder.setVisible(true);
 				}
 			}
 		});
 		nameField.setBounds(205, 199, 344, 31);
 		frame.getContentPane().add(nameField);
 		nameField.setColumns(10);
 		
 		
 		// ------------------------- Email Field -------------------------
 		JLabel emailPlaceholder = new JLabel("Email");
 		emailPlaceholder.setForeground(new Color(156, 156, 156));
 		emailPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
 		emailPlaceholder.setBounds(219, 263, 330, 14);
 		frame.getContentPane().add(emailPlaceholder);
 		
 		RoundedTextFieldComponent emailField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
 		emailPlaceholder.setLabelFor(emailField);
 		emailField.addFocusListener(new FocusAdapter() {
 			@Override
 			public void focusGained(FocusEvent e) {
 				emailPlaceholder.setVisible(false);
 			}
 			@Override
 			public void focusLost(FocusEvent e) {
 				if(emailField.getText().length() == 0) {
 					emailPlaceholder.setVisible(true);
 				}
 			}
 		});
 		emailField.setColumns(10);
 		emailField.setBounds(205, 255, 344, 31);
 		frame.getContentPane().add(emailField);
 		
 		
 	// ------------------------- Cpf Field -------------------------
 		JLabel cpfPlaceholder = new JLabel("CPF");
		cpfPlaceholder.setForeground(new Color(156, 156, 156));
		cpfPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		cpfPlaceholder.setBounds(219, 323, 330, 14);
		frame.getContentPane().add(cpfPlaceholder);
		
		RoundedTextFieldComponent cpfField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		cpfField.setColumns(10);
		cpfField.setBounds(205, 315, 344, 31);
		cpfField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				cpfPlaceholder.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(cpfField.getText().length() == 0) {
					cpfPlaceholder.setVisible(true);
				}
			}
		});
		frame.getContentPane().add(cpfField);
 		
	
 		// ------------------------- Password Field -------------------------
		JLabel passwordPlaceholder = new JLabel("Senha");
		passwordPlaceholder.setForeground(new Color(156, 156, 156));
		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordPlaceholder.setBounds(219, 385, 330, 14);
		frame.getContentPane().add(passwordPlaceholder);
		
		RoundedPasswordFieldComponent passwordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
		passwordPlaceholder.setLabelFor(passwordField);
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordPlaceholder.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(passwordField.getPassword().length == 0) {
					passwordPlaceholder.setVisible(true);
				}
			}
		});
		passwordField.setBounds(205, 377, 344, 31);
		frame.getContentPane().add(passwordField);
		
		// ------------------------- Confirm Password Field -------------------------
		JLabel confirmPasswordPlaceholder = new JLabel("Confirme sua senha");
		confirmPasswordPlaceholder.setForeground(new Color(156, 156, 156));
		confirmPasswordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		confirmPasswordPlaceholder.setBounds(219, 441, 330, 14);
		frame.getContentPane().add(confirmPasswordPlaceholder);
		
		RoundedPasswordFieldComponent confirmPasswordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
		confirmPasswordPlaceholder.setLabelFor(confirmPasswordField);
		confirmPasswordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				confirmPasswordPlaceholder.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(confirmPasswordField.getPassword().length == 0) {
					confirmPasswordPlaceholder.setVisible(true);
				}
			}
		});
		confirmPasswordField.setBounds(205, 433, 344, 31);
		frame.getContentPane().add(confirmPasswordField);
		
		
		// ------------------------- SignUp Button -------------------------
		RoundedButtonComponent button = new RoundedButtonComponent("Cadastrar", new Color(255, 215, 0), new Color(102, 203, 102));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				AdminUser newUser = new AdminUser(
						nameField.getText(),
						null,
						cpfField.getText(),
						emailField.getText(),
						new String(passwordField.getPassword()),
						"admin");
				
				try {
					String validUser = userService.createUser(newUser, new String(confirmPasswordField.getPassword()));
					
					if(!validUser.equals("200")) {
						JOptionPane.showMessageDialog(null, validUser);
					}
					else {
						JOptionPane.showMessageDialog(null, "Administrador Cadastrado com Sucesso!");
					}
				}
				catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setFont(new Font("Tahoma", Font.PLAIN, 19));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setFont(new Font("Tahoma", Font.PLAIN, 24));
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(288, 598, 179, 59);
        button.setBackground(new Color(102, 203, 102));
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
		
	}
}
