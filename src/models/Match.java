package models;

import java.time.LocalDateTime;

public class Match {

	
	private Integer id;
	
	private Integer idEvent;
	
	
	private Integer idTeamA;
	private Integer scoreTeamA;
	private float oddTeamA;
	
	private Integer idTeamB;
	private Integer scoreTeamB;
	private float oddTeamB;
	
	private LocalDateTime date;
	
		
	
	
	
	public Match(Integer id, Integer idEvent, Integer idTeamA, Integer scoreTeamA, float oddTeamA, Integer idTeamB,
			Integer scoreTeamB, float oddTeamB, LocalDateTime date) {
		super();
		this.id = id;
		this.idEvent = idEvent;
		this.idTeamA = idTeamA;
		this.scoreTeamA = scoreTeamA;
		this.oddTeamA = oddTeamA;
		this.idTeamB = idTeamB;
		this.scoreTeamB = scoreTeamB;
		this.oddTeamB = oddTeamB;
		this.date = date;
	}
	
	
	//Sem Id
	public Match(Integer idEvent, Integer idTeamA, Integer scoreTeamA, float oddTeamA, Integer idTeamB,
			Integer scoreTeamB, float oddTeamB, LocalDateTime date) {
		super();
		this.idEvent = idEvent;
		this.idTeamA = idTeamA;
		this.scoreTeamA = scoreTeamA;
		this.oddTeamA = oddTeamA;
		this.idTeamB = idTeamB;
		this.scoreTeamB = scoreTeamB;
		this.oddTeamB = oddTeamB;
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

	public Integer getIdTeamA() {
		return idTeamA;
	}

	public void setIdTeamA(Integer idTeamA) {
		this.idTeamA = idTeamA;
	}

	public Integer getScoreTeamA() {
		return scoreTeamA;
	}

	public void setScoreTeamA(Integer scoreTeamA) {
		this.scoreTeamA = scoreTeamA;
	}

	public Integer getIdTeamB() {
		return idTeamB;
	}

	public void setIdTeamB(Integer idTeamB) {
		this.idTeamB = idTeamB;
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
	
}
