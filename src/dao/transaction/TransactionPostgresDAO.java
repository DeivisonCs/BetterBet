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
		
		String query = "SELECT * FROM transactions";
		
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

	
}
