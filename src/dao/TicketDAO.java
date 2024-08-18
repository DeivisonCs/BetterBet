package dao;

import java.sql.SQLException;
import java.util.List;

import models.Ticket;

public interface TicketDAO {
	public List<Ticket> getTicketsByUser()throws SQLException;
	public int createTicket(Ticket ticket)throws SQLException;
}
