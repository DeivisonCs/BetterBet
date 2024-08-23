package app.profile;

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
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;

import javax.swing.SwingConstants;

import app.ImageUtils;
import app.RoundedButton;
import app.TransactionComponent;
import app.auth.LogInView;
import app.edit.EditUser;
import app.homeUser.HomeUserUI;
import dao.transaction.TransactionDAO;
import dao.transaction.TransactionPostgresDAO;
import database.InitDatabase;
import models.CommonUser;
import models.Transaction;
import models.User;
import service.users.UserService;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class WindowProfile {
	private int positionX;
	private int positionY;
	
    private JFrame frame;
    private final JPanel panelTransaction = new JPanel();
    
    private ImageIcon profileImg;
    
    private List<Transaction> transactions = new ArrayList<Transaction>();
    //private List<TransactionComponent> transactionComponents = new ArrayList<TransactionComponent>();
    
    private User user;
    private UserService userService = new UserService();
    
    private TransactionDAO transactionDAO = new TransactionPostgresDAO();
    
    private RoundedButton logOut;
    private JScrollPane scrollPane;
    private JPanel panelProfile;
    
    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//            	InitDatabase.initializeDatabase();
//                WindowProfile window = new WindowProfile(1);
//                window.frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    /**
     * Create the application.
     */
    public WindowProfile(Integer userId, int positionX, int positionY) {
    	this.positionX = positionX;
    	this.positionY = positionY;
    	
    	try {
    		this.user = userService.getUser(userId);    		
    		this.transactions = transactionDAO.getTransactions();
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
        initialize();
        updateTransactions();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(positionX, positionY, 1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        

        panelProfile = new JPanel();
        panelProfile.setBorder(null);
        panelProfile.setBackground(new Color(40, 40, 40));
        panelProfile.setBounds(0, 0, 945, 661);
        frame.getContentPane().add(panelProfile);
        panelProfile.setLayout(null);
        
        
        if(!user.getPermission().equals("admin")) {
        	
	       
        }
        
// --------------------- Return button ----------------------------------------------        
        
        ImageUtils backButton = new ImageUtils();
        backButton.setBorder(null);
        backButton.setBorderSize(0);
        backButton.setImage(new ImageIcon(getClass().getResource("/resources/images/back-arrow.jpg")));
        backButton.setBounds(5, 5, 30, 30);
        backButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;
        		frame.dispose();
        		
        		new HomeUserUI(user.getId(), x, y);
        	}
        	
        });
        panelProfile.add(backButton);
        
// -------------------- Profile Image and Edit Button ----------------------------------------------   
        profileImg = 
        		user.getProfileImage() != null?
        		user.getProfileImage() : 
    			new ImageIcon(getClass().getResource("/resources/images/Profile-Icon.jpg"));
        
        ImageUtils profilePicture = new ImageUtils();
        profilePicture.setBorderSize(0);
        
        profilePicture.setBorder(null);
        profilePicture.setImage(profileImg); // NOI18N
        profilePicture.setBounds(52, 68, 147, 147);
        panelProfile.add(profilePicture);
        
        ImageUtils editButton = new ImageUtils();
        editButton.setBorder(null);
        editButton.setBorderSize(0);
        editButton.setImage(new ImageIcon(getClass().getResource("/resources/images/edit-pencil.jpg")));
        
      // int distanciaDinamica = lblNome.getWidth() + 10;
        
        editButton.setBounds(52, 190, 35, 35);
        editButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;
				frame.dispose();
				
				new EditUser(user.getId(), x, y);
			}
			
		});
        panelProfile.add(editButton);
      
//--------------- Log Out ---------------------------------------------
        
        logOut = new RoundedButton("LogOut");
        logOut.setBounds(785, 10, 120, 30);
        logOut.setBackground(new Color(64, 128, 128));
        logOut.setForeground(Color.WHITE); 
        logOut.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;
        		frame.dispose();
        		
        		new LogInView(x, y);
        	}
        	
        });
        
        panelProfile.add(logOut);
        
// ------------------- Profile Info --------------------------------------------------------------
         
        JLabel nameField = new JLabel(user.getName());
        nameField.setForeground(new Color(156, 156, 156));
        nameField.setFont(new Font("Tahoma", Font.PLAIN, 25));
        nameField.setBounds(39, 249, 294, 31);
        panelProfile.add(nameField);
          
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(new Color(156, 156, 156));
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        emailLabel.setBounds(39, 306, 107, 14);
        panelProfile.add(emailLabel);
        
        JLabel emailField = new JLabel(user.getEmail());
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        emailField.setForeground(new Color(255, 255, 255));
        emailField.setBounds(49, 331, 267, 14);
        panelProfile.add(emailField);
        
        JLabel cpfLabel = new JLabel("CPF");
        cpfLabel.setForeground(new Color(156, 156, 156));
        cpfLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cpfLabel.setBounds(39, 370, 46, 14);
        panelProfile.add(cpfLabel);
        
        JLabel cpfField = new JLabel(user.getCpf());
        cpfField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cpfField.setForeground(new Color(255, 255, 255));
        cpfField.setBounds(49, 394, 197, 14);
        panelProfile.add(cpfField);
        

        // Campo de endereço aparece apenas se for usuário comun (adição no final)

        
        if(user.getPermission().equals("admin")) {
        	placeAdmContents();       	
        }
        if(user.getPermission().equals("user")) {
        	placeUserContents();    	
        }
    }
    
    public void updateTransactions() {
        	panelTransaction.removeAll();
        	
        	for(Transaction transaction : transactions) {
        		
        		TransactionComponent transactionComponent = new TransactionComponent(transaction);
        		
        		//transactionComponents.add(transactionComponent);
        	
        		panelTransaction.add(transactionComponent);        		
        		
        	}
        	
        	panelTransaction.revalidate();
    		panelTransaction.repaint();
        	
        }
    
    
    // Mostra conteudos específicos para usuários adm
    public void placeAdmContents() {
    	logOut.setBounds(1050, 10, 120, 30);
		panelProfile.setBounds(0, 0, 1185, 661); 
		
		// ---------------- Create Event Button ----------------
		RoundedButton buttonCreateEvent = new RoundedButton("Criar Evento");
		buttonCreateEvent.setBounds(990, 140, 179, 59);
		buttonCreateEvent.setBackground(new Color(64, 128, 128)); 
		buttonCreateEvent.setForeground(Color.WHITE);
		
		buttonCreateEvent.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new CreateEvent(user.getId());
			}
		});
        panelProfile.add(buttonCreateEvent);
        
        
     // ---------------- Add Match Button ----------------
     		RoundedButton buttonAddMatch = new RoundedButton("Criar Partida");
     		buttonAddMatch.setBounds(990, 210, 179, 59);
     		buttonAddMatch.setBackground(new Color(64, 128, 128)); 
     		buttonAddMatch.setForeground(Color.WHITE);
     		
     		buttonAddMatch.addMouseListener(new MouseAdapter() {
     			
     			@Override
     			public void mouseClicked(MouseEvent e) {
     				new AddMatch(user.getId());
     			}
     		});
            panelProfile.add(buttonAddMatch);
        
        
     // ---------------- Close Event Button ----------------
        RoundedButton buttonCloseEvent = new RoundedButton("Finalizar Partida");
        buttonCloseEvent.setBounds(990, 280, 179, 59);
        buttonCloseEvent.setBackground(new Color(64, 128, 128)); 
        buttonCloseEvent.setForeground(Color.WHITE);      
        buttonCloseEvent.addMouseListener(new MouseAdapter() {
 			
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				new FinishMatch(user.getId());
 			}
 		});
        panelProfile.add(buttonCloseEvent);

    
        // ---------------- Create Admin User Button ----------------
        RoundedButton addAdminEvent = new RoundedButton("Criar Admin");
        addAdminEvent.setBounds(990, 585, 179, 59);
        addAdminEvent.setBackground(new Color(64, 128, 128)); 
        addAdminEvent.setForeground(Color.WHITE);      
        addAdminEvent.addMouseListener(new MouseAdapter() {
 			
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;

 				
 				new CreateAdminUser(user.getId(), x, y);
 			}
 		});
        panelProfile.add(addAdminEvent);
        
    }
    
 // Mostra conteudos específicos para usuários comuns
	public void placeUserContents() {
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
	
	    		Transaction deposito = new Transaction(user.getId(), "Deposito", (Double)spinnerValorDeposito.getValue());
        		TransactionComponent depositoComponent = new TransactionComponent(deposito);
        		
        		try {
					transactionDAO.insertTransaction(deposito);
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
        		
        		panelTransaction.add(depositoComponent);
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
     	        
     	        JSpinner spinnerValorSaque = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1000.00, 1.00)); // O valor max está 100, mas deve ser mudado depois para o valor do saldo
     	        spinnerValorSaque.setBounds(50, 60, 100, 25);
     	        dialogSaque.getContentPane().add(spinnerValorSaque);
     	        
     	        RoundedButton btnConfirmarSaque = new RoundedButton("Confirmar Saque");
     	        btnConfirmarSaque.addActionListener(new ActionListener() {
     	        	public void actionPerformed(ActionEvent e) {
     	        		
     	        		Transaction saque = new Transaction(user.getId(), "Saque", (Double)spinnerValorSaque.getValue());
    	        		TransactionComponent saqueComponent = new TransactionComponent(saque); 
    	        		
    	        		try {
							transactionDAO.insertTransaction(saque);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
    	        		
    	        		panelTransaction.add(saqueComponent);
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
    	
		logOut.setBounds(785, 10, 120, 30);
		
		JLabel balanceField = new JLabel("Saldo");
	  	balanceField.setForeground(new Color(156, 156, 156));
	  	balanceField.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    balanceField.setBounds(39, 486, 108, 37);
        panelProfile.add(balanceField);
   
	   	balanceField = new JLabel(String.format("R$ %.2f", ((CommonUser) user).getBalance()));
	   	balanceField.setForeground(new Color(255, 255, 255));
	   	balanceField.setFont(new Font("Tahoma", Font.PLAIN, 14));
	   	balanceField.setBounds(49, 511, 108, 37);
        panelProfile.add(balanceField);
		
		JLabel addressLabel = new JLabel("Endereço");
        addressLabel.setForeground(new Color(156, 156, 156));
        addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        addressLabel.setBounds(39, 436, 138, 14);
        panelProfile.add(addressLabel);
        
        JLabel addressField = new JLabel("vdvvv");
        addressField.setForeground(Color.WHITE);
        addressField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        addressField.setBounds(52, 461, 267, 14);
        panelProfile.add(addressField);
        
        
        //------------------ Transaction history --------------------------------
        
        scrollPane = new JScrollPane();
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

        panelTransaction.setBackground(new Color(30, 30, 30));
        panelTransaction.setPreferredSize(new Dimension(240, 1000));
 
        scrollPane.setViewportView(panelTransaction);

        panelTransaction.setLayout(new BoxLayout(panelTransaction, BoxLayout.Y_AXIS));
        
        JPanel panelTransactionTxt = new JPanel();
        panelTransactionTxt.setBorder(null);
        panelTransactionTxt.setBackground(new Color(30, 30, 30));
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
}
