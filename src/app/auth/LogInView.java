package app.auth;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

import app.homeUser.HomeUserUI;
import components.RoundedButtonComponent;
import components.RoundedPasswordFieldComponent;
import components.RoundedTextFieldComponent;
import service.users.UserService;

public class LogInView {
	private UserService service = new UserService();
	private JFrame frame;
	private JTextPane txtpnBemVindoAo;
	private JLabel emailPlaceholder;
	private JLabel passwordPlaceholder;
	private RoundedTextFieldComponent emailField;
	private JPasswordField passwordField;
	
	/**
	* Interface de login do usuário.
	* Após o usuário ser validado ele é redirecionado para a tela home do site (src/app/homeUser/HomeUserUI)
	* 
	* @param positionX Posição X da tela anterior
	* @param positionY Posição Y da tela anterior
	*/
	public LogInView(int positionX, int positionY) {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.setBounds(positionX, positionY, 1170, 699);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		// ------------------------- Welcome Section ------------------------- 
		
		Panel WelcomeSection = new Panel();
		WelcomeSection.setBackground(new Color(0, 0, 0));
		WelcomeSection.setBounds(0, 0, 341, 660);
		frame.getContentPane().add(WelcomeSection);
		WelcomeSection.setLayout(null);
		
		txtpnBemVindoAo  = new JTextPane();
		txtpnBemVindoAo.setForeground(new Color(255, 215, 0));
		txtpnBemVindoAo.setEditable(false);
		txtpnBemVindoAo.setBackground(new Color(0, 0, 0));
		txtpnBemVindoAo.setFont(new Font("Tahoma", Font.PLAIN, 37));
		txtpnBemVindoAo.setText("Bem Vindo\r\n De Volta\r\n     Ao\r\n BetterBet");
		txtpnBemVindoAo.setBounds(81, 226, 204, 188);
		WelcomeSection.add(txtpnBemVindoAo);
		
		
		// ------------------------- SignIn Section ------------------------- 
		
		JLabel PageTitle = new JLabel("Entrar");
		PageTitle.setForeground(new Color(255, 215, 0));
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 33));
		PageTitle.setBounds(681, 128, 98, 31);
		frame.getContentPane().add(PageTitle);
		
		
		// ------------------------- Email Field -------------------------
		emailPlaceholder = new JLabel("Email");
		emailPlaceholder.setForeground(new Color(156, 156, 156));
		emailPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		emailPlaceholder.setBounds(589, 278, 274, 14);
		frame.getContentPane().add(emailPlaceholder);
		
		emailField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
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
		emailField.setBounds(575, 270, 288, 31);
		frame.getContentPane().add(emailField);
		
		// ------------------------- Password Field -------------------------
		passwordPlaceholder = new JLabel("Senha");
		passwordPlaceholder.setForeground(new Color(156, 156, 156));
		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordPlaceholder.setBounds(589, 341, 274, 14);
		frame.getContentPane().add(passwordPlaceholder);
		
		passwordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
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
		passwordField.setBounds(575, 333, 288, 31);
		frame.getContentPane().add(passwordField);
		
		
		
		// ------------------------- LogIn Button -------------------------
		/**
		* Ao ser clicado, o button verifica as credenciais digitadas pelo usuário,
		* chama o userService para verificar os dados inseridos e fazer a validação do usuário.
		* Após validação dos dados o usuário é redirecionado para a tela home do site (src/app/homeUser/HomeUserUI)
		* 
		* @throws SQLException Caso haja algum erro nas credenciais do usuário
		*/
		RoundedButtonComponent button = new RoundedButtonComponent("Logar", new Color(255, 215, 0), new Color(102, 203, 102));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());
				
				try {
					Integer userId = service.loginUser(email, password);
					
					Point location = frame.getLocationOnScreen();
					int x = location.x;
					int y = location.y;
					frame.dispose();
					
					new HomeUserUI(userId, x, y);
				}
				catch(SQLException ex) {
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
		button.setFont(new Font("Tahoma", Font.PLAIN, 24));
        button.setBounds(642, 540, 179, 59);
        button.setBackground(new Color(102, 203, 102)); // Example color
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
		
		// ------------------------- SignUn Link ------------------------- 
        /**
		* Ao ser clicado troca a tela de login para a tela de cadastro (src/app/auth/SignUpView.java).
		*/
		JLabel lblNewLabel = new JLabel("<html><a href='' style='color: #A3C2FF; text-decoration: none;'>Não Possui Conta?</a></html>");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new SignUpView();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel.setText("<html><a href='' style='color: #A3C2FF; text-decoration: underline;'>Não Possui Conta?</a></html>");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setText("<html><a href='' style='color: #A3C2FF; text-decoration: none;'>Não Possui Conta?</a></html>");
			}
		});
		lblNewLabel.setForeground(new Color(255, 2, 255));
		lblNewLabel.setBounds(682, 610, 117, 14);
		frame.getContentPane().add(lblNewLabel);
			
	}
}
