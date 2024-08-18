package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import database.ConnectionDB;
import models.Ticket;

public class TicketPostgresDAO implements TicketDAO {

	@Override
	public List<Ticket> getTicketsByUser() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int createTicket(Ticket ticket) throws SQLException {
		String query = "INSERT INTO TICKET (odd, time_stamp, user_id, amount) VALUES (?, ?, ?, ?)";

        try(PreparedStatement ps = ConnectionDB.getInstance().getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS))
        {

            ps.setFloat(1, ticket.getOdd());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, ticket.getIdUser());
            ps.setFloat(4, ticket.getAmount());

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
