package dao.team;

import java.sql.SQLException;
import java.util.List;


import models.Team;

public interface TeamDAO {
	public List<Team> getAllTeams() throws SQLException;
	public Team getTeamById(int id) throws SQLException;
	public boolean deleteTeamById(int id) throws SQLException;
	public boolean editTeam(Team team) throws SQLException;
}
