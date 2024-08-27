package service.bets;

import java.sql.SQLException;
import java.util.List;

import dao.bet.BetDAO;
import dao.bet.BetPostgresDAO;
import models.Bet;


/**
 * BetService é responsável pela lógica de negócio relacionada a apostas.
 * Ele interage com o DAO para realizar operações de criação e recuperação de apostas no banco de dados.
 */
public class BetService {
	
	BetDAO betDao = new BetPostgresDAO();
	
	
	/**
     * Cria várias apostas a partir de uma lista de objetos Bet.
     * 
     * @param bets Lista de apostas a serem criadas.
     * @throws SQLException Se ocorrer um erro ao inserir uma aposta no banco de dados.
     */
	public void createBets(List<Bet> bets) throws SQLException {
		//add verificacoes(talvez)
	    for (Bet bet : bets) {
	        try {
	            betDao.createBet(bet);
	        } catch (SQLException e) {
	            throw new SQLException("Erro ao criar bet com o ID: " + bet.getId(), e);
	        }
	    }
	}

	 /**
     * Recupera todas as apostas associadas a um determinado ticket.
     * 
     * @param ticketId O ID do ticket cujas apostas devem ser recuperadas.
     * @return Uma lista de apostas associadas ao ticket.
     * @throws SQLException Se ocorrer um erro ao buscar as apostas no banco de dados.
     */
	public List<Bet> getBetsByTicketId(Integer ticketId) throws SQLException {
		List<Bet> bets = null;
	    
        try {
            bets = betDao.getBetsByTicket(ticketId);
            return bets;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar apostas ");
        }
    
	}
	
}
