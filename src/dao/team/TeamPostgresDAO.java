package dao.team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionDB;

import models.Team;


public class TeamPostgresDAO implements TeamDAO{

    /**
     * Recupera uma lista de todos os times disponíveis no banco de dados.
     * 
     * @return Uma lista de objetos `Team` representando todos os times.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
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
    /**
     * Recupera um time do banco de dados com base no seu ID.
     * 
     * @param id O ID do time que será recuperado.
     * @return Um objeto `Team` representando o time encontrado, ou `null` se não for encontrado.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
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