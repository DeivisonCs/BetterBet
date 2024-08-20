//package app;
//
//import java.awt.CardLayout;
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.Panel;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.awt.geom.Ellipse2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextPane;
//
//import com.toedter.calendar.JCalendar;
//
//import app.auth.SignUpView;
//import components.RoundedButton;
//import components.RoundedPasswordFieldComponent;
//import database.InitDatabase;
//
//
//public class testes{
//	public static void main(String[] args) {
//	JFrame frame = new JFrame("Round Profile Icon");
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setSize(300, 300);
//
//    try {
//        // Carrega a imagem de um arquivo
//        BufferedImage originalImage = ImageIO.read(new File("src/public/images/Profile-Icon.jpg"));
//
//        // Redimensiona a imagem
//        int size = 100;
//        Image scaledImage = originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
//
//        // Converte a imagem redimensionada em BufferedImage
//        BufferedImage roundedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2 = roundedImage.createGraphics();
//
//        // Cria uma máscara circular
//        g2.setClip(new Ellipse2D.Float(0, 0, size, size));
//        g2.drawImage(scaledImage, 0, 0, null);
//        g2.dispose();
//
//        // Cria o ícone arredondado
//        ImageIcon roundedIcon = new ImageIcon(roundedImage);
//
//        // Exibe o ícone em um JLabel
//        JLabel label = new JLabel(roundedIcon);
//        frame.add(label);
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//
//    frame.setVisible(true);
//	}
//}
