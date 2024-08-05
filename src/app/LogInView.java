package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

import components.RoundedButton;
import components.RoundedPasswordField;
import components.RoundedTextField;

public class LogInView {
	private JFrame frame;
	private JTextPane txtpnBemVindoAo;
	private JLabel emailPlaceholder;
	private JLabel passwordPlaceholder;
	private RoundedTextField emailField;
	private JPasswordField passwordField;
	
	public LogInView() {
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
		emailPlaceholder.setBounds(588, 251, 274, 14);
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
		emailField.setBounds(574, 243, 288, 31);
		frame.getContentPane().add(emailField);
		
		// ------------------------- Password Field -------------------------
		passwordPlaceholder = new JLabel("Senha");
		passwordPlaceholder.setForeground(new Color(156, 156, 156));
		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordPlaceholder.setBounds(588, 314, 274, 14);
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
				if(passwordField.getText().length() == 0) {
					passwordPlaceholder.setVisible(true);
				}
			}
		});
		passwordField.setBounds(574, 306, 288, 31);
		frame.getContentPane().add(passwordField);
		
		
		
		// ------------------------- SignIp Button -------------------------
		RoundedButton button = new RoundedButton("Logar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 24));
        button.setBounds(641, 472, 179, 59);
        button.setBackground(new Color(102, 203, 102)); // Example color
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
		
		// ------------------------- SignUn Link ------------------------- 
		JLabel lblNewLabel = new JLabel("<html><a href='' style='color: #A3C2FF;'>Não Possui Conta?</a></html>");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new SignUpView();
			}
		});
		lblNewLabel.setForeground(new Color(255, 2, 255));
		lblNewLabel.setBounds(681, 542, 117, 14);
		frame.getContentPane().add(lblNewLabel);
			
	}
}
