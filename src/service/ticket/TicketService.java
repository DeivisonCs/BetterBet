package service.ticket;

import java.sql.SQLException;
import java.util.List;

import dao.ticket.TicketDAO;
import dao.ticket.TicketPostgresDAO;
import exceptions.MatchAlreadyFinishedException;
import middleware.TicketMiddleware;
import models.Ticket;
import service.bets.BetService;

/**
 * A classe TicketService é responsável por gerenciar a lógica de negócios relacionada aos tickets de apostas.
 */
public class TicketService {
	TicketDAO ticketDao = new TicketPostgresDAO();
	BetService betService = new BetService();
	TicketMiddleware middleware = new TicketMiddleware();
	
    /**
     * Cria um novo ticket de aposta no sistema.
     * 
     * Verifica se as partidas relacionadas ao ticket ainda estão pendentes, e se sim,
     * insere o ticket e suas apostas no banco de dados.
     * 
     * @param ticket O ticket a ser criado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     * @throws MatchAlreadyFinishedException Se uma ou mais partidas já foram finalizadas.
     */
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
	
	 /**
     * Busca todos os tickets de apostas de um usuário específico.
     * 
     * @param userId O ID do usuário.
     * @return Uma lista de tickets relacionados ao usuário.
     * @throws SQLException Se ocorrer um erro ao buscar os tickets no banco de dados.
     */
	public List<Ticket> getTicketsByUser(Integer userId) throws SQLException {

		try {
			return ticketDao.getTicketsByUser(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
    /**
     * Busca todos os tickets de um usuário para um evento específico.
     * 
     * @param eventDescription A descrição do evento.
     * @param userId O ID do usuário.
     * @return Uma lista de tickets relacionados ao evento e ao usuário.
     * @throws SQLException Se ocorrer um erro ao buscar os tickets no banco de dados.
     */
	public List<Ticket> getTicketsByEventAndUser(String EventDescription, Integer userId) throws SQLException{

		try {
			return ticketDao.getTicketsByEventAndUser(EventDescription, userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	 /**
     * Atualiza o status de um ticket de aposta.
     * 
     * @param ticket O ticket cujo status será atualizado.
     * @throws SQLException Se ocorrer um erro durante a atualização no banco de dados.
     */
	public void updateStatus(Ticket ticket) throws SQLException{

		try {
			ticketDao.updateStatus(ticket);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
}
