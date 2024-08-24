package app.historyView;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;

import app.betView.BetComponent;
import components.RoundedButtonComponent;
import models.Bet;
import models.Ticket;

import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionEvent;

public class ShowTicketComponent {
	private JFrame frame;
	private JPanel betsPanel;
	
	private Ticket ticket;
    private Color statusBackground;
    private String statusImagePath;
    
	
	/**
	 * Create the application.
	 */
	public ShowTicketComponent(Ticket ticket) {
		this.ticket = ticket;
		setTicketStatus();
		initialize();
		updatebets();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 394, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(40, 40, 40));
		panel.setBounds(0, 0, 394, 442);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton cancelButton = new RoundedButtonComponent("Cancelar");
		cancelButton.setText("Voltar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		cancelButton.setBounds(151, 402, 100, 29);
		panel.add(cancelButton);
		
		JScrollPane betScrollPane = new JScrollPane();
		betScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		betScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		betScrollPane.setBorder(null);
		betScrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                JScrollBar verticalScrollBar = betScrollPane.getVerticalScrollBar();
                int unitsToScroll = e.getWheelRotation() * 15;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        });
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
		
		String finalOddValue = String.format("Odd: %.2f", ticket.getOdd());
		JLabel lblNewLabel = new JLabel(finalOddValue);
		lblNewLabel.setBounds(29, 15, 84, 14);
		panel_1.add(lblNewLabel);
		
		String value = String.format("Valor apostado: %.2f", ticket.getAmount());
		JLabel lblNewLabel_1 = new JLabel(value);
		lblNewLabel_1.setBounds(113, 15, 121, 14);
		panel_1.add(lblNewLabel_1);
		
		String expectedProfit = String.format("Ganhos: %.2f", ticket.getExpectedProfit());
		JLabel lblNewLabel_2 = new JLabel(expectedProfit);
		lblNewLabel_2.setBounds(260, 15, 78, 14);
		panel_1.add(lblNewLabel_2);
		
		int radius = 50;
        RoundedImagePanel roundedImagePanel = new RoundedImagePanel(statusImagePath, statusBackground,radius);
        roundedImagePanel.setBounds(336, 386, 48, 49); 
        panel.add(roundedImagePanel);
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
	        gbc.gridy++;
	    }
	    
	    gbc.weighty = 1.0;
	    JPanel filler = new JPanel();
	    filler.setBackground(new Color(128, 128, 128));
	    betsPanel.add(filler, gbc);

	    betsPanel.revalidate();
	    betsPanel.repaint();
	}
	
    private void setTicketStatus() {
        switch (ticket.getStatus()) {
            case "GANHOU":
                statusBackground = Color.GREEN;
                statusImagePath = "/resources/images/check.png";
                break;
            case "PERDEU":
                statusBackground = Color.RED;
                statusImagePath = "/resources/images/fail.png";
                break;
            default:
                statusBackground = Color.YELLOW;
                statusImagePath = "/resources/images/pending.png";
                break;
        }
    }
}
