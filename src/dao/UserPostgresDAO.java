package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;
import models.CommonUser;
import models.User;

public class UserPostgresDAO implements UserDAO {

	@Override
	public User createUser(User newUser) throws SQLException {
		String query = "INSERT INTO users (name, age, email, password, permission) VALUES (?, ?, ?, ?, ?)";
		String betUserQuery = "INSERT INTO bet_users (user_id, balance) VALUES (?, ?)";

		Connection connection = null;
        try {
        	connection = ConnectionDB.getInstance().getConnection();
        	
        	// Desativar o auto-commit para gerenciar a inserção no banco manualmente
        	// Em caso de erro, reverte as operações feitas para evitar inconsistencia no banco
            connection.setAutoCommit(false);
        	
        	try(
            	PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            	PreparedStatement betUserPs = connection.prepareStatement(betUserQuery)
            )
            {	
                ps.setString(1, newUser.getName());
                ps.setInt(2, newUser.getAge());
                ps.setString(3, newUser.getEmail());
                ps.setString(4, newUser.getPassword());
                ps.setString(5, newUser.getPermission());
                ps.executeUpdate();
                
                ResultSet userKey = ps.getGeneratedKeys();
                
                if(userKey.next()) {
                	int userId = userKey.getInt(1);
                	
                	if("user".equals(newUser.getPermission())) {
                		betUserPs.setInt(1, userId);
                		betUserPs.setFloat(2, (float)0.0);
                		betUserPs.executeUpdate();
                	}
                	
                	// Confirmar a transação
                    connection.commit();
                }
                else {
                	// Se a pk não for obtida e reverte a transação
                    connection.rollback();
                    throw new SQLException("Falha ao obter o ID do novo usuário.");
                }

                return newUser;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            
            if(connection != null) {
         	   try {
                	// Tenta reverter a inserção caso dê algum erro
                	connection.rollback();
                	throw new SQLException("Failed to get user id!");
                }
                catch(SQLException ex){
                	ex.printStackTrace();
                }
            }
            
            throw e;
         }
         finally {
         	if(connection != null) {
         		try {
             		// Ativa o auto-commit da transição novamente
             		connection.setAutoCommit(true);
             		// Fecha conexão depois do uso
             		connection.close();
             	}
             	catch(SQLException ex) {
             		ex.printStackTrace();
             	}
         	}
         }
	}

	@Override
	public List<User> getAllUser() throws SQLException {
		String query = "SELECT * FROM users";
        List<User> users = new ArrayList<User>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            ResultSet response = ps.executeQuery()
        ){
            while(response.next()){
                users.add(
            		new CommonUser(
                            response.getInt("user_id"),
                            response.getString("name"), 
                            response.getInt("age"),
                            response.getString("email"), 
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
	public User getUserById(int id) throws SQLException {
		String query = "SELECT * FROM users WHERE user_id=?";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query))
        {
            ps.setInt(1, id);
            
            ResultSet response = ps.executeQuery();
            
            User user = new CommonUser();

            if(response.next()){
                
                user.setId(response.getInt("user_id"));
                user.setName(response.getString("name"));
                user.setAge(response.getInt("age"));
                user.setEmail(response.getString("email")); 
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
	public boolean editUser(User user, int id) throws SQLException {
		String query = "UPDATE users SET name=? age=? email=? password=? WHERE user_id=?";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){

            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setInt(5, id);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected>0;
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
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
