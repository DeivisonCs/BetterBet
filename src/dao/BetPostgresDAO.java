package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.ConnectionDB;
import models.Bet;

public class BetPostgresDAO implements BetDAO{

	@Override
	public List<Bet> getBetsByTicket() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createBet(Bet bet) throws SQLException {
		String query = "INSERT INTO BET (ticket_id, bet_type, status, team_bet, match_id, odd_draw, odd_team_A, odd_team_B) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS))
        {

            ps.setInt(1, bet.getIdTicket());
            ps.setString(2, bet.getBetType());
            ps.setString(3, bet.getStatus());
            ps.setString(4, bet.getTeamBet());
            ps.setInt(5, bet.getMatch().getId());
            ps.setFloat(6, bet.getOddDraw());
            ps.setFloat(7, bet.getOddTeamA());
            ps.setFloat(8, bet.getOddTeamB());
            
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
