package dao.bet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.match.MatchDAO;
import dao.match.MatchPostgresDAO;
import database.ConnectionDB;
import models.Bet;
import models.Match;

/**
 * Implementação da interface BetDAO para realizar operações com a tabela de apostas no banco de dados.
 */
public class BetPostgresDAO implements BetDAO{

	
	MatchDAO matchDao = new MatchPostgresDAO();

    /**
     * Recupera todas as apostas associadas a um ticket específico.
     * 
     * @param ticketId O ID do ticket para filtrar as apostas.
     * @return Uma lista de objetos `Bet` representando as apostas associadas ao ticket.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public List<Bet> getBetsByTicket(Integer ticketId) throws SQLException {
	    String query = "SELECT * FROM BET WHERE TICKET_ID =?";

	    try (PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)) {
	        ps.setInt(1, ticketId);
	        ResultSet betRS = ps.executeQuery();

	        List<Bet> betsByTicket = new ArrayList<>();

	        while (betRS.next()) {
	            Integer betId = betRS.getInt("bet_id");
	            float oddTeamA = betRS.getFloat("odd_team_A");
	            float oddTeamB = betRS.getFloat("odd_team_B");
	            float oddDraw = betRS.getFloat("odd_draw");
	            Integer matchId = betRS.getInt("match_id");
	            Integer idTicket = betRS.getInt("ticket_id");
	            String selectedBet = betRS.getString("selected_bet");

	            Match match = matchDao.getMatchById(matchId);

	            Bet bet = new Bet(betId, oddTeamA, oddTeamB, oddDraw, match, idTicket, selectedBet);
	            betsByTicket.add(bet);
	        }

	        return betsByTicket;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	 /**
     * Cria uma nova aposta no banco de dados e retorna o ID gerado.
     * 
     * @param bet O objeto `Bet` a ser inserido.
     * @return O ID da aposta recém-criada.
     * @throws SQLException Se houver um erro ao acessar o banco de dados.
     */
	@Override
	public int createBet(Bet bet) throws SQLException {
		String query = "INSERT INTO BET (ticket_id, selected_bet, match_id, odd_draw, odd_team_A, odd_team_B) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS))
        {

            ps.setInt(1, bet.getIdTicket());
            ps.setString(2, bet.getSelectedBet());
            ps.setInt(3, bet.getMatch().getId());
            ps.setFloat(4, bet.getOddDraw());
            ps.setFloat(5, bet.getOddTeamA());
            ps.setFloat(6, bet.getOddTeamB());
            
            ps.executeUpdate();
            
            ResultSet betRS = ps.getGeneratedKeys();
            int betId = 0;
            if (betRS.next()) {
            	betId = betRS.getInt(1);
            }
            
            return betId;
        }
        catch(Exception e){
            e.printStackTrace();

            throw e;
        }
	}

}
