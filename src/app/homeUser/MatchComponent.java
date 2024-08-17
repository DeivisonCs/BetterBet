package app.homeUser;

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

import models.Match;

@SuppressWarnings("serial")
public class MatchComponent extends JPanel {

	private Match match;
	private static final int PANEL_TEAM_WIDTH = 400;
	private static final int PANEL_TEAM_HEIGHT = 400;
	
	private static final int PANEL_X_WIDTH = 50; 
	private static final int PANEL_X_HEIGHT = 400; 

	private static final Color DEFAULT_COLOR_MATCH_COMPONENT = new Color(40, 40, 40); 
	
	private static final Color SELECTED_FONT_COLOR =  new Color(30, 30, 30); 
	private static final Color DEFAULT_FONT_COLOR =  new Color(150, 150, 150);
	
    private static final Color SELECTED_COLOR = new Color(150, 150, 150);
    private static final Color DEFAULT_COLOR = new Color(30, 30, 30); 
    private static final int COMPONENT_HEIGHT = 50;
    private boolean isSelectedTeamA = false;
    private boolean isSelectedTeamB = false;
    private boolean isSelectedDraw = false;

    public MatchComponent(Match match) {
        this.match = match;
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
        panelTimeA.setBackground(DEFAULT_COLOR);
        panelTimeA.setLayout(new BorderLayout());
        
        String lblTimeAStr = String.format("%s", match.getTeamA().getName());
        JLabel lblTimeA = new JLabel(lblTimeAStr);
        lblTimeA.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblTimeA.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTimeA.setHorizontalAlignment(SwingConstants.CENTER);
        lblTimeA.setForeground(DEFAULT_FONT_COLOR);
        
        JLabel lblOddTimeA =  new JLabel(String.format("(%.1f)", match.getOddTeamA()));
        lblOddTimeA.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblOddTimeA.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblOddTimeA.setHorizontalAlignment(SwingConstants.CENTER);
        lblOddTimeA.setForeground(DEFAULT_FONT_COLOR);
        
        panelTimeA.add(lblTimeA, BorderLayout.NORTH);
        panelTimeA.add(lblOddTimeA, BorderLayout.SOUTH);
        gbc.gridx = 0;
        add(panelTimeA, gbc);
        
        //CONFIGURAÇÕES DE EXIBIÇÃO DO EMPATE
        
        JPanel panelDraw = new JPanel();
        panelDraw.setLayout(new BorderLayout());
        panelDraw.setBackground(DEFAULT_COLOR);
        
        JLabel lblOddDraw =  new JLabel(String.format("(%.1f)", match.getOddDraw()));
        lblOddDraw.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblOddDraw.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblOddDraw.setHorizontalAlignment(SwingConstants.CENTER);
        lblOddDraw.setForeground(DEFAULT_FONT_COLOR);
        panelDraw.add(lblOddDraw, BorderLayout.CENTER);
        
      //CONFIGURAÇÕES DE EXIBIÇÃO DO X (VERSUS)
                
        JPanel panelX = new JPanel();
        panelX.setPreferredSize(xPanelSize);
        panelX.setBackground(DEFAULT_COLOR);
        panelX.setLayout(new BorderLayout());
        panelX.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               //Não implementar
            }
        });
        
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
        panelTimeB.setBackground(DEFAULT_COLOR);
        panelTimeB.setLayout(new BorderLayout());
        
        String lblTimeBStr = String.format("%s", match.getTeamB().getName());
        JLabel lblTimeB = new JLabel(lblTimeBStr);
        lblTimeB.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblTimeB.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTimeB.setHorizontalAlignment(SwingConstants.CENTER);
        lblTimeB.setForeground(DEFAULT_FONT_COLOR);

        JLabel lblOddTimeB =  new JLabel(String.format("(%.1f)", match.getOddTeamB()));
        lblOddTimeB.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblOddTimeB.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblOddTimeB.setHorizontalAlignment(SwingConstants.CENTER);
        lblOddTimeB.setForeground(DEFAULT_FONT_COLOR);
        
        panelTimeB.add(lblTimeB, BorderLayout.NORTH);
        panelTimeB.add(lblOddTimeB, BorderLayout.SOUTH);
        
        gbc.gridx = 2;
        add(panelTimeB, gbc);
        
        //EVENTO DE CLIQUE DE MOUSE 'TIME A' - MUDANÇA DE COR
        panelTimeA.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((isSelectedTeamB || isSelectedDraw ) && !isSelectedTeamA) {
                    isSelectedTeamB = false;
                    
                    isSelectedDraw = false;
                    panelDraw.setBackground(DEFAULT_COLOR);
                    lblOddDraw.setForeground(DEFAULT_FONT_COLOR);
                    
                    panelTimeB.setBackground(DEFAULT_COLOR);
                    lblTimeB.setForeground(DEFAULT_FONT_COLOR);
                    lblOddTimeB.setForeground(DEFAULT_FONT_COLOR);
                }

                isSelectedTeamA = !isSelectedTeamA;
                Color backgroundColor = isSelectedTeamA ? SELECTED_COLOR : DEFAULT_COLOR;
                Color fontColor = isSelectedTeamA ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
                MatchComponent.this.dispatchEvent(e);
                panelTimeA.setBackground(backgroundColor);
                lblTimeA.setForeground(fontColor);
                lblOddTimeA.setForeground(fontColor);
                revalidate();
                repaint();
            }
        });
        

      //EVENTO DE CLIQUE DE MOUSE 'EMPATE' - MUDANÇA DE COR
        
        panelDraw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((isSelectedTeamB || isSelectedTeamA) && !isSelectedDraw) {
                    isSelectedTeamB = false;
                    panelTimeB.setBackground(DEFAULT_COLOR);
                    lblTimeB.setForeground(DEFAULT_FONT_COLOR);
                    lblOddTimeB.setForeground(DEFAULT_FONT_COLOR);
                    
                    isSelectedTeamA = false;
                    panelTimeA.setBackground(DEFAULT_COLOR);
                    lblTimeA.setForeground(DEFAULT_FONT_COLOR);
                    lblOddTimeA.setForeground(DEFAULT_FONT_COLOR);
                }

                isSelectedDraw = !isSelectedDraw;
                Color backgroundColor = isSelectedDraw ? SELECTED_COLOR : DEFAULT_COLOR;
                Color fontColor = isSelectedDraw ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
                MatchComponent.this.dispatchEvent(e);
                panelDraw.setBackground(backgroundColor);
                lblOddDraw.setForeground(fontColor);
                revalidate();
                repaint();
            }
        });
        
      //EVENTO DE CLIQUE DE MOUSE 'TIME B' - MUDANÇA DE COR
        
        panelTimeB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((isSelectedTeamA || isSelectedDraw) && !isSelectedTeamB) {
                    isSelectedTeamA = false;
                    isSelectedDraw = false;
                    panelDraw.setBackground(DEFAULT_COLOR);
                    lblOddDraw.setForeground(DEFAULT_FONT_COLOR);
                    
                    panelTimeA.setBackground(DEFAULT_COLOR);
                    lblTimeA.setForeground(DEFAULT_FONT_COLOR);
                    lblOddTimeA.setForeground(DEFAULT_FONT_COLOR);
                }

                isSelectedTeamB = !isSelectedTeamB;
                Color backgroundColor = isSelectedTeamB ? SELECTED_COLOR : DEFAULT_COLOR;
                Color fontColor = isSelectedTeamB ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
                MatchComponent.this.dispatchEvent(e);
                panelTimeB.setBackground(backgroundColor);
                lblTimeB.setForeground(fontColor);
                lblOddTimeB.setForeground(fontColor);
                revalidate();
                repaint();
            }
        });
        
        

    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, COMPONENT_HEIGHT);
    }
	
	public Match getMatch() {
	    return match;
	}

	public boolean isSelectedTeamA() {
		return isSelectedTeamA;
	}

	public boolean isSelectedTeamB() {
		return isSelectedTeamB;
	}

}
