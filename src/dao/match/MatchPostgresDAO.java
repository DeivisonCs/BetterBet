package dao.match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dao.team.TeamDAO;
import dao.team.TeamPostgresDAO;
import database.ConnectionDB;
import models.Match;
import models.Team;

public class MatchPostgresDAO implements MatchDAO {

	private TeamDAO teamPostgresDAO = new TeamPostgresDAO();
	
	@Override
	public Match create(Match newMatch) throws SQLException{
		String query = "INSERT INTO match ("
				+ "event_id,"
				+ "a_team, b_team,"
				+ "date_time,"
				+ "a_team_score, b_team_score,"
				+ "a_team_odd, b_team_odd, draw_odd,"
				+ "status, "
				+ "a_team_bet_amount, b_team_bet_amount, draw_bet_amount"
				+ ") "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			Timestamp dateTime = Timestamp.valueOf(newMatch.getDate());
			
			ps.setInt(1, newMatch.getIdEvent());
			ps.setInt(2, newMatch.getTeamA().getId());
			ps.setInt(3, newMatch.getTeamB().getId());
			ps.setTimestamp(4, dateTime);
			ps.setInt(5, newMatch.getScoreTeamA());
			ps.setInt(6, newMatch.getScoreTeamB());
			ps.setFloat(7, newMatch.getOddTeamA());
			ps.setFloat(8, newMatch.getOddTeamB());
			ps.setFloat(9, newMatch.getOddDraw());
			ps.setString(10, newMatch.getStatus());
			ps.setFloat(11, newMatch.getBetAmountTeamA());
			ps.setFloat(12, newMatch.getBetAmountTeamB());
			ps.setFloat(13, newMatch.getBetAmountDraw());
			
			ps.executeUpdate();
			
			ResultSet response = ps.getGeneratedKeys();
			
			if(response.next()) {
				return getMatchById(response.getInt(1));
			}
			else {
				throw new SQLException();
			}
		}
		catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
	}
	
	@Override
	public List<Match> getAllMatches() throws SQLException {
		String query = "SELECT * FROM match";
        List<Match> matches = new ArrayList<Match>();

        

        try(
        	PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)
        ){
        	
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
                            response.getString("status"),
                            dateTime,
                            response.getFloat("a_team_bet_amount"),
                            response.getFloat("b_team_bet_amount"),
                            response.getFloat("draw_bet_amount")
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
                            response.getString("status"),
                            dateTime,
                            response.getFloat("a_team_bet_amount"),
                            response.getFloat("b_team_bet_amount"),
                            response.getFloat("draw_bet_amount")
                            
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
	public Match getMatchById(Integer matchId) throws SQLException {
	    String query = "SELECT * FROM match WHERE match_id = ?";

	    Connection conn = ConnectionDB.getInstance().getConnection();

	    try (PreparedStatement ps =conn.prepareStatement(query)) {
	    	
	        ps.setInt(1, matchId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            Integer id = rs.getInt("match_id");
	            Integer idEvent = rs.getInt("event_id");
	            Integer teamAId = rs.getInt("a_team");
	            Integer scoreTeamA = rs.getInt("a_team_score");
	            float oddTeamA = rs.getFloat("a_team_odd");
	            float oddDraw = rs.getFloat("draw_odd");
	            Integer teamBId = rs.getInt("b_team");
	            Integer scoreTeamB = rs.getInt("b_team_score");
	            float oddTeamB = rs.getFloat("b_team_odd");
	            String status = rs.getString("status");
	            LocalDateTime date = rs.getTimestamp("date_time").toLocalDateTime();
	            float betAmountTeamA = rs.getFloat("a_team_bet_amount");
	    		float betAmountTeamB = rs.getFloat("b_team_bet_amount");
	    		float betAmountDraw = rs.getFloat("draw_bet_amount");

	            Team teamA = teamPostgresDAO.getTeamById(teamAId);
	            Team teamB = teamPostgresDAO.getTeamById(teamBId);

	            return new Match(id, idEvent, teamA, scoreTeamA, oddTeamA, oddDraw, teamB, scoreTeamB, oddTeamB, status, date, betAmountTeamA, betAmountTeamB, betAmountDraw);
	        }

	        return null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	@Override
	public boolean finishMatch(int matchId) throws SQLException{
		String query = "UPDATE match SET status='finalizado', a_team_score=?, b_team_score=?  WHERE match_id = ?";
		Random random = new Random();
		
		try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)){
			
			ps.setInt(1, random.nextInt(6));
			ps.setInt(2, random.nextInt(6));
			ps.setInt(3, matchId);
			
			int rowsAffected = ps.executeUpdate();
	        
			
	        if (rowsAffected == 0) {
	        	return false;
	        }
	        
	        return true;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
	        throw ex;
		}
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

	@Override
	public void UpdateAmount(Integer id, float amount, String teamBetAmount) throws SQLException {

	    String query = String.format(
	        "UPDATE MATCH " +
	        "SET %s = %s + ? " +
	        "WHERE match_id = ?", teamBetAmount, teamBetAmount);
	    
	    try (PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)) {

	    	ps.setFloat(1, amount);
	        ps.setInt(2, id);

	        ps.executeUpdate();

	    } catch (SQLException e) {

	        e.printStackTrace();
	        throw e;
	    }
	}


	@Override
	public void UpdateOdds(Match matchUpdatedAmount) throws SQLException {
		String query = "UPDATE MATCH " +
				        "SET a_team_odd = ?, b_team_odd = ?, draw_odd = ? " +
				        "WHERE match_id = ?";

		    
        try (PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)) {

            ps.setFloat(1, matchUpdatedAmount.getOddTeamA());
            ps.setFloat(2, matchUpdatedAmount.getOddTeamB());
            ps.setFloat(3, matchUpdatedAmount.getOddDraw());
            ps.setInt(4, matchUpdatedAmount.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
        	System.out.println("testando update de odd");
            e.printStackTrace();
            throw e;
        }
		    
		
	}


}