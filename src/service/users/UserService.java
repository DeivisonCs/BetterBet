package service.users;

import java.sql.SQLException;

import dao.users.UserDAO;
import dao.users.UserPostgresDAO;
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

	public String createUser(User newUser, String confirmPassword) throws SQLException{
		
		String validField = middleware.verifyNewUser((CommonUser)newUser, confirmPassword);
		
		if(validField.equals("200")) {
			try {
				newUser.setPassword(PasswordHandler.hashPassword(newUser.getPassword()));
				userDb.create(newUser);
			}
			catch(SQLException ex) {
				throw ex;
			}
		}
		
		
		return validField;
	}
	
	public String updateUser(User user, String newPassword, String confirmPassword) throws SQLException{
		String validField = middleware.updateUser(user, newPassword, confirmPassword);
		
		if(validField.equals("200")) {
			try {
				if(newPassword != null) {
					user.setPassword(PasswordHandler.hashPassword(newPassword));
				}
					
				userDb.edit(user);
			}
			catch(SQLException ex) {
				throw ex;
			}
		}
		
		return validField;
	}
	
	public Integer loginUser(String email, String password) throws SQLException{
		try {
			return userDb.login(email, password);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
	public User getUser(Integer id) throws SQLException {
		try {
			return userDb.getById(id);
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
}
