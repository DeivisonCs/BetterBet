package service.users;

import java.sql.SQLException;

import dao.users.UserDAO;
import dao.users.UserPostgresDAO;
import exceptions.InsufficientBalanceException;
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
	
	public void decreaseBalance(CommonUser user, float betAmount) throws SQLException,InsufficientBalanceException {
		
			if(middleware.isInsufficientBalance(user, betAmount)) {
				throw new InsufficientBalanceException("Saldo Insuficiente");
			}
			float newBalance = user.getBalance() - betAmount;
			userDao.updateBalance(user, newBalance);

	}
	
	public void increaseBalance(CommonUser user, float expectedProfit) throws SQLException {
		
		float newBalance = user.getBalance() + expectedProfit;
		
		userDao.updateBalance(user, newBalance);

}
	
}
