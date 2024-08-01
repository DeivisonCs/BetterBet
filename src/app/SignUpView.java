package app;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JTextField;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFormattedTextField;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import com.toedter.calendar.JCalendar;

public class SignUpView {

	private JFrame frame;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField addressField;
	private JTextField cpfField;
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
		frame.setBounds(100, 100, 1170, 699);
		frame.getContentPane().setLayout(null);
		
		// ------------------------- Welcome Section ------------------------- 
		
		Panel WelcomeSection = new Panel();
		WelcomeSection.setBackground(new Color(192, 192, 192));
		WelcomeSection.setBounds(0, 0, 341, 660);
		frame.getContentPane().add(WelcomeSection);
		WelcomeSection.setLayout(null);
		
		txtpnBemVindoAo  = new JTextPane();
		txtpnBemVindoAo.setEditable(false);
		txtpnBemVindoAo.setBackground(new Color(192, 192, 192));
		txtpnBemVindoAo.setFont(new Font("Tahoma", Font.PLAIN, 31));
		txtpnBemVindoAo.setText("Bem Vindo\r\n      Ao\r\n BetterBet");
		txtpnBemVindoAo.setBounds(84, 202, 204, 134);
		WelcomeSection.add(txtpnBemVindoAo);
		
		JTextPane txtpnOSiteCom = new JTextPane();
		txtpnOSiteCom.setEditable(false);
		txtpnOSiteCom.setText("O site com as apostas seguras, lucrativas e com melhor conversão em ganhos do mercado!");
		txtpnOSiteCom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnOSiteCom.setBackground(Color.LIGHT_GRAY);
		txtpnOSiteCom.setBounds(44, 347, 262, 134);
		WelcomeSection.add(txtpnOSiteCom);
		
		
		// ------------------------- SignUp Section ------------------------- 
		
		JLabel PageTitle = new JLabel("Criar Conta");
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PageTitle.setBounds(637, 78, 191, 31);
		frame.getContentPane().add(PageTitle);
		
		// Name Field
		namePlaceholder = new JLabel("Nome");
		namePlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		namePlaceholder.setBounds(519, 173, 426, 14);
		frame.getContentPane().add(namePlaceholder);
		
		
		nameField = new JTextField();
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
		
		
		// Email Field
		emailPlaceholder = new JLabel("Email");
		emailPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		emailPlaceholder.setBounds(519, 229, 426, 14);
		frame.getContentPane().add(emailPlaceholder);
		
		emailField = new JTextField();
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
		
		// Password Field 
		passwordPlaceholder = new JLabel("Senha");
		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordPlaceholder.setBounds(519, 332, 209, 14);
		frame.getContentPane().add(passwordPlaceholder);
		
		passwordField = new JPasswordField();
		passwordPlaceholder.setLabelFor(passwordField);
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordPlaceholder.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(passwordField.getText().length() == 0) {
					passwordPlaceholder.setVisible(true);
				}
			}
		});
		passwordField.setBounds(505, 324, 223, 31);
		frame.getContentPane().add(passwordField);
		
		
		// Confirm Password Field
		confirmPasswordPlaceholder = new JLabel("Confirme sua senha");
		confirmPasswordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		confirmPasswordPlaceholder.setBounds(519, 391, 209, 14);
		frame.getContentPane().add(confirmPasswordPlaceholder);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordPlaceholder.setLabelFor(confirmPasswordField);
		confirmPasswordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				confirmPasswordPlaceholder.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(confirmPasswordField.getText().length() == 0) {
					confirmPasswordPlaceholder.setVisible(true);
				}
			}
		});
		confirmPasswordField.setBounds(505, 383, 223, 31);
		frame.getContentPane().add(confirmPasswordField);
	
		
		// Address Field
		addressPlaceholder = new JLabel("Endereço");
		addressPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		addressPlaceholder.setBounds(519, 284, 426, 14);
		frame.getContentPane().add(addressPlaceholder);
		
		addressField = new JTextField();
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
		
		
		// CPF Field
		cpfPlaceholder = new JLabel("CPF");
		cpfPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		cpfPlaceholder.setBounds(519, 448, 61, 14);
		frame.getContentPane().add(cpfPlaceholder);
		
		cpfField = new JTextField();
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
		
		
		// BirthDate Field
		JCalendar calendar = new JCalendar();
		frame.getContentPane().add(calendar);
		calendar.setBounds(745, 324, 200, 180);
//		lblDataDeNascimento = new JLabel("Data de Nascimento");
//		lblDataDeNascimento.setFont(new Font("Arial", Font.PLAIN, 12));
//		lblDataDeNascimento.setBounds(750, 442, 140, 14);
//		frame.getContentPane().add(lblDataDeNascimento);
		
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(637, 549, 179, 59);
		frame.getContentPane().add(btnNewButton);
		
		// ------------------------- SignIn Link ------------------------- 
		JLabel lblNewLabel = new JLabel("<html><a href=''>Já Possui Conta?</a></html>");
		lblNewLabel.setBounds(690, 618, 117, 14);
		frame.getContentPane().add(lblNewLabel);
			
	}
}
