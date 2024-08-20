package service.bets;

import java.sql.SQLException;
import java.util.List;

import dao.bet.BetDAO;
import dao.bet.BetPostgresDAO;
import models.Bet;

public class BetService {
	
	BetDAO betDao = new BetPostgresDAO();
	
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

	
}
