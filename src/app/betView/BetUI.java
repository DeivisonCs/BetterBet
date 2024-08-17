package app.betView;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.BetDAO;
import dao.BetPostgresDAO;
import dao.TicketDAO;
import dao.TicketPostgresDAO;
import models.Bet;
import models.Match;
import models.Team;
import models.Ticket;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BetUI {

	private JFrame frame;
	private JTextField textField;
	private JPanel betsPanel;
	
	Ticket ticket;

	private BetDAO betDAO = new BetPostgresDAO();
	private TicketDAO ticketDAO = new TicketPostgresDAO();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {/*
					Team teamA1 = new Team(1, "Barcelona", "Football");
			        Team teamB1 = new Team(2, "Real Madrid", "Football");
			        Team teamA2 = new Team(3, "Manchester United", "Football");
			        Team teamB2 = new Team(4, "Liverpool", "Football");

			        // Criação dos matches
			        Match match1 = new Match(1, 1001, teamA1, 2, 1.85f, 3.2f, teamB1, 1, 2.1f, LocalDateTime.now().plusDays(1));
			        Match match2 = new Match(2, 1002, teamA2, 0, 2.5f, 3.5f, teamB2, 0, 2.8f, LocalDateTime.now().plusDays(2));

			        // Criação das bets
			        Bet bet1 = new Bet(1, "1x2", 1.85f, 2.1f, 3.2f, match1, 101, "Pending", "TEAM_A");
			        Bet bet2 = new Bet(2, "1x2", 1.85f, 2.1f, 3.2f, match1, 101, "Pending", "TEAM_B");
			        Bet bet3 = new Bet(3, "1x2", 2.5f, 2.8f, 3.5f, match2, 102, "Pending", "DRAW");
			        Bet bet4 = new Bet(4, "1x2", 2.5f, 2.8f, 3.5f, match2, 102, "Pending", "TEAM_A");

			        // Adicionando as bets em uma lista
			        List<Bet> bets = new ArrayList<>();
			        bets.add(bet1);
			        bets.add(bet2);
			        bets.add(bet3);
			        bets.add(bet4);*/
					//Ticket ticket = new Ticket()
					BetUI window = new BetUI(null);
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
	public BetUI(Ticket ticket) {
		
		initialize();
		updatebets();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 410, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(40, 40, 40));
		panel.setBounds(0, 0, 394, 442);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton betButton = new JButton("Apostar");
		betButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fazer verificação de data
				try {
					/*
					public Ticket(float odd, Integer idUser, float expectedProfit, float amount) {
						super();
						this.bets = new ArrayList<Bet>();
						this.odd = odd;
						this.idUser = idUser;
						this.expectedProfit = expectedProfit;
						this.amount = amount;
					}*/
					
					//Ticket newTicket = new Ticket(calculateOdd(),);
					int ticketId = ticketDAO.createTicket(null);
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		betButton.setBounds(68, 397, 89, 23);
		panel.add(betButton);
		
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setBounds(239, 397, 89, 23);
		panel.add(cancelButton);
		
		JScrollPane betScrollPane = new JScrollPane();
		betScrollPane.setBounds(10, 11, 374, 317);
		panel.add(betScrollPane);
		
		betsPanel = new JPanel();
		betsPanel.setBackground(new Color(128, 128, 128));
		betScrollPane.setViewportView(betsPanel);
		GridBagLayout gbl_betsPanel = new GridBagLayout();
		gbl_betsPanel.columnWidths = new int[]{0};
		gbl_betsPanel.rowHeights = new int[]{0};
		gbl_betsPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_betsPanel.rowWeights = new double[]{Double.MIN_VALUE};
		betsPanel.setLayout(gbl_betsPanel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(128, 128, 128));
		panel_1.setBounds(10, 328, 374, 47);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		String finalOddValue = String.format("(%.1f)", calculateOdd());
		JLabel lblNewLabel = new JLabel(finalOddValue);
		lblNewLabel.setBounds(289, 11, 46, 14);
		panel_1.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(49, 8, 155, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Valor: ");
		lblNewLabel_1.setBounds(11, 11, 46, 14);
		panel_1.add(lblNewLabel_1);
	}
	
	public void updatebets() {
		betsPanel.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
	        gbc.gridx = 0;
	        gbc.gridy = GridBagConstraints.RELATIVE;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.anchor = GridBagConstraints.NORTH;  
	        gbc.weightx = 1.0;
	        gbc.insets.bottom = 10;
	        gbc.insets.top = 5;
        
        for (Bet bet : ticket.getBets()) {
        	
        	BetComponent betComponent = new BetComponent(bet);
        	betsPanel.add(betComponent, gbc);
            gbc.gridy++;
        }
	    gbc.weighty = 1.0;
	    JPanel filler = new JPanel();
	    filler.setBackground(new Color(40, 40, 40));
	    betsPanel.add(filler, gbc);
	    betsPanel.revalidate();
	    betsPanel.repaint();
	}
	
	//vai pra dentro do ticket
	private float calculateOdd() {
		
		float finalOdd = 1f;
		
		for (Bet bet: ticket.getBets()) {
			if(bet.getTeamBet().equals("TEAM_A")) {
				finalOdd = finalOdd * bet.getOddTeamA();
			}else if(bet.getTeamBet().equals("TEAM_B")) {
				finalOdd = finalOdd * bet.getOddTeamB();
			}else  if(bet.getTeamBet().equals("DRAW")) {
				finalOdd = finalOdd * bet.getOddDraw();
			}

		}

		return finalOdd;
	}
	
	
}
