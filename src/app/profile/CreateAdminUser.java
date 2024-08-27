package app.profile;

import java.awt.Color;
import java.awt.Font;
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

import components.ImageUtils;
import components.RoundedButtonComponent;
import components.RoundedPasswordFieldComponent;
import components.RoundedTextFieldComponent;
import exceptions.InvalidAddressException;
import exceptions.InvalidBirthDateException;
import exceptions.InvalidCpfException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPasswordException;
import models.AdminUser;
import service.users.UserService;

/**
 * Classe responsável pela criação da interface gráfica para o cadastro de um administrador.
 */
public class CreateAdminUser {
	private int positionX;
	private int positionY;
	
	private UserService userService = new UserService();
	private JFrame frame;


	/**
	* Interface de criação de usuários administradores (acessível apenas para usuários administradores).
	* 
	* @param positionX Posição X da tela anterior
	* @param positionY Posição Y da tela anterior
	*/
	public CreateAdminUser(int userId, int positionX, int positionY) {
		this.positionX = positionX;
    	this.positionY = positionY;
    	
		initialize();
	}

	/**
	 * Inicializa o conteúdo da janela.
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
		/**
    	* Fecha a tela atual.
    	*/
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
		
		
		// ------------------------- Create Button -------------------------
		/**
		* Ao ser clicado, o button cria uma instância de AdminUser,
		* chama o userService para verificar os dados inseridos e fazer a  inserção no banco.
		* 
		* @throws SQLException Caso haja algum erro no banco
		* @throws IOException Caso haja algum erro relacionado a inserção da imagem de perfil do usuário
		* @throws InvalidNameException Caso o nome digitado pelo usuário seja inválido
		* @throws InvalidEmailException Caso o email digitado pelo usuário seja inválido
		* @throws InvalidAddressException Caso o endereço digitado pelo usuário seja inválido (Usuário administrador não possui o campo de endereço)
		* @throws InvalidCpfException Caso o CPF digitado pelo usuário seja inválido
		* @throws InvalidPasswordException Caso a senha digitada pelo usuário seja inválida
		* @throws InvalidBirthDateException Caso a data de nascimento inserida pelo usuário seja inválida (Usuário administrador não possui o campo de data de nascimento)
		*/ 

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
					userService.createUser(newUser, new String(confirmPasswordField.getPassword()));
					
					JOptionPane.showMessageDialog(null, "Administrador Cadastrado com Sucesso!");	
				}
				catch(SQLException | IOException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				catch(InvalidNameException | InvalidEmailException | InvalidAddressException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				catch(InvalidCpfException | InvalidPasswordException | InvalidBirthDateException ex) {
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
