package app.auth;

import javax.swing.JFrame;

import java.awt.Panel;
import java.awt.Point;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import com.toedter.calendar.JCalendar;

import app.homeUser.HomeUserUI;
import components.RoundedTextFieldComponent;
import exceptions.InvalidAddressException;
import exceptions.InvalidBirthDateException;
import exceptions.InvalidCpfException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPasswordException;
import models.CommonUser;
import components.RoundedButtonComponent;
import components.RoundedPasswordFieldComponent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import service.users.CommonUserService;

/**
 * Classe que representa a tela de cadastro de novos usuários.
 */
public class SignUpView {
	private CommonUserService userService = new CommonUserService();
	public SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy/MM/dd");
	
	private JFrame frame;
	private RoundedTextFieldComponent nameField;
	private RoundedTextFieldComponent emailField;
	private RoundedTextFieldComponent addressField;
	private RoundedTextFieldComponent cpfField;
	private RoundedPasswordFieldComponent passwordField;
	private RoundedPasswordFieldComponent confirmPasswordField;
	private JTextPane txtpnBemVindoAo;
	private JLabel namePlaceholder;
	private JLabel emailPlaceholder;
	private JLabel passwordPlaceholder;
	private JLabel confirmPasswordPlaceholder;
	private JLabel addressPlaceholder;
	private JLabel cpfPlaceholder;
	
	/**
	* Interface de cadastro de usuário.
	* Os campos são verificados para garantir a integridade dos dados.
	* Após validação dos dados o usuário é redirecionado para a tela home do site (src/app/homeUser/HomeUserUI.java)
	*/
	public SignUpView() {	
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.setBounds(100, 100, 1170, 699);
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
		txtpnBemVindoAo.setFont(new Font("Tahoma", Font.PLAIN, 31));
		txtpnBemVindoAo.setText("Bem Vindo\r\n      Ao\r\n BetterBet");
		txtpnBemVindoAo.setBounds(84, 202, 204, 134);
		WelcomeSection.add(txtpnBemVindoAo);
		
		JTextPane txtpnOSiteCom = new JTextPane();
		txtpnOSiteCom.setForeground(new Color(255, 255, 255));
		txtpnOSiteCom.setEditable(false);
		txtpnOSiteCom.setText("O site com as apostas seguras, lucrativas e com melhor conversão em ganhos do mercado!");
		txtpnOSiteCom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnOSiteCom.setBackground(new Color(0, 0, 0));
		txtpnOSiteCom.setBounds(44, 347, 262, 134);
		WelcomeSection.add(txtpnOSiteCom);
		
		
		// ------------------------- SignUp Section ------------------------- 
		
		JLabel PageTitle = new JLabel("Criar Conta");
		PageTitle.setForeground(new Color(255, 215, 0));
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PageTitle.setBounds(637, 78, 191, 31);
		frame.getContentPane().add(PageTitle);
		
		// ------------------------- Name Field -------------------------
		namePlaceholder = new JLabel("Nome");
		namePlaceholder.setForeground(new Color(156, 156, 156));
		namePlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		namePlaceholder.setBounds(519, 173, 426, 14);
		frame.getContentPane().add(namePlaceholder);
		
		
		nameField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		namePlaceholder.setLabelFor(nameField);
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				namePlaceholder.setVisible(false);
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if(nameField.getText().length() == 0) {
					namePlaceholder.setVisible(true);
				}
			}
		});
		nameField.setBounds(505, 165, 440, 31);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		
		// ------------------------- Email Field -------------------------
		emailPlaceholder = new JLabel("Email");
		emailPlaceholder.setForeground(new Color(156, 156, 156));
		emailPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		emailPlaceholder.setBounds(519, 229, 426, 14);
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
		emailField.setBounds(505, 221, 440, 31);
		frame.getContentPane().add(emailField);
		
		// ------------------------- Password Field -------------------------
		passwordPlaceholder = new JLabel("Senha");
		passwordPlaceholder.setForeground(new Color(156, 156, 156));
		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordPlaceholder.setBounds(519, 332, 209, 14);
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
		passwordField.setBounds(505, 324, 223, 31);
		frame.getContentPane().add(passwordField);
		
		// ------------------------- Confirm Password Field -------------------------
		confirmPasswordPlaceholder = new JLabel("Confirme sua senha");
		confirmPasswordPlaceholder.setForeground(new Color(156, 156, 156));
		confirmPasswordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		confirmPasswordPlaceholder.setBounds(519, 391, 209, 14);
		frame.getContentPane().add(confirmPasswordPlaceholder);
		
		confirmPasswordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
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
		confirmPasswordField.setBounds(505, 383, 223, 31);
		frame.getContentPane().add(confirmPasswordField);
	
		
		// ------------------------- Address Field -------------------------
		addressPlaceholder = new JLabel("Endereço");
		addressPlaceholder.setForeground(new Color(156, 156, 156));
		addressPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		addressPlaceholder.setBounds(519, 284, 426, 14);
		frame.getContentPane().add(addressPlaceholder);
		
		addressField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		addressPlaceholder.setLabelFor(addressField);
		addressField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				addressPlaceholder.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(addressField.getText().length() == 0) {
					addressPlaceholder.setVisible(true);
				}
			}
		});
		addressField.setColumns(10);
		addressField.setBounds(505, 276, 440, 31);
		frame.getContentPane().add(addressField);
		
		
		// ------------------------- CPF Field -------------------------
		cpfPlaceholder = new JLabel("CPF");
		cpfPlaceholder.setForeground(new Color(156, 156, 156));
		cpfPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		cpfPlaceholder.setBounds(519, 448, 61, 14);
		frame.getContentPane().add(cpfPlaceholder);
		
		cpfField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		cpfPlaceholder.setLabelFor(cpfField);
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
		cpfField.setColumns(10);
		cpfField.setBounds(505, 440, 223, 31);
		frame.getContentPane().add(cpfField);
		
		
		// ------------------------- BirthDate Field -------------------------
		JCalendar birthDateField = new JCalendar();
		frame.getContentPane().add(birthDateField);
		birthDateField.setBounds(745, 324, 200, 145);
		
		
		// ------------------------- SignUp Button -------------------------
		/**
		* Ao ser clicado, o button cria uma instância de CommonUser,
		* chama o userService para verificar os dados inseridos e fazer a  inserção no banco.
		*  Após validação dos dados o usuário é redirecionado para a tela home do site (src/app/homeUser/HomeUserUI)
		* 
		* @throws SQLException Caso haja algum erro no banco
		* @throws IOException Caso haja algum erro relacionado a inserção da imagem de perfil do usuário
		* @throws InvalidNameException Caso o nome digitado pelo usuário seja inválido
		* @throws InvalidEmailException Caso o email digitado pelo usuário seja inválido
		* @throws InvalidAddressException Caso o endereço digitado pelo usuário seja inválido
		* @throws InvalidCpfException Caso o CPF digitado pelo usuário seja inválido
		* @throws InvalidPasswordException Caso a senha digitada pelo usuário seja inválida
		* @throws InvalidBirthDateException Caso a data de nascimento inserida pelo usuário seja inválida
		*/ 
		RoundedButtonComponent button = new RoundedButtonComponent("Cadastrar", new Color(255, 215, 0), new Color(102, 203, 102));
		button.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				CommonUser newUser = new CommonUser(
						nameField.getText(),
						null,
						cpfField.getText(),
						formatedDate.format(birthDateField.getDate()),
						emailField.getText(),
						addressField.getText(),
						new String(passwordField.getPassword()),
						"user",
						(float) 0.0);
				
				try {
					userService.createUser(newUser, new String(confirmPasswordField.getPassword()));
					
					JOptionPane.showMessageDialog(null, "Usuário cadastrado!\nBem vindo " + newUser.getName());
							
					Point location = frame.getLocationOnScreen();
					int x = location.x;
					int y = location.y;
					frame.dispose();
					
					new HomeUserUI(userService.loginUser(newUser.getEmail(), new String(passwordField.getPassword())), x, y);
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
        button.setBounds(637, 522, 179, 59);
        button.setBackground(new Color(102, 203, 102));
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
        
        
		
		// ------------------------- SignIn Link ------------------------- 
        /**
		* Ao ser clicado troca a tela de cadastro para a tela de login (src/app/auth/LogInView.java).
		* Antes de realizar a troca de tela é feito a captura da localização x e y da tela, para suavização da troca de tela.
		*/
		JLabel lblNewLabel = new JLabel("<html><a href='' style='color: #A3C2FF; text-decoration: none;'>Já Possui Conta?</a></html>");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;
        		frame.dispose();
        		
        		new LogInView(x, y);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel.setText("<html><a href='' style='color: #A3C2FF; text-decoration: underline;'>Já Possui Conta?</a></html>");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setText("<html><a href='' style='color: #A3C2FF; text-decoration: none;'>Já Possui Conta?</a></html>");
			}
		});
		lblNewLabel.setForeground(new Color(255, 2, 255));
		lblNewLabel.setBounds(676, 592, 117, 14);
		frame.getContentPane().add(lblNewLabel);
			
	}
}
