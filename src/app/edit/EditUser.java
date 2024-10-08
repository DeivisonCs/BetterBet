package app.edit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.profile.WindowProfile;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import components.ImageUtils;
import components.RoundedButtonComponent;
import components.RoundedPasswordFieldComponent;
import components.RoundedTextFieldComponent;
import exceptions.InvalidAddressException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPasswordException;
import models.AdminUser;
import models.CommonUser;
import models.User;
import service.users.UserService;


public class EditUser{
	private int positionX;
	private int positionY;
	
	private JFrame frame;
	private User user;
	private UserService userService = new UserService();
	private File selectedImgFile = null;
	
	private ImageIcon profile_img;
	private ImageUtils profilePicture;
	
	private JLabel namePlaceholder;
	private RoundedTextFieldComponent nameField;
	
	private JLabel emailPlaceholder;
	private RoundedTextFieldComponent emailField;
	
	private JLabel addressPlaceholder;
	private RoundedTextFieldComponent addressField;

	private JLabel passwordPlaceholder;
	private RoundedPasswordFieldComponent passwordField;
	
	private JLabel confirmPasswordPlaceholder;
	private RoundedPasswordFieldComponent confirmPasswordField;
	
	/**
	* Interface de edição de usuário.
	* Os campos são verificados novamente para garantir a integridade dos dados.
	* Após validação dos dados o usuário é redirecionado para a tela de perfil(src/app/profile/WindowProfile.java)
	* 
	* @param userId Id do usuário para recuperação e atualização dos dados
	* @param positionX Posição X da tela anterior
	* @param positionY Posição Y da tela anterior
	*/
	public EditUser(Integer userId, int positionX, int positionY) {
		this.positionX = positionX;
    	this.positionY = positionY;
    	
		try {
			User loggedUser = userService.getUser(userId);
			
			this.user = loggedUser.getPermission().equals("user")? (CommonUser) loggedUser : (AdminUser) loggedUser;

		} catch (SQLException e) {
			e.printStackTrace();
			frame.dispose();
		} catch (IOException e) {
			e.printStackTrace();
			frame.dispose();
		}
		
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.setBounds(positionX, positionY, 800, 750);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		// ------------------------- Page Text -------------------------
		JLabel PageTitle = new JLabel("Atualizar Informações");
		PageTitle.setForeground(new Color(255, 215, 0));
		PageTitle.setFont(new Font("Tahoma", Font.PLAIN, 33));
		PageTitle.setBounds(216, 63, 319, 31);
		frame.getContentPane().add(PageTitle);
		
		
		// ------------------------- ToolTip -------------------------
		ImageUtils informationIcon = new ImageUtils();
        informationIcon.setForeground(new Color(255, 255, 255));
        informationIcon.setBorderColor(new Color(255, 255, 255));
        informationIcon.setBorderSize(0);
        
        informationIcon.setBorder(null);
        informationIcon.setImage(new ImageIcon(getClass().getResource("/resources/images/help-icon.png")));
        informationIcon.setBounds(538, 74, 20, 20);
        informationIcon.setToolTipText("Edite apenas os campos que deseja alterar");
        
        frame.getContentPane().add(informationIcon);
		
		
		// ------------------------- Return Button -------------------------        
        /**
    	* Retorna para a tela de perfil do usuário (src/app/profile/WindowProfile.java). 
    	* A localização da tela é passada como parâmetro para suavização da troca de tela.
    	*/
        ImageUtils returnButton = new ImageUtils();
        returnButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Point location = frame.getLocationOnScreen();
				int x = location.x;
				int y = location.y;
        		frame.dispose();
        		
        		new WindowProfile(user.getId(), x, y);
        	}
        });
        returnButton.setBorderSize(0);
        
        returnButton.setBorder(null);
        returnButton.setImage(new ImageIcon(getClass().getResource("/resources/images/back-arrow.jpg")));
        returnButton.setBounds(24, 24, 30, 30);
        frame.getContentPane().add(returnButton);
		
		
		// ------------------------- Profile Img -------------------------        
        profile_img = 
        		user.getProfileImage() != null?
        		user.getProfileImage() : 
    			new ImageIcon(getClass().getResource("/resources/images/Profile-Icon.jpg"));
             
        profilePicture = new ImageUtils();
        profilePicture.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		editImage();
        	}
        });
        profilePicture.setBorderSize(0);
        
        profilePicture.setBorder(null);
        profilePicture.setImage(profile_img);
        profilePicture.setBounds(138, 159, 147, 147);
        frame.getContentPane().add(profilePicture);
        
        
        ImageUtils editButton = new ImageUtils();
        editButton.setBorder(null);
        editButton.setBorderSize(0);
        editButton.setImage(new ImageIcon(getClass().getResource("/resources/images/edit-pencil.jpg")));
        editButton.setBounds(138, 281, 35, 35);
        editButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editImage();
			}	
		});
        frame.add(editButton);
		
		
		// ------------------------- Name Field -------------------------
		namePlaceholder = new JLabel("Nome");
		namePlaceholder.setForeground(new Color(156, 156, 156));
		namePlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		namePlaceholder.setBounds(312, 179, 319, 14);
		frame.getContentPane().add(namePlaceholder);
		
		
		nameField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		namePlaceholder.setLabelFor(nameField);
		nameField.setBounds(312, 204, 319, 31);
		nameField.setText(user.getName());
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		
		// ------------------------- Email Field -------------------------
		emailPlaceholder = new JLabel("Email");
		emailPlaceholder.setForeground(new Color(156, 156, 156));
		emailPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		emailPlaceholder.setBounds(312, 250, 319, 14);
		frame.getContentPane().add(emailPlaceholder);
		
		emailField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
		emailPlaceholder.setLabelFor(emailField);
		emailField.setColumns(10);
		emailField.setBounds(312, 275, 319, 31);
		emailField.setText(user.getEmail());
		frame.getContentPane().add(emailField);
		
		// ------------------------- Address Field -------------------------
		if(user instanceof CommonUser) {
			addressPlaceholder = new JLabel("Endereço");
			addressPlaceholder.setForeground(new Color(156, 156, 156));
			addressPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
			addressPlaceholder.setBounds(163, 333, 468, 14);
			frame.getContentPane().add(addressPlaceholder);

			addressField = new RoundedTextFieldComponent(20, 20, 20, 10, 10);
			addressPlaceholder.setLabelFor(addressField);
			addressField.setColumns(10);
			addressField.setBounds(163, 358, 468, 31);
			addressField.setText(((CommonUser)user).getAddress());
			frame.getContentPane().add(addressField);
		}

		
		
		// ------------------------- Password Field -------------------------
		passwordPlaceholder = new JLabel("Senha");
		passwordPlaceholder.setForeground(new Color(156, 156, 156));
		passwordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordPlaceholder.setBounds(163, 400, 468, 14);
		frame.getContentPane().add(passwordPlaceholder);
		
		passwordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
		passwordPlaceholder.setLabelFor(passwordField);
		passwordField.setBounds(163, 425, 468, 31);
		frame.getContentPane().add(passwordField);
		
		
		// ------------------------- Confirm Password Field -------------------------
		confirmPasswordPlaceholder = new JLabel("Confirme sua senha");
		confirmPasswordPlaceholder.setForeground(new Color(156, 156, 156));
		confirmPasswordPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
		confirmPasswordPlaceholder.setBounds(166, 467, 465, 14);
		frame.getContentPane().add(confirmPasswordPlaceholder);
		
		confirmPasswordField = new RoundedPasswordFieldComponent(20, 20, 20, 10, 10);
		confirmPasswordPlaceholder.setLabelFor(confirmPasswordField);
		confirmPasswordField.setBounds(166, 492, 465, 31);
		frame.getContentPane().add(confirmPasswordField);
		
		
		// ------------------------- Save Button -------------------------
		/**
		* Ao ser clicado, o button salva as novas informações do usuário,
		* chama o userService para verificar os dados inseridos e fazer a  inserção no banco
		* 
		* @throws SQLException Caso haja algum erro no banco
		* @throws FileNotFoundException Caso haja algum erro relacionado a inserção da imagem de perfil do usuário
		* @throws InvalidNameException Caso o nome digitado pelo usuário seja inválido
		* @throws InvalidEmailException Caso o email digitado pelo usuário seja inválido
		* @throws InvalidAddressException Caso o endereço digitado pelo usuário seja inválido
		* @throws InvalidPasswordException Caso a senha digitada pelo usuário seja inválida
		*/ 
		RoundedButtonComponent button = new RoundedButtonComponent("Salvar", new Color(255, 215, 0), new Color(102, 203, 102));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setFont(new Font("Tahoma", Font.PLAIN, 19));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setFont(new Font("Tahoma", Font.PLAIN, 24));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				String password = passwordField.getPassword().length == 0? null : new String(passwordField.getPassword());
				String confirmPassword = confirmPasswordField.getPassword().length == 0 ? null : new String(confirmPasswordField.getPassword());
				
				user.setName(nameField.getText());
				user.setEmail(emailField.getText());
				user.setPassword(user.getPassword());
				
				if(user.getPermission().equals("user")) {
					((CommonUser)user).setAddress(addressField.getText());
				}
				
				try {
					userService.updateUser(user, password, confirmPassword, selectedImgFile);
					
					JOptionPane.showMessageDialog(null, "Usuário Atualizado!" );
					
					Point location = frame.getLocationOnScreen();
					int x = location.x;
					int y = location.y;
					frame.dispose();
					
					new WindowProfile(user.getId(), x, y);
					
				}
				catch(SQLException | FileNotFoundException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				catch(InvalidNameException | InvalidEmailException | InvalidAddressException | InvalidPasswordException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
			
			
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(301, 596, 179, 59);
        button.setBackground(new Color(102, 203, 102));
        button.setForeground(Color.WHITE);
        frame.getContentPane().add(button);
        
	}
	
	/**
	* Cria a tela de seleção da foto de perfil do usuário, fazendo filtro
	* dos tipos de arquivos válidos (png, jpg, jpeg)
	*/
	private void editImage() {
		final String[] VALID_EXTENSIONS = {"png", "jpg", "jpeg"};
		
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Selecione a imagem", VALID_EXTENSIONS);
		
		fileChooser.setFileFilter(filter);
		
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
        	File selectedFile = fileChooser.getSelectedFile();
        	
        	if(isValidExtension(VALID_EXTENSIONS, selectedFile.getName().toLowerCase())) {
        		profile_img = new ImageIcon(selectedFile.getAbsolutePath());
                
        		selectedImgFile = selectedFile;
                updateProfileImage(profile_img);
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Por favor, selecione um arquivo de imagem válido (JPG, JPEG, PNG).", "Tipo de arquivo inválido", JOptionPane.ERROR_MESSAGE);
        	}       
        }
	}
	
	/**
	* Atualiza a foto mostrada na tela de edição
	*/
	private void updateProfileImage(ImageIcon newImage) {
	    profilePicture.setImage(newImage);
	    profilePicture.repaint();
	}
	
	/**
	* Verifica se o arquivo selecionado está dentro da lista de arquivos válidos (png, jpg, jpeg).
	* A verificação é feita verificando a terminação do nome do arquivo.
	*/
	private boolean isValidExtension(String[] validExtensions, String fileName) {
		for(String extension : validExtensions) {
			if(fileName.endsWith("." + extension)) {
				return true;
			}
		}
		
		return false;
	}
}
