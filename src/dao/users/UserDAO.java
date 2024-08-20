package dao.users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import models.User;

public interface UserDAO {

	public Integer login(String email, String password) throws SQLException;
	public User create(User newUser) throws SQLException, IOException;
	public User getUserByEmail(String email) throws SQLException;
	public boolean isCpfRegistered(String cpf) throws SQLException;
    public List <User> getAll() throws SQLException, IOException;
    public User getById(Integer id) throws SQLException, IOException;
    public boolean deleteById(int id) throws SQLException;
    public boolean edit(User user, File selectedImgFile) throws SQLException , FileNotFoundException;
}
