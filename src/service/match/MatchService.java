package service.match;

import java.sql.SQLException;
import java.util.List;

import dao.match.MatchDAO;
import dao.match.MatchPostgresDAO;
import middleware.MatchMiddleware;
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
