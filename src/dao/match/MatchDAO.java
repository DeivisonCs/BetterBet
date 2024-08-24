package dao.match;

import java.sql.SQLException;
import java.util.List;

import models.Match;

public interface MatchDAO {
	
	public Match create(Match newMatch) throws SQLException;
	public List<Match> getAllMatches()throws SQLException;
	public List<Match> getMatchesByEvent(Integer event_id)throws SQLException;
	public Match getMatchById(Integer matchId)throws SQLException;
	public boolean deleteMatchById()throws SQLException;
	public boolean editMatch()throws SQLException;
	public void UpdateAmount(Integer id, float amount, String string) throws SQLException;
	public void UpdateOdds(Match matchUpdatedAmount) throws SQLException;
}