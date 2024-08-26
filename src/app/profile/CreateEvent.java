package app.profile;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import components.ImageUtils;
import components.RoundedButtonComponent;
import components.RoundedTextFieldComponent;
import database.InitDatabase;
import models.Event;
import service.event.EventService;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class CreateEvent {
	private String[] sports = {"futebol"};
	private int userId;
	
	private EventService eventService = new EventService();
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InitDatabase.initializeDatabase();
//					CreateEvent window = new CreateEvent();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public CreateEvent(int userId) {
		this.userId = userId;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.setBounds(100, 100, 800, 750);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		
		// ------------------------- Page Text -------------------------
		JLabel PageTitle = new JLabel("Criar Evento");
		PageTitle.setForeground(new Color(255, 215, 0));
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 33));
		PageTitle.setBounds(294, 96, 193, 31);
		frame.getContentPane().add(PageTitle);
		
		// ------------------------- Return Button -------------------------        
		        
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
		JLabel namePlaceholder = new JLabel("Nome do Evento");
		namePlaceholder.setForeground(new Color(156, 156, 156));
		namePlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		namePlaceholder.setBounds(231, 251, 327, 14);
		frame.getContentPane().add(namePlaceholder);
		
		RoundedTextFieldComponent nameField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		namePlaceholder.setLabelFor(nameField);
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
//				namePlaceholder.setVisible(false);
				namePlaceholder.setBounds(224, 202, 327, 14);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(nameField.getText().length() == 0) {
//					namePlaceholder.setVisible(true);
					namePlaceholder.setBounds(237, 229, 327, 14);
				}
			}
		});
		nameField.setColumns(10);
		nameField.setBounds(217, 243, 341, 31);
		frame.getContentPane().add(nameField);
		
		JComboBox<String> sportComboBox = new JComboBox<>(sports);
		sportComboBox.setBounds(294, 379, 183, 22);
		frame.getContentPane().add(sportComboBox);
		
		JLabel sportPlaceholder = new JLabel("Modalidade");
		sportPlaceholder.setForeground(new Color(156, 156, 156));
		sportPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		sportPlaceholder.setBounds(346, 346, 87, 14);
		frame.getContentPane().add(sportPlaceholder);
		
		
		// ------------------------- Create Button -------------------------
		RoundedButtonComponent button = new RoundedButtonComponent("Criar Evento", new Color(255, 215, 0), new Color(102, 203, 102));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setFont(new Font("Tahoma", Font.PLAIN, 19));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setFont(new Font("Tahoma", Font.PLAIN, 24));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Event newEvent = new Event(
						LocalDateTime.now(),
						(String) sportComboBox.getSelectedItem(),
						nameField.getText()
						);
				
				try {
					newEvent = eventService.save(newEvent);
					
					JOptionPane.showMessageDialog(null, "Evento criado com sucesso");
					
					frame.dispose();
					new AddMatch(userId);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Algo inesperado aconteceu :(", "Erro ao criar evento", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
			
			
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(236, 600, 261, 59);
        button.setBackground(new Color(102, 203, 102));
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
		
	}
}
