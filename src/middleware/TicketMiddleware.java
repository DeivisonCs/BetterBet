package middleware;

import java.util.concurrent.atomic.AtomicBoolean;

import models.Ticket;

public class TicketMiddleware {
	private final String VALIDATED = "200";
	
	public String areMatchesPending(Ticket ticket) {
		
		AtomicBoolean isNotValid = new AtomicBoolean(false);
		ticket.getBets().forEach(bet ->{ 
			if(!bet.getMatch().getStatus().equals("pendente")) {
				isNotValid.set(true);
			}
		});
		
		if(isNotValid.get()) {
			return "Não é possivel apostar em partidas finalizadas";
		}
		
		return VALIDATED;
	}
}
