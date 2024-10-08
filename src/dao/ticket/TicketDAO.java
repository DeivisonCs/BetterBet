package dao.ticket;

import java.sql.SQLException;
import java.util.List;

import models.Ticket;

public interface TicketDAO {
	public List<Ticket> getTicketsByUser(Integer userId)throws SQLException;
	public int createTicket(Ticket ticket)throws SQLException;
	public List<Ticket> getTicketsByEventAndUser(String description, Integer user_id) throws SQLException;
	public void updateStatus(Ticket ticket) throws SQLException;
}
