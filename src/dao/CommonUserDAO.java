package dao;

import java.sql.SQLException;
import java.util.List;

import models.CommonUser;
import models.User;

public interface CommonUserDAO {

	public CommonUser loginUser(String email, String password) throws SQLException;
	public CommonUser createUser(CommonUser newUser) throws SQLException;
    public List <CommonUser> getAllUser() throws SQLException;
    public CommonUser getUserById(int id) throws SQLException;
    public boolean deleteUserById(int id) throws SQLException;
    public boolean editUser(CommonUser user, int id) throws SQLException;
    public boolean addDepositUser(int id, float amount) throws SQLException;
}
