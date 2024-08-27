package dao.users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import components.ImageUtils;
import database.ConnectionDB;
import models.AdminUser;
import models.CommonUser;
import models.User;
import security.PasswordHandler;


/**
 * Classe responsável pela implementação de operações relacionadas ao usuário no banco de dados PostgreSQL.
 * Utiliza as tabelas 'users' e 'bet_users' para armazenar informações gerais e específicas de usuários apostadores.
 */
public class UserPostgresDAO implements UserDAO {
	
	// Instância da classe de utilitários para manipulação de imagens
	private ImageUtils imgUtils = new ImageUtils();

	
	 /**
     * Cria um novo usuário no banco de dados.
     * @param newUser O objeto do tipo User contendo os dados do novo usuário.
     * @return O usuário criado.
     * @throws SQLException Se ocorrer algum erro durante a operação de inserção.
     */
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

	        if(getUserByEmail(newUser.getEmail()) != null) {
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
	
	
	 /**
     * Autentica o usuário com base no email e senha fornecidos.
     * @param email O email do usuário.
     * @param password A senha do usuário.
     * @return O ID do usuário autenticado.
     * @throws SQLException Se o email ou senha forem inválidos.
     */
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

	

    /**
     * Recupera todos os usuários do banco de dados.
     * @return Uma lista contendo todos os usuários.
     * @throws SQLException Se ocorrer algum erro ao buscar os dados.
     * @throws IOException Se ocorrer algum erro na leitura da imagem de perfil.
     */
	@Override
	public List<User> getAll() throws SQLException, IOException {
		String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            ResultSet response = ps.executeQuery()
        ){
            while(response.next()){	
            	byte[] profileImgData = response.getBytes("profile_image");
				InputStream is = new ByteArrayInputStream(profileImgData);
				BufferedImage imgBuffer = ImageIO.read(is);
            	
                users.add(
            		new CommonUser(
                            response.getInt("user_id"),
                            response.getString("name"),
                            new ImageIcon(imgBuffer),
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
        catch(SQLException e){
            e.printStackTrace();
            throw e;
        }   
        
        return users;
	}

	
	 /**
     * Recupera um usuário pelo seu ID.
     * @param id O ID do usuário.
     * @return O objeto User contendo os dados do usuário.
     * @throws SQLException Se ocorrer algum erro na consulta.
     * @throws IOException Se ocorrer algum erro na leitura da imagem de perfil.
     */
	@Override
	public User getById(Integer id) throws SQLException, IOException {
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
				
				if(response.getBytes("profile_image") != null) {
					byte[] profileImgData = response.getBytes("profile_image");
					InputStream is = new ByteArrayInputStream(profileImgData);
					BufferedImage imgBuffer = ImageIO.read(is);
					
					user.setProfileImage(new ImageIcon(imgBuffer));
					
				}
				
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

	
	/**
     * Exclui um usuário do banco de dados com base no ID.
     * 
     * @param id O ID do usuário a ser excluído.
     * @return `true` se o usuário foi excluído com sucesso, `false` caso contrário.
     * @throws SQLException Se houver um erro ao executar a exclusão no banco de dados.
     */
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

	
	/**
     * Edita as informações de um usuário existente no banco de dados.
     * 
     * @param user O objeto `User` com as novas informações.
     * @param selectedImgFile Um arquivo de imagem de perfil atualizado, se houver.
     * @return `true` se a edição foi bem-sucedida, `false` caso contrário.
     * @throws SQLException Se houver um erro ao atualizar as informações no banco de dados.
     * @throws FileNotFoundException Se o arquivo de imagem não for encontrado.
     */
	@Override
	public boolean edit(User user, File selectedImgFile) throws SQLException, FileNotFoundException {
		String query1 = "UPDATE users SET name=?, email=?, password=? WHERE user_id=?";
		String query2 = "UPDATE bet_users  SET address=? WHERE user_id=?";
		
		Connection connection = ConnectionDB.getInstance().getConnection();
		try (
			PreparedStatement ps = connection.prepareStatement(query1);
			PreparedStatement betInf = connection.prepareStatement(query2))
		{
			connection.setAutoCommit(false);
			
			// Verifica se tem o email ja cadastrado em outro usuário
			if(this.getUserByEmail(user.getEmail()) != null && this.getUserByEmail(user.getEmail()).getId() != user.getId()) {
				throw new SQLException("Email já cadastrado.");
			}
			
			if(selectedImgFile != null) {
				updateProfileImage(user.getId(), selectedImgFile);
			}
			
			
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getId());
			
			ps.executeUpdate();
			
			if(user.getPermission().equals("user")) {
				betInf.setString(1, ((CommonUser)user).getAddress());
				betInf.setInt(2, user.getId());
				
				betInf.executeUpdate();
			}
			
			connection.commit();
			return true;
		}
		catch (SQLException e) {
	        e.printStackTrace();
	        if (connection != null) {
	            try {
	                connection.rollback(); // Reverte a transação em caso de erro
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        throw e;
	    } 
		catch(FileNotFoundException e){
            e.printStackTrace();
            throw e;
        }
		finally {
	        if (connection != null) {
	            try {
	                connection.setAutoCommit(true); // Ativa o auto-commit de volta
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	}
	
	/**
     * Atualiza a imagem de perfil de um usuário no banco de dados.
     * 
     * @param userId O ID do usuário.
     * @param selectedImgFile O arquivo de imagem a ser definido como a nova imagem de perfil.
     * @throws SQLException Se houver um erro ao atualizar a imagem no banco de dados.
     * @throws FileNotFoundException Se o arquivo de imagem não for encontrado.
     */
	public void updateProfileImage(int userId, File selectedImgFile) throws SQLException, FileNotFoundException{
		String query = "UPDATE users SET profile_image=? WHERE user_id=?";
		
		try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){
			
			FileInputStream fis = new FileInputStream(selectedImgFile);
			
			ps.setBinaryStream(1, fis, (int) selectedImgFile.length());
			ps.setInt(2, userId);
			
			ps.executeUpdate();
			
			System.out.println("add image: " + selectedImgFile);
		}
		catch(SQLException | FileNotFoundException e){
            e.printStackTrace();
            throw e;
        }
		
	}

	 /**
     * Adiciona um depósito ao saldo do usuário.
     * 
     * @param userId O ID do usuário que receberá o depósito.
     * @param depositAmount O valor a ser depositado na conta do usuário.
     * @throws SQLException Se houver um erro ao atualizar o saldo no banco de dados.
     */
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
	
	/**
     * Recupera um usuário pelo email informado.
     * 
     * @param email O email do usuário a ser recuperado.
     * @return O objeto `User` correspondente ao email fornecido ou `null` se o usuário não for encontrado.
     * @throws SQLException Se houver um erro ao consultar o banco de dados.
     * @throws IOException Se houver um erro ao processar a imagem do perfil do usuário.
     */
	@Override
	public User getUserByEmail(String email) throws SQLException{
		String query = "SELECT * FROM users WHERE email=?";
		User user = null;
		
		try(
			PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)
		){
			ps.setString(1, email);
			
			ResultSet response = ps.executeQuery();
			
			if(response.next()) {
				user = response.getString("permission").equals("admin") ? new AdminUser() : new CommonUser();
				
				user.setId(response.getInt("user_id"));
				user.setName(response.getString("name"));
				user.setCpf(response.getString("cpf"));
				user.setEmail(response.getString("email"));
				user.setPassword(response.getString("password"));
				user.setPermission(response.getString("permission"));
			}
			
			return user;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	/**
     * Verifica se um CPF já está registrado no banco de dados.
     * 
     * @param cpf O CPF a ser verificado.
     * @return `true` se o CPF estiver registrado, `false` caso contrário.
     * @throws SQLException Se houver um erro ao consultar o banco de dados.
     */
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
	
	 /**
     * Atualiza o saldo de um usuário.
     * 
     * @param userId O ID do usuário cujo saldo será atualizado.
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









