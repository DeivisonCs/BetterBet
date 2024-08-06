package app;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Panel;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import com.toedter.calendar.JCalendar;

import components.RoundedTextField;
import dao.CommonUserDAO;
import dao.UserPostgresDAO;
import database.InitDatabase;
import models.CommonUser;

import components.RoundedButton;
import components.RoundedPasswordField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import security.*;

import middleware.UserMiddleware; 

public class SignUpView {
	private CommonUserDAO userDb = new UserPostgresDAO();
	private UserMiddleware userMiddleware = new UserMiddleware();
	public SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy/MM/dd");
	
	private JFrame frame;
	private RoundedTextField nameField;
	private RoundedTextField emailField;
	private RoundedTextField addressField;
	private RoundedTextField cpfField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextPane txtpnBemVindoAo;
	private JLabel namePlaceholder;
	private JLabel emailPlaceholder;
	private JLabel passwordPlaceholder;
	private JLabel confirmPasswordPlaceholder;
	private JLabel addressPlaceholder;
	private JLabel cpfPlaceholder;
	private JLabel lblDataDeNascimento;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitDatabase.initializeDatabase();
					SignUpView window = new SignUpView();
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
	public SignUpView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		
		nameField = new RoundedTextField();
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
		nameField.setBounds(505, 165, 440, 31);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		
		// ------------------------- Email Field -------------------------
		emailPlaceholder = new JLabel("Email");
		emailPlaceholder.setForeground(new Color(156, 156, 156));
		emailPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		emailPlaceholder.setBounds(519, 229, 426, 14);
		frame.getContentPane().add(emailPlaceholder);
		
		emailField = new RoundedTextField();
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
		
		passwordField = new RoundedPasswordField();
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
		
		confirmPasswordField = new RoundedPasswordField();
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
		
		addressField = new RoundedTextField();
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
		
		cpfField = new RoundedTextField();
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
//		lblDataDeNascimento = new JLabel("Data de Nascimento");
//		lblDataDeNascimento.setFont(new Font("Arial", Font.PLAIN, 12));
//		lblDataDeNascimento.setBounds(750, 442, 140, 14);
//		frame.getContentPane().add(lblDataDeNascimento);
		
		
		// ------------------------- SignUp Button -------------------------
		RoundedButton button = new RoundedButton("Cadastrar");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				CommonUser newUser = new CommonUser(
						nameField.getText(),
						cpfField.getText(),
						formatedDate.format(birthDateField.getDate()),
						emailField.getText(),
						addressField.getText(),
						new String(passwordField.getPassword()),
						"user",
						(float) 0.0);
				
				// Faz as validações necessárias no banco
				String validField = userMiddleware.verifyNewUser(newUser, new String(confirmPasswordField.getPassword()));
				
				if(validField != "200") {
					JOptionPane.showMessageDialog(null, validField);
				}
				else {
					try{
						newUser.setPassword(PasswordHandler.hashPassword(newUser.getPassword()));
						userDb.createUser(newUser);
						JOptionPane.showMessageDialog(null, "Usuário cadastrado!\nBem vindo " + newUser.getName());
					}
					catch(SQLException ex) {
						JOptionPane.showMessageDialog(null, ex);
					}
				}
				
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(637, 522, 179, 59);
        button.setBackground(new Color(102, 203, 102)); // Example color
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
		
		// ------------------------- SignIn Link ------------------------- 
		JLabel lblNewLabel = new JLabel("<html><a href='' style='color: #A3C2FF;'>Já Possui Conta?</a></html>");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new LogInView();
			}
		});
		lblNewLabel.setForeground(new Color(255, 2, 255));
		lblNewLabel.setBounds(685, 592, 117, 14);
		frame.getContentPane().add(lblNewLabel);
			
	}
}
