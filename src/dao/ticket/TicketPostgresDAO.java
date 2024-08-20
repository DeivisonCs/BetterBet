package dao.ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.bet.BetDAO;
import dao.bet.BetPostgresDAO;
import database.ConnectionDB;
import models.Bet;
import models.Ticket;

public class TicketPostgresDAO implements TicketDAO {

	BetDAO betDao = new BetPostgresDAO();
	
	@Override
	public List<Ticket> getTicketsByUser(Integer userId) throws SQLException {
	    String query = "SELECT * FROM TICKET WHERE user_id = ?";

	    try (PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query)) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        List<Ticket> tickets = new ArrayList<Ticket>();

	        while (rs.next()) {
	            Integer id = rs.getInt("ticket_id");
	            float odd = rs.getFloat("ODD");
	            LocalDateTime timeStamp = rs.getTimestamp("time_stamp").toLocalDateTime();
	            float amount = rs.getFloat("amount");
	            String type = rs.getString("ticket_type");
	            String status = rs.getString("status");


	            float expectedProfit = odd * amount;

	            Ticket ticket = new Ticket(id, odd, timeStamp, userId, expectedProfit, amount, type, status);
	            tickets.add(ticket);
	        }

	        return tickets;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	@Override
	public int createTicket(Ticket ticket) throws SQLException {
		String query = "INSERT INTO TICKET (odd, time_stamp, user_id, amount, ticket_type, status) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS))
        {

            ps.setFloat(1, ticket.getOdd());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, ticket.getIdUser());
            ps.setFloat(4, ticket.getAmount());
            ps.setString(5, ticket.getTicketType());
            ps.setString(6, ticket.getStatus());

            ps.executeUpdate();
            
            
            ResultSet ticketRS = ps.getGeneratedKeys();
            int ticketId = 0;
            if (ticketRS.next()) {
            	ticketId = ticketRS.getInt(1);
            }
            
            return ticketId;
        }
        catch(Exception e){
            e.printStackTrace();

            throw e;
        }
	}

}
