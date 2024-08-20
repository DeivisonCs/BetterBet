package components;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import models.Event;

@SuppressWarnings("serial")
public class EventComponent extends JPanel {

    private Event event;
    private boolean isSelected = false;
    private static final Color SELECTED_COLOR_LABEL = new Color(20, 20, 20); 
    private static final Color DEFAULT_COLOR_LABEL = Color.BLACK;
    private static final Color SELECTED_COLOR_PANEL = new Color(234, 199, 0); 
    private static final Color DEFAULT_COLOR_PANEL = new Color(255, 215, 0); 
    private static final int COMPONENT_HEIGHT = 50;
    private JLabel eventLabel;

    public EventComponent(Event event) {
        this.event = event;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setBackground(DEFAULT_COLOR_PANEL);

        eventLabel = new JLabel(event.getName());
        eventLabel.setPreferredSize(new Dimension(200, 60));
        eventLabel.setForeground(DEFAULT_COLOR_LABEL);
        eventLabel.setHorizontalAlignment(SwingConstants.CENTER);
        eventLabel.setFont(new Font("Verdana", Font.PLAIN, 15));

        add(eventLabel, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSelection();
            }
        });
    }

    public void toggleSelection() {
        isSelected = !isSelected;
        Color backgroundColor = isSelected ? SELECTED_COLOR_PANEL : DEFAULT_COLOR_PANEL;
        setBackground(backgroundColor);
        
        Color fontColor = isSelected ? SELECTED_COLOR_LABEL : DEFAULT_COLOR_LABEL;
        eventLabel.setForeground(fontColor);
        
        Font currentFont = eventLabel.getFont();
        
        Font newFont = isSelected ? currentFont.deriveFont(Font.BOLD) : currentFont.deriveFont(Font.PLAIN);
        eventLabel.setFont(newFont);
        
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, COMPONENT_HEIGHT);
    }

    public boolean isSelected() {
        return isSelected;
    }
}