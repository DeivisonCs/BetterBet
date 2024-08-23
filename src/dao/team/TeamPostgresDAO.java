package dao.team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;

import models.Team;


public class TeamPostgresDAO implements TeamDAO{

	@Override
	public List<Team> getAllTeams() throws SQLException {
		String query = "SELECT * FROM team";
        List<Team> teams = new ArrayList<Team>();

        try(
            PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query);
            ResultSet response = ps.executeQuery()
        ){
            while(response.next()){
                teams.add(
            		new Team(
                            response.getInt("team_id"),
                            response.getString("name"), 
                            response.getString("sport")
                            )
                );
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }   
        
        return teams;
		
	}

	@Override
	public Team getTeamById(int id) throws SQLException {
	    String query = "SELECT * FROM team WHERE team_id = ?";
	    
	    try (PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)) {
	        ps.setInt(1, id);
	        ResultSet response = ps.executeQuery();
	        
	        if (response.next()) {
	            Team team = new Team(
	                response.getInt("team_id"),
	                response.getString("name"), 
	                response.getString("sport")
	            );
	            return team;
	        }
	        return null;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	@Override
	public boolean deleteTeamById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editTeam(Team team) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}