package models;

public class Bet {
	private Integer id;
	private String betType;
	private float odd;
	private Integer idMatch;
	private Integer idTicket;
	private String status;
	
	
	public Bet(Integer id, String betType, float odd, Integer idMatch, Integer idTicket, String status) {
		super();
		this.id = id;
		this.betType = betType;
		this.odd = odd;
		this.idMatch = idMatch;
		this.idTicket = idTicket;
		this.status = status;
	}


	public Bet(String betType, float odd, Integer idMatch, Integer idTicket, String status) {
		super();
		this.betType = betType;
		this.odd = odd;
		this.idMatch = idMatch;
		this.idTicket = idTicket;
		this.status = status;
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


	public float getOdd() {
		return odd;
	}


	public void setOdd(float odd) {
		this.odd = odd;
	}


	public Integer getIdMatch() {
		return idMatch;
	}


	public void setIdMatch(Integer idMatch) {
		this.idMatch = idMatch;
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
	
	
	
	
}
