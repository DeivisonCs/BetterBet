package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.DropMode;

public class SignUpView {

	private JFrame frame;
	private JTextField nameField;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextPane txtpnBemVindoAo;

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
		frame.getContentPane().setLayout(null);
		
		Panel WelcomeSection = new Panel();
		WelcomeSection.setBackground(new Color(192, 192, 192));
		WelcomeSection.setBounds(0, 0, 341, 655);
		frame.getContentPane().add(WelcomeSection);
		WelcomeSection.setLayout(null);
		
		txtpnBemVindoAo = new JTextPane();
		txtpnBemVindoAo.setBackground(new Color(192, 192, 192));
		txtpnBemVindoAo.setFont(new Font("Tahoma", Font.PLAIN, 31));
		txtpnBemVindoAo.setText("Bem Vindo\r\n      Ao\r\n BetterBet");
		txtpnBemVindoAo.setBounds(92, 182, 204, 134);
		WelcomeSection.add(txtpnBemVindoAo);
		
		JTextPane txtpnOSiteCom = new JTextPane();
		txtpnOSiteCom.setDropMode(DropMode.INSERT_ROWS);
		txtpnOSiteCom.setText("O site com as apostas seguras, lucrativas e com melhor conversão em ganhos do mercado!");
		txtpnOSiteCom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnOSiteCom.setBackground(Color.LIGHT_GRAY);
		txtpnOSiteCom.setBounds(52, 327, 262, 134);
		WelcomeSection.add(txtpnOSiteCom);
		
		JLabel PageTitle = new JLabel("Criar Conta");
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PageTitle.setBounds(637, 115, 191, 31);
		frame.getContentPane().add(PageTitle);
		
		nameField = new JTextField();
		nameField.setBounds(505, 208, 400, 31);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(505, 320, 400, 31);
		frame.getContentPane().add(textField_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(505, 264, 400, 31);
		frame.getContentPane().add(textField_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(505, 380, 230, 31);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(745, 380, 160, 31);
		frame.getContentPane().add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(505, 433, 230, 31);
		frame.getContentPane().add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(745, 433, 160, 31);
		frame.getContentPane().add(textField_6);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(637, 519, 179, 59);
		frame.getContentPane().add(btnNewButton);
	}
}
