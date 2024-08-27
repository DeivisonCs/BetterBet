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

/**
 * Implementação da interface MatchDAO para realizar operações com a tabela de partidas no banco de dados.
 */
public class MatchPostgresDAO implements MatchDAO {

	private TeamDAO teamPostgresDAO = new TeamPostgresDAO();
	
	
	/**
     * Cria uma nova partida no banco de dados e retorna o objeto Match com o ID gerado.
     * 
     * @param newMatch O objeto `Match` a ser inserido.
     * @return O objeto `Match` com o ID gerado pela inserção.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
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
	 
    /**
     * Recupera todas as partidas com o status 'pendente'.
     * 
     * @return Uma lista de objetos `Match` representando todas as partidas pendentes.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<Match> getAllMatches() throws SQLException {
		String query = "SELECT * FROM match WHERE status = 'pendente'";
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
	 /**
     * Recupera todas as partidas de um evento específico com o status 'pendente'.
     * 
     * @param event_id O ID do evento para filtrar as partidas.
     * @return Uma lista de objetos `Match` representando as partidas pendentes do evento especificado.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<Match> getMatchesByEvent(Integer event_id) throws SQLException {
		String query = "SELECT * FROM match WHERE event_id = ? AND status = 'pendente'";
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
	
	/**
     * Recupera uma partida específica pelo ID.
     * 
     * @param matchId O ID da partida a ser recuperada.
     * @return O objeto `Match` representando a partida com o ID especificado, ou `null` se não for encontrada.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
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
	
	/**
     * Finaliza uma partida, atualizando seu status e atribuindo pontuações aleatórias para os times.
     * 
     * @param matchId O ID da partida a ser finalizada.
     * @return `true` se a atualização for bem-sucedida, `false` caso contrário.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
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

	/**
     * Atualiza o valor das apostas para um time específico em uma partida.
     * 
     * @param id O ID da partida.
     * @param amount O valor a ser adicionado à aposta.
     * @param teamBetAmount O campo da tabela a ser atualizado (e.g., 'a_team_bet_amount').
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
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

	 /**
     * Atualiza as odds para uma partida específica.
     * 
     * @param matchUpdatedAmount O objeto `Match` com as novas odds a serem atualizadas.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
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