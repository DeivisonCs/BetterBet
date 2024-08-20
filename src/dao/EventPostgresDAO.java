package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;
import models.Event;


public class EventPostgresDAO implements EventDAO{

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
	
	@Override
	public Event getEventById(int id) throws SQLException {

		return null;
	}

	@Override
	public boolean deleteEventById(int id) throws SQLException {

		return false;
	}

	@Override
	public boolean editEvent(Event event) throws SQLException {

		return false;
	}

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
