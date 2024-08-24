package service.match;

import java.sql.SQLException;
import java.util.List;
import java.util.List;

import dao.match.MatchDAO;
import dao.match.MatchPostgresDAO;
import middleware.MatchMiddleware;
import models.Bet;
import models.Match;

public class MatchService {
	private MatchDAO matchDB;
	private MatchMiddleware middleware;
	
	public MatchService () {
		this.matchDB = new MatchPostgresDAO();
		this.middleware = new MatchMiddleware();
	}
	
	public String save(Match newMatch) throws SQLException{
		String valid = middleware.verifyNewMatch(newMatch);
		
		if(valid.equals("200")) {
			try {
				matchDB.create(newMatch);
			}
			catch (SQLException ex) {
				throw new SQLException();
			}
		}
		
		return valid;
	}
	
	public Match UpdateAmount(Bet bet, float amount)  throws SQLException, Exception{
		try {
			switch (bet.getSelectedBet()) {
		    case "TEAM_A":
		        matchDB.UpdateAmount(bet.getMatch().getId(), amount, "a_team_bet_amount");
		        break;
		    case "TEAM_B":
		        matchDB.UpdateAmount(bet.getMatch().getId(), amount, "b_team_bet_amount");
		        break;
		    case "DRAW":
		        matchDB.UpdateAmount(bet.getMatch().getId(), amount, "draw_bet_amount");
		        break;
		    default:
		        throw new Exception("Bet inválida");
		}
			
		return matchDB.getMatchById(bet.getMatch().getId());
		
		} catch (SQLException e1) {
			throw e1;
		} catch (Exception e2) {
			throw e2;
		}
		
	}

	public void UpdateOdds(Match matchUpdatedAmount)   throws SQLException, Exception{
		try {
			matchDB.UpdateOdds(matchUpdatedAmount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Match> getAllMatches() throws SQLException{
		try {
			return matchDB.getAllMatches();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	public List<Match> getMatchesByEvent(Integer id) throws SQLException {
		try {
			return matchDB.getMatchesByEvent(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public List<Match> getAll() throws SQLException{
		try {
			return this.matchDB.getAllMatches();
		}
		catch (SQLException ex) {
			throw new SQLException();
		}
	}
	
	public List<Match> getAllByEventId(int eventId) throws SQLException{
		try {
			return this.matchDB.getMatchesByEvent(eventId);
		}
		catch (SQLException ex) {
			throw new SQLException();
		}
	}
	
	public boolean finishMatch(int matchId) throws SQLException{	
		if(matchId != -1) {
			try {
				return this.matchDB.finishMatch(matchId);
			}
			catch (SQLException ex) {
				throw new SQLException();
			}
		}
		
		return false;
	}
}
