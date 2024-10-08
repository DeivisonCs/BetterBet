package app.profile;

//import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;

import javax.swing.SwingConstants;

import components.ImageUtils;
import components.RoundedButtonComponent;
import components.RoundedTextFieldComponent;
import components.TransactionComponent;

import app.auth.LogInView;
import app.edit.EditUser;
import app.historyView.HistoryView;
import app.homeUser.HomeUserUI;
import dao.transaction.TransactionDAO;
import dao.transaction.TransactionPostgresDAO;
import models.CommonUser;
import models.Transaction;
import models.User;
import service.users.UserService;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;

/**
 * Classe que representa a janela de perfil do usuário.
 * Permite visualizar e editar informações do perfil, realizar transações.
 * Ela é materializada de forma diferente a depender do tipo de usuário(Admin, Comum)
 */
public class WindowProfile {
	private int positionX;
	private int positionY;

    private JFrame frame;
    private final JPanel panelTransaction = new JPanel();
    
    private ImageIcon profileImg;
    
    private List<Transaction> transactions = new ArrayList<Transaction>();
    
    private User user;
    private UserService userService = new UserService();
    
    private float userBalance;
    
    private TransactionDAO transactionDAO = new TransactionPostgresDAO();
    
    private RoundedButtonComponent logOut;
    private JScrollPane scrollPane;
    private JPanel panelProfile;
    private JLabel balanceField;
    
    /**
     * Construtor da classe. Inicializa o perfil do usuário e carrega suas informações e transações.
     * @param userId ID do usuário
     * @param positionX Posição X da janela
     * @param positionY Posição Y da janela
     */
    public WindowProfile(Integer userId, int positionX, int positionY) {
    	this.positionX = positionX;
    	this.positionY = positionY;
    	
    	try {
    		this.user = userService.getUser(userId);    		
    		this.transactions = transactionDAO.getTransactions(userId);
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	if(this.user instanceof CommonUser)
    		this.userBalance = ((CommonUser) this.user).getBalance();
    	
        initialize();
        updateTransactions();
    }

    /**
     * Inicializa o conteúdo da janela.
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
        
        if(this.user instanceof CommonUser) {
	    	balanceField = new JLabel(String.format("R$ %.2f", ((CommonUser) user).getBalance()));
		   	balanceField.setForeground(new Color(255, 255, 255));
		   	balanceField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		   	balanceField.setBounds(49, 511, 108, 37);
	        panelProfile.add(balanceField);
        }
        
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
        
        logOut = new RoundedButtonComponent("LogOut", new Color(50, 50, 50), new Color(150, 150, 150));
        logOut.setBounds(785, 10, 120, 30);
        logOut.setBackground(new Color(150, 150, 150));
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
    
    /**
     * Atualiza a lista de transações na interface.
     */
    public void updateTransactions() {
    	
        	panelTransaction.removeAll();

        	  	
        	GridBagConstraints gbc = new GridBagConstraints();
    	    gbc.gridx = 0;
    	    gbc.gridy = 0;
    	    gbc.anchor = GridBagConstraints.NORTH;  
    	    gbc.insets = new Insets(5, 0, 10, 0);
    	    
    	    for (Transaction transaction : transactions) {

    	    	TransactionComponent transactionComponent = new TransactionComponent(transaction);
    	        panelTransaction.add(transactionComponent, gbc);
    	        gbc.gridy++;
    	    }
        	
    	    
    	    gbc.weighty = 1.0;
    	    JPanel filler = new JPanel();
    	    filler.setBackground(new Color(35, 35, 35));
    	    panelTransaction.add(filler, gbc);

    	    panelTransaction.revalidate();
    	    panelTransaction.repaint();
    	            	
        }
    
    /**
     * Mostra conteudos específicos para usuários adm
     */
    public void placeAdmContents() {
    	logOut.setBounds(1050, 10, 120, 30);
		panelProfile.setBounds(0, 0, 1185, 661); 
		
		// ---------------- Create Event Button ----------------
		RoundedButtonComponent buttonCreateEvent = new RoundedButtonComponent("Criar Evento", new Color(150, 150, 150), new Color(64, 128, 128));
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
    	RoundedButtonComponent buttonAddMatch = new RoundedButtonComponent("Criar Partida", new Color(150, 150, 150), new Color(64, 128, 128));
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
        RoundedButtonComponent buttonCloseEvent = new RoundedButtonComponent("Finalizar Partida", new Color(150, 150, 150), new Color(64, 128, 128));
        buttonCloseEvent.setBackground(new Color(64, 128, 128));
        buttonCloseEvent.setBounds(990, 280, 179, 59); 
        buttonCloseEvent.setForeground(Color.WHITE);      
        buttonCloseEvent.addMouseListener(new MouseAdapter() {
 			
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				new FinishMatch(user.getId());
 			}
 		});
        panelProfile.add(buttonCloseEvent);

    
        // ---------------- Create Admin User Button ----------------
        RoundedButtonComponent addAdminEvent = new RoundedButtonComponent("Criar Admin", new Color(150, 150, 150), new Color(64, 128, 128));
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
    
    /**
	* Mostra conteúdos específicos para a tela do usuário comun
	*/ 
	public void placeUserContents() {
		RoundedButtonComponent buttonHistApostas = new RoundedButtonComponent("Histórico de Apostas", new Color(150, 150, 150), new Color(64, 128, 128));
		buttonHistApostas.setText("Histórico de Apostas");
		buttonHistApostas.setBounds(737, 568, 179, 59);
		buttonHistApostas.setBackground(new Color(64, 128, 128)); // Example color
		buttonHistApostas.setForeground(Color.WHITE);
		buttonHistApostas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;
				
				frame.dispose();
				new HistoryView(user.getId(), x, y);
				
			}
			
		});
		panelProfile.add(buttonHistApostas);        
		
//------------------------- Deposit button -------------------------------------
     
		RoundedButtonComponent buttonDepositar = new RoundedButtonComponent("Depositar", new Color(150, 150, 150), new Color(64, 128, 128));

	    buttonDepositar.addActionListener(new ActionListener() {
     	public void actionPerformed(ActionEvent e) {
     		JDialog dialogDeposito = new JDialog(frame, "Depósito", true);
 	        dialogDeposito.setSize(400, 300);
 	        dialogDeposito.getContentPane().setLayout(null);
 	        dialogDeposito.setBackground(new Color(30, 30, 30));
 	        
 	        JLabel lblInformeDeposito = new JLabel("Informe quanto deseja depositar");
 	        lblInformeDeposito.setBounds(85, 25, 230, 25);
 	        lblInformeDeposito.setFont(new Font("Tahoma", Font.PLAIN, 15));
 	        dialogDeposito.getContentPane().add(lblInformeDeposito);
 	        
 	        JLabel lblReal = new JLabel("R$");
 	        lblReal.setBounds(100, 82, 30, 20);
 	        lblReal.setFont(new Font("Tahoma", Font.PLAIN, 15));
 	        dialogDeposito.getContentPane().add(lblReal);
 	        
 	        RoundedTextFieldComponent textValorDeposito = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
	        textValorDeposito.setBounds(130, 80, 150, 30);
	        dialogDeposito.getContentPane().add(textValorDeposito);	
 	        
 	        RoundedButtonComponent btnConfirmarDeposito = new RoundedButtonComponent("Confirmar Depósito", new Color(150, 150, 150), new Color(64, 128, 128));
 	        btnConfirmarDeposito.setBounds(120, 200, 150, 30);
 	        btnConfirmarDeposito.setBackground(new Color(64, 128, 128));
 	        btnConfirmarDeposito.setForeground(Color.WHITE);
 	        btnConfirmarDeposito.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {

	    		if(!inputValidator(textValorDeposito.getText())) {
	    			
	    			JOptionPane.showMessageDialog(null, "Valor ou entrada inválidos");
    			
	    		}else {
	    		
		    		Double valor = Double.parseDouble(textValorDeposito.getText());
	
		    		Transaction deposito = new Transaction(user.getId(), "Deposito", valor, LocalDateTime.now());
	        		transactions.add(0, deposito);
	        		
	     		
	        		userBalance += valor;
	        		
	        		//-------Atualiza no banco a nova transação 
	        		try {
						transactionDAO.insertTransaction(deposito);
						transactionDAO.updateBalance(user, userBalance);
					} catch (SQLException e1) {
	
						e1.printStackTrace();
					}
	        		
	        		//----- Instancia novamente o usuário com o saldo atualizado
	        		try {
						user = userService.getUser(user.getId());
					} catch (SQLException e1) {
	
						e1.printStackTrace();
					} catch (IOException e1) {
	
						e1.printStackTrace();
					}
	        		
	        		balanceField.setText(String.format("R$ %.2f", ((CommonUser) user).getBalance()));	        		
	        		updateTransactions();
	        		
	        		
	        		dialogDeposito.dispose();
	    		}
	    	}
 	        });
 	        
 	        dialogDeposito.getContentPane().add(btnConfirmarDeposito);
 	        
 	        dialogDeposito.setLocationRelativeTo(frame); // Centraliza em relação à janela principal
 	        dialogDeposito.setVisible(true); // Exibe o pop-up
         	}
         });
         buttonDepositar.setBounds(737, 140, 179, 59);
         buttonDepositar.setBackground(new Color(64, 128, 128)); // Example color
         buttonDepositar.setForeground(Color.WHITE);
         panelProfile.add(buttonDepositar);
     
//--------------------- Withdrawal button -----------------------------        
     
         RoundedButtonComponent buttonSacar = new RoundedButtonComponent("Sacar", new Color(150, 150, 150), new Color(255, 58, 58));
         
         buttonSacar.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
     		JDialog dialogSaque = new JDialog(frame, "Saque", true);
 	        dialogSaque.setSize(400, 300);
 	        dialogSaque.getContentPane().setLayout(null);
 	        
 	        JLabel lblInformeSaque = new JLabel("Informe quanto deseja sacar");
 	        lblInformeSaque.setBounds(95, 25, 230, 25);
 	        lblInformeSaque.setFont(new Font("Tahoma", Font.PLAIN, 15));
 	        dialogSaque.getContentPane().add(lblInformeSaque);
 	        
 	        JLabel lblReal = new JLabel("R$");
	        lblReal.setBounds(100, 82, 30, 20);
	        lblReal.setFont(new Font("Tahoma", Font.PLAIN, 15));
 	        dialogSaque.getContentPane().add(lblReal);
 	        
 	        RoundedTextFieldComponent textValorSaque = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
 	        textValorSaque.setBounds(130, 80, 150, 30);
 	        dialogSaque.getContentPane().add(textValorSaque);	
 	        
     	    RoundedButtonComponent btnConfirmarSaque = new RoundedButtonComponent("Confirmar Saque");
     	        btnConfirmarSaque.addActionListener(new ActionListener() {
     	        	public void actionPerformed(ActionEvent e) {
     	        		
     	        		if(!inputValidator(textValorSaque.getText())) {
     		    			JOptionPane.showMessageDialog(null, "Valor ou entrada inválidos");
     		    		}else {
     	        		
	     	        		Double valor = Double.parseDouble(textValorSaque.getText());
	     	        		
	     		    		if(valor > userBalance) {
	     		    			JOptionPane.showMessageDialog(null, "Valor superior ao saldo disponível!");

	     		    		}else {

		     	        		Transaction saque = new Transaction(user.getId(), "Saque", valor, LocalDateTime.now());
		     	        		transactions.add(0, saque);
		     	        		
		    	        		userBalance -= valor;
		    	        		
		    	        		//-------Atualiza no banco a nova transação 
		    	        		try {
									transactionDAO.insertTransaction(saque);
									transactionDAO.updateBalance(user, userBalance);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
		    	        		
		    	        		//----- Instancia novamente o usuário com o saldo atualizado
		    	        		try {
		    						user = userService.getUser(user.getId());
		    					} catch (SQLException e1) {
	
		    						e1.printStackTrace();
		    					} catch (IOException e1) {
	
		    						e1.printStackTrace();
		    					}
		    	        		
		    	        		
		    	        		balanceField.setText(String.format("R$ %.2f", ((CommonUser) user).getBalance()));		    	        		
		    	        		updateTransactions();


		     	        		dialogSaque.dispose();
	     		    		}
	     	        	}

     	        	}
     	        });
     	        
     	        btnConfirmarSaque.setBounds(120, 200, 150, 30);
     	        btnConfirmarSaque.setBackground(new Color(64, 128, 128));
     	        btnConfirmarSaque.setForeground(Color.WHITE); 
     	        dialogSaque.getContentPane().add(btnConfirmarSaque);
     	        
     	        dialogSaque.setLocationRelativeTo(frame); 
     	        dialogSaque.setVisible(true); 
         	}

         });
         
         buttonSacar.setBounds(737, 210, 179, 59);
         buttonSacar.setBackground(new Color(255, 58, 58)); // Example color

         buttonSacar.setForeground(Color.WHITE);       
         panelProfile.add(buttonSacar);
    	
		logOut.setBounds(785, 10, 120, 30);
		
		JLabel balanceLabel = new JLabel("Saldo");
	  	balanceLabel.setForeground(new Color(156, 156, 156));
	  	balanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    balanceLabel.setBounds(39, 486, 108, 37);
        panelProfile.add(balanceLabel);
		
		JLabel addressLabel = new JLabel("Endereço");
        addressLabel.setForeground(new Color(156, 156, 156));
        addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        addressLabel.setBounds(39, 436, 138, 14);
        panelProfile.add(addressLabel);
        
        JLabel addressField = new JLabel(((CommonUser)user).getAddress());
        addressField.setForeground(Color.WHITE);
        addressField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        addressField.setBounds(52, 461, 267, 14);
        panelProfile.add(addressField);
        
        
        //------------------ Transaction history --------------------------------
        
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(945, 66, 240, 595);
        scrollPane.setBorder(null);
        scrollPane.addMouseWheelListener(new MouseWheelListener() {
	  		@Override
	  		public void mouseWheelMoved(MouseWheelEvent e) {

	  			JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	  			int unitsToScroll = e.getWheelRotation() * 20;
	  			verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
	  		}
        });

        frame.getContentPane().add(scrollPane);
        panelTransaction.setBorder(null);

        panelTransaction.setBackground(new Color(35, 35, 35));
        panelTransaction.setLayout(new GridBagLayout());
 
        scrollPane.setViewportView(panelTransaction);

        
        JPanel panelTransactionTxt = new JPanel();
        panelTransactionTxt.setBorder(null);
        panelTransactionTxt.setBackground(new Color(35, 35, 35));
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
	
	/**
	 * Valida a entrada de texto para garantir que seja um número decimal positivo.
	 * 
	 * @param text O texto a ser validado.
	 * @return true se o texto for um número decimal positivo válido, caso contrário, false.
	 */
	public boolean inputValidator(String text) {
		if (text.isEmpty()) {

	        return false;
	    }

	    // Verifica se o texto contém caracteres inválidos
	    for (char c : text.toCharArray()) {
	        if (!Character.isDigit(c) && c != '.') {
	            return false;
	        }
	    }
	    	    

	    // Verifica se há mais de um separador decimal
	    int commaCount = 0;
	    for (int i = 0; i < text.length(); i++) {
	        if (text.charAt(i) == '.') {
	            commaCount++;
	            
	            // Se houver mais de um separador decimal, retorna falso
	            if (commaCount > 1) {
	                return false;
	            }
	            // Verifica se o separador decimal está no início ou no final
	            if (i == 0 || i == text.length() - 1) {
	                return false;
	            }
	        }
	    }
	    
	    Double zeroVerifier = Double.parseDouble(text);
	    
	    if (zeroVerifier <= 0.0) {
	    	return false;
	    }

	    return true;
		
	}

}


