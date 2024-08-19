//package app;
//
//import java.awt.CardLayout;
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.Panel;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextPane;
//
//import com.toedter.calendar.JCalendar;
//
//import app.auth.SignUpView;
//import components.RoundedButton;
//import components.RoundedPasswordFieldComponent;
//import database.InitDatabase;
//
//
//public class testes{
//	private JLabel passwordPlaceholder;
//	private RoundedPasswordFieldComponent passwordField;
//	private JFrame frame;
//	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InitDatabase.initializeDatabase();
//					testes window = new testes();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	
//	private void initialize () {
//		frame = new JFrame();
//		frame.getContentPane().setBackground(new Color(40, 40, 40));
//		frame.setBounds(100, 100, 800, 750);
//		frame.setResizable(false);
//		frame.getContentPane().setLayout(null);
//		
//		passwordPlaceholder = new JLabel("Senha");
//		passwordPlaceholder.setForeground(new Color(156, 156, 156));
//		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
//		passwordPlaceholder.setBounds(163, 400, 468, 14);
//		frame.getContentPane().add(passwordPlaceholder);
//		
//		passwordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
//		passwordPlaceholder.setLabelFor(passwordField);
//		passwordField.setBounds(163, 425, 468, 31);
//		frame.getContentPane().add(passwordField);
//	}
//}
