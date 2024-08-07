package database;

import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {
	
	public static void initializeDatabase() {
		
		String dropTables = 
			    "DROP TABLE IF EXISTS users CASCADE;"
			  + "DROP TABLE IF EXISTS bet_users CASCADE;";
		
		String createTableUsers = 
				"CREATE TABLE users("
				+ "    user_id SERIAL NOT NULL,"
				+ "    cpf VARCHAR(14) NOT NULL UNIQUE,"
				+ "    name VARCHAR(50) NOT NULL,"
				+ "    email VARCHAR(50) NOT NULL UNIQUE,"
				+ "    password VARCHAR(70) NOT NULL,"
				+ "    permission VARCHAR(20) NOT NULL,"
				+ "    CONSTRAINT pk_user PRIMARY KEY (user_id)"
				+ ");";
		
		String createTableBetUsers = 
				"CREATE TABLE bet_users("
				+ "    bet_user_id SERIAL NOT NULL,"
				+ "    user_id int,"
				+ "    birthDate VARCHAR(10),"
				+ "    address VARCHAR(100),"
				+ "    balance FLOAT,"
				+ "    CONSTRAINT pk_bet_user PRIMARY KEY (bet_user_id),"
				+ "    CONSTRAINT fk_user_bet_user FOREIGN KEY (user_id) REFERENCES users"
				+ ");";
		
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			
			statement.execute(dropTables);
			statement.execute(createTableUsers);
			statement.execute(createTableBetUsers);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
}
