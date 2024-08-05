package app.homeUser;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class RoundedButtonComponent extends JButton{

    private static final Color ENTERED_COLOR = new Color(150, 150, 150);
    private static final Color EXITED_COLOR = new Color(40, 40, 40);
    
    
	
	
	public RoundedButtonComponent(String text) {
		super(text);
		initialize();
	}
	
	
    private void initialize() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setText(getText());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(ENTERED_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(EXITED_COLOR);
            }
        });
    }
		
	protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape round = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        g2.setColor(getBackground());
        g2.fill(round);

        super.paintComponent(g);

	}
	
	
	
}
