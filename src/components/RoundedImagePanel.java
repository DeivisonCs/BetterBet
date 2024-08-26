package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

@SuppressWarnings("serial")
public class RoundedImagePanel extends JPanel {
    private BufferedImage image;
    private Color backgroundColor;
    private int radius;

    public RoundedImagePanel(String imagePath, Color backgroundColor, int radius) {
    	this.radius = radius;
        this.backgroundColor = backgroundColor;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(radius, radius));
        setOpaque(false);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, radius, radius);
        g2d.setClip(circle);

        g2d.setColor(backgroundColor);
        g2d.fill(circle);

        if (image != null) {
            g2d.drawImage(image, 0, 0, radius, radius, null);
        }
    }

}
