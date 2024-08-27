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
import models.CommonUser;
import models.User;
import security.PasswordHandler;

public class UserService {
	private UserDAO userDb;
	private UserMiddleware middleware;
	
	public UserService() {
		this.userDb = new UserPostgresDAO();
		this.middleware = new UserMiddleware();
	}

	public void createUser(User newUser, String confirmPassword) throws SQLException, IOException, InvalidNameException, InvalidCpfException, InvalidAddressException, InvalidEmailException, InvalidBirthDateException, InvalidPasswordException{
			
		try {
			middleware.verifyNewUser(newUser, confirmPassword);
			newUser.setPassword(PasswordHandler.hashPassword(newUser.getPassword()));
			userDb.create(newUser);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
	public void updateUser(User user, String newPassword, String confirmPassword, File selectedImgFile) throws SQLException, FileNotFoundException, InvalidNameException, InvalidEmailException, InvalidPasswordException, InvalidAddressException{
		
		try {
			middleware.updateUser(user, newPassword, confirmPassword);
			
			if(newPassword != null) {
				user.setPassword(PasswordHandler.hashPassword(newPassword));
			}
				
			userDb.edit(user, selectedImgFile);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
	public Integer loginUser(String email, String password) throws SQLException{
		try {
			return userDb.login(email, password);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
	public User getUser(Integer id) throws SQLException, IOException {
		try {
			return userDb.getById(id);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
}
