package components;

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

import models.Match;

@SuppressWarnings("serial")
public class MatchComponent extends JPanel {

	private Match match;
	private static final int PANEL_TEAM_WIDTH = 400;
	private static final int PANEL_TEAM_HEIGHT = 100;
	private static final int PANEL_X_WIDTH = 50; 
	private static final int PANEL_X_HEIGHT = 100; 

	private static final Color SELECTED_FONT_COLOR =  new Color(40, 40, 40); 
	private static final Color DEFAULT_FONT_COLOR =  new Color(150, 150, 150);
	
    private static final Color SELECTED_COLOR = new Color(150, 150, 150); 
    private static final Color DEFAULT_COLOR = new Color(40, 40, 40); 
    private static final int COMPONENT_HEIGHT = 50;
    private boolean isSelectedTeamA = false;
    private boolean isSelectedTeamB = false;

    public MatchComponent(Match match) {
        this.match = match;
        initialize();
    }

    private void initialize() {
        setLayout(new GridBagLayout());
        setBackground(DEFAULT_COLOR);
        GridBagLayout layout = (GridBagLayout) getLayout();
        layout.columnWidths = new int[]{PANEL_TEAM_WIDTH, PANEL_X_WIDTH, PANEL_TEAM_WIDTH}; // Largura das colunas
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        Dimension timePanelSize = new Dimension(PANEL_TEAM_WIDTH, PANEL_TEAM_HEIGHT);
        Dimension xPanelSize = new Dimension(PANEL_X_WIDTH, PANEL_X_HEIGHT);
        
        JPanel panelTime2 = new JPanel();

        String lblTime1Str = String.format("(%.1f) %s", match.getOddTeamA(), match.getTeamA().getName());
        JLabel lblTime1 = new JLabel(lblTime1Str);
        
        String lblTime2Str = String.format("%s (%.1f)", match.getTeamB().getName(), match.getOddTeamB());
        JLabel lblTime2 = new JLabel(lblTime2Str);
        
        
        JPanel panelTime1 = new JPanel();
        panelTime1.setPreferredSize(timePanelSize);
        panelTime1.setBackground(DEFAULT_COLOR);
        panelTime1.setLayout(new BorderLayout());
        panelTime1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSelectedTeamB && !isSelectedTeamA) {
                    isSelectedTeamB = false;
                    panelTime2.setBackground(DEFAULT_COLOR);
                    lblTime2.setForeground(DEFAULT_FONT_COLOR);
                }

                isSelectedTeamA = !isSelectedTeamA;
                Color backgroundColor = isSelectedTeamA ? SELECTED_COLOR : DEFAULT_COLOR;
                Color fontColor = isSelectedTeamA ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
                MatchComponent.this.dispatchEvent(e);
                panelTime1.setBackground(backgroundColor);
                lblTime1.setForeground(fontColor);
                revalidate();
                repaint();
            }
        });
        
        lblTime1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTime1.setHorizontalAlignment(SwingConstants.CENTER);
        lblTime1.setForeground(DEFAULT_FONT_COLOR);
        panelTime1.add(lblTime1, BorderLayout.CENTER);

        gbc.gridx = 0;
        //gbc.weightx = 0;
        add(panelTime1, gbc);

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
        lblX.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblX.setHorizontalAlignment(SwingConstants.CENTER);
        lblX.setForeground(DEFAULT_FONT_COLOR);
        panelX.add(lblX, BorderLayout.CENTER);

        gbc.gridx = 1;
        //gbc.weightx = 0;
        add(panelX, gbc);
        
        
        panelTime2.setPreferredSize(timePanelSize);
        panelTime2.setBackground(DEFAULT_COLOR);
        panelTime2.setLayout(new BorderLayout());
        panelTime2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSelectedTeamA && !isSelectedTeamB) {
                    isSelectedTeamA = false;
                    panelTime1.setBackground(DEFAULT_COLOR);
                    lblTime1.setForeground(DEFAULT_FONT_COLOR);
                }

                isSelectedTeamB = !isSelectedTeamB;
                Color backgroundColor = isSelectedTeamB ? SELECTED_COLOR : DEFAULT_COLOR;
                Color fontColor = isSelectedTeamB ? SELECTED_FONT_COLOR : DEFAULT_FONT_COLOR;
                MatchComponent.this.dispatchEvent(e);
                panelTime2.setBackground(backgroundColor);
                lblTime2.setForeground(fontColor);
                revalidate();
                repaint();
            }
        });
        
        lblTime2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTime2.setHorizontalAlignment(SwingConstants.CENTER);
        lblTime2.setForeground(DEFAULT_FONT_COLOR);
        panelTime2.add(lblTime2, BorderLayout.CENTER);

        gbc.gridx = 2;
        //gbc.weightx = 0; 
        add(panelTime2, gbc);
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
