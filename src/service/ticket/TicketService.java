package service.ticket;

import java.sql.SQLException;

import dao.ticket.TicketDAO;
import dao.ticket.TicketPostgresDAO;
import models.Ticket;

public class TicketService {
	TicketDAO ticketDao = new TicketPostgresDAO();
	
	
	public Ticket createTicket(Ticket ticket) throws SQLException{
		
		//add verificacoes
		Integer ticketId = ticketDao.createTicket(ticket);
		ticket.setId(ticketId);
		return ticket;
	}
	
}
