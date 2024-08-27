 package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe Ticket representa um bilhete de aposta no sistema.
 * Um bilhete pode conter várias apostas, ter um valor da odd do time apostado e informações sobre o usuário e o status.
 */
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
	
	
	 /**
     * Construtor para criar um bilhete a partir de dados recuperados do banco de dados.
     * 
     * @param id Identificador único do bilhete.
     * @param odd Valor da odd do bilhete.
     * @param timeStamp Data e hora em que o bilhete foi criado.
     * @param idUser Identificador do usuário associado ao bilhete.
     * @param expectedProfit Lucro esperado com base na odd e no valor apostado.
     * @param amount Valor total apostado.
     * @param ticketType Tipo do bilhete (SIMPLICE ou MULTIPLA).
     * @param status Status do bilhete.
     * @param bets Lista de apostas associadas ao bilhete.
     */
	public Ticket(Integer id, float odd, LocalDateTime timeStamp, Integer idUser, float expectedProfit, float amount, String ticketType, String status, List<Bet> bets) {
		super();
		this.id = id;
		this.odd = odd;
		this.timeStamp = timeStamp;
		this.idUser = idUser;
		this.expectedProfit = expectedProfit;
		this.amount = amount;
		this.bets = bets;
		this.ticketType = ticketType;
		this.status = status;
	}
	
    /**
     * Construtor usado para criar um bilhete antes de inseri-lo no banco de dados.
     * 
     * @param odd Valor da odd do bilhete.
     * @param idUser Identificador do usuário associado ao bilhete.
     * @param expectedProfit Lucro esperado com base na odd e no valor apostado.
     * @param amount Valor total apostado.
     * @param ticketType Tipo do bilhete (SIMPLICE ou MULTIPLA).
     * @param status Status do bilhete.
     */
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

    /**
     * Construtor usado para criar um bilhete antes do usuário inserir os valores de aposta.
     * O tipo de bilhete e o valor da odd são calculados com base nas apostas.
     * 
     * @param idUser Identificador do usuário associado ao bilhete.
     * @param status Status do bilhete.
     * @param bets Lista de apostas associadas ao bilhete.
     */
	public Ticket(Integer idUser,String status, List<Bet> bets) {
		super();
		this.bets = bets;
		this.idUser = idUser;
		this.odd = calculateOdd();
		this.status = status;
		setTicketType(bets);
	}
	
	
	  /**
     * Calcula o valor da odd do bilhete com base nas apostas.
     * 
     * @return Valor calculado da odd.
     */
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
