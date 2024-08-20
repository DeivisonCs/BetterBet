package dao.ticket;

import java.sql.SQLException;
import java.util.List;

import models.Ticket;

public interface TicketDAO {
	public List<Ticket> getTicketsByUser(Integer userId)throws SQLException;
	public int createTicket(Ticket ticket)throws SQLException;
}
