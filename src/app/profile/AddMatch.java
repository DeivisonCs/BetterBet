package app.profile;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import components.ImageUtils;
import components.RoundedButtonComponent;
import exceptions.InvalidMatchException;
import models.Event;
import models.Match;
import models.Team;
import service.event.EventService;
import service.match.MatchService;
import service.team.TeamService;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class AddMatch {
	
	private int userId;
	private EventService eventService = new EventService();
	private TeamService teamService = new TeamService();
	private MatchService matchService = new MatchService();
	List<Event> allEvents = new ArrayList<Event>();
	List<Team> allTeams = new ArrayList<Team>();

	private JFrame frame;


	/**
	* Interface de cadastro de partidas (acessível apenas para usuários administradores).
	* Após o usuário ser validado ele é redirecionado para a tela home do site (src/app/homeUser/HomeUserUI)
	* 
	* @param userId Id do usuário logado
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
		/**
    	* Fecha a tela atual.
    	*/
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
		/**
		* Ao ser clicado, o button cria uma instância de Match,
		* chama o matchService para verificar os dados inseridos e fazer a  inserção no banco.
		* 
		* @throws SQLException Caso haja algum erro no banco
		* @throws InvalidMatchException Caso haja algum erro relacionado aos times escolhidos
		*/ 
		RoundedButtonComponent button = new RoundedButtonComponent("Adicionar Partida", new Color(255, 215, 0), new Color(102, 203, 102));
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
						"pendente",
						LocalDateTime.now(),
						1,
						1,
						1);
				
				try {
					 matchService.save(newMatch);
					
					JOptionPane.showMessageDialog(null, "Partida adicionada com sucesso");

				}
				catch (SQLException | InvalidMatchException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao adicionar partida", JOptionPane.ERROR_MESSAGE);
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
