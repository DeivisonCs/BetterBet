package service.team;

import java.sql.SQLException;
import java.util.List;

import dao.team.TeamDAO;
import dao.team.TeamPostgresDAO;
import models.Team;

public class TeamService {
	private TeamDAO teamDB;
	
	public TeamService () {
		this.teamDB = new TeamPostgresDAO();
	}
	
	public List<Team> getAll() throws SQLException{
		return teamDB.getAllTeams();
	}
}
