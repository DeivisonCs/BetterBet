package app.betView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import models.Bet;
import models.Match;

public class BetComponent extends JPanel{
	
	private Bet bet;
	
	private static final int PANEL_TEAM_WIDTH = 165;
	private static final int PANEL_TEAM_HEIGHT = 50;
	
	private static final int PANEL_X_WIDTH = 44; 
	private static final int PANEL_X_HEIGHT = 50; 

	private static final Color DEFAULT_COLOR_MATCH_COMPONENT = new Color(40, 40, 40); 
	
	private static final Color SELECTED_FONT_COLOR =  new Color(30, 30, 30); 
	private static final Color DEFAULT_FONT_COLOR =  new Color(234, 217, 0);
	
    private static final Color SELECTED_COLOR = new Color(234, 217, 0);
    private static final Color DEFAULT_COLOR = new Color(30, 30, 30); 

    private boolean isSelectedTeamA = false;
    private boolean isSelectedTeamB = false;
    private boolean isSelectedDraw = false;

    public BetComponent(Bet bet) {
    	if(bet.getSelectedBet().equals("TEAM_A")) {
    		isSelectedTeamA = true;
    	}else if(bet.getSelectedBet().equals("TEAM_B")) {
    		isSelectedTeamB = true;
    	}else if(bet.getSelectedBet().equals("DRAW")) {
    		isSelectedDraw = true;
    	}
    	
        this.bet = bet;
        initialize();
    }

    private void initialize() {
    	setLayout(new GridBagLayout());
        setBackground(DEFAULT_COLOR_MATCH_COMPONENT);
        
        //DEFINIÇÃO DA LARGURA DOS GRIDS DO MATCH COMPONENT
        GridBagLayout layout = (GridBagLayout) getLayout();
        layout.columnWidths = new int[]{PANEL_TEAM_WIDTH, PANEL_X_WIDTH, PANEL_TEAM_WIDTH};
        layout.rowHeights = new int[]{PANEL_TEAM_HEIGHT};
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        Dimension timePanelSize = new Dimension(PANEL_TEAM_WIDTH, PANEL_TEAM_HEIGHT);
        Dimension xPanelSize = new Dimension(PANEL_X_WIDTH, PANEL_X_HEIGHT);
        
        //CONFIGURAÇÕES DE EXIBIÇÃO DO TIME A
        
        JPanel panelTimeA = new JPanel();
        panelTimeA.setPreferredSize(timePanelSize);
        
        Color backgroundColor = isSelectedTeamA ? SELECTED_COLOR : DEFAULT_COLOR;
        panelTimeA.setBackground(backgroundColor);
        panelTimeA.setLayout(new BorderLayout());
        
        String lblTimeAStr = String.format("%s", bet.getMatch().getTeamA().getName());
        JLabel lblTimeA = new JLabel(lblTimeAStr);
        lblTimeA.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblTimeA.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTimeA.setHorizontalAlignment(SwingConstants.CENTER);
        Color fontColor = isSelectedTeamA ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
        lblTimeA.setForeground(fontColor);
        
        JLabel lblOddTimeA =  new JLabel(String.format("(%.1f)", bet.getOddTeamA()));
        lblOddTimeA.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblOddTimeA.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblOddTimeA.setHorizontalAlignment(SwingConstants.CENTER);
        lblOddTimeA.setForeground(fontColor);
        
        panelTimeA.add(lblTimeA, BorderLayout.NORTH);
        panelTimeA.add(lblOddTimeA, BorderLayout.SOUTH);
        gbc.gridx = 0;
        add(panelTimeA, gbc);
        
        //CONFIGURAÇÕES DE EXIBIÇÃO DO EMPATE
        
        JPanel panelDraw = new JPanel();
        panelDraw.setLayout(new BorderLayout());
        backgroundColor = isSelectedDraw ? SELECTED_COLOR : DEFAULT_COLOR;
        panelDraw.setBackground(backgroundColor);
        
        JLabel lblOddDraw =  new JLabel(String.format("(%.1f)", bet.getOddDraw()));
        lblOddDraw.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblOddDraw.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblOddDraw.setHorizontalAlignment(SwingConstants.CENTER);
        fontColor = isSelectedDraw ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
        lblOddDraw.setForeground(fontColor);
        panelDraw.add(lblOddDraw, BorderLayout.CENTER);
        
      //CONFIGURAÇÕES DE EXIBIÇÃO DO X (VERSUS)
                
        JPanel panelX = new JPanel();
        panelX.setPreferredSize(xPanelSize);
        panelX.setBackground(DEFAULT_COLOR);
        panelX.setLayout(new BorderLayout());
        
        JLabel lblX = new JLabel("X");
        lblX.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblX.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblX.setHorizontalAlignment(SwingConstants.CENTER);
        lblX.setForeground(DEFAULT_FONT_COLOR);
        
        
        panelX.add(lblX, BorderLayout.NORTH);
        panelX.add(panelDraw, BorderLayout.SOUTH);
        gbc.gridx = 1;
        add(panelX, gbc);
        
      //CONFIGURAÇÕES DE EXIBIÇÃO DO TIME B
        
        JPanel panelTimeB = new JPanel();
        panelTimeB.setPreferredSize(timePanelSize);
        backgroundColor = isSelectedTeamB ? SELECTED_COLOR : DEFAULT_COLOR;
        panelTimeB.setBackground(backgroundColor);
        panelTimeB.setLayout(new BorderLayout());
        
        String lblTimeBStr = String.format("%s", bet.getMatch().getTeamB().getName());
        JLabel lblTimeB = new JLabel(lblTimeBStr);
        lblTimeB.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblTimeB.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTimeB.setHorizontalAlignment(SwingConstants.CENTER);
        fontColor = isSelectedTeamB ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
        lblTimeB.setForeground(fontColor);

        JLabel lblOddTimeB =  new JLabel(String.format("(%.1f)", bet.getOddTeamB()));
        lblOddTimeB.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblOddTimeB.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblOddTimeB.setHorizontalAlignment(SwingConstants.CENTER);
        lblOddTimeB.setForeground(DEFAULT_FONT_COLOR);
        lblOddTimeB.setForeground(fontColor);
        panelTimeB.add(lblTimeB, BorderLayout.NORTH);
        panelTimeB.add(lblOddTimeB, BorderLayout.SOUTH);
        
        gbc.gridx = 2;
        add(panelTimeB, gbc);
    	
    }
}
