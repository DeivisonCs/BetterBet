package dao.match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.team.TeamPostgresDAO;
import database.ConnectionDB;
import models.Match;
import models.Team;

public class MatchPostgresDAO implements MatchDAO {

	private TeamPostgresDAO teamPostgresDAO = new TeamPostgresDAO();
	
	@Override
	public List<Match> getAllMatches() throws SQLException {
		String query = "SELECT * FROM match";
        List<Match> matches = new ArrayList<Match>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            ResultSet response = ps.executeQuery()
        ){
        	
        	
            while(response.next()) {
            	Team teamA = teamPostgresDAO.getTeamById(response.getInt("a_team"));
            	Team teamB = teamPostgresDAO.getTeamById(response.getInt("b_team"));
            	Timestamp timestamp = response.getTimestamp("date_time");
            	LocalDateTime dateTime = timestamp.toLocalDateTime();
            	
                matches.add(
            		new Match(
                            response.getInt("match_id"),
                            response.getInt("event_id"),
                            teamA,
                            response.getInt("a_team_score"),
                            response.getFloat("a_team_odd"),
                            response.getFloat("draw_odd"),
                            teamB,
                            response.getInt("b_team_score"),
                            response.getFloat("b_team_odd"),
                            dateTime
                            )
                );
            }
            return matches;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
		
	}

	@Override
	public List<Match> getMatchesByEvent(Integer event_id) throws SQLException {
		String query = "SELECT * FROM match WHERE event_id = ?";
        List<Match> matches = new ArrayList<Match>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            
        ){
        	ps.setInt(1, event_id);
        	ResultSet response = ps.executeQuery();
            while(response.next()) {
            	Team teamA = teamPostgresDAO.getTeamById(response.getInt("a_team"));
            	Team teamB = teamPostgresDAO.getTeamById(response.getInt("b_team"));
            	Timestamp timestamp = response.getTimestamp("date_time");
            	LocalDateTime dateTime = timestamp.toLocalDateTime();
            	
                matches.add(
            		new Match(
                            response.getInt("match_id"),
                            response.getInt("event_id"),
                            teamA,
                            response.getInt("a_team_score"),
                            response.getFloat("a_team_odd"),
                            response.getFloat("draw_odd"),
                            teamB,
                            response.getInt("b_team_score"),
                            response.getFloat("b_team_odd"),
                            dateTime
                            )
                );
            }
            return matches;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
	}

	@Override
	public Match getMatchById() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteMatchById() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editMatch() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
