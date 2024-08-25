package dao.transaction;

import java.sql.SQLException;
import java.util.List;

import models.Transaction;

public interface TransactionDAO {

	public List<Transaction> getTransactions() throws SQLException;
	public void insertTransaction(Transaction transaction) throws SQLException;
	
}
