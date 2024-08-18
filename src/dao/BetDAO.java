package dao;

import java.sql.SQLException;
import java.util.List;

import models.Bet;
import models.Event;

public interface BetDAO {
	public List<Bet> getBetsByTicket()throws SQLException;
	public int createBet(Bet bet)throws SQLException;
}
