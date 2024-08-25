package dao.transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;
import models.Transaction;

public class TransactionPostgresDAO implements TransactionDAO{

	@Override
	public List<Transaction> getTransactions() throws SQLException {
		
		String query ="SELECT * FROM transactions T INNER JOIN users U ON T.user_id = U.user_id";
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try( 
			PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
			ResultSet response = ps.executeQuery();				
		){
			while(response.next()) {
				transactions.add(
						new Transaction(
								response.getInt("transaction_id"),
								response.getInt("user_id"),
								response.getString("type_transaction"),
								response.getDouble("value_transaction")	
						)
						
						
				);
			}
			
			return transactions;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	@Override
	public void insertTransaction(Transaction transaction) throws SQLException{
		String query = "INSERT INTO transactions (user_id, type_transaction, value_transaction) "
				+ "VALUES (?, ?, ?);";
		try (PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){
			
			ps.setInt(1, transaction.getUserId());
			ps.setString(2, transaction.getType());
			ps.setDouble(3, transaction.getValue());

			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	
}
