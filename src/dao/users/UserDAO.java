package dao.users;

import java.sql.SQLException;
import java.util.List;

import models.User;

public interface UserDAO {

	public Integer login(String email, String password) throws SQLException;
	public User create(User newUser) throws SQLException;
	public boolean isEmailRegistered(String email) throws SQLException;
	public boolean isCpfRegistered(String cpf) throws SQLException;
    public List <User> getAll() throws SQLException;
    public User getById(Integer id) throws SQLException;
    public boolean deleteById(int id) throws SQLException;
    public boolean edit(User user, int id) throws SQLException;
}
