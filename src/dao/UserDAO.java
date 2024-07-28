package dao;

import java.sql.SQLException;
import java.util.List;

import models.User;

public interface UserDAO {

	public User createUser(User newUser) throws SQLException;
    public List <User> getAllUser() throws SQLException;
    public User getUserById(int id) throws SQLException;
    public boolean deleteUserById(int id) throws SQLException;
    public boolean editUser(User user, int id) throws SQLException;
    public boolean addDepositUser(int id, float amount) throws SQLException;
}
