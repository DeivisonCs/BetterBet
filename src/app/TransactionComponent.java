package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Transaction;

@SuppressWarnings("serial")
public class TransactionComponent extends JPanel{
	
	private Transaction transaction;
	
	private static final int COMPONENT_WIDTH = 240;
	private static final int COMPONENT_HEIGHT = 100;
	
	private static final Color DEFAULT_COLOR = new Color(128, 128, 128);
	private static final Color ENTERED_COLOR = new Color(192,192,192);
	
	private JPanel componentTransaction;
	
	public TransactionComponent(Transaction transaction) {
		this.transaction = transaction;
		initialize();
	}
	
	public void initialize() {
		
		Dimension componentTransactionSize = new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT);
		
		String lblTransactionStr = String.format("%s: R$ %.2f", transaction.getType(), transaction.getValue());
		JLabel lblTransaction = new JLabel(lblTransactionStr);
		
		//componentTransaction = new JPanel();

		this.setMaximumSize(componentTransactionSize);
		this.setMinimumSize(componentTransactionSize);
		this.setBackground(DEFAULT_COLOR);
		this.setLayout(null);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(ENTERED_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(DEFAULT_COLOR);
			}
		
		
			
		});
		
		lblTransaction.setBounds(10, 20, 200, 20);
		this.add(lblTransaction);
		
	}
	
	
	public JPanel getComponentTransaction() {
		
		return this.componentTransaction;
	}
	
	
}
