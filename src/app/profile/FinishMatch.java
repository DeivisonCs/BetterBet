package app.profile;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import components.ImageUtils;
import components.RoundedButton;
import database.InitDatabase;
import models.Event;
import models.Match;
import service.event.EventService;
import service.match.MatchService;

public class FinishMatch {
	
	private int userId;
	private EventService eventService = new EventService();
	private MatchService matchService = new MatchService();
	List<Event> allEvents = new ArrayList<Event>();
	List<Match> allMatchs;
	Event selectedEvent;
	Match selectedMatch;

	private JComboBox<Match> matchComboBox = new JComboBox<>();
	private JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InitDatabase.initializeDatabase();
//					FinishMatch window = new FinishMatch(1);
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
	public FinishMatch(int userId) {
		this.userId = userId;
		
		try {
			allEvents = this.eventService.getAll();
			
			allMatchs = new ArrayList<Match>();
			allMatchs = this.matchService.getAllByEventId(allEvents.get(0).getId());
			
			updateItens(allMatchs);
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
        
 		JLabel PageTitle = new JLabel("Finalizar Partida");
 		PageTitle.setForeground(new Color(255, 215, 0));
 		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 33));
 		PageTitle.setBounds(254, 117, 261, 31);
 		frame.getContentPane().add(PageTitle);
 		
 		
 		
 		// ------------------------- Event Field -------------------------
 		
		JLabel eventPlaceholder = new JLabel("Evento");
		eventPlaceholder.setForeground(new Color(156, 156, 156));
		eventPlaceholder.setFont(new Font("Arial", Font.PLAIN, 16));
		eventPlaceholder.setBounds(243, 264, 142, 14);
		frame.getContentPane().add(eventPlaceholder);
		
		JComboBox<Event> eventComboBox = new JComboBox<>(allEvents.toArray(new Event[0]));
		eventComboBox.setBounds(243, 285, 272, 22);
		eventComboBox.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					allMatchs = matchService.getAllByEventId(((Event) eventComboBox.getSelectedItem()).getId());
					
					updateItens(allMatchs);
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
			}
		});
		frame.getContentPane().add(eventComboBox);
		
		
		// ------------------------- Match Field -------------------------
 		
		JLabel matchPlaceholder = new JLabel("Partida");
		matchPlaceholder.setForeground(new Color(156, 156, 156));
		matchPlaceholder.setFont(new Font("Arial", Font.PLAIN, 16));
		matchPlaceholder.setBounds(243, 358, 142, 14);
		frame.getContentPane().add(matchPlaceholder);
		
		matchComboBox.setBounds(243, 378, 272, 22);
		frame.getContentPane().add(matchComboBox);
		
		
		// ------------------------- Create Button -------------------------
		RoundedButton button = new RoundedButton("Finalizar Partida");
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
				try {
					selectedMatch = (Match) matchComboBox.getSelectedItem();
					
					boolean finished = matchService.finishMatch(selectedMatch != null ? selectedMatch.getId() : -1);
					
					if(finished) {
						JOptionPane.showMessageDialog(null, "Evento finalizado com sucesso");
						
						
						allMatchs = matchService.getAllByEventId(((Event) eventComboBox.getSelectedItem()).getId());
						updateItens(allMatchs);
					}
					else {
						JOptionPane.showMessageDialog(null, "Partida não finalizada", "Erro ao finalizar partida", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "Algo inesperado aconteceu :(", "Erro ao finalizar partida", JOptionPane.ERROR_MESSAGE);
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
	
	private void updateItens(List<Match> matchs) {
		matchComboBox.removeAllItems();
		
		for(Match match: allMatchs) {
			if(match.getStatus().equals("pendente")) {
				matchComboBox.addItem(match);
			}
		}
	}

}
