package service.ticket;

import java.sql.SQLException;

import dao.ticket.TicketDAO;
import dao.ticket.TicketPostgresDAO;
import exceptions.MatchAlreadyFinishedException;
import middleware.TicketMiddleware;
import models.Ticket;
import service.bets.BetService;

public class TicketService {
	TicketDAO ticketDao = new TicketPostgresDAO();
	BetService betService = new BetService();
	TicketMiddleware middleware = new TicketMiddleware();
	
	public void createTicket(Ticket ticket) throws SQLException, MatchAlreadyFinishedException{
		
		String valid = middleware.areMatchesPending(ticket);

		if(valid.equals("200")) {
			Integer ticketId = ticketDao.createTicket(ticket);
			ticket.setId(ticketId);
			ticket.getBets().forEach((bet) -> bet.setIdTicket(ticket.getId()));
			betService.createBets(ticket.getBets());
					
			return;
		}
		
		throw new MatchAlreadyFinishedException(valid);
	}
	
}
