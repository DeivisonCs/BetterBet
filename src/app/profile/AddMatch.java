package app.profile;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import components.ImageUtils;
import components.RoundedButton;
import database.InitDatabase;
import models.CommonUser;
import models.Event;
import models.Match;
import models.Team;
import service.event.EventService;
import service.match.MatchService;
import service.team.TeamService;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class AddMatch {
	
	private int userId;
	private EventService eventService = new EventService();
	private TeamService teamService = new TeamService();
	private MatchService matchService = new MatchService();
	List<Event> allEvents = new ArrayList<Event>();
	List<Team> allTeams = new ArrayList<Team>();

	private JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
////					InitDatabase.initializeDatabase();
//					AddMatch window = new AddMatch();
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
	public AddMatch(int userId) {
		this.userId = userId;
		
		try {
			allEvents = this.eventService.getAll();
			allTeams = this.teamService.getAll();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
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
        returnButton.setImage(new ImageIcon(getClass().getResource("/public/images/back-arrow.jpg")));
        returnButton.setBounds(24, 24, 30, 30);
        frame.getContentPane().add(returnButton);
		
		
		// ------------------------- Page Text -------------------------
		JLabel PageTitle = new JLabel("Adicionar Partida");
		PageTitle.setForeground(new Color(255, 215, 0));
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 33));
		PageTitle.setBounds(254, 97, 261, 31);
		frame.getContentPane().add(PageTitle);
		
		// ------------------------- Event Field -------------------------
		JLabel eventPlaceholder = new JLabel("Evento");
		eventPlaceholder.setForeground(new Color(156, 156, 156));
		eventPlaceholder.setFont(new Font("Arial", Font.PLAIN, 16));
		eventPlaceholder.setBounds(236, 210, 142, 14);
		frame.getContentPane().add(eventPlaceholder);
		
		JComboBox<Event> eventComboBox = new JComboBox<>(allEvents.toArray(new Event[0]));
		eventComboBox.setBounds(236, 231, 272, 22);
		frame.getContentPane().add(eventComboBox);
		
		// ------------------------- Team A ComboBox -------------------------
		JLabel teamAPlaceholder = new JLabel("Time A");
		teamAPlaceholder.setForeground(new Color(156, 156, 156));
		teamAPlaceholder.setFont(new Font("Arial", Font.PLAIN, 24));
		teamAPlaceholder.setBounds(216, 345, 85, 31);
		frame.getContentPane().add(teamAPlaceholder);
		
		JComboBox<Team> teamAComboBox = new JComboBox<>(allTeams.toArray(new Team[0]));
		teamAComboBox.setBounds(115, 387, 175, 22);
		frame.getContentPane().add(teamAComboBox);
		
		JLabel versusLabel = new JLabel("X");
		versusLabel.setForeground(new Color(156, 156, 156));
		versusLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		versusLabel.setBounds(361, 379, 27, 31);
		frame.getContentPane().add(versusLabel);
		
		// ------------------------- Team B ComboBox -------------------------
		JLabel teamBPlaceholder = new JLabel("Time B");
		teamBPlaceholder.setForeground(new Color(156, 156, 156));
		teamBPlaceholder.setFont(new Font("Arial", Font.PLAIN, 24));
		teamBPlaceholder.setBounds(436, 345, 85, 31);
		frame.getContentPane().add(teamBPlaceholder);
		
		JComboBox<Team> teamBComboBox = new JComboBox<>(allTeams.toArray(new Team[0]));
		teamBComboBox.setBounds(436, 387, 175, 22);
		frame.getContentPane().add(teamBComboBox);
		
		
		
		// ------------------------- Save Button -------------------------
				RoundedButton button = new RoundedButton("Adicionar Partida");
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						button.setBackground(new Color(255, 215, 0));
						button.setFont(new Font("Tahoma", Font.PLAIN, 19));
					}
					@Override
					public void mouseExited(MouseEvent e) {
						button.setBackground(new Color(102, 203, 102));
						button.setFont(new Font("Tahoma", Font.PLAIN, 24));
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						Event event = (Event) eventComboBox.getSelectedItem();
						Team teamA = (Team) teamAComboBox.getSelectedItem();
						Team teamB = (Team) teamBComboBox.getSelectedItem();
						
						Match newMatch = new Match(
								event.getId(),
								teamA,
								0,
								(float) 1.8,
								(float) 1.8,
								teamB,
								0,
								(float) 1.8,
								LocalDateTime.now(),
								1,
								1,
								1);
						
						try {
							String valid = matchService.save(newMatch);
							
							if(valid.equals("200")) {
								JOptionPane.showMessageDialog(null, "Partida adicionada com sucesso");
							}
							else {
								JOptionPane.showMessageDialog(null, valid, "Erro adicionar partida", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, "Algo inesperado aconteceu :(", "Erro adicionar partida", JOptionPane.ERROR_MESSAGE);
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
