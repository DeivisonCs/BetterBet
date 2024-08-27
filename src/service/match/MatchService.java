package service.match;

import java.sql.SQLException;
import java.util.List;
import java.util.List;

import dao.match.MatchDAO;
import dao.match.MatchPostgresDAO;
import exceptions.InvalidMatchException;
import middleware.MatchMiddleware;
import models.Bet;
import models.Match;

/**
 * Serviço responsável pela lógica de negócio relacionada às partidas (Matches).
 */
public class MatchService {
	private MatchDAO matchDB;
	private MatchMiddleware middleware;
	
	public MatchService () {
		this.matchDB = new MatchPostgresDAO();
		this.middleware = new MatchMiddleware();
	}
	

	/**
	 * Salva uma nova partida após validar os dados.
	 * 
	 * @param newMatch A partida a ser salva.
	 * @return Um código de status indicando o resultado da operação. "200" para sucesso, caso contrário, a mensagem de erro.
	 * @throws SQLException Se ocorrer um erro ao salvar a partida.
	 */
	public void save(Match newMatch) throws SQLException, InvalidMatchException{
		try {
			matchDB.create(newMatch);
			middleware.verifyNewMatch(newMatch);
		}
		catch (SQLException ex) {
			throw new SQLException();
		}
	}
	

    /**
     * Atualiza o valor da aposta em uma partida com base na seleção da aposta (Time A, Time B, ou Empate).
     * 
     * @param bet A aposta que contém a seleção (Time A, Time B, ou Empate).
     * @param amount O valor a ser atualizado.
     * @return A partida atualizada.
     * @throws SQLException Se ocorrer um erro ao atualizar a aposta.
     * @throws Exception Se a aposta selecionada for inválida.
     */
	public Match UpdateAmount(Bet bet, float amount)  throws SQLException, Exception{
		try {
			switch (bet.getSelectedBet()) {
		    case "TEAM_A":
		        matchDB.UpdateAmount(bet.getMatch().getId(), amount, "a_team_bet_amount");
		        break;
		    case "TEAM_B":
		        matchDB.UpdateAmount(bet.getMatch().getId(), amount, "b_team_bet_amount");
		        break;
		    case "DRAW":
		        matchDB.UpdateAmount(bet.getMatch().getId(), amount, "draw_bet_amount");
		        break;
		    default:
		        throw new Exception("Bet inválida");
		}
			
		return matchDB.getMatchById(bet.getMatch().getId());
		
		} catch (SQLException e1) {
			throw e1;
		} catch (Exception e2) {
			throw e2;
		}
		
	}

	  /**
     * Atualiza as odds de uma partida com base nos novos valores.
     * 
     * @param matchUpdatedAmount A partida com os valores atualizados.
     * @throws SQLException Se ocorrer um erro ao atualizar as odds.
     * @throws Exception Se houver um erro genérico.
     */
	public void UpdateOdds(Match matchUpdatedAmount)   throws SQLException, Exception{
		try {
			matchDB.UpdateOdds(matchUpdatedAmount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 /**
     * Retorna todas as partidas do banco de dados.
     * 
     * @return Uma lista de todas as partidas.
     * @throws SQLException Se ocorrer um erro ao buscar as partidas.
     */
	public List<Match> getAllMatches() throws SQLException{
		try {
			return matchDB.getAllMatches();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	 /**
     * Retorna as partidas associadas a um evento específico.
     * 
     * @param id O ID do evento.
     * @return Uma lista de partidas associadas ao evento.
     * @throws SQLException Se ocorrer um erro ao buscar as partidas.
     */
	public List<Match> getMatchesByEvent(Integer id) throws SQLException {
		try {
			return matchDB.getMatchesByEvent(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Retorna todas as partidas do banco de dados.
	 * 
	 * @return Uma lista de todas as partidas.
	 * @throws SQLException Se ocorrer um erro ao buscar as partidas.
	 */
	public List<Match> getAll() throws SQLException{
		try {
			return this.matchDB.getAllMatches();
		}
		catch (SQLException ex) {
			throw new SQLException();
		}
	}
	
	public List<Match> getAllByEventId(int eventId) throws SQLException{
		try {
			return this.matchDB.getMatchesByEvent(eventId);
		}
		catch (SQLException ex) {
			throw new SQLException();
		}
	}
	
	  /**
     * Finaliza uma partida específica.
     * 
     * @param matchId O ID da partida a ser finalizada.
     * @return True se a partida foi finalizada com sucesso, False se ocorrer um erro.
     * @throws SQLException Se ocorrer um erro ao finalizar a partida.
     */
	public boolean finishMatch(int matchId) throws SQLException{	
		if(matchId != -1) {
			try {
				return this.matchDB.finishMatch(matchId);
			}
			catch (SQLException ex) {
				throw new SQLException();
			}
		}
		
		return false;
	}
}
