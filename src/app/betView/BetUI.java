package app.betView;


import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;


import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.homeUser.HomeUserUI;
import app.homeUser.RoundedButtonComponent;
import app.homeUser.RoundedTextFieldComponent;
import models.Bet;
import models.CommonUser;
import models.Ticket;

import service.bets.BetService;
import service.ticket.TicketService;
import service.users.CommonUserService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BetUI {
	private HomeUserUI mainFrame;
	private JFrame frame;
	private JTextField textField;
	private JPanel betsPanel;
	
	private Ticket ticket;
	private TicketService ticketService= new TicketService();
	private BetService betService = new BetService();
	private CommonUserService userService = new CommonUserService();

	
	/**
	 * Create the application.
	 */
	public BetUI(Ticket ticket, HomeUserUI mainFrame) {
		this.ticket = ticket;
		this.mainFrame = mainFrame;
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
		
		JButton cancelButton = new RoundedButtonComponent("Cancelar");
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
		
		String finalOddValue = String.format("(%.1f)", ticket.getOdd());
		JLabel lblNewLabel = new JLabel(finalOddValue);
		lblNewLabel.setBounds(289, 11, 46, 14);
		panel_1.add(lblNewLabel);
		
		textField = new RoundedTextFieldComponent(10, 20, 20, 10, 10);
		textField.setBounds(49, 8, 155, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Valor: ");
		lblNewLabel_1.setBounds(11, 11, 46, 14);
		panel_1.add(lblNewLabel_1);
		
		JButton betButton = new RoundedButtonComponent("Apostar");
		betButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fazer verificação de data
				
				float amount = Float.parseFloat(textField.getText());					
					
				try {
					userService.updateBalance(((CommonUser)mainFrame.getUser()), amount);
					ticket.setAmount(amount);
					Ticket updatedTicket = ticketService.createTicket(ticket);
					updatedTicket.getBets().forEach((bet) -> bet.setIdTicket(updatedTicket.getId()));
					betService.createBets(updatedTicket.getBets());
					float newBalance = ((CommonUser)mainFrame.getUser()).getBalance() - amount;
					((CommonUser)mainFrame.getUser()).setBalance(newBalance);
					
		            JLabel balanceLabel = mainFrame.getBalanceLabel();
		            balanceLabel.setText("Saldo: " + newBalance);
		            
					mainFrame.getFrame().revalidate();
					mainFrame.getFrame().repaint();
					frame.dispose();
					
				} catch (SQLException e1) {
					 JOptionPane.showMessageDialog(null, "O sistema enconntra-se fora do ar", "Aviso", JOptionPane.ERROR_MESSAGE);
					 e1.printStackTrace();
				} catch (Exception e2) {
					 JOptionPane.showMessageDialog(null, "Saldo Insuficiente", "Aviso", JOptionPane.CANCEL_OPTION);
					e2.printStackTrace();
				}
				
				
			}
		});
		betButton.setBounds(68, 397, 89, 23);
		panel.add(betButton);
		
		
		
		frame.setVisible(true);
	}
	
	public void updatebets() {
	    betsPanel.removeAll();
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.anchor = GridBagConstraints.NORTH;  
	    gbc.weightx = 1.0;
	    gbc.insets = new Insets(5, 0, 10, 0);
	    
	    for (Bet bet : ticket.getBets()) {
	        BetComponent betComponent = new BetComponent(bet);
	        betsPanel.add(betComponent, gbc);
	    }
	    
	    gbc.weighty = 1.0;
	    JPanel filler = new JPanel();
	    filler.setBackground(new Color(40, 40, 40));
	    betsPanel.add(filler, gbc);

	    betsPanel.revalidate();
	    betsPanel.repaint();
	}
	
	
}
