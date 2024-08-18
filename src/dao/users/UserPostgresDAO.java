package dao.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;
import models.AdminUser;
import models.CommonUser;
import models.User;
import security.PasswordHandler;

public class UserPostgresDAO implements UserDAO {

	@Override
	public User create(User newUser) throws SQLException {
		String query = "INSERT INTO users (name, cpf, email, password, permission) VALUES (?, ?, ?, ?, ?)";
	    String betUserQuery = "INSERT INTO bet_users (user_id, birthDate, address, balance) VALUES (?, ?, ?, ?)";

	    Connection connection = ConnectionDB.getInstance().getConnection();

	    try (
	    	PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	PreparedStatement betUserPs = connection.prepareStatement(betUserQuery);
	    ){
	        connection.setAutoCommit(false);

	        if(isEmailRegistered(newUser.getEmail())) {
	        	throw new SQLException("Email já cadastrado.");
	        }
	        
	        if(isCpfRegistered(newUser.getCpf())) {
	        	throw new SQLException("CPF já cadastrado.");
	        }

	        ps.setString(1, newUser.getName());
	        ps.setString(2, newUser.getCpf());
	        ps.setString(3, newUser.getEmail());
	        ps.setString(4, newUser.getPassword());
	        ps.setString(5, newUser.getPermission());
	        ps.executeUpdate();

	        // Recupera id gerado na query
	        ResultSet userKey = ps.getGeneratedKeys();
	        
	        if (userKey.next()) {
	            int userId = userKey.getInt(1);

	            // Verifica se o tipo de permissão é realmente de usuário
	            if ("user".equals(newUser.getPermission())) {
	                betUserPs.setInt(1, userId);
	                betUserPs.setString(2, ((CommonUser) newUser).getBirthDate());
	                betUserPs.setString(3, ((CommonUser) newUser).getAddress());
	                betUserPs.setFloat(4, 0.0f);
	                betUserPs.executeUpdate();
	            }

	            // Confirmar a transação
	            connection.commit();
	        } else {
	            // Se a pk não for obtida e reverte a transação
	            connection.rollback();
	            throw new SQLException("Falha ao obter o ID do novo usuário.");
	        }

	        return newUser;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (connection != null) {
	            try {
	                connection.rollback(); // Reverte a transação em caso de erro
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        throw e;
	    } finally {
	        if (connection != null) {
	            try {
	                connection.setAutoCommit(true); // Ativa o auto-commit de volta
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	}
	
	@Override
	public Integer login(String email, String password) throws SQLException{
		String query1 = "SELECT user_id, password FROM users WHERE email = ?";

		Integer id = null;
		try(PreparedStatement psUser = ConnectionDB.getInstance().getConnection().prepareStatement(query1)){
			
			// --------------------- Informações Gerais do Usuário ---------------------
			psUser.setString(1, email);
			ResultSet response = psUser.executeQuery();
			
			if(response.next() && PasswordHandler.validPassword(password, response.getString("password"))) {
				
				id = response.getInt("user_id");
				
//				if(response.getString("permission").equals("user"))
//					user = new CommonUser();
//				else {
//					user = new AdminUser();
//				}
//				
//				user.setId(response.getInt("user_id"));
//				user.setName(response.getString("name"));
//				user.setEmail(response.getString("email"));
//				user.setCpf(response.getString("cpf"));
//				user.setPassword(password);
//				user.setPermission(response.getString("permission"));
//			}
//			
//			// --------------------- Informações do Usuário de Apostas ---------------------
//			if(user != null && user.getPermission().equals("user")) {
//				
//				psBetUser.setInt(1, user.getId());
//				ResultSet responseBetUser = psBetUser.executeQuery();
//				 
//				if(responseBetUser.next()) {
//					((CommonUser) user).setBalance(responseBetUser.getFloat("balance"));
//					((CommonUser) user).setAddress(responseBetUser.getString("address"));
//					((CommonUser) user).setBirthDate(responseBetUser.getString("birthDate"));
//				}
			}
			else {
				throw new SQLException("Email ou senha inválidos!");
			}
			
			return id;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public List<User> getAll() throws SQLException {
		String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            ResultSet response = ps.executeQuery()
        ){
            while(response.next()){
                users.add(
            		new CommonUser(
                            response.getInt("user_id"),
                            response.getString("name"), 
                            response.getString("cpf"), 
                            response.getString("birthDate"),
                            response.getString("email"),
                            response.getString("address"), 
                            response.getString("password"), 
                            response.getString("permission"), 
                            response.getFloat("balance")
                        )
                );
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
        
        return users;
	}

	@Override
	public User getById(Integer id) throws SQLException {
		String query1 = "SELECT * FROM users WHERE user_id = ?";
		String query2 = "SELECT * FROM bet_users WHERE user_id=?";
		
		User user = null;
		Connection connection = ConnectionDB.getInstance().getConnection();
		try(
			PreparedStatement psUser = connection.prepareStatement(query1);
			PreparedStatement psBetUser = connection.prepareStatement(query2)){
			
			// --------------------- Informações Gerais do Usuário ---------------------
			psUser.setInt(1, id);
			ResultSet response = psUser.executeQuery();
			
			if(response.next()) {
				user = response.getString("permission").equals("user")? new CommonUser() : new AdminUser(); 
				
				user.setId(response.getInt("user_id"));
				user.setName(response.getString("name"));
				user.setEmail(response.getString("email"));
				user.setCpf(response.getString("cpf"));
				user.setPassword(response.getString("password"));
				user.setPermission(response.getString("permission"));
			}
			
			
			// --------------------- Informações do Usuário de Apostas ---------------------
			if(user.getId() != null) {
				psBetUser.setInt(1, user.getId());
				ResultSet responseBetUser = psBetUser.executeQuery();
				
				if(responseBetUser.next()) {
					((CommonUser) user).setBalance(responseBetUser.getFloat("balance"));
					((CommonUser) user).setAddress(responseBetUser.getString("address"));
					((CommonUser) user).setBirthDate(responseBetUser.getString("birthDate"));
				}
			}
			else {
				throw new SQLException("Erro ao logar com usuário!");
			}
			
			return user;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public boolean deleteById(int id) throws SQLException {
		String query = "DELETE FROM users WHERE user_id=?";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){    

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            return rowsAffected>0;
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	}

	@Override
	public boolean edit(User user, int id) throws SQLException {
//		String query = "UPDATE users SET name=? age=? email=? password=? WHERE user_id=?";
//
//        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){
//
//            ps.setString(1, user.getName());
//            ps.setInt(2, user.getAge());
//            ps.setString(3, user.getEmail());
//            ps.setString(4, user.getPassword());
//            ps.setInt(5, id);
//
//            int rowsAffected = ps.executeUpdate();
//
//            return rowsAffected>0;
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }
		return false;
	}


	public boolean addDepositUser(int id, float amount) throws SQLException {
		String query = "UPDATE users SET deposit=? WHERE user_id=?";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){
            ps.setFloat(1, amount);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected>0;
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	}

	@Override
	public boolean isEmailRegistered(String email) throws SQLException{
		String query = "SELECT COUNT(*) FROM users WHERE email=?";
		int count = 0;
		
		try(
			PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)
		){
			ps.setString(1, email);
			
			ResultSet response = ps.executeQuery();
			
			if(response.next()) {
				count = response.getInt(1);
			}
			
			return count >= 1;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Override
	public boolean isCpfRegistered(String cpf) throws SQLException{
		String query = "SELECT COUNT(*) FROM users WHERE cpf=?";
		int count = 0;
		
		try(
			PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)
		){
			ps.setString(1, cpf);
			
			ResultSet response = ps.executeQuery();
			
			if(response.next()) {
				count = response.getInt(1);
			}
			
			return count >= 1;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

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









