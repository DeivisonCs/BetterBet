package app.historyView;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.betView.BetComponent;
import dao.EventDAO;
import dao.EventPostgresDAO;
import dao.TicketDAO;
import dao.TicketPostgresDAO;
import models.Bet;
import models.Ticket;
import models.User;
import service.users.UserService;

import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;

public class HistoryView {
	
	
	private JPanel panel_1;
	private EventDAO eventDao = new EventPostgresDAO();
	private UserService userService = new UserService();
	private TicketDAO ticketDAO = new TicketPostgresDAO();
	private User user;
	private List<Ticket> tickets = new ArrayList<Ticket>();
	private List<String> events;
	private String[] status =  {"PENDENTE", "FINALIZADO"};
	private String[] types = {"SIMPLES", "MULTIPLA"};
	
	private List<TicketComponent> ticketsDisplayed = new ArrayList<TicketComponent>();;
	
	private JFrame frame;
	/**
	 * Launch the application.
	 */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    SwingUtilities.invokeLater(() -> {
//                    	CommonUser commonUser = new CommonUser(1, "John Doe", 30, "johndoe@example.com", "password123", "user", 500.0f);
//                        HomeUserUI window = new HomeUserUI(commonUser);
//                        window.frame.setVisible(true);
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

	/**
	 * Create the application.
	 */
	public HistoryView(Integer userId) {
		
		try {
			events = eventDao.userRelatedEvents(userId);
			user = userService.getUser(userId);
			tickets = ticketDAO.getTicketsByUser(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
		updateTickets();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(0, 0, 1184, 65);
		frame.getContentPane().add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(600, 63, 584, 598);
		scrollPane.setBorder(null);
		frame.getContentPane().add(scrollPane);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(40, 40, 40));
		scrollPane.setViewportView(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0};
		gbl_panel_1.rowHeights = new int[]{0};
		gbl_panel_1.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

			
		
		
		JComboBox<String> typeComboBox = new JComboBox<>(this.types);
		typeComboBox.setBounds(53, 319, 214, 22);
		frame.getContentPane().add(typeComboBox);
	
		String[] events = this.events.toArray(new String[this.events.size()]);
		JComboBox<String> eventsComboBox = new JComboBox<>(events);
		eventsComboBox.setBounds(53, 249, 214, 22);
		frame.getContentPane().add(eventsComboBox);
		
		
		JComboBox<String> statusComboBox = new JComboBox<>(this.status);
		statusComboBox.setBounds(53, 396, 214, 22);
		frame.getContentPane().add(statusComboBox);
		
		
		JLabel lblNewLabel = new JLabel("Histórico");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setBounds(53, 96, 224, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tipo:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setBounds(53, 292, 46, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Evento:");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setBounds(53, 221, 59, 14);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Status:");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(53, 371, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		frame.setResizable(false);
		
		frame.setBounds(100, 100, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void updateTickets() {
		panel_1.removeAll();
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.anchor = GridBagConstraints.NORTH;  
	    gbc.weightx = 1.0;
	    gbc.insets = new Insets(5, 0, 10, 0);
	    
	    for (Ticket ticket : tickets) {
	        TicketComponent ticketComponent = new TicketComponent(ticket);
	        panel_1.add(ticketComponent, gbc);
	        gbc.gridy++;
	    }
	    
	    gbc.weighty = 1.0;
	    JPanel filler = new JPanel();
	    filler.setBackground(new Color(40, 40, 40));
	    panel_1.add(filler, gbc);

	    panel_1.revalidate();
	    panel_1.repaint();
	}
	
}
