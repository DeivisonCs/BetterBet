package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

public class RoundedTextField extends JTextField{
	private static final long serialVersionUID = 1L;
	private static final int ARC_WIDTH = 20;
	private static final int ARC_HEIGHT = 20;
	
	public RoundedTextField (){
        setOpaque(false); 
        setBorder(null); // Tira a borda
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g.create();
		// Melhora qualidade da borda
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Background
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
	
		// Borda
		graphics.setColor(Color.GRAY);
		graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT);

        // Texto
        super.paintComponent(g);

     // Libera os recursos gráficos
        graphics.dispose();
	}
}
