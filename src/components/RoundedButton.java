package components;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
	private static final long serialVersionUID = 1L;
    private static final int ARC_WIDTH = 20;   // Largura Arco da Borda
    private static final int ARC_HEIGHT = 20;  // Altura Arco da Borda (ambas devem ter o mesmo valor)

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(false); 
        setContentAreaFilled(false);
        setBorderPainted(false); // Remove borda padrão
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
        graphics.setColor(Color.GRAY); // Border color
        graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT);

        // Texto do botão
        super.paintComponent(g);

        // Libera os recursos gráficos
        graphics.dispose();
    }
}
