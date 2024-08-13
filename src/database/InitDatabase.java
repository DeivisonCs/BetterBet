package database;

import java.sql.SQLException;
import java.sql.Statement;

import security.PasswordHandler;

public class InitDatabase {
	
	public static void initializeDatabase() {
		
		String dropTables = 
			    "DROP TABLE IF EXISTS users CASCADE;"
			  + "DROP TABLE IF EXISTS bet_users CASCADE;"
			  + "DROP TABLE IF EXISTS team CASCADE;"
			  + "DROP TABLE IF EXISTS match CASCADE;"
			  + "DROP TABLE IF EXISTS event CASCADE;";
		
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
		
		String createTableEvent = 
				"CREATE TABLE event("
				+ "	event_id SERIAL NOT NULL,"
				+ "	sport VARCHAR(30),"
				+ "	date_time TIMESTAMP,"
				+ "	description VARCHAR(100),"
				+ "	status VARCHAR(20),"
				+ "	"
				+ "	CONSTRAINT pk_event PRIMARY KEY (event_id)"
				+ ");";
		
		String createTableTeam = 
				"CREATE TABLE team("
				+ "	team_id SERIAL NOT NULL,"
				+ "	name VARCHAR(50),"
				+ "	sport VARCHAR(30),"
				+ ""
				+ "	CONSTRAINT pk_team PRIMARY KEY (team_id)"
				+ ");";
		
		String createTableMatch = 
				"CREATE TABLE match("
				+ "	match_id SERIAL NOT NULL,"
				+ "	event_id INT NULL,"
				+ "	a_team INT NOT NULL,"
				+ "  	b_team INT NOT NULL,"
				+ "  	date_time TIMESTAMP,"
				+ "  	a_team_score INT,"
				+ "  	b_team_score INT,"
				+ "  	a_team_odd FLOAT,"
				+ "  	b_team_odd FLOAT,"
				+ "  	status VARCHAR(20),"
				+ "  	draw_odd FLOAT,"
				+ "  	"
				+ "  	CONSTRAINT pk_match PRIMARY KEY (match_id),"
				+ "  	"
				+ "  	CONSTRAINT fk_match_event FOREIGN KEY "
				+ "		(event_id) REFERENCES event (event_id),"
				+ "		"
				+ "  	CONSTRAINT fk_match_team_a FOREIGN KEY "
				+ "		(a_team) REFERENCES team (team_id),"
				+ "		"
				+ "	  CONSTRAINT fk_match_team_b FOREIGN KEY "
				+ "		(b_team) REFERENCES team (team_id)"
				+ "  	"
				+ ");";
		
		String insertAdmUser =	
				"INSERT INTO users (name, cpf, email, password, permission) VALUES"
				+ "('MainAdminUser', "
				+ "'111.111.111-11', "
				+ "'admin@gmail.com'," 
				+ "'" + PasswordHandler.hashPassword("adminpass") + "',"
				+ " 'admin');";
		
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			
			statement.execute(dropTables);
			statement.execute(createTableUsers);
			statement.execute(createTableBetUsers);
			statement.execute(createTableEvent);
			statement.execute(createTableTeam);
			statement.execute(createTableMatch);
			statement.execute(insertAdmUser);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
}
