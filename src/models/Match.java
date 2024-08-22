package models;

import java.time.LocalDateTime;

public class Match {

	
	private Integer id;
	
	private Integer idEvent;
	private String status;
	
	private Team teamA;
	private Integer scoreTeamA;
	private float oddTeamA;
	
	private float oddDraw;
	
	private Team teamB;
	private Integer scoreTeamB;
	private float oddTeamB;
	
	private LocalDateTime date;
	
		
	
	public Match(Integer id, Integer idEvent, Team teamA, Integer scoreTeamA, float oddTeamA, float oddDraw, Team teamB,
			Integer scoreTeamB, float oddTeamB, String status, LocalDateTime date) {
		super();
		this.id = id;
		this.idEvent = idEvent;
		this.teamA = teamA;
		this.scoreTeamA = scoreTeamA;
		this.oddTeamA = oddTeamA;
		this.oddDraw = oddDraw;
		this.teamB = teamB;
		this.scoreTeamB = scoreTeamB;
		this.oddTeamB = oddTeamB;
		this.status = status;
		this.date = date;
	}
	
	
	public Match(Integer idEvent, Team teamA, Integer scoreTeamA, float oddTeamA, float oddDraw, Team teamB,
			Integer scoreTeamB, float oddTeamB, String status, LocalDateTime date) {
		super();
		this.idEvent = idEvent;
		this.teamA = teamA;
		this.scoreTeamA = scoreTeamA;
		this.oddTeamA = oddTeamA;
		this.oddDraw = oddDraw;
		this.teamB = teamB;
		this.scoreTeamB = scoreTeamB;
		this.oddTeamB = oddTeamB;
		this.status = status;
		this.date = date;
	}




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Integer idEvent) {
		this.idEvent = idEvent;
	}


	public Integer getScoreTeamA() {
		return scoreTeamA;
	}

	public void setScoreTeamA(Integer scoreTeamA) {
		this.scoreTeamA = scoreTeamA;
	}


	public Integer getScoreTeamB() {
		return scoreTeamB;
	}

	public void setScoreTeamB(Integer scoreTeamB) {
		this.scoreTeamB = scoreTeamB;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public float getOddTeamA() {
		return oddTeamA;
	}

	public void setOddTeamA(float oddTeamA) {
		this.oddTeamA = oddTeamA;
	}

	public float getOddTeamB() {
		return oddTeamB;
	}

	public void setOddTeamB(float oddTeamB) {
		this.oddTeamB = oddTeamB;
	}


	public float getOddDraw() {
		return oddDraw;
	}


	public void setOddDraw(float oddDraw) {
		this.oddDraw = oddDraw;
	}


	public Team getTeamA() {
		return teamA;
	}


	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}


	public Team getTeamB() {
		return teamB;
	}


	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setString(String status) {
		this.status = status;
	}
	
	
	@Override
    public String toString() {
        return getTeamA().getName() + " x " + getTeamB().getName();
    }
	
}