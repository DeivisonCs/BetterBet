package service.team;

import java.sql.SQLException;
import java.util.List;

import dao.team.TeamDAO;
import dao.team.TeamPostgresDAO;
import models.Team;

/**
 * A classe TeamService é responsável por encapsular a lógica de negócio relacionada aos times.
 */
public class TeamService {
	private TeamDAO teamDB;
	
	public TeamService () {
		this.teamDB = new TeamPostgresDAO();
	}
	
    /**
     * Retorna uma lista com todos os times disponíveis no banco de dados.
     * 
     * @return Uma lista de objetos Team.
     * @throws SQLException Se ocorrer um erro ao buscar os times no banco de dados.
     */
	public List<Team> getAll() throws SQLException{
		return teamDB.getAllTeams();
	}
}
