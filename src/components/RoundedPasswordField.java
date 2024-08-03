package components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPasswordField;

public class RoundedPasswordField extends JPasswordField{
	private static final long serialVersionUID = 1L; 
	private static final int ARC_WIDTH = 20;
	private static final int ARC_HEIGHT = 20;
	
	public RoundedPasswordField () {
		setOpaque(false); 
        setBorder(null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        super.paintComponent(g);

        g2d.dispose();
	}
}
