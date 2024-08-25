package dao.transaction;

import java.sql.SQLException;
import java.util.List;

import models.Transaction;
import models.User;

public interface TransactionDAO {

	public List<Transaction> getTransactions(int userId) throws SQLException;
	public void insertTransaction(Transaction transaction) throws SQLException;
	public void updateBalance(User user, float newBalance)  throws SQLException;
	
}
