package service.users;

import java.sql.SQLException;

import dao.users.UserDAO;
import dao.users.UserPostgresDAO;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidNumberException;
import middleware.UserMiddleware;
import models.CommonUser;

/**
 * A classe CommonUserService é responsável pelos serviços e operações relacionadas ao usuário comum (CommonUser),
 * como a manipulação do saldo do usuário. 
 * Herda da classe abstrata UserService e utiliza UserMiddleware para validações de negócios e UserDAO para persistência dos dados.
 */
public class CommonUserService extends UserService{
	
	public CommonUserService() {
		super();
	}
	
	  /**
     * Reduz o saldo do usuário comum pelo valor da aposta.
     * 
     * @param user O usuário comum cujo saldo será reduzido.
     * @param betAmount O valor da aposta a ser deduzido.
     * @throws SQLException Se ocorrer um erro ao atualizar o saldo no banco de dados.
     * @throws InsufficientBalanceException Se o saldo do usuário for insuficiente para a aposta.
     * @throws InvalidNumberException Se o valor da aposta for menor ou igual a zero.
     */
	public void decreaseBalance(CommonUser user, float betAmount) throws SQLException, InsufficientBalanceException, InvalidNumberException {
		
			if(middleware.isInsufficientBalance(user, betAmount)) {
				throw new InsufficientBalanceException("Saldo Insuficiente");
			}
			
			if(betAmount <= 0) {
				throw new InvalidNumberException("O valor da aposta não pode ser menor ou igual a 0");
			}
			
			float newBalance = user.getBalance() - betAmount;
			userDb.updateBalance(user, newBalance);

	}
	

    /**
     * Aumenta o saldo do usuário comum pelo valor do lucro esperado.
     * 
     * @param user O usuário comum cujo saldo será aumentado.
     * @param expectedProfit O valor do lucro a ser adicionado ao saldo do usuário.
     * @throws SQLException Se ocorrer um erro ao atualizar o saldo no banco de dados.
     */
	public void increaseBalance(CommonUser user, float expectedProfit) throws SQLException {
		
		float newBalance = user.getBalance() + expectedProfit;
		
		userDb.updateBalance(user, newBalance);
		user.setBalance(user.getBalance()+ expectedProfit);

	}
	
}
