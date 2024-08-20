package service.users;

import java.sql.SQLException;

import dao.users.UserDAO;
import dao.users.UserPostgresDAO;
import middleware.UserMiddleware;
import models.CommonUser;

public class CommonUserService extends UserService{
	
	private UserMiddleware middleware;
	
	private UserDAO userDao;
	
	
	public CommonUserService() {
		super();
		this.userDao = new UserPostgresDAO();
		this.middleware = new UserMiddleware();
	}
	
	public void updateBalance(CommonUser user, float betAmount) throws SQLException,Exception {
		
			if(middleware.isInsufficientBalance(user, betAmount)) {
				throw new Exception("Saldo Insuficiente");
			}
			float newBalance = user.getBalance() - betAmount;
			userDao.updateBalance(user, newBalance);

	}
}
