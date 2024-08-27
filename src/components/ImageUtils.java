package components;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class ImageUtils extends JComponent {

	private static final long serialVersionUID = 1L;

	public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    private Icon image;
    private int borderSize = 5;
    private Color borderColor = new Color(60, 60, 60);

    @Override
    public void paint(Graphics g) {
    	if (image != null) {
            // Obtém largura e altura da imagem
            int width = image.getIconWidth();
            int height = image.getIconHeight();
            
            // Define o diâmetro do círculo que será a máscara, baseado no menor valor entre largura e altura
            int diameter = Math.min(width, height);

            // Cria uma máscara oval (círculo) para a imagem
            BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = mask.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Suaviza a imagem
            g2d.fillOval(0, 0, diameter - 1, diameter - 1); // Desenha um círculo preenchido
            g2d.dispose();

            // Aplica a máscara oval à imagem para criar um efeito de imagem circular
            BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            g2d = masked.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Suaviza a imagem
            int x = (diameter - width) / 2;  // Calcula a posição horizontal para centralizar
            int y = (diameter - height) / 2; // Calcula a posição vertical para centralizar
            g2d.drawImage(toImage(image), x, y, null); // Desenha a imagem no gráfico
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN)); // Define a composição para aplicar a máscara
            g2d.drawImage(mask, 0, 0, null); // Aplica a máscara
            g2d.dispose();

            // Converte a imagem mascarada em um ícone
            Icon icon = new ImageIcon(masked);

            // Obtém o tamanho ajustado automaticamente para a imagem
            Rectangle size = getAutoSize(icon);

            // Obtém o objeto Graphics2D para desenhar
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Suaviza a imagem

            // Desenha a imagem redimensionada e centralizada
            g2.drawImage(toImage(icon), size.getLocation().x, size.getLocation().y, size.getSize().width, size.getSize().height, null);

            // Se o tamanho da borda for maior que zero, desenha a borda ao redor da imagem
            if (borderSize > 0) {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Suaviza a borda
                g2.setColor(borderColor); // Define a cor da borda
                g2.setStroke(new BasicStroke(borderSize)); // Define a espessura da borda
                g2.drawOval(size.x + (borderSize / 2), size.y + (borderSize / 2), size.width - borderSize, size.height - borderSize); // Desenha a borda oval
            }
        }
        super.paint(g);
    }

    //Método para calcular o tamanho e posição automáticos da imagem para centralizá-la no componente
    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();  // Largura do componente
        int h = getHeight(); // Altura do componente
        int iw = image.getIconWidth();  // Largura da imagem
        int ih = image.getIconHeight(); // Altura da imagem
        
        // Calcula as escalas para redimensionamento
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        
        // Usa a maior escala para manter a proporção da imagem
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw); // Largura ajustada
        int height = (int) (scale * ih); // Altura ajustada
        
        // Calcula a posição para centralizar a imagem
        int x = (w - width) / 2;
        int y = (h - height) / 2;

        // Retorna um retângulo que representa a área da imagem redimensionada e centralizada
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    // Método utilitário para converter um Icon em uma Image
    public Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
}