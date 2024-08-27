package middleware;

import exceptions.InvalidMatchException;
import models.Match;

public class MatchMiddleware {
	
	public void verifyNewMatch(Match match) throws InvalidMatchException {
		if(match.getTeamA() == match.getTeamB()) {
			throw new InvalidMatchException("OS times não podem ser iguais!");
		}
	}
}
