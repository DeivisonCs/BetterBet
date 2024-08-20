package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private Integer id;
	private Integer idUser;
	private LocalDateTime timeStamp;
	private float odd;
	private float expectedProfit;
	private float amount;
	private List<Bet> bets;
	private String ticketType;
	private String status;
	
	
	//Construtor para quando traz o ticket do banco
	public Ticket(Integer id, float odd, LocalDateTime timeStamp, Integer idUser, float expectedProfit, float amount, String ticketType, String status) {
		super();
		this.id = id;
		this.odd = odd;
		this.timeStamp = timeStamp;
		this.idUser = idUser;
		this.expectedProfit = expectedProfit;
		this.amount = amount;
		this.bets = new ArrayList<Bet>();
		this.ticketType = ticketType;
		this.status = status;
	}
	
	//Construtor usado para inserir o Ticket no banco(VERIFICAR SE ESTA SENDO USADO)
	public Ticket(float odd, Integer idUser, float expectedProfit, float amount, String ticketType, String status) {
		super();
		this.bets = new ArrayList<Bet>();
		this.odd = odd;
		this.idUser = idUser;
		this.expectedProfit = expectedProfit;
		this.amount = amount;
		this.ticketType = ticketType;
		this.status = status;
	}

	//Construtor usado para criar o ticket antes do usuario inserir os valores de aposta
	public Ticket(Integer idUser,String status, List<Bet> bets) {
		super();
		this.bets = bets;
		this.idUser = idUser;
		this.odd = calculateOdd();
		this.status = status;
		setTicketType(bets);
	}
	
	private float calculateOdd() {
		
		float finalOdd = 1f;
		
		for (Bet bet: this.bets) {
			if(bet.getSelectedBet().equals("TEAM_A")) {
				finalOdd = finalOdd * bet.getOddTeamA();
			}else if(bet.getSelectedBet().equals("TEAM_B")) {
				finalOdd = finalOdd * bet.getOddTeamB();
			}else  if(bet.getSelectedBet().equals("DRAW")) {
				finalOdd = finalOdd * bet.getOddDraw();
			}

		}

		return finalOdd;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getOdd() {
		return odd;
	}
	
	public void setOdd(float odd) {
		this.odd = odd;
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Integer getIdUser() {
		return idUser;
	}
	
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	
	public float getExpectedProfit() {
		return expectedProfit;
	}
	
	public void setExpectedProfit(float expectedProfit) {
		this.expectedProfit = expectedProfit;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
    
    
    public String getTicketType() {
		return ticketType;
	}

    
	private void setTicketType(List<Bet> bets) {
		this.ticketType = bets.size() > 1 ? "MULTIPLA" : "SIMPLES";
			
	}
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<Bet> getBets() {
        return this.bets;
    }
	
}
