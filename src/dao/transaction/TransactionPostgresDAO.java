package dao.transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;
import models.Transaction;
import models.User;

/**
 * Implementação da interface TransactionDAO para realizar operações com a tabela de transações no banco de dados.
 */
public class TransactionPostgresDAO implements TransactionDAO{

	/**
     * Recupera uma lista de transações associadas a um determinado usuário.
     * 
     * @param userId O ID do usuário cujas transações serão recuperadas.
     * @return Uma lista de objetos `Transaction` que pertencem ao usuário.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<Transaction> getTransactions(int userId) throws SQLException {
		
		String query ="SELECT * FROM transactions WHERE user_id = ? ORDER BY (date_transaction) DESC";
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try( 
			PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
		){
			
			ps.setInt(1, userId);
			ResultSet response = ps.executeQuery();				
			
			while(response.next()) {
				
				Timestamp timestamp = response.getTimestamp("date_transaction");
            	LocalDateTime dateTime = timestamp.toLocalDateTime();
				
				transactions.add(
						new Transaction(
								response.getInt("transaction_id"),
								response.getInt("user_id"),
								response.getString("type_transaction"),
								response.getDouble("value_transaction"),
								dateTime
						)
						
						
				);
			}
			
			return transactions;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	/**
     * Insere uma nova transação no banco de dados.
     * 
     * @param transaction O objeto `Transaction` contendo os detalhes da nova transação.
     * @throws SQLException Se houver um erro ao inserir a transação no banco de dados.
     */
	@Override
	public void insertTransaction(Transaction transaction) throws SQLException{
		String query = "INSERT INTO transactions (user_id, type_transaction, value_transaction, date_transaction) "
				+ "VALUES (?, ?, ?, ?);";
		try (PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){
			
			Timestamp dateTime = Timestamp.valueOf(transaction.getDate());
			
			ps.setInt(1, transaction.getUserId());
			ps.setString(2, transaction.getType());
			ps.setDouble(3, transaction.getValue());
			ps.setTimestamp(4, dateTime);

			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	 /**
     * Atualiza o saldo de um usuário no banco de dados.
     * 
     * @param user O objeto `User` cujo saldo será atualizado.
     * @param newBalance O novo saldo a ser atribuído ao usuário.
     * @throws SQLException Se houver um erro ao atualizar o saldo no banco de dados.
     */
	@Override
	public void updateBalance(User user, float newBalance)  throws SQLException{
		String query = "UPDATE BET_USERS SET BALANCE =? WHERE USER_ID =?";
		
		try(
			PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)
		){
			ps.setFloat(1, newBalance);
			ps.setInt(2, user.getId());
			ps.executeUpdate();
			
		}catch (SQLException ex) {
			throw ex;
		}
		
	}

	
}
