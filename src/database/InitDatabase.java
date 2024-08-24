package database;

import java.sql.SQLException;
import java.sql.Statement;

import security.PasswordHandler;

public class InitDatabase {
	private static String dropTables;
	private static String createTableUsers;
	private static String createTableBetUsers;
	private static String createTableEvent;
	private static String createTableTeam;
	private static String createTableMatch;
	private static String createTableTicket;
	private static String createTableBet;
	private static String insertAdmUser;
	private static String insertCommonUser;
	private static String insertEvents;
	private static String insertTeams;
	private static String insertMatch;
	
	private static String dropTables;
	private static String createTableUsers;
	private static String createTableBetUsers;
	private static String createTableEvent;
	private static String createTableTeam;
	private static String createTableMatch;
	private static String createTableTicket;
	private static String createTableBet;
	private static String createTableTransactions;
	private static String insertAdmUser;
	private static String insertCommonUser;
	private static String insertEvents;
	private static String insertTeams;
	private static String insertMatch;
	private static String insertTransaction;
	
	
	public static void initializeDatabase() {

		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			addQueries();
			
			statement.execute(dropTables);
			statement.execute(createTableUsers);
			statement.execute(createTableBetUsers);
			statement.execute(createTableEvent);
			statement.execute(createTableTeam);
			statement.execute(createTableMatch);
			statement.execute(insertAdmUser);
			statement.execute(createTableTicket);
			statement.execute(createTableBet);
			statement.execute(createTableTransactions);
			statement.execute(insertCommonUser);
			statement.execute(insertEvents);
			statement.execute(insertTeams);
			statement.execute(insertMatch);
			statement.execute(insertTransaction);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void addQueries() {
		InitDatabase.dropTables = 
			    "DROP TABLE IF EXISTS bet CASCADE;"
			  + "DROP TABLE IF EXISTS ticket CASCADE;"
			  +  "DROP TABLE IF EXISTS users CASCADE;"
			  + "DROP TABLE IF EXISTS bet_users CASCADE;"
			  + "DROP TABLE IF EXISTS team CASCADE;"
			  + "DROP TABLE IF EXISTS match CASCADE;"
			  + "DROP TABLE IF EXISTS event CASCADE;"
			  + "DROP TABLE IF EXISTS transactions CASCADE";
		
		InitDatabase.createTableUsers = 
		InitDatabase.createTableUsers = 
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
		
		InitDatabase.createTableBetUsers = 
		InitDatabase.createTableBetUsers = 
				"CREATE TABLE bet_users("
				+ "    bet_user_id SERIAL NOT NULL,"
				+ "    user_id int UNIQUE,"
				+ "    birthDate VARCHAR(10),"
				+ "    address VARCHAR(100),"
				+ "    balance FLOAT,"
				+ "    CONSTRAINT pk_bet_user PRIMARY KEY (bet_user_id),"
				+ "    CONSTRAINT fk_user_bet_user FOREIGN KEY (user_id) REFERENCES users"
				+ ");";
		
		InitDatabase.createTableEvent = 
		InitDatabase.createTableEvent = 
				"CREATE TABLE event("
				+ "	event_id SERIAL NOT NULL,"
				+ "	sport VARCHAR(30),"
				+ "	date_time TIMESTAMP,"
				+ "	description VARCHAR(100),"
				+ "	status VARCHAR(20),"
				+ "	"
				+ "	CONSTRAINT pk_event PRIMARY KEY (event_id)"
				+ ");";
		
		InitDatabase.createTableTeam = 
		InitDatabase.createTableTeam = 
				"CREATE TABLE team("
				+ "	team_id SERIAL NOT NULL,"
				+ "	name VARCHAR(50),"
				+ "	sport VARCHAR(30),"
				+ ""
				+ "	CONSTRAINT pk_team PRIMARY KEY (team_id)"
				+ ");";
		
		InitDatabase.createTableMatch = 
		InitDatabase.createTableMatch = 
				"CREATE TABLE match("
				+ "	 match_id SERIAL NOT NULL,"
				+ "	 event_id INT NULL,"
				+ "	 a_team INT NOT NULL,"
				+ "  b_team INT NOT NULL,"
				+ "  date_time TIMESTAMP,"
				+ "  a_team_score INT,"
				+ "  b_team_score INT,"
				+ "  a_team_odd FLOAT,"
				+ "  b_team_odd FLOAT,"
				+ "  status VARCHAR(20),"
				+ "  draw_odd FLOAT,"
				+ "  a_team_bet_amount FLOAT,"
				+ "  b_team_bet_amount FLOAT,"
				+ "	 draw_bet_amount FLOAT,"
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
		
		InitDatabase.createTableTicket = 
		InitDatabase.createTableTicket = 
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

		InitDatabase.createTableBet = 
		InitDatabase.createTableBet = 
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
		
		InitDatabase.createTableTransactions = 
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
		
		InitDatabase.insertAdmUser =	
				"INSERT INTO users (name, profile_image, cpf, email, password, permission) VALUES"
				+ "('MainAdminUser', "
				+ "null,"
				+ "'111.111.111-11', "
				+ "'admin@gmail.com'," 
				+ "'" + PasswordHandler.hashPassword("adminpass") + "',"
				+ " 'admin');";
		
		InitDatabase.insertCommonUser =
		InitDatabase.insertCommonUser =
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
		
		InitDatabase.insertEvents = 
				"INSERT INTO event (sport, date_time, description, status) VALUES"
				+ "('futebol',"
				+ "'2024-08-10 15:30:00',"
				+ "'Campeonato Brasileiro Série A',"
				+ "'aberto'),"
				+ "('futebol',"
				+ "'2024-05-19 17:12:00',"
				+ "'Liga dos Campeões da UEFA',"
				+ "'aberto'),"
				+ "('futebol',"
				+ "'2024-05-19 17:12:00',"
				+ "'Copa do Mundo da FIFA',"
				+ "'aberto');";
		
		InitDatabase.insertTeams =
				"INSERT INTO team (name, sport) VALUES"
				+ "('Flamengo', 'futebol'),"
				+ "('Palmeiras', 'futebol'),"
				+ "('Corinthians', 'futebol'),"
				+ "('São Paulo', 'futebol'),"
				+ "('Atlético Mineiro', 'futebol'),"
				+ "('Internacional', 'futebol'),"
				+ "('Real Madrid', 'futebol'),"
				+ "('Manchester City', 'futebol'),"
				+ "('Bayern de Munique', 'futebol'),"
				+ "('Paris Saint-Germain', 'futebol'),"
				+ "('Chelsea', 'futebol'),"
				+ "('Barcelona', 'futebol'),"
				+ "('Brasil', 'futebol'),"
				+ "('Argentina', 'futebol'),"
				+ "('França', 'futebol'),"
				+ "('Alemanha', 'futebol'),"
				+ "('Itália', 'futebol'),"
				+ "('Espanha', 'futebol');";
		
		InitDatabase.insertMatch =
				"INSERT INTO match (event_id, a_team, b_team, a_team_odd, b_team_odd, draw_odd, status, a_team_score, b_team_score, date_time, a_team_bet_amount, b_team_bet_amount, draw_bet_amount) VALUES "
				+ "((SELECT event_id FROM event WHERE description = 'Campeonato Brasileiro Série A'),"
				+ " (SELECT team_id FROM team WHERE name = 'Flamengo'),"
				+ " (SELECT team_id FROM team WHERE name = 'Palmeiras'),"
				+ " 2.20, 3.10, 3.00, 'finalizado', 1, 1, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Campeonato Brasileiro Série A'),"
				+ " (SELECT team_id FROM team WHERE name = 'Corinthians'),"
				+ " (SELECT team_id FROM team WHERE name = 'São Paulo'),"
				+ " 2.80, 2.90, 3.20, 'pendente', 0, 0, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Campeonato Brasileiro Série A'),"
				+ " (SELECT team_id FROM team WHERE name = 'Atlético Mineiro'),"
				+ " (SELECT team_id FROM team WHERE name = 'Internacional'),"
				+ " 2.50, 2.95, 3.10, 'pendente', 0, 0, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Liga dos Campeões da UEFA'),"
				+ " (SELECT team_id FROM team WHERE name = 'Real Madrid'),"
				+ " (SELECT team_id FROM team WHERE name = 'Manchester City'),"
				+ " 2.60, 2.75, 3.25, 'pendente', 0, 0, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Liga dos Campeões da UEFA'),"
				+ " (SELECT team_id FROM team WHERE name = 'Bayern de Munique'),"
				+ " (SELECT team_id FROM team WHERE name = 'Paris Saint-Germain'),"
				+ " 2.40, 3.00, 3.20, 'finalizado', 3, 2, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Liga dos Campeões da UEFA'),"
				+ " (SELECT team_id FROM team WHERE name = 'Chelsea'),"
				+ " (SELECT team_id FROM team WHERE name = 'Barcelona'),"
				+ " 2.85, 2.65, 3.10, 'pendente', 0, 0, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Copa do Mundo da FIFA'),"
				+ " (SELECT team_id FROM team WHERE name = 'Brasil'),"
				+ " (SELECT team_id FROM team WHERE name = 'Argentina'),"
				+ " 2.10, 3.25, 3.00, 'pendente', 2, 2, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Copa do Mundo da FIFA'),"
				+ " (SELECT team_id FROM team WHERE name = 'França'),"
				+ " (SELECT team_id FROM team WHERE name = 'Alemanha'),"
				+ " 2.30, 3.00, 3.12, 'finalizado', 1, 0, '2024-05-19 17:12:00',1,1,1),"
				
				+ "((SELECT event_id FROM event WHERE description = 'Copa do Mundo da FIFA'),"
				+ " (SELECT team_id FROM team WHERE name = 'Itália'),"
				+ " (SELECT team_id FROM team WHERE name = 'Espanha'),"
				+ " 2.75, 2.85, 3.05, 'pendente', 0, 0, '2024-05-19 17:12:00',1,1,1);";
		
	}
	
}
