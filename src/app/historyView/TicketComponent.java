package app.historyView;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import models.Ticket;

@SuppressWarnings("serial")
public class TicketComponent extends JPanel {

    private JPanel ticketPanel;
    private Ticket ticket;
    private Color statusBackground;
    private String statusImagePath;


    public TicketComponent(Ticket ticket) {
        this.ticket = ticket; 
        setTicketStatus();
        initialize();
    }


    private void initialize() {

        setBackground(new Color(20, 20, 20));
        setLayout(null);

        JPanel panelOdd = new JPanel();
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

        JPanel panelBet = new JPanel();
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

        JPanel panelProfit = new JPanel();
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

        JLabel labelEventName = new JLabel("Nome do Evento");
        labelEventName.setFont(new Font("Tahoma", Font.PLAIN, 13));
        labelEventName.setForeground(Color.WHITE);
        labelEventName.setBounds(25, 21, 103, 14);
        this.add(labelEventName);

        String betType = ticket.getTicketType();
        JLabel labelBetType = new JLabel(betType);
        labelBetType.setFont(new Font("Tahoma", Font.PLAIN, 13));
        labelBetType.setForeground(Color.WHITE);
        labelBetType.setBounds(25, 46, 90, 14);
        this.add(labelBetType);

        RoundedImagePanel roundedImagePanel = new RoundedImagePanel(statusImagePath, statusBackground);
        roundedImagePanel.setBounds(470, 21, 48, 49); 
        this.add(roundedImagePanel);

        JLabel labelOutcome = new JLabel("Caso Simples: Vencedor: Nome do Time");
        labelOutcome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        labelOutcome.setForeground(Color.WHITE);
        labelOutcome.setBounds(25, 71, 250, 14);
        this.add(labelOutcome);
    }


    private void setTicketStatus() {
        switch (ticket.getStatus()) {
            case "GANHOU":
                statusBackground = Color.GREEN;
                statusImagePath = "/images/check.png";
                break;
            case "PERDEU":
                statusBackground = Color.RED;
                statusImagePath = "/images/fail.png";
                break;
            default:
                statusBackground = Color.YELLOW;
                statusImagePath = "/images/pending.png";
                break;
        }
    }
}
