package app.historyView;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import components.ImageUtils;
import app.homeUser.HomeUserUI;
import app.profile.WindowProfile;
import components.RoundedImagePanel;
import components.TicketComponent;
import dao.event.EventDAO;
import dao.event.EventPostgresDAO;
import dao.match.MatchDAO;
import dao.match.MatchPostgresDAO;
import dao.ticket.TicketDAO;
import dao.ticket.TicketPostgresDAO;
import models.Bet;
import models.CommonUser;
import models.Match;
import models.Ticket;
import models.User;
import service.event.EventService;
import service.ticket.TicketService;
import service.users.CommonUserService;
import service.users.UserService;

import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;

public class HistoryView {
	private int positionX;
	private int positionY;
	
	private JPanel ticketsPanel;
	private EventService eventService = new EventService();
	private TicketService ticketService = new TicketService();
	private UserService userService = new UserService();
	private User user;
	private List<Ticket> allTickets;
	private List<Ticket> ticketsDisplayed;
	private List<String> events;
	private String[] status =  {"----------","PENDENTE", "GANHOU", "PERDEU"};
	private String[] types = {"----------","SIMPLES", "MULTIPLA"};
	private JComboBox<String> typeComboBox;
	private JComboBox<String> eventsComboBox;
	private JComboBox<String> statusComboBox;
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public HistoryView(Integer userId, int positionX, int positionY) {
		this.positionX = positionX;
    	this.positionY = positionY;
    	
		try {
			events = eventService.getUserRelatedEvents(userId);
			user = userService.getUser(userId);
			allTickets = ticketService.getTicketsByUser(userId);
			verifyTickets();
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.getContentPane().setLayout(null);
		frame.setBounds(positionX, positionY, 1200, 700);
		
		JPanel navPanel = new JPanel();
		navPanel.setBackground(new Color(40, 40, 40));
		navPanel.setBounds(0, 0, 1184, 65);
		frame.getContentPane().add(navPanel);
		
		JScrollPane ticketsScrollPane = new JScrollPane();
		ticketsScrollPane.setBounds(600, 63, 584, 598);
		ticketsScrollPane.setBorder(null);
		ticketsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ticketsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		ticketsScrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                JScrollBar verticalScrollBar = ticketsScrollPane.getVerticalScrollBar();
                int unitsToScroll = e.getWheelRotation() * 15;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        });
		frame.getContentPane().add(ticketsScrollPane);
		
		ticketsPanel = new JPanel();
		ticketsPanel.setBackground(new Color(40, 40, 40));
		ticketsScrollPane.setViewportView(ticketsPanel);
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
		
		
		JLabel historyLabel = new JLabel("Histórico");
		historyLabel.setForeground(new Color(255, 255, 255));
		historyLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		historyLabel.setBounds(53, 96, 224, 31);
		frame.getContentPane().add(historyLabel);
		
		JLabel typeLabel = new JLabel("Tipo:");
		typeLabel.setForeground(Color.WHITE);
		typeLabel.setBounds(53, 292, 46, 14);
		frame.getContentPane().add(typeLabel);
		
		JLabel eventLabel = new JLabel("Evento:");
		eventLabel.setForeground(Color.WHITE);
		eventLabel.setBounds(53, 221, 59, 14);
		frame.getContentPane().add(eventLabel);
		
		JLabel statusLabel = new JLabel("Status:");
		statusLabel.setForeground(new Color(255, 255, 255));
		statusLabel.setBounds(53, 371, 46, 14);
		frame.getContentPane().add(statusLabel);
		frame.setResizable(false);
		
		ImageUtils backButton = new ImageUtils();
        backButton.setBorder(null);
        backButton.setBorderSize(0);
        backButton.setImage(new ImageIcon(getClass().getResource("/resources/images/back-arrow.jpg")));
        backButton.setBounds(5, 5, 30, 30);
        backButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {

						Point location = frame.getLocationOnScreen();
						int x = location.x;
						int y = location.y;
						frame.dispose();
					
					new WindowProfile(user.getId(), x, y);
        	}
        	
        });
        navPanel.setLayout(null);
        navPanel.add(backButton);
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
		
		ticketsDisplayed = ticketService.getTicketsByEventAndUser(description, user.getId());
		
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
	
	private void verifyTickets() throws SQLException {
		
		List<Ticket> tickets = allTickets;
		AtomicBoolean pendingBet = new AtomicBoolean(false);
		CommonUserService commonUserService = new CommonUserService();
		
		tickets.forEach(ticket -> {
			
			if(ticket.getStatus().equals("PENDENTE")) {
				
				pendingBet.set(false);
				//verifica se todas as partidas da bet foram finalizadas
				ticket.getBets().forEach(bet->{
					if(!bet.getMatch().getStatus().equals("finalizado")) {
						pendingBet.set(true);
						return;
					}
				});
				if(pendingBet.get()) {
					return;
				}
				
				AtomicBoolean youWon = new AtomicBoolean(true);
				//verifica se aerrou algum palpite
				ticket.getBets().forEach(bet->{
					if(bet.getSelectedBet().equals("TEAM_A")) {
						if(bet.getMatch().getScoreTeamA() <= bet.getMatch().getScoreTeamB()) {
							youWon.set(false);
						}
					}else if(bet.getSelectedBet().equals("TEAM_B")) {
						if(bet.getMatch().getScoreTeamA() >= bet.getMatch().getScoreTeamB()) {
							youWon.set(false);
						}
					}else if(bet.getSelectedBet().equals("DRAW")) {
						if(bet.getMatch().getScoreTeamA() != bet.getMatch().getScoreTeamB()) {
							youWon.set(false);
						}
					}
				});
				
				if(youWon.get()) {
					ticket.setStatus("GANHOU");
					
					CommonUser commonUser = (CommonUser)user;

					try {
						ticketService.updateStatus(ticket);
						commonUserService.increaseBalance(commonUser, ticket.getExpectedProfit());
					} catch (SQLException e) {
						throw new RuntimeException("Erro ao atualizar status do ticket: " + ticket.getId(), e);	
					} catch (Exception e) {
						throw new RuntimeException("Erro ao atualizar saldo do usuário: " + user.getId(), e);	
						
					}
					
					
				}else {
					ticket.setStatus("PERDEU");
					try {
						ticketService.updateStatus(ticket);
					} catch (SQLException e) {
						throw new RuntimeException("Erro ao atualizar status do ticket: " + ticket.getId(), e);
					}
				}
			}
			
			ticket.getBets().clear();
			
		});
		
		
	}
	
}
