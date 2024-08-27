package app.historyView;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;

import components.ImageUtils;
import components.BetComponent;
import components.RoundedButtonComponent;
import components.RoundedImagePanel;
import models.Bet;
import models.Ticket;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * A classe `ShowTicketComponent` é responsável por exibir os detalhes de um ticket específico,
 * incluindo as apostas associadas, a odd, o valor apostado e os ganhos esperados.
 */
public class ShowTicketComponent {
	private JFrame frame;
	private JPanel betsPanel;
	
	private Ticket ticket;
    private ImageIcon statusImagePath;
    
	
	/**
	 * Cria a Janela
	 */
	public ShowTicketComponent(Ticket ticket) {
		this.ticket = ticket;
		setTicketStatus();
		initialize();
		updatebets();
		
	}

	/**
	 * Inicializa os conteúdos da Janela
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(40, 40, 40));
		panel.setBounds(0, 0, 450, 507);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton cancelButton = new RoundedButtonComponent("Cancelar");
		cancelButton.setText("Voltar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ticket.getBets().clear();
				frame.dispose();
			}
		});
		cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	cancelButton.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	cancelButton.setBackground(new Color(150, 150, 150));
            }
        });
		cancelButton.setBounds(20, 455, 100, 29);
		cancelButton.setBackground(new Color(150, 150, 150));
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
		betScrollPane.setBounds(10, 11, 430, 317);
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
		panel_1.setBounds(10, 328, 430, 84);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		String finalOddValue = String.format("Odd: %.2f", ticket.getOdd());
		JLabel lblNewLabel = new JLabel(finalOddValue);
		lblNewLabel.setBounds(296, 15, 84, 14);
		panel_1.add(lblNewLabel);
		
		String value = String.format("Valor apostado: %.2f", ticket.getAmount());
		JLabel lblNewLabel_1 = new JLabel(value);
		lblNewLabel_1.setBounds(33, 15, 137, 14);
		panel_1.add(lblNewLabel_1);
		
		String expectedProfit = String.format("Ganhos: %.2f", ticket.getExpectedProfit());
		JLabel lblNewLabel_2 = new JLabel(expectedProfit);
		lblNewLabel_2.setBounds(33, 59, 104, 14);
		panel_1.add(lblNewLabel_2);
		
		ImageUtils profilePicture = new ImageUtils();
		profilePicture.setImage(statusImagePath);
		profilePicture.setBounds(384, 455, 35, 35);
		profilePicture.setBorder(null);
		profilePicture.setBorderSize(0);
		panel.add(profilePicture);
		
        JLabel betStatusLabel = new JLabel("Status:");
        betStatusLabel.setForeground(new Color(150, 150, 150));
        betStatusLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        betStatusLabel.setBounds(315, 460, 59, 14);
        panel.add(betStatusLabel);
		frame.setVisible(true);
	}
	
    /**
     * Atualiza a lista de apostas exibidas no painel.
     */
	public void updatebets() {
	    betsPanel.removeAll();
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.NORTH;  
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
	
    /**
     * Define o ícone de status com base no status do ticket.
     */
    private void setTicketStatus() {
        switch (ticket.getStatus()) {
            case "GANHOU":
//                statusBackground = Color.GREEN;
                statusImagePath = new ImageIcon(getClass().getResource("/resources/images/win_icon.png"));
                break;
            case "PERDEU":
//                statusBackground = Color.RED;
                statusImagePath = new ImageIcon(getClass().getResource("/resources/images/loss_icon.png"));
                break;
            default:
//                statusBackground = Color.YELLOW;
                statusImagePath = new ImageIcon(getClass().getResource("/resources/images/pending_icon.png"));
                break;
        }
    }
}
