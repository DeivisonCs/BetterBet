package app.edit;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import components.ImageUtils;
import components.RoundedButton;
import components.RoundedPasswordFieldComponent;
import components.RoundedTextFieldComponent;
import models.AdminUser;
import models.CommonUser;
import models.User;
import service.users.UserService;

public class EditUser {

	private JFrame frame;
	private User user;
	private User userEdited;
	private UserService userService = new UserService();
	
	private JLabel namePlaceholder;
	private RoundedTextFieldComponent nameField;
	
	private JLabel emailPlaceholder;
	private RoundedTextFieldComponent emailField;
	
	private JLabel addressPlaceholder;
	private RoundedTextFieldComponent addressField;

	private JLabel passwordPlaceholder;
	private RoundedPasswordFieldComponent passwordField;
	
	private JLabel confirmPasswordPlaceholder;
	private RoundedPasswordFieldComponent confirmPasswordField;
	
	
	public EditUser(Integer userId) {
//		user = new CommonUser("Maria", "111.111.111-11", "2023/23/23", "teste@.com", "rua tal de tal", "sfdf", "user", (float) 0.0);
		
		try {
			User loggedUser = userService.getUser(userId);
			
			if(loggedUser.getPermission().equals("user")) {
				this.user = (CommonUser) loggedUser;
				System.out.println("Home user: " + user.toString());
			}
			else {			
				this.user = (AdminUser) loggedUser;
				System.out.println("Home admin user: " + user.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.setBounds(100, 100, 800, 750);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		// ------------------------- Page Text -------------------------
		JLabel PageTitle = new JLabel("Atualizar Informações");
		PageTitle.setForeground(new Color(255, 215, 0));
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 33));
		PageTitle.setBounds(216, 63, 327, 31);
		frame.getContentPane().add(PageTitle);
		
		
		// ------------------------- ToolTip -------------------------
		ImageUtils informationIcon = new ImageUtils();
        informationIcon.setForeground(new Color(255, 255, 255));
        informationIcon.setBorderColor(new Color(255, 255, 255));
        informationIcon.setBorderSize(0);
        
        informationIcon.setBorder(null);
        informationIcon.setImage(new ImageIcon(getClass().getResource("/public/images/help-icon.png")));
        informationIcon.setBounds(538, 74, 20, 20);
        informationIcon.setToolTipText("Edite apenas os campos que deseja alterar");
        
        frame.getContentPane().add(informationIcon);
		
		
		// ------------------------- Return Button -------------------------        
        
        ImageUtils returnButton = new ImageUtils();
        returnButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		frame.dispose();
        	}
        });
        returnButton.setBorderSize(0);
        
        returnButton.setBorder(null);;
        returnButton.setImage(new ImageIcon(getClass().getResource("/public/images/back-arrow.jpg")));
        returnButton.setBounds(24, 24, 30, 30);
        frame.getContentPane().add(returnButton);
		
		
		// ------------------------- Profile Img -------------------------        
        
        ImageUtils profilePicture = new ImageUtils();
        profilePicture.setBorderSize(0);
        
        profilePicture.setBorder(null);;
        profilePicture.setImage(new ImageIcon(getClass().getResource("/public/images/Profile-Icon.jpg")));
        profilePicture.setBounds(138, 159, 147, 147);
        frame.getContentPane().add(profilePicture);
		
		
		// ------------------------- Name Field -------------------------
		namePlaceholder = new JLabel("Nome");
		namePlaceholder.setForeground(new Color(156, 156, 156));
		namePlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		namePlaceholder.setBounds(312, 179, 319, 14);
		frame.getContentPane().add(namePlaceholder);
		
		
		nameField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		namePlaceholder.setLabelFor(nameField);
		nameField.setBounds(312, 204, 319, 31);
		nameField.setText(user.getName());
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		
		// ------------------------- Email Field -------------------------
		emailPlaceholder = new JLabel("Email");
		emailPlaceholder.setForeground(new Color(156, 156, 156));
		emailPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		emailPlaceholder.setBounds(312, 250, 319, 14);
		frame.getContentPane().add(emailPlaceholder);
		
		emailField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		emailPlaceholder.setLabelFor(emailField);
		emailField.setColumns(10);
		emailField.setBounds(312, 275, 319, 31);
		emailField.setText(user.getEmail());
		frame.getContentPane().add(emailField);
		
		// ------------------------- Address Field -------------------------
		if(user instanceof CommonUser) {
			addressPlaceholder = new JLabel("Endereço");
			addressPlaceholder.setForeground(new Color(156, 156, 156));
			addressPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
			addressPlaceholder.setBounds(163, 333, 468, 14);
			frame.getContentPane().add(addressPlaceholder);

			addressField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
			addressPlaceholder.setLabelFor(addressField);
			addressField.setColumns(10);
			addressField.setBounds(163, 358, 468, 31);
			addressField.setText(((CommonUser)user).getAddress());
			frame.getContentPane().add(addressField);
		}

		
		
		// ------------------------- Password Field -------------------------
		passwordPlaceholder = new JLabel("Senha");
		passwordPlaceholder.setForeground(new Color(156, 156, 156));
		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordPlaceholder.setBounds(163, 400, 468, 14);
		frame.getContentPane().add(passwordPlaceholder);
		
		passwordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
		passwordPlaceholder.setLabelFor(passwordField);
		passwordField.setBounds(163, 425, 468, 31);
		frame.getContentPane().add(passwordField);
		
		
		// ------------------------- Confirm Password Field -------------------------
		confirmPasswordPlaceholder = new JLabel("Confirme sua senha");
		confirmPasswordPlaceholder.setForeground(new Color(156, 156, 156));
		confirmPasswordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		confirmPasswordPlaceholder.setBounds(166, 467, 465, 14);
		frame.getContentPane().add(confirmPasswordPlaceholder);
		
		confirmPasswordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
		confirmPasswordPlaceholder.setLabelFor(confirmPasswordField);
		confirmPasswordField.setBounds(166, 492, 465, 31);
		frame.getContentPane().add(confirmPasswordField);
		
		
		// ------------------------- SignUp Button -------------------------
		RoundedButton button = new RoundedButton("Salvar");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(255, 215, 0));
				button.setFont(new Font("Tahoma", Font.PLAIN, 19));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(102, 203, 102));
				button.setFont(new Font("Tahoma", Font.PLAIN, 24));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				String password = passwordField.getPassword().length == 0? null : new String(passwordField.getPassword());
				String confirmPassword = confirmPasswordField.getPassword().length == 0 ? null : new String(confirmPasswordField.getPassword());
				
				user.setName(nameField.getText());
				user.setEmail(emailField.getText());
				user.setPassword(user.getPassword());
				
				if(user.getPermission().equals("user")) {
					((CommonUser)user).setAddress(addressField.getText());
				}
				
				try {
					String validUser = userService.updateUser(user, password, confirmPassword);
					
					if(!validUser.equals("200")) {
						JOptionPane.showMessageDialog(null, validUser);
					}
					else {
						JOptionPane.showMessageDialog(null, "Usuário Atualizado!" );
						frame.dispose();
					}
				}
				catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
//				userEdited = user.getPermission().equals("admin") ? new AdminUser() : new CommonUser();
//				
//				String password = new String(passwordField.getPassword()).equals("")? user.getPassword() : new String(passwordField.getPassword());
//				
//				userEdited.setName(nameField.getText());
//				userEdited.setCpf(user.getCpf());
//				userEdited.setEmail(emailField.getText());
//				userEdited.setPassword(password);
//				userEdited.setPermission(user.getPermission());
//				
//				if(userEdited.getPermission().equals("user")) {
//					((CommonUser)userEdited).setAddress(((CommonUser)user).getAddress());
//				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(301, 596, 179, 59);
        button.setBackground(new Color(102, 203, 102));
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
        
	}
}
