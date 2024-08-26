package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Transaction;

@SuppressWarnings("serial")
public class TransactionComponent extends JPanel{
	
	private Transaction transaction;
	
	private static final int COMPONENT_WIDTH = 240;
	private static final int COMPONENT_HEIGHT = 100;
	
	private static final Color DEFAULT_COLOR = new Color(35, 35, 35);
	private static final Color WITHDRAWAL_COLOR = new Color(220, 40, 40);
	private static final Color DEPOSIT_COLOR = new Color(107, 207, 107);
		
	public TransactionComponent(Transaction transaction) {
		this.transaction = transaction;
		initialize();
	}
	
	public void initialize() {
		
		Dimension componentTransactionSize = new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT);
		
		String lblTransactionStr = String.format("%s: R$ %.2f", transaction.getType(), transaction.getValue());
		JLabel lblTransaction = new JLabel(lblTransactionStr);
		lblTransaction.setForeground(new Color(255, 255, 255));

		LocalDateTime dataHora = transaction.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");		
		String lblDateTimeStr = dataHora.format(formatter);		
		JLabel lblDateTime = new JLabel(lblDateTimeStr);
		lblDateTime.setForeground(new Color(255, 255, 255));
		lblDateTime.setBounds(10, 10, 150, 20);
		this.add(lblDateTime);
		
		JPanel typeColorIdentifier = new JPanel();
		typeColorIdentifier.setBounds(230, 5, 3, 90);
		
		if(transaction.getType().equals("Saque")) {
			typeColorIdentifier.setBackground(WITHDRAWAL_COLOR);
		}else {
			typeColorIdentifier.setBackground(DEPOSIT_COLOR);
		}
		
		this.add(typeColorIdentifier);
	
		this.setPreferredSize(componentTransactionSize);
		this.setBackground(DEFAULT_COLOR);
		this.setLayout(null);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(transaction.getType().equals("Saque")) {					
					setBackground(WITHDRAWAL_COLOR);
				}else {
					setBackground(DEPOSIT_COLOR);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(DEFAULT_COLOR);
			}
		
		
			
		});
		
		lblTransaction.setBounds(10, 40, 200, 20);
		this.add(lblTransaction);
		
	}
	
	
	
}
