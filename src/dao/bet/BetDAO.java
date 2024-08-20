package dao.bet;

import java.sql.SQLException;
import java.util.List;

import models.Bet;
import models.Event;

public interface BetDAO {
	public List<Bet> getBetsByTicket(Integer ticketId)throws SQLException;
	public int createBet(Bet bet)throws SQLException;
}
