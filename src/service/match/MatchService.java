package service.match;

import java.sql.SQLException;

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
}
