package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;

@SuppressWarnings("serial")
public class RoundedPasswordFieldComponent extends JPasswordField {
	private int arcWidth;
    private int arcHeight;
    private int paddingLeft;
    private int paddingRight;

    public RoundedPasswordFieldComponent(int columns, int arcWidth, int arcHeight, int paddingLeft, int paddingRight) {
        super(columns);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, paddingLeft, 0, paddingRight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape round = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2.setColor(getBackground());
        g2.fill(round);

        super.paintComponent(g);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
      
    }

    @Override
    public Insets getInsets() {
        Insets insets = super.getInsets();
        return new Insets(insets.top, paddingLeft, insets.bottom, paddingRight);
    }
}
