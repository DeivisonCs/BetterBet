package app.historyView;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.ImageUtils;
import models.Ticket;
import service.bets.BetService;

@SuppressWarnings("serial")
public class TicketComponent extends JPanel {

    private Ticket ticket;
    private Color statusBackground;
    private ImageIcon statusImagePath;
    private BetService betService = new BetService();


    public TicketComponent(Ticket ticket) {
        this.ticket = ticket; 
        setTicketStatus();
        initialize();
    }


    private void initialize() {

        setBackground(new Color(20, 20, 20));
        setLayout(null);
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
		
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            	try {
					ticket.setBets(betService.getBetsByTicketId(ticket.getId()));
					new ShowTicketComponent(ticket);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Erro ao carregar aposta");
				}
               
            }
        });

        RoundedPanel panelOdd = new RoundedPanel();
        panelOdd.setBackground(new Color(55, 55, 55));
        panelOdd.setBounds(63, 102, 137, 60);
        this.add(panelOdd);
        panelOdd.setLayout(null);

        JLabel labelOddTitle = new JLabel("ODD");
        labelOddTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelOddTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelOddTitle.setForeground(Color.WHITE);
        labelOddTitle.setBounds(0, 11, 137, 14);
        panelOdd.add(labelOddTitle);

        String oddValue = String.format("%.2f", ticket.getOdd());
        JLabel labelOddValue = new JLabel(oddValue);
        labelOddValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelOddValue.setForeground(Color.WHITE);
        labelOddValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelOddValue.setBounds(0, 35, 137, 14);
        panelOdd.add(labelOddValue);

        RoundedPanel panelBet = new RoundedPanel();
        panelBet.setBackground(new Color(55, 55, 55));
        panelBet.setBounds(231, 102, 137, 60);
        this.add(panelBet);
        panelBet.setLayout(null);

        JLabel labelBetTitle = new JLabel("APOSTA");
        labelBetTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelBetTitle.setForeground(Color.WHITE);
        labelBetTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelBetTitle.setBounds(0, 11, 137, 14);
        panelBet.add(labelBetTitle);

        String betValue = String.format("%.2f", ticket.getAmount());
        JLabel labelBetValue = new JLabel(betValue);
        labelBetValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelBetValue.setForeground(Color.WHITE);
        labelBetValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelBetValue.setBounds(0, 36, 137, 14);
        panelBet.add(labelBetValue);

        RoundedPanel panelProfit = new RoundedPanel();
        panelProfit.setBackground(new Color(55, 55, 55));
        panelProfit.setBounds(395, 102, 137, 60);
        this.add(panelProfit);
        panelProfit.setLayout(null);

        JLabel labelProfitTitle = new JLabel("GANHOS");
        labelProfitTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelProfitTitle.setForeground(Color.WHITE);
        labelProfitTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelProfitTitle.setBounds(0, 11, 137, 14);
        panelProfit.add(labelProfitTitle);

        String expectedProfit = String.format("%.2f", ticket.getExpectedProfit());
        JLabel labelProfitValue = new JLabel(expectedProfit);
        labelProfitValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelProfitValue.setForeground(Color.WHITE);
        labelProfitValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelProfitValue.setBounds(0, 36, 137, 14);
        panelProfit.add(labelProfitValue);

        String betType = ticket.getTicketType();
        JLabel labelBetType = new JLabel(betType);
        labelBetType.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelBetType.setForeground(Color.WHITE);
        labelBetType.setBounds(47, 45, 153, 14);
        this.add(labelBetType);
        
//        int radius = 50;
//        RoundedImagePanel roundedImagePanel = new RoundedImagePanel(statusImagePath, statusBackground, radius);
//        roundedImagePanel.setBounds(470, 21, 48, 49); 
//        this.add(roundedImagePanel);
//        
        ImageUtils profilePicture = new ImageUtils();
		profilePicture.setImage(statusImagePath);
		profilePicture.setBounds(500, 21, 35, 35);
		profilePicture.setBorder(null);
		profilePicture.setBorderSize(0);
		this.add(profilePicture);

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));
        
        g2.dispose();
    }


    private void setTicketStatus() {
    	switch (ticket.getStatus()) {
        case "GANHOU":
//            statusBackground = Color.GREEN;
            statusImagePath = new ImageIcon(getClass().getResource("/resources/images/win_icon.png"));
            break;
        case "PERDEU":
//            statusBackground = Color.RED;
            statusImagePath = new ImageIcon(getClass().getResource("/resources/images/loss_icon.png"));
            break;
        default:
//            statusBackground = Color.YELLOW;
            statusImagePath = new ImageIcon(getClass().getResource("/resources/images/pending_icon.png"));
            break;
    }
    }
}
