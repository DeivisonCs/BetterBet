package app.homeUser;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import models.AdminUser;
import models.Bet;
import models.CommonUser;
import models.Event;
import models.Match;
import models.Ticket;
import models.User;
import security.Permission;
import service.event.EventService;
import service.match.MatchService;
import service.users.UserService;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import components.EventComponent;
import components.MatchComponent;
import components.RoundedButtonComponent;
import components.RoundedTextFieldComponent;

import app.ImageUtils;
import app.betView.BetUI;
import app.profile.WindowProfile;
import dao.event.EventDAO;
import dao.event.EventPostgresDAO;
import dao.match.MatchDAO;
import dao.match.MatchPostgresDAO;

import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;


public class HomeUserUI {
	private int positionX;
	private int positionY;
	
	private User user;
	private UserService userService= new UserService();

	private JFrame frame;
	JLabel balanceLabel;
	
	private List<Match> matches = new ArrayList<Match>();
	private List<MatchComponent> selectedMatches = new ArrayList<MatchComponent>();
	
	private List<Event> events = new ArrayList<Event>();
	private EventComponent selectedEventComponent;
	
	private JPanel gamesPanel;
	private JPanel eventsPanel;
	
	private  String textFieldValue;
	private ImageIcon profileImg;
	
	private MatchService matchService = new MatchService();
	private EventService eventService = new EventService();
	
	/**
	 * Create the application.
	 */
	public HomeUserUI(Integer userId, int positionX, int positionY) {
		System.out.println("UserId Home " + userId);
		
		this.positionX = positionX;
		this.positionY = positionY;
		
		try {
			setMatchesWithAllMatches();
			setEventsWithAllEvents();
			
			User loggedUser = userService.getUser(userId);
			
			if(loggedUser.getPermission().equals("user")) {
				this.user = (CommonUser) loggedUser;
				System.out.println("Home user: " + user.toString());
			}
			else {			
				this.user = (AdminUser) loggedUser;
				System.out.println("Home admin user: " + user.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initialize();
		updateMatches();
		updateEvents();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(positionX, positionY, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JPanel navBarPanel = new JPanel();
		navBarPanel.setBackground(new Color(0, 0, 0));
		navBarPanel.setBounds(0, 0, 1184, 70);
		frame.getContentPane().add(navBarPanel);
		navBarPanel.setLayout(null);
		

		JLabel searchPlaceholder = new JLabel("Pesquisar Eventos");
        searchPlaceholder.setForeground(new Color(156, 156, 156));
        searchPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPlaceholder.setBounds(588, 251, 274, 14);
		navBarPanel.add(searchPlaceholder);
		
		
		RoundedTextFieldComponent roundedTextField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
        
        roundedTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				searchPlaceholder.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(roundedTextField.getText().length() == 0) {
					searchPlaceholder.setVisible(true);
				}
			}
		});
        
        roundedTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldValue = roundedTextField.getText();
                List<Event> lista = null;
                try {lista = eventService.getEventsByName(textFieldValue);} 
                catch (SQLException e1) {e1.printStackTrace();}
                
                if(lista != null && lista.size() != 0) {
                	events.clear();
                	events = lista;
                	updateEvents();
                }
            }
        });
        searchPlaceholder.setLabelFor(roundedTextField);
        roundedTextField.setBackground(Color.WHITE);
        roundedTextField.setBounds(389, 15, 390, 35);
        navBarPanel.add(roundedTextField);
		

		
		if(this.user instanceof CommonUser) {
			balanceLabel = new JLabel(String.format("Saldo: R$ %.2f ", ((CommonUser) user).getBalance()));
			balanceLabel.setForeground(new Color(255, 255, 255));
			balanceLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
			balanceLabel.setBounds(25, 30, 164, 14);
			navBarPanel.add(balanceLabel);
		}

			
		if(this.user instanceof CommonUser) {
			JButton makeBetButton = new RoundedButtonComponent("Fazer Aposta");
			makeBetButton.setBackground(new Color(35, 35, 35));
			makeBetButton.setForeground(new Color(255, 255, 255));
			makeBetButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
			makeBetButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(selectedMatches.isEmpty()) {
						 JOptionPane.showMessageDialog(null, "Nenhuma partida selecionada.", "Aviso", JOptionPane.WARNING_MESSAGE);
				         return;
					}
					
					List<Bet> bets = selectedMatches.stream()
						    .map(m -> new Bet(m.getMatch().getOddTeamA(), m.getMatch().getOddTeamB(), m.getMatch().getOddDraw(), m.getMatch(), m.getBetSelectedOption()))
						    .collect(Collectors.toList());

					Ticket ticket = new Ticket(user.getId(),"PENDENTE", bets);
					
					new BetUI(ticket, HomeUserUI.this);
				}
			});
			makeBetButton.setBounds(846, 18, 128, 29);
			navBarPanel.add(makeBetButton);
		}
		
		profileImg = 
        		user.getProfileImage() != null?
        		user.getProfileImage() : 
    			new ImageIcon(getClass().getResource("/public/images/Profile-Icon.jpg"));
		
		ImageUtils profilePicture = new ImageUtils();
		profilePicture.setImage(profileImg);
		profilePicture.setBounds(1100, 13, 44, 44);
		profilePicture.setBorder(null);
		profilePicture.setBorderSize(0);
		profilePicture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;
				frame.dispose();
				
				new WindowProfile(user.getId(), x, y);
			}
			
		});
		navBarPanel.add(profilePicture);
		
		
		GridBagLayout layoutGamesPanel = new GridBagLayout();
		gamesPanel = new JPanel(layoutGamesPanel);
		gamesPanel.setForeground(new Color(40, 40, 40));
		gamesPanel.setBackground(new Color(40, 40, 40));
		gamesPanel.setBorder(null);
		JScrollPane gameScrollPane = new JScrollPane(gamesPanel);
		gameScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		gameScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		gameScrollPane.setBounds(211, 162, 973, 499);
		gameScrollPane.setBorder(null);
        gameScrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                JScrollBar verticalScrollBar = gameScrollPane.getVerticalScrollBar();
                int unitsToScroll = e.getWheelRotation() * 15;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        });

		frame.getContentPane().add(gameScrollPane);
		
		JPanel gamesDescriptionPanel = new JPanel();
		gamesDescriptionPanel.setForeground(new Color(40, 40, 40));
		gamesDescriptionPanel.setBackground(new Color(30, 30, 30));
		gamesDescriptionPanel.setBounds(211, 69, 973, 103);
		frame.getContentPane().add(gamesDescriptionPanel);
		gamesDescriptionPanel.setLayout(null);
		
		JLabel gamesDescriptionLabel = new JLabel("Jogos em Andamento:");
		gamesDescriptionLabel.setForeground(new Color(255, 255, 255));
		gamesDescriptionLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		gamesDescriptionLabel.setBounds(25, 11, 264, 43);
		gamesDescriptionPanel.add(gamesDescriptionLabel);
		
		JScrollPane eventScrollPane = new JScrollPane();
		eventScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		eventScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		eventScrollPane.setBorder(null);
		eventScrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                JScrollBar verticalScrollBar = eventScrollPane.getVerticalScrollBar();
                int unitsToScroll = e.getWheelRotation() * 15;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        });
		
		eventScrollPane.setBounds(0, 134, 213, 527);
		frame.getContentPane().add(eventScrollPane);
		
		eventsPanel = new JPanel();
		eventsPanel.setBackground(new Color(30, 30, 30));
		eventsPanel.setBorder(null);
		eventScrollPane.setViewportView(eventsPanel);
		GridBagLayout gbl_eventsPanel = new GridBagLayout();
		gbl_eventsPanel.columnWidths = new int[]{0};
		gbl_eventsPanel.rowHeights = new int[]{0};
		gbl_eventsPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_eventsPanel.rowWeights = new double[]{Double.MIN_VALUE};
		eventsPanel.setLayout(gbl_eventsPanel);
		
		JPanel eventsDescriptionPanel = new JPanel();
		eventsDescriptionPanel.setBackground(new Color(30, 30, 30));
		eventsDescriptionPanel.setBounds(0, 69, 213, 70);
		frame.getContentPane().add(eventsDescriptionPanel);
		eventsDescriptionPanel.setLayout(null);
		
		JLabel eventsDescriptionLabel = new JLabel("Eventos: ");
		eventsDescriptionLabel.setBounds(10, 21, 123, 23);
		eventsDescriptionLabel.setForeground(new Color(255, 255, 255));
		eventsDescriptionLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		eventsDescriptionPanel.add(eventsDescriptionLabel);
			
	}
	
	
	//Adiciona as partidas no gridbaglayout
	public void updateMatches() {
		gamesPanel.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.anchor = GridBagConstraints.NORTH;  
	        gbc.weightx = 1.0;
	        gbc.insets.bottom = 10;
	        gbc.insets.top = 5;
        
        for (Match match : matches) {
        	
        	MatchComponent matchComponent;
        	Optional<Integer> positionIfExists = positionIfExistsSelectedMatchComponent(match.getIdEvent(), match.getTeamA().getName(), match.getTeamB().getName());
        	
        	if (positionIfExists.isEmpty()) {
				matchComponent = new MatchComponent(match);
				matchComponent.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent e) {
						//Adiciona a partida na lista de partidas selecionadas
						if ((matchComponent.isSelectedTeamA() || matchComponent.isSelectedTeamB() || matchComponent.isSelectedDraw())
								&& !selectedMatches.contains(matchComponent)) {
							selectedMatches.add(matchComponent);
						}
						//Remove a partida da lista de partidas selecionadas caso seja desselecionada
						if (!matchComponent.isSelectedTeamA() && !matchComponent.isSelectedTeamB() && !matchComponent.isSelectedDraw()) {
							selectedMatches.remove(matchComponent);
						}
					}
				});
			}else {
				matchComponent = selectedMatches.get(positionIfExists.get());
			}
        	
        	
			gamesPanel.add(matchComponent, gbc);
            gbc.gridy++;
        }
	    gbc.weighty = 1.0;
	    JPanel filler = new JPanel();
	    filler.setBackground(new Color(40, 40, 40));
	    gamesPanel.add(filler, gbc);
        gamesPanel.revalidate();
        gamesPanel.repaint();
	}
	
	//Adiciona os eventos no gridbagLayout
	public void updateEvents() {
	    eventsPanel.removeAll();

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.anchor = GridBagConstraints.NORTH;
	    gbc.weightx = 1.0;
	    gbc.insets = new Insets(5, 5, 5, 5);

	    for (Event event : events) {
	        EventComponent eventComponent = new EventComponent(event);
	        eventComponent.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (eventComponent.isSelected()) {
	                	
	                    if (selectedEventComponent != null) selectedEventComponent.toggleSelection();
	                  
	                    selectedEventComponent = eventComponent;
	                    matches.clear();
	                    
	                    try {matches = matchService.getMatchesByEvent(event.getId());} 
	                    catch (SQLException e1) {e1.printStackTrace();}
	                    
	                    updateMatches();
	                } else {
	                    selectedEventComponent = null;
	                    try {matches = matchService.getAllMatches();} 
	                    catch (SQLException e1) {e1.printStackTrace();}
	                    
	                    updateMatches();
	                }
	            }
	        });
	        eventsPanel.add(eventComponent, gbc);
	        gbc.gridy++;
	    }

	    gbc.weighty = 1.0;
	    JPanel filler = new JPanel();
	    filler.setBackground(new Color(30, 30, 30)); 
	    eventsPanel.add(filler, gbc);

	    eventsPanel.revalidate();
	    eventsPanel.repaint();
	}
	
	
	//No momento da atualização das partidas ao clicar em um evento, verifica se a partida daquele evento ja esta selecionada e a mantém selecionada.
	private Optional<Integer> positionIfExistsSelectedMatchComponent(Integer event_id, String team_a, String team_b) {
		if(!selectedMatches.isEmpty()) {
			for (MatchComponent component : selectedMatches) {
				if(component.getMatch().getIdEvent() == event_id 
						&& component.getMatch().getTeamA().getName().equals(team_a)
						&& component.getMatch().getTeamB().getName().equals(team_b)) {
					return Optional.of(selectedMatches.indexOf(component));
				}
			}
		}
		return Optional.empty();
	}
	
	public User getUser() {
		return this.user;
	}

	public JFrame getFrame() {
		return this.frame;
	}
	
	public JLabel getBalanceLabel() {
		return this.balanceLabel;
	}
	
	public List<MatchComponent> getSelectedMatches(){
		return this.selectedMatches;
	}
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public void setMatchesWithAllMatches() throws SQLException{
		this.matches = matchService.getAllMatches();	
	}
	
	public void setEventsWithAllEvents() throws SQLException{
		this.events = eventService.getAll();	
	}
	
}
