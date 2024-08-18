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
//	private static final int PANEL_TRANSACTION_WIDTH = 240;
//	private static final int PANEL_TRANSACTION_HEIGHT = 1000;
	
	private static final Color DEFAULT_COLOR = new Color(128, 128, 128);
	private static final Color ENTERED_COLOR = new Color(192,192,192);
	
	public TransactionComponent(Transaction transaction) {
		this.transaction = transaction;
		initialize();
	}
	
	public void initialize() {
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
//		JPanel panelTransaction = new JPanel();
//		panelTransaction.setPreferredSize(new Dimension(PANEL_TRANSACTION_WIDTH, PANEL_TRANSACTION_HEIGHT));
		
		Dimension componentTransactionSize = new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT);
		
		String lblTransactionStr = String.format(transaction.getType(), "%.2d %s", transaction.getValue());
		JLabel lblTransaction = new JLabel(lblTransactionStr);
		
		JPanel componentTransaction = new JPanel();
		//componentTransaction.setPreferredSize(componentTransactionSize);
		componentTransaction.setMaximumSize(componentTransactionSize);
		componentTransaction.setMinimumSize(componentTransactionSize);
		componentTransaction.setBackground(DEFAULT_COLOR);
		componentTransaction.setLayout(null);
		componentTransaction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(ENTERED_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(DEFAULT_COLOR);
			}
		
		
			
		});
		
		
		componentTransaction.add(lblTransaction);
		
	}
	
	
	
}
