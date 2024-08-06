package dao;

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
import models.CommonUser;
import models.User;
import security.PasswordHandler;

public class UserPostgresDAO implements CommonUserDAO {

	@Override
	public CommonUser createUser(CommonUser newUser) throws SQLException {
		String query = "INSERT INTO users (name, cpf, email, password, permission) VALUES (?, ?, ?, ?, ?)";
	    String betUserQuery = "INSERT INTO bet_users (user_id, birthDate, address, balance) VALUES (?, ?, ?, ?)";

	    Connection connection = null;
	    PreparedStatement ps = null;
	    PreparedStatement betUserPs = null;
	    ResultSet userKey = null;

	    try {
	        connection = ConnectionDB.getInstance().getConnection();
	        connection.setAutoCommit(false);

	        ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        betUserPs = connection.prepareStatement(betUserQuery);

	        ps.setString(1, newUser.getName());
	        ps.setString(2, newUser.getCpf());
	        ps.setString(3, newUser.getEmail());
	        ps.setString(4, newUser.getPassword());
	        ps.setString(5, newUser.getPermission());
	        ps.executeUpdate();

	        // Recupera id gerado na query
	        userKey = ps.getGeneratedKeys();
	        
	        if (userKey.next()) {
	            int userId = userKey.getInt(1);

	            // Verifica se o tipo de permissão é realmente de usuário
	            if ("user".equals(newUser.getPermission())) {
	                betUserPs.setInt(1, userId);
	                betUserPs.setString(2, newUser.getBirthDate());
	                betUserPs.setString(3, newUser.getAddress());
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
	        if (userKey != null) {
	            try {
	                userKey.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        if (betUserPs != null) {
	            try {
	                betUserPs.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.setAutoCommit(true); // Ativa o auto-commit de volta
	                connection.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	}
	
	@Override
	public CommonUser loginUser(String email, String password) throws SQLException{
//		String getPasswordQuery = "SELECT password FROM users WHERE email = ?";
		String query1 = "SELECT * FROM users WHERE email = ?";
		String query2 = "SELECT * FROM bet_users WHERE bet_user_id=?";
		
		CommonUser user = new CommonUser();
		
		try(
//			PreparedStatement psGetPassword = ConnectionDB.getInstance().getConnection().prepareStatement(getPasswordQuery);
			PreparedStatement psUser = ConnectionDB.getInstance().getConnection().prepareStatement(query1);
			PreparedStatement psBetUser = ConnectionDB.getInstance().getConnection().prepareStatement(query2)){
			
			// --------------------- Informações Gerais do Usuário ---------------------
			psUser.setString(1, email);
			ResultSet response = psUser.executeQuery();
			
			if(response.next() && PasswordHandler.validPassword(password, response.getString("password"))) {
				user.setId(response.getInt("user_id"));
				user.setName(response.getString("name"));
				user.setEmail(response.getString("email"));
				user.setCpf(response.getString("cpf"));
				user.setPassword(password);
				user.setPermission(response.getString("permission"));
			}
			
			// --------------------- Informações do Usuário de Apostas ---------------------
			psBetUser.setInt(1, user.getId());
			ResultSet responseBetUser = psBetUser.executeQuery();
			
			if(responseBetUser.next()) {
				user.setBalance(responseBetUser.getFloat("balance"));
				user.setAddress(responseBetUser.getString("address"));
				user.setBirthDate(responseBetUser.getString("birthDate"));
			}
			
			return user;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public List<CommonUser> getAllUser() throws SQLException {
		String query = "SELECT * FROM users";
        List<CommonUser> users = new ArrayList<CommonUser>();

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
	public CommonUser getUserById(int id) throws SQLException {
		String query = "SELECT * FROM users WHERE user_id=?";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query))
        {
            ps.setInt(1, id);
            
            ResultSet response = ps.executeQuery();
            
            CommonUser user = new CommonUser();

            if(response.next()){
                
                user.setId(response.getInt("user_id"));
                user.setName(response.getString("name"));
                user.setCpf(response.getString("cpf"));
                user.setBirthDate(response.getString("birthDate"));
                user.setEmail(response.getString("email"));
                user.setAddress(response.getString("address"));
                user.setPassword(response.getString("password"));
                user.setPermission(response.getString("permission"));
//                user.setBalance(response.getFloat("balance"));
                
            }
            

            return user;
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	}

	@Override
	public boolean deleteUserById(int id) throws SQLException {
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
	public boolean editUser(CommonUser user, int id) throws SQLException {
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

	@Override
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

    
}
