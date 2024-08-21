package app.historyView;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.betView.BetComponent;
import dao.event.EventDAO;
import dao.event.EventPostgresDAO;
import dao.ticket.TicketDAO;
import dao.ticket.TicketPostgresDAO;
import models.Bet;
import models.Ticket;
import models.User;
import service.users.UserService;

import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;

public class HistoryView {
	
	
	private JPanel ticketsPanel;
	private EventDAO eventDao = new EventPostgresDAO();
	private TicketDAO ticketDAO = new TicketPostgresDAO();
	private UserService userService = new UserService();
	private User user;
	private List<Ticket> allTickets;
	private List<Ticket> ticketsDisplayed;
	private List<String> events;
	private String[] status =  {"----------","PENDENTE", "GANHOU", "PERDEU"};
	private String[] types = {"----------","SIMPLES", "MULTIPLA"};
	JComboBox<String> typeComboBox;
	JComboBox<String> eventsComboBox;
	JComboBox<String> statusComboBox;
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public HistoryView(Integer userId) {
		
		try {
			events = eventDao.userRelatedEvents(userId);
			user = userService.getUser(userId);
			allTickets = ticketDAO.getTicketsByUser(userId);
			ticketsDisplayed = new ArrayList<Ticket>();
			allTickets.forEach(ticket -> ticketsDisplayed.add(ticket));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
		
		ticketsPanel = new JPanel();
		ticketsPanel.setBackground(new Color(40, 40, 40));
		scrollPane.setViewportView(ticketsPanel);
		GridBagLayout gbl_ticketsPanel = new GridBagLayout();
		gbl_ticketsPanel.columnWidths = new int[]{0};
		gbl_ticketsPanel.rowHeights = new int[]{0};
		gbl_ticketsPanel.columnWeights = new double[]{1.0};
		gbl_ticketsPanel.rowWeights = new double[]{1.0};
		ticketsPanel.setLayout(gbl_ticketsPanel);

			
		typeComboBox = new JComboBox<>(this.types);
		typeComboBox.setBounds(53, 319, 214, 22);
		typeComboBox.addActionListener((e) -> {
			
			try {
				ticketsFilter();
				updateTickets();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(frame, "Falha ao carregar apostas!");
				e1.printStackTrace();
			}
			updateTickets();
		});
			
		
		frame.getContentPane().add(typeComboBox);
	
		this.events.add(0, "----------");
		String[] events = this.events.toArray(new String[this.events.size()]);
		eventsComboBox = new JComboBox<>(events);
		eventsComboBox.setBounds(53, 249, 214, 22);
		eventsComboBox.addActionListener((e) -> {
			
			try {
				ticketsFilter();
				updateTickets();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(frame, "Falha ao carregar apostas!");
				e1.printStackTrace();
			}
			updateTickets();
		});
		frame.getContentPane().add(eventsComboBox);
		
		
		statusComboBox = new JComboBox<>(this.status);
		statusComboBox.setBounds(53, 396, 214, 22);
		statusComboBox.addActionListener((e) -> {
			try {
				ticketsFilter();
				updateTickets();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(frame, "Falha ao carregar apostas!");
				e1.printStackTrace();
			}
			updateTickets();
		});
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
		ticketsPanel.removeAll();
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.anchor = GridBagConstraints.NORTH;  
	    gbc.weightx = 1.0;
	    gbc.insets = new Insets(5, 0, 10, 0);
	    
	    for (Ticket ticket : ticketsDisplayed) {
	        TicketComponent ticketComponent = new TicketComponent(ticket);
	        ticketComponent.setPreferredSize(new Dimension(584, 200)); 
	        ticketsPanel.add(ticketComponent, gbc);
	        gbc.gridy++;
	    }
	    
	    gbc.weighty = 1.0;
	    JPanel filler = new JPanel();
	    filler.setBackground(new Color(40, 40, 40));
	    ticketsPanel.add(filler, gbc);

	    ticketsPanel.revalidate();
	    ticketsPanel.repaint();
	}
	

	private void filterByStatus(String status) {
		
		ticketsDisplayed = ticketsDisplayed.stream()
			    .filter(ticket -> ticket.getStatus().equals(status))
			    .collect(Collectors.toList());

		
	}
	
	private void filterByType(String type) {
		
		ticketsDisplayed = ticketsDisplayed.stream()
			    .filter(ticket -> ticket.getTicketType().equals(type))
			    .collect(Collectors.toList());
		
	}
	
	private void filterByEvent(String description) throws SQLException{
		
		ticketsDisplayed = ticketDAO.getTicketsByEventAndUser(description, user.getId());
		
	}
	
	private void ticketsFilter() throws SQLException {
		
		final String type = (String)typeComboBox.getSelectedItem();
		final String event = (String)eventsComboBox.getSelectedItem();
		final String status = (String)statusComboBox.getSelectedItem();
		
		if(ticketsDisplayed.size() != allTickets.size()) {
			ticketsDisplayed.clear();
			allTickets.forEach(ticket -> ticketsDisplayed.add(ticket));
		}
		
		
		if(!event.equals("----------")) {
			filterByEvent(event);
		}
		
		if(!type.equals("----------")) {
			filterByType(type);
		}
		
		if(!status.equals("----------")) {
			filterByStatus(status);
		}
		
	}
	
}
