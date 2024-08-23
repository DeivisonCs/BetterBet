package middleware;

import models.Match;

public class MatchMiddleware {
	private final String VALIDATED = "200";
	
	public String verifyNewMatch(Match match) {
		if(match.getTeamA() == match.getTeamB()) {
			System.out.println("Match Middleware: " + match.getTeamA() + " " + match.getTeamB());
			
			return "OS times não podem ser iguais!";
		}
		
		return VALIDATED;
	}
}
