package service.users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dao.users.UserDAO;
import dao.users.UserPostgresDAO;
import exceptions.InvalidAddressException;
import exceptions.InvalidBirthDateException;
import exceptions.InvalidCpfException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPasswordException;
import middleware.UserMiddleware;
import models.User;
import security.PasswordHandler;

/**
 * A classe UserService é responsável por gerenciar as operações de CRUD relacionadas a usuários,
 * como criação, atualização, login e recuperação de usuários.
 */
public class UserService {
	protected UserDAO userDb;
	protected UserMiddleware middleware;
	
	public UserService() {
		this.userDb = new UserPostgresDAO();
		this.middleware = new UserMiddleware();
	}

	/**
	 * Cria um novo usuário após verificar os campos e confirmar a senha.
	 * 
	 * @param newUser O novo usuário a ser criado.
	 * @param confirmPassword A senha de confirmação para validação.
	 * @return Uma string indicando o resultado da validação.
	 * @throws SQLException Se ocorrer um erro de banco de dados ao criar o usuário.
	 * @throws IOException Se ocorrer um erro ao manipular arquivos.
	 * @throws InvalidNameException Se o nome do usuário for inválido.
	 * @throws InvalidCpfException Se o CPF do usuário for inválido.
	 * @throws InvalidAddressException Se o endereço do usuário for inválido.
	 * @throws InvalidEmailException Se o e-mail do usuário for inválido.
	 * @throws InvalidBirthDateException Se a data de nascimento do usuário for inválida.
	 * @throws InvalidPasswordException Se a senha do usuário for inválida.
	 */
	public void createUser(User newUser, String confirmPassword) throws SQLException, IOException, InvalidNameException, InvalidCpfException, InvalidAddressException, InvalidEmailException, InvalidBirthDateException, InvalidPasswordException{
			
		middleware.verifyNewUser(newUser, confirmPassword);
		newUser.setPassword(PasswordHandler.hashPassword(newUser.getPassword()));
		userDb.create(newUser);

	}
	

	/**
	 * Atualiza as informações de um usuário, incluindo a senha e a imagem de perfil.
	 * 
	 * @param user O usuário a ser atualizado.
	 * @param newPassword A nova senha (se houver).
	 * @param confirmPassword A senha de confirmação (se houver).
	 * @param selectedImgFile O arquivo da nova imagem de perfil.
	 * @return Uma string indicando o resultado da validação. 
	 * @throws SQLException Se ocorrer um erro ao atualizar o usuário no banco de dados.
	 * @throws FileNotFoundException Se o arquivo da imagem não for encontrado.
	 * @throws InvalidNameException Se o nome do usuário for inválido.
	 * @throws InvalidEmailException Se o e-mail do usuário for inválido.
	 * @throws InvalidPasswordException Se a nova senha for inválida.
	 * @throws InvalidAddressException Se o endereço do usuário for inválido.
	 * @throws IOException Se ocorrer um erro ao manipular o arquivo da imagem.
	 */
	public void updateUser(User user, String newPassword, String confirmPassword, File selectedImgFile) throws SQLException, FileNotFoundException, InvalidNameException, InvalidEmailException, InvalidPasswordException, InvalidAddressException{
		

		middleware.updateUser(user, newPassword, confirmPassword);
		
		if(newPassword != null) {
			user.setPassword(PasswordHandler.hashPassword(newPassword));
		}
			
		userDb.edit(user, selectedImgFile);

	}
	
	 /**
     * Autentica o usuário com base no email e senha fornecidos.
     * 
     * @param email O email do usuário.
     * @param password A senha do usuário.
     * @return O ID do usuário se a autenticação for bem-sucedida.
     * @throws SQLException Se ocorrer um erro durante o processo de login.
     */
	public Integer loginUser(String email, String password) throws SQLException{
		try {
			return userDb.login(email, password);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
	 /**
     * Recupera as informações de um usuário com base no ID.
     * 
     * @param id O ID do usuário.
     * @return O objeto User correspondente ao ID fornecido.
     * @throws SQLException Se ocorrer um erro de banco de dados ao recuperar o usuário.
     * @throws IOException Se ocorrer um erro ao carregar a imagem de perfil do usuário.
     */
	public User getUser(Integer id) throws SQLException, IOException {
		try {
			return userDb.getById(id);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
}
