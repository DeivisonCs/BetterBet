package service.ticket;

import java.sql.SQLException;
import java.util.List;

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
	
	public List<Ticket> getTicketsByUser(Integer userId) throws SQLException {

		try {
			return ticketDao.getTicketsByUser(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	
	public List<Ticket> getTicketsByEventAndUser(String EventDescription, Integer userId) throws SQLException{

		try {
			return ticketDao.getTicketsByEventAndUser(EventDescription, userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public void updateStatus(Ticket ticket) throws SQLException{

		try {
			ticketDao.updateStatus(ticket);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
}
