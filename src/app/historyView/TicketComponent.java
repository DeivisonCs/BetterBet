package app.historyView;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import models.Ticket;

@SuppressWarnings("serial")
public class TicketComponent extends JPanel{


    JPanel ticketPanel;

    private Ticket ticket;
    private Color statusBackground;
    private String statusImagePath;

    /**
     * Launch the application.
     */
    /*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TicketComponent window = new TicketComponent(null);
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
    public TicketComponent(Ticket ticket) {
    	setTicketStatus();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        ticketPanel = new JPanel();
        ticketPanel.setBackground(new Color(20, 20, 20));
        ticketPanel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(55, 55, 55));
        panel_1.setBounds(63, 102, 137, 60);
        ticketPanel.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("ODD");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_3.setForeground(new Color(255, 255, 255));
        lblNewLabel_3.setBounds(0, 11, 137, 14);
        panel_1.add(lblNewLabel_3);

        String oddValue = String.format("%.2f", ticket.getOdd());
        JLabel lblNewLabel_3_3 = new JLabel(oddValue);
        lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_3.setForeground(Color.WHITE);
        lblNewLabel_3_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_3_3.setBounds(0, 35, 137, 14);
        panel_1.add(lblNewLabel_3_3);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(55, 55, 55));
        panel_2.setBounds(231, 102, 137, 60);
        ticketPanel.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel_3_1 = new JLabel("APOSTA");
        lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_1.setForeground(Color.WHITE);
        lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_3_1.setBounds(0, 11, 137, 14);
        panel_2.add(lblNewLabel_3_1);

        String betValue = String.format("%.2f", ticket.getAmount());
        JLabel lblNewLabel_3_4 = new JLabel(betValue);
        lblNewLabel_3_4.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_4.setForeground(Color.WHITE);
        lblNewLabel_3_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_3_4.setBounds(0, 36, 137, 14);
        panel_2.add(lblNewLabel_3_4);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(55, 55, 55));
        panel_3.setBounds(395, 102, 137, 60);
        ticketPanel.add(panel_3);
        panel_3.setLayout(null);

        JLabel lblNewLabel_3_2 = new JLabel("GANHOS");
        lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_2.setForeground(Color.WHITE);
        lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_3_2.setBounds(0, 11, 137, 14);
        panel_3.add(lblNewLabel_3_2);

        String expectedProfit = String.format("%.2f", ticket.getExpectedProfit());
        JLabel lblNewLabel_3_5 = new JLabel(expectedProfit);
        lblNewLabel_3_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_5.setForeground(Color.WHITE);
        lblNewLabel_3_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_3_5.setBounds(0, 36, 137, 14);
        panel_3.add(lblNewLabel_3_5);

        JLabel lblNewLabel = new JLabel("Nome do Evento");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(25, 21, 103, 14);
        ticketPanel.add(lblNewLabel);

        String betType = ticket.getTicketType();
        JLabel lblNewLabel_1 = new JLabel(betType);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(25, 46, 90, 14);
        ticketPanel.add(lblNewLabel_1);

        
        RoundedImagePanel roundedImagePanel = new RoundedImagePanel(statusImagePath, statusBackground);
        roundedImagePanel.setBounds(470, 21, 48, 49); 
        ticketPanel.add(roundedImagePanel);

        JLabel lblNewLabel_2 = new JLabel("Caso Simples: Vencedor: Nome do Time");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setBounds(25, 71, 250, 14);
        ticketPanel.add(lblNewLabel_2);
        
    }
    
    private void setTicketStatus() {
    	if(ticket.getStatus().equals("GANHOU")) {
    	    statusBackground = Color.GREEN;
    	    statusImagePath = "/images/check.png";
    	}else if(ticket.getStatus().equals("PERDEU")) {
    	    statusBackground = Color.RED;
    	    statusImagePath = "/images/fail.png";
    	}else {
    		statusBackground = Color.YELLOW;
    	    statusImagePath = "/images/pending.png";
    	}
    }
}
