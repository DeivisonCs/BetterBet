package service.event;

import java.sql.SQLException;
import java.util.List;

import dao.event.EventDAO;
import dao.event.EventPostgresDAO;
import models.Event;

public class EventService {
	private EventDAO eventDB;
	
	public EventService() {
		this.eventDB = new EventPostgresDAO();
	}
	
	public Event save(Event newEvent) throws SQLException {
		try {
			return this.eventDB.create(newEvent);
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	public List<Event> getAll() throws SQLException{
		try {
			return this.eventDB.getAllEvents();
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	public List<Event> getEventsByName(String eventName) throws SQLException {
		try {
			return this.eventDB.getEventsByName(eventName);
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	public List<String> getUserRelatedEvents(int userId) throws SQLException {
		try {
			return this.eventDB.userRelatedEvents(userId);
		}
		catch (SQLException ex) {
			throw ex;
		}
	}

}
