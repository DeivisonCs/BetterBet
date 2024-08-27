package dao.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;
import models.Event;

/**
 * Implementação da interface EventDAO para realizar operações com a tabela de eventos no banco de dados.
 */
public class EventPostgresDAO implements EventDAO{

	 /**
     * Cria um novo evento no banco de dados e retorna o objeto Event com o ID gerado.
     * 
     * @param newEvent O objeto `Event` a ser inserido.
     * @return O objeto `Event` com o ID gerado pela inserção.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public Event create (Event newEvent)throws SQLException {
		String query = "INSERT INTO event (description, sport, date_time) VALUES (?, ?, ?)";
		
		
		try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			Timestamp dateTime = Timestamp.valueOf(newEvent.getDate());
			
			ps.setString(1, newEvent.getName());
			ps.setString(2, newEvent.getModality());
//			ps.setString(3, newEvent.ge);
			ps.setTimestamp(3, dateTime);
			
			ps.executeUpdate();
			
			ResultSet pKey = ps.getGeneratedKeys();
			
			if(pKey.next()) {
				return getEventById(pKey.getInt(1));
			}
			else {
				throw new SQLException();
			}
			
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
		
	}
	
	/**
     * Recupera todos os eventos do banco de dados.
     * 
     * @return Uma lista de objetos `Event` representando todos os eventos.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<Event> getAllEvents() throws SQLException {
		String query = "SELECT * FROM event";
        List<Event> events = new ArrayList<Event>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            ResultSet response = ps.executeQuery()
        ){
        	
        	
            while(response.next()) {
            	Timestamp timestamp = response.getTimestamp("date_time");
            	LocalDateTime dateTime = timestamp.toLocalDateTime();
            	
            	events.add(
            		new Event(
                            response.getInt("event_id"),
                            dateTime,
                            response.getString("sport"),
                            response.getString("description")
                            )
                );
            }
            return events;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
		
	}

	/**
     * Recupera eventos do banco de dados com base no esporte especificado.
     * 
     * @param sport O esporte para filtrar os eventos.
     * @return Uma lista de objetos `Event` representando os eventos do esporte especificado.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<Event> getEventsBySport(String sport) throws SQLException {
		String query = "SELECT * FROM EVENT WHERE (sport) LIKE ? ";
        List<Event> events = new ArrayList<Event>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            
        ){
        	ps.setString(1, sport);
        	ResultSet response = ps.executeQuery();
        	while(response.next()) {
            	Timestamp timestamp = response.getTimestamp("date_time");
            	LocalDateTime dateTime = timestamp.toLocalDateTime();
            	
            	events.add(
            		new Event(
                            response.getInt("event_id"),
                            dateTime,
                            response.getString("sport"),
                            response.getString("description")
                            )
                );
            }
            return events;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
	}
	
	  /**
     * Recupera eventos do banco de dados com base no nome especificado.
     * 
     * @param name O nome para filtrar os eventos.
     * @return Uma lista de objetos `Event` representando os eventos com o nome especificado.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<Event> getEventsByName(String name) throws SQLException {
		String query = "SELECT * FROM EVENT WHERE LOWER(description) LIKE LOWER(?)";

        List<Event> events = new ArrayList<Event>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            
        ){
        	ps.setString(1,"%" + name + "%");
        	ResultSet response = ps.executeQuery();
        	while(response.next()) {
            	Timestamp timestamp = response.getTimestamp("date_time");
            	LocalDateTime dateTime = timestamp.toLocalDateTime();
            	
            	events.add(
            		new Event(
                            response.getInt("event_id"),
                            dateTime,
                            response.getString("sport"),
                            response.getString("description")
                            )
                );
            }
            return events;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
	}
	
	/**
     * Recupera um evento específico pelo ID.
     * 
     * @param id O ID do evento a ser recuperado.
     * @return O objeto `Event` representando o evento com o ID especificado, ou `null` se não for encontrado.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public Event getEventById(int id) throws SQLException {
		String query = "SELECT * FROM event WHERE event_id=?";
		Event event = null;
		
		try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){
			ps.setInt(1, id);
			
			ResultSet response = ps.executeQuery();
			
			if(response.next()) {
				Timestamp timestamp = response.getTimestamp("date_time");
            	LocalDateTime dateTime = timestamp.toLocalDateTime();
            	
				event = new Event(
						response.getInt("event_id"),
						dateTime,
						response.getString("sport"),
						response.getString("description"));
				
				return event;
			}
			else {
				throw new SQLException();
			}
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }  
		
	}

	@Override
	public boolean deleteEventById(int id) throws SQLException {

		return false;
	}

	@Override
	public boolean editEvent(Event event) throws SQLException {

		return false;
	}

	 /**
     * Recupera o nome dos eventos relacionados a um usuário específico com base em suas apostas.
     * 
     * @param userId O ID do usuário para filtrar os eventos.
     * @return Uma lista de descrições de eventos relacionados ao usuário.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<String> userRelatedEvents(Integer userId) throws SQLException {
		String query = "SELECT DISTINCT E.DESCRIPTION "
				+ "FROM EVENT AS E  "
				+ "INNER JOIN MATCH AS MA\r\n"
				+ "	ON (E.EVENT_ID = MA.EVENT_ID) "
				+ "WHERE MA.MATCH_ID IN ( "
				+ "	SELECT BET.MATCH_ID "
				+ "	FROM BET "
				+ "	WHERE BET.TICKET_ID IN ( "
				+ "		SELECT TICKET.TICKET_ID  "
				+ "		FROM TICKET\r\n"
				+ "		WHERE TICKET.USER_ID = ? "
				+ "	) "
				+ ");";

        List<String> events = new ArrayList<String>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            
        ){
        	ps.setInt(1, userId);
        	ResultSet response = ps.executeQuery();
        	while(response.next()) {
        		events.add(response.getString("description"));
            }
            return events;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	}



}
	
