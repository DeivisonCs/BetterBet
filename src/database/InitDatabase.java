package database;

import java.sql.SQLException;
import java.sql.Statement;

import security.PasswordHandler;

public class InitDatabase {
	
	public static void initializeDatabase() {
		
		String dropTables = 
			    "DROP TABLE IF EXISTS bet CASCADE;"
			  + "DROP TABLE IF EXISTS ticket CASCADE;"
			  + "DROP TABLE IF EXISTS users CASCADE;"
			  + "DROP TABLE IF EXISTS bet_users CASCADE;"
			  + "DROP TABLE IF EXISTS team CASCADE;"
			  + "DROP TABLE IF EXISTS match CASCADE;"
			  + "DROP TABLE IF EXISTS event CASCADE;"
			  + "DROP TABLE IF EXISTS transactions CASCADE";
		
		String createTableUsers = 
				"CREATE TABLE users("
				+ "    user_id SERIAL NOT NULL,"
				+ "    cpf VARCHAR(14) NOT NULL UNIQUE,"
				+ "    name VARCHAR(50) NOT NULL,"
				+ "    email VARCHAR(50) NOT NULL UNIQUE,"
				+ "    password VARCHAR(70) NOT NULL,"
				+ "    permission VARCHAR(20) NOT NULL,"
				+ "	   profile_image BYTEA,"
				+ "    CONSTRAINT pk_userDB PRIMARY KEY (user_id)"
				+ ");";
		
		String createTableBetUsers = 
				"CREATE TABLE bet_users("
				+ "    bet_user_id SERIAL NOT NULL,"
				+ "    user_id int UNIQUE,"
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
		
		String createTableTicket = 
				"CREATE TABLE ticket("
				+ "	ticket_id SERIAL NOT NULL,"
				+ "	odd FLOAT NOT NULL,"
				+ "	time_stamp TIMESTAMP NOT NULL,"
				+ "	user_id INT NOT NULL,"
				+ "	amount FLOAT NOT NULL,"
				+ " ticket_type VARCHAR(20),"
				+ " status VARCHAR(20),"
				+ "	"
				+ "	CONSTRAINT pk_ticket PRIMARY KEY (ticket_id),"
				+ "	"
				+ "	CONSTRAINT fk_ticket_user FOREIGN KEY "
				+ "		(user_id) REFERENCES bet_users (user_id)"
				+ ");";

		String createTableBet = 
				"CREATE TABLE bet("
				+ "	bet_id SERIAL NOT NULL,"
				+ "	ticket_id INT NOT NULL,"
				+ "	selected_bet VARCHAR(10),"
				+ "	match_id INT NOT NULL,"
				+ "	odd_draw FLOAT NOT NULL,"
				+ "	odd_team_A FLOAT NOT NULL,"
				+ "	odd_team_B FLOAT NOT NULL,"
				+ "	"
				+ "	CONSTRAINT pk_bet PRIMARY KEY (bet_id),"
				+ "	"
				+ "	CONSTRAINT fk_bet_ticket FOREIGN KEY "
				+ "		(ticket_id) REFERENCES ticket (ticket_id),"
				+ "	"
				+ "	CONSTRAINT fk_bet_match FOREIGN KEY "
				+ "		(match_id) REFERENCES match (match_id)"
				+ ");";
		
		String createTableTransactions = 
				"CREATE TABLE transactions("
				+ "	transaction_id SERIAL NOT NULL,"
				+ "	user_id INT NOT NULL,"
				+ "	type_transaction VARCHAR(10) NOT NULL,"
				+ "	value_transaction FLOAT NOT NULL,"
				+ " "
				+ "	CONSTRAINT pk_transaction PRIMARY KEY (transaction_id),"
				+ " "
				+ "	CONSTRAINT fk_user_transaction FOREIGN KEY (user_id)"
				+ "	REFERENCES users (user_id)"
				+ ");";
		
		String insertAdmUser =	
				"INSERT INTO users (name, profile_image, cpf, email, password, permission) VALUES"
				+ "('MainAdminUser', "
				+ "null,"
				+ "'111.111.111-11', "
				+ "'admin@gmail.com'," 
				+ "'" + PasswordHandler.hashPassword("adminpass") + "',"
				+ " 'admin');";
		
		String insertCommonUser =
				"INSERT INTO users (name, profile_image, cpf, email, password, permission) VALUES"
				+ "('Vanessa', "
				+ "null,"
				+ "'222.222.222-22', "
				+ "'van@gmail.com',"
				+ "'" + PasswordHandler.hashPassword("van123") + "',"
				+ " 'user');"
				+ "INSERT INTO bet_users (user_id, address, birthdate, balance) VALUES"
				+ "(2,"
				+ "'Rua José Sátiro de Oliveira' ,"
				+ "'1992/08/01' ,"
				+ "100.00);";
		
		String insertTransaction = 
				"INSERT INTO transactions (user_id, type_transaction, value_transaction) VALUES"
				+ "((SELECT user_id FROM USERS WHERE name = 'Vanessa'), 'Saque', 10.0),"
				+ "((SELECT user_id FROM USERS WHERE name = 'Vanessa'), 'Deposito', 50.0),"
				+ "((SELECT user_id FROM USERS WHERE name = 'Vanessa'), 'Saque', 5.0),"
				+ "((SELECT user_id FROM USERS WHERE name = 'Vanessa'), 'Deposito', 30.0),"
				+ "((SELECT user_id FROM USERS WHERE name = 'Vanessa'), 'Deposito', 10.0),"
				+ "((SELECT user_id FROM USERS WHERE name = 'Vanessa'), 'Saque', 25.0);";
		
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			
			statement.execute(dropTables);
			statement.execute(createTableUsers);
			statement.execute(createTableBetUsers);
			statement.execute(createTableEvent);
			statement.execute(createTableTeam);
			statement.execute(createTableMatch);
			statement.execute(insertAdmUser);
			statement.execute(createTableTicket);
			statement.execute(createTableBet);
			statement.execute(insertCommonUser);
			statement.execute(createTableTransactions);
			statement.execute(insertTransaction);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
}
