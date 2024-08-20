package dao;

import java.sql.SQLException;
import java.util.List;

import models.Event;


public interface EventDAO {
	public List<Event> getAllEvents()throws SQLException;
	public List<Event> getEventsBySport(String sport)throws SQLException;
	public Event getEventById(int id)throws SQLException;
	public boolean deleteEventById(int id)throws SQLException;
	public boolean editEvent(Event event)throws SQLException;
	public List<Event> getEventsByName(String name)throws SQLException;
	public List<String> userRelatedEvents(Integer userId) throws SQLException;
}
