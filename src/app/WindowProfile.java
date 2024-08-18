package app;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;

import javax.swing.SwingConstants;

import app.homeUser.HomeUserUI;
import models.CommonUser;
import models.Transaction;
import models.User;

import dao.TransactionDAO;
import dao.TransactionPostgresDAO;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class WindowProfile {

    private JFrame frame;
    private final JPanel panelTransaction = new JPanel();
    
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private List<TransactionComponent> transactionComponents = new ArrayList<TransactionComponent>();
    
    private User user;
    
    private TransactionDAO transactionDAO = new TransactionPostgresDAO();
    
    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                WindowProfile window = new WindowProfile();
//                window.frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    /**
     * Create the application.
     */
    public WindowProfile(User user) {
    	this.user = user;
    	
    	try {
    		
    		this.transactions = transactionDAO.getTransactions();
    		
    	}catch (Exception e) {
			
		}
    	
        initialize();
        updateTransactions();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        

        JPanel panelProfile = new JPanel();
        panelProfile.setBorder(null);
        panelProfile.setBackground(new Color(0, 0, 0));
        panelProfile.setBounds(0, 0, 945, 661);
        frame.getContentPane().add(panelProfile);
        panelProfile.setLayout(null);
        
        RoundedButton buttonHistApostas = new RoundedButton("Histórico de Apostas");
        buttonHistApostas.setText("Histórico de Apostas");
        buttonHistApostas.setBounds(737, 568, 179, 59);
        buttonHistApostas.setBackground(new Color(64, 128, 128)); // Example color
        buttonHistApostas.setForeground(Color.WHITE);
        panelProfile.add(buttonHistApostas);
        
//------------------------- Deposit button -------------------------------------
        
        RoundedButton buttonDepositar = new RoundedButton("Depositar");
        buttonDepositar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JDialog dialogDeposito = new JDialog(frame, "Depósito", true);
    	        dialogDeposito.setSize(400, 300);
    	        dialogDeposito.getContentPane().setLayout(null);
    	        
    	        JLabel lblInformeSaque = new JLabel("Informe quanto deseja depositar");
    	        lblInformeSaque.setBounds(20, 20, 200, 20);
    	        dialogDeposito.getContentPane().add(lblInformeSaque);
    	        
    	        JLabel lblReal = new JLabel("R$");
    	        lblReal.setBounds(20, 60, 30, 20);
    	        dialogDeposito.getContentPane().add(lblReal);
    	        
    	        JSpinner spinnerValorDeposito = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1000.00, 1.00)); // Depois mudar o valor máx permitido
    	        spinnerValorDeposito.setBounds(50, 60, 100, 25);
    	        dialogDeposito.getContentPane().add(spinnerValorDeposito);
    	        
    	        RoundedButton btnConfirmarDeposito = new RoundedButton("Confirmar Depósito");
    	        btnConfirmarDeposito.setBounds(20, 200, 150, 30);
    	        btnConfirmarDeposito.setBackground(new Color(64, 128, 128));
    	        btnConfirmarDeposito.setForeground(Color.WHITE);
    	        btnConfirmarDeposito.addActionListener(new ActionListener() {
    	        	public void actionPerformed(ActionEvent e) {
    	        		
//    	        		Transaction deposito = new Transaction("Deposito", (Double)spinnerValorDeposito.getValue());
//    	        		transactions.add(deposito);
//    	        		updateTransactions();
//    	        				
    	        		JPanel panelSaque = new JPanel();
    	        		panelSaque.setLayout(null);
    	        		panelSaque.setMinimumSize(new Dimension(240, 100));
    	        		panelSaque.setMaximumSize(new Dimension(240,100));
    	        		panelSaque.setBackground(new Color(128, 128, 128));
                        panelSaque.addMouseListener(new MouseAdapter() {

                			@Override
                			public void mouseEntered(MouseEvent e) {
                				// TODO Auto-generated method stub
                				panelSaque.setBackground(new Color(192,192,192));
                			}

                			@Override
                			public void mouseExited(MouseEvent e) {
                				// TODO Auto-generated method stub
                				panelSaque.setBackground(new Color(128, 128, 128));
                			}
                		
                        	
                        });
    	        		
    	        		JLabel lblPanelSaque = new JLabel("Depósito R$ " + (Double)spinnerValorDeposito.getValue());
    	        		lblPanelSaque.setBounds(10, 20, 200, 20);
    	        		panelSaque.add(lblPanelSaque);
    	        		
    	        		panelTransaction.add(panelSaque);
    	        		panelTransaction.revalidate();
    	        		panelTransaction.repaint();
    	        		
    	        		
    	        		dialogDeposito.dispose();
    	        	}
    	        });
    	        
    	        dialogDeposito.getContentPane().add(btnConfirmarDeposito);
    	        
    	        dialogDeposito.setLocationRelativeTo(frame); // Centraliza em relação à janela principal
    	        dialogDeposito.setVisible(true); // Exibe o pop-up
        	}
        });
        buttonDepositar.setBounds(737, 210, 179, 59);
        buttonDepositar.setBackground(new Color(64, 128, 128)); // Example color
        buttonDepositar.setForeground(Color.WHITE);
        panelProfile.add(buttonDepositar);
        
//--------------------- Withdrawal button -----------------------------        
        
        RoundedButton buttonSacar = new RoundedButton("Sacar");       
        buttonSacar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JDialog dialogSaque = new JDialog(frame, "Saque", true);
    	        dialogSaque.setSize(400, 300);
    	        dialogSaque.getContentPane().setLayout(null);
    	        
    	        JLabel lblInformeSaque = new JLabel("Informe quanto deseja sacar");
    	        lblInformeSaque.setBounds(20, 20, 200, 20);
    	        dialogSaque.getContentPane().add(lblInformeSaque);
    	        
    	        JLabel lblReal = new JLabel("R$");
    	        lblReal.setBounds(20, 60, 30, 20);
    	        dialogSaque.getContentPane().add(lblReal);
    	        
    	        JSpinner spinnerValorSaque = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 1.0)); // O valor max está 100, mas deve ser mudado depois para o valor do saldo
    	        spinnerValorSaque.setBounds(50, 60, 100, 25);
    	        dialogSaque.getContentPane().add(spinnerValorSaque);
    	        
    	        RoundedButton btnConfirmarSaque = new RoundedButton("Confirmar Saque");
    	        btnConfirmarSaque.addActionListener(new ActionListener() {
    	        	public void actionPerformed(ActionEvent e) {
    	        		JPanel panelSaque = new JPanel();
    	        		panelSaque.setLayout(null);
    	        		panelSaque.setMinimumSize(new Dimension(240, 100));
    	        		panelSaque.setMaximumSize(new Dimension(240,100));
    	        		panelSaque.setBackground(new Color(128, 128, 128));
                        panelSaque.addMouseListener(new MouseAdapter() {

                			@Override
                			public void mouseEntered(MouseEvent e) {
                				// TODO Auto-generated method stub
                				panelSaque.setBackground(new Color(192,192,192));
                			}

                			@Override
                			public void mouseExited(MouseEvent e) {
                				// TODO Auto-generated method stub
                				panelSaque.setBackground(new Color(128, 128, 128));
                			}
                		
                        	
                        });
    	        		
    	        		JLabel lblPanelSaque = new JLabel("Saque R$ " + spinnerValorSaque.getValue());
    	        		lblPanelSaque.setBounds(10, 20, 200, 20);
    	        		panelSaque.add(lblPanelSaque);
    	        		
    	        		panelTransaction.add(panelSaque);
    	        		panelTransaction.revalidate();
    	        		panelTransaction.repaint();
    	        		
    	        		
    	        		dialogSaque.dispose();
    	        	}
    	        });
    	        btnConfirmarSaque.setBounds(20, 200, 150, 30);
    	        btnConfirmarSaque.setBackground(new Color(64, 128, 128));
    	        btnConfirmarSaque.setForeground(Color.WHITE); 
    	        dialogSaque.getContentPane().add(btnConfirmarSaque);
    	        
    	        dialogSaque.setLocationRelativeTo(frame); // Centraliza em relação à janela principal
    	        dialogSaque.setVisible(true); // Exibe o pop-up
        	}
        });
        buttonSacar.setBounds(737, 140, 179, 59);
        buttonSacar.setBackground(new Color(64, 128, 128)); // Example color
        buttonSacar.setForeground(Color.WHITE);       
        panelProfile.add(buttonSacar);
        
// --------------------- Return button ----------------------------------------------        
        
        ImageUtils backButton = new ImageUtils();
        backButton.setBorder(null);
        backButton.setBorderSize(0);
        backButton.setImage(new ImageIcon(getClass().getResource("/resources/images/back-arrow.jpg")));
        backButton.setBounds(5, 5, 50, 50);
//        backButton.addMouseListener(new MouseAdapter() {
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		frame.dispose();
//        		new HomeUserUI(user);
//        	}
//        	
//        });
        panelProfile.add(backButton);
        
// -------------------- Profile Image and Edit Button ----------------------------------------------        
        
        ImageUtils profilePicture = new ImageUtils();
        profilePicture.setBorderSize(0);
        
        profilePicture.setBorder(null);;
        profilePicture.setImage(new ImageIcon(getClass().getResource("/resources/images/Profile-Icon.jpg"))); // NOI18N
        profilePicture.setBounds(52, 68, 147, 147);
        panelProfile.add(profilePicture);
        
        ImageUtils editButton = new ImageUtils();
        editButton.setBorder(null);
        editButton.setBorderSize(0);
        editButton.setImage(new ImageIcon(getClass().getResource("/resources/images/edit-pencil.jpg")));
        editButton.setBounds(211, 270, 40, 40);
        panelProfile.add(editButton);
      
//--------------- Log Out ---------------------------------------------
        
        RoundedButton logOut = new RoundedButton("LogOut");
        logOut.setBounds(785, 10, 120, 30);
        logOut.setBackground(new Color(64, 128, 128));
        logOut.setForeground(Color.WHITE); 
        logOut.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		new LogInView();
        	}
        	
        });
        
        panelProfile.add(logOut);
        
// ------------------- Profile Info --------------------------------------------------------------
        
        if(this.user instanceof CommonUser) {
        	 JLabel lblNewLabel_3 = new JLabel(String.format("Saldo: R$ %.2f ", ((CommonUser) user).getBalance()));
             lblNewLabel_3.setForeground(new Color(255, 255, 255));
             lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
             lblNewLabel_3.setBounds(764, 62, 108, 37);
             panelProfile.add(lblNewLabel_3);
        }       
        
        JLabel lblNewLabel_9 = new JLabel(user.getName());
        lblNewLabel_9.setForeground(new Color(255, 255, 255));
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblNewLabel_9.setBounds(39, 275, 187, 31);
        panelProfile.add(lblNewLabel_9);
        
        JLabel lblNewLabel_4 = new JLabel(user.getEmail());
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_4.setForeground(new Color(255, 255, 255));
        lblNewLabel_4.setBounds(39, 343, 179, 14);
        panelProfile.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("CPF");
        lblNewLabel_5.setForeground(new Color(255, 255, 255));
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_5.setBounds(39, 382, 46, 14);
        panelProfile.add(lblNewLabel_5);
        
        JLabel lblNewLabel_7 = new JLabel(user.getCpf());
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_7.setForeground(new Color(255, 255, 255));
        lblNewLabel_7.setBounds(39, 407, 138, 14);
        panelProfile.add(lblNewLabel_7);
        
        JLabel lblNewLabel_6 = new JLabel("Endereço");
        lblNewLabel_6.setForeground(new Color(255, 255, 255));
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_6.setBounds(39, 453, 62, 14);
        panelProfile.add(lblNewLabel_6);
        
        if(this.user instanceof CommonUser) {
        	JLabel lblNewLabel_8 = new JLabel(((CommonUser) user).getAddress());
        	lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
        	lblNewLabel_8.setForeground(new Color(255, 255, 255));
        	lblNewLabel_8.setBounds(39, 478, 138, 14);
        	panelProfile.add(lblNewLabel_8);
        }
        
        
   //------------------ Transaction history --------------------------------     

        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(945, 66, 240, 595);
        scrollPane.addMouseWheelListener(new MouseWheelListener() {
  		@Override
  		public void mouseWheelMoved(MouseWheelEvent e) {
  			// TODO Auto-generated method stub
  			JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
  			int unitsToScroll = e.getWheelRotation() * 20;
  			verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
  		}
     	  
        });

        frame.getContentPane().add(scrollPane);
        panelTransaction.setBorder(null);

        panelTransaction.setBackground(new Color(128, 128, 128));
        panelTransaction.setPreferredSize(new Dimension(240, 1000));
 
        scrollPane.setViewportView(panelTransaction);

        panelTransaction.setLayout(new BoxLayout(panelTransaction, BoxLayout.Y_AXIS));
        
        JPanel panelTransactionTxt = new JPanel();
        panelTransactionTxt.setBorder(null);
        panelTransactionTxt.setBackground(new Color(128, 128, 128));
        panelTransactionTxt.setBounds(945, 0, 239, 66);
        frame.getContentPane().add(panelTransactionTxt);
        panelTransactionTxt.setLayout(null);
        
        JLabel lblTransaction = new JLabel("Movimentação");
        lblTransaction.setHorizontalAlignment(SwingConstants.CENTER);
        lblTransaction.setForeground(new Color(255, 255, 255));
        lblTransaction.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTransaction.setBounds(0, 22, 244, 33);
        panelTransactionTxt.add(lblTransaction);
                
        
    }
    
    public void updateTransactions() {
        	//panelTransaction.removeAll();
        	
        	for(Transaction transaction : transactions) {
        		
        		TransactionComponent transactionComponent = new TransactionComponent(transaction);
        		
        		//transactionComponents.add(transactionComponent);
        	
        		panelTransaction.add(transactionComponent);        		
        		
        	}
        	
        	panelTransaction.revalidate();
    		panelTransaction.repaint();
        	
        }
    
}


