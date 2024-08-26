package service.users;

import java.sql.SQLException;

import dao.users.UserDAO;
import dao.users.UserPostgresDAO;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidNumberException;
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
	
	public void decreaseBalance(CommonUser user, float betAmount) throws SQLException, InsufficientBalanceException, InvalidNumberException {
		
			if(middleware.isInsufficientBalance(user, betAmount)) {
				throw new InsufficientBalanceException("Saldo Insuficiente");
			}
			
			if(betAmount <= 0) {
				throw new InvalidNumberException("O valor da aposta não pode ser menor ou igual a 0");
			}
			
			float newBalance = user.getBalance() - betAmount;
			userDao.updateBalance(user, newBalance);

	}
	
	public void increaseBalance(CommonUser user, float expectedProfit) throws SQLException {
		
		float newBalance = user.getBalance() + expectedProfit;
		
		userDao.updateBalance(user, newBalance);
		user.setBalance(user.getBalance()+ expectedProfit);

	}
	
}
