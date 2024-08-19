package app.historyView;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class TicketComponent {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicketComponent window = new TicketComponent();
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
	public TicketComponent() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(40, 40, 40));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(20, 20, 20));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(55, 55, 55));
		panel_1.setBounds(63, 102, 137, 60);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("ODD");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(0, 11, 137, 14);
		panel_1.add(lblNewLabel_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(55, 55, 55));
		panel_2.setBounds(231, 102, 137, 60);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3_1 = new JLabel("APOSTA");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3_1.setBounds(0, 11, 137, 14);
		panel_2.add(lblNewLabel_3_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(55, 55, 55));
		panel_3.setBounds(395, 102, 137, 60);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_3_2 = new JLabel("GANHOS");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_2.setForeground(Color.WHITE);
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3_2.setBounds(0, 11, 137, 14);
		panel_3.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel = new JLabel("Nome do Evento");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(25, 21, 103, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tipo da aposta");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(25, 46, 90, 14);
		panel.add(lblNewLabel_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(470, 21, 48, 49);
		panel.add(panel_4);
		
		JLabel lblNewLabel_2 = new JLabel("Caso Simples: Vencedor: Nome do Time");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(25, 71, 250, 14);
		panel.add(lblNewLabel_2);
		frame.setBounds(100, 100, 584, 212);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
