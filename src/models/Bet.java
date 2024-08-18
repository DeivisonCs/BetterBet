package models;

public class Bet {
	private Integer id;
	private String betType;
	private float oddTeamA;
	private float oddTeamB;
	private float oddDraw;
	private Match match;
	private Integer idTicket;
	private String status;
	private String selectedBet;
	
	
	public Bet(Integer id, String betType, float oddTeamA, float oddTeamB,float oddDraw, Match match, Integer idTicket, String status, String selectedBet) {
		super();
		this.id = id;
		this.betType = betType;
		this.oddTeamA = oddTeamA;
		this.oddTeamB = oddTeamB;
		this.oddDraw = oddDraw;
		this.match = match;
		this.idTicket = idTicket;
		this.status = status;
		this.selectedBet = selectedBet;
	}


	public Bet(String betType, float oddTeamA, float oddTeamB,float oddDraw, Match match, Integer idTicket, String status, String selectedBet) {
		super();
		this.betType = betType;
		this.oddTeamA = oddTeamA;
		this.oddTeamB = oddTeamB;
		this.oddDraw = oddDraw;
		this.match = match;
		this.idTicket = idTicket;
		this.status = status;
		this.selectedBet = selectedBet;
	}

	public Bet(String betType, float oddTeamA, float oddTeamB,float oddDraw, Match match, String status, String selectedBet) {
		super();
		this.betType = betType;
		this.oddTeamA = oddTeamA;
		this.oddTeamB = oddTeamB;
		this.oddDraw = oddDraw;
		this.match = match;
		this.status = status;
		this.selectedBet = selectedBet;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getBetType() {
		return betType;
	}


	public void setBetType(String betType) {
		this.betType = betType;
	}

	public Match getMatch() {
		return match;
	}


	public void setMatch(Match match) {
		this.match = match;
	}


	public Integer getIdTicket() {
		return idTicket;
	}


	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public String getSelectedBet() {
		return selectedBet;
	}


	public void setSelectedBet(String selectedBet) {
		this.selectedBet = selectedBet;
	}
	
	
	
	
}
