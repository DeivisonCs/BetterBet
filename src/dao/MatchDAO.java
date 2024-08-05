package dao;

import java.sql.SQLException;
import java.util.List;

import models.Match;

public interface MatchDAO {
	
	public List<Match> getAllMatches()throws SQLException;
	public List<Match> getMatchesByEvent(Integer event_id)throws SQLException;
	public Match getMatchById()throws SQLException;
	public boolean deleteMatchById()throws SQLException;
	public boolean editMatch()throws SQLException;
}
