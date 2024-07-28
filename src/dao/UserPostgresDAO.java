package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;
import models.CommonUser;
import models.User;

public class UserPostgresDAO implements UserDAO {

	@Override
	public User createUser(User newUser) throws SQLException {
		String query = "INSERT INTO bet_user (name, age, email, balance, password, permission) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query))
        {
            ps.setString(1, newUser.getName());
            ps.setInt(2, newUser.getAge());
            ps.setString(3, newUser.getEmail());
            ps.setFloat(4, newUser.getBalance());
            ps.setString(5, newUser.getPassword());
            ps.setString(6, newUser.getPermission());
            ps.executeUpdate();

            return newUser;
        }
        catch(Exception e){
            e.printStackTrace();

            throw e;
        }
	}

	@Override
	public List<User> getAllUser() throws SQLException {
		String query = "SELECT * FROM bet_user";
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
		String query = "SELECT * FROM bet_user WHERE user_id=?";

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
                user.setBalance(response.getFloat("balance"));
                
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
		String query = "DELETE FROM bet_user WHERE user_id=?";

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
		String query = "UPDATE bet_user SET name=? age=? email=? password=? WHERE user_id=?";

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
		String query = "UPDATE bet_user SET deposit=? WHERE user_id=?";

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
