package app.homeUser;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class ProfileImageComponent extends JPanel {
    private BufferedImage image;

    public ProfileImageComponent(byte[] imageData) {
        initialize(imageData);
    }

    private void initialize(byte[] imageData) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
            BufferedImage originalImage = ImageIO.read(bais);
            this.image = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = this.image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, 60, 60, null);
            g2d.dispose();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 50));
            g2.fill(new Ellipse2D.Double(5, 5, 50, 50));

            Ellipse2D.Double circle = new Ellipse2D.Double(5, 5, 50, 50);
            g2.setClip(circle);

            g2.drawImage(this.image, 5, 5, 50, 50, null);

            g2.setClip(null);

            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLUE);
            g2.draw(circle);

            g2.dispose();
        }
    }

}
