package models;

import java.time.LocalDateTime;

/**
 * A classe Match representa uma partida entre dois times no sistema.
 * Ela contém informações sobre os times, as odds, os valores das apostas e o status da partida.
 */
public class Match {

	
	private Integer id;
	
	private Integer idEvent;
	private String status;
	
	private Team teamA;
	private Integer scoreTeamA;
	private float oddTeamA;
	private float betAmountTeamA;
	
	private float oddDraw;
	private float betAmountDraw;
	
	private Team teamB;
	private Integer scoreTeamB;
	private float oddTeamB;
	private float betAmountTeamB;
	
	private LocalDateTime date;
	
		
	 /**
     * Construtor da classe Match que inicializa todos os atributos.
     * 
     * @param id Identificador único da partida.
     * @param idEvent Identificador do evento associado à partida.
     * @param teamA Time A.
     * @param scoreTeamA Pontuação do Time A.
     * @param oddTeamA Odd para a vitória do Time A.
     * @param oddDraw Odd para o empate.
     * @param teamB Time B.
     * @param scoreTeamB Pontuação do Time B.
     * @param oddTeamB Odd para a vitória do Time B.
     * @param status Status atual da partida.
     * @param date Data e hora da partida.
     * @param betAmountTeamA Valor apostado no Time A.
     * @param betAmountTeamB Valor apostado no Time B.
     * @param betAmountDraw Valor apostado no empate.
     */
	public Match(Integer id, Integer idEvent, Team teamA, Integer scoreTeamA, float oddTeamA, float oddDraw, Team teamB,
			Integer scoreTeamB, float oddTeamB, String status, LocalDateTime date, float betAmountTeamA, float betAmountTeamB, float betAmountDraw) {
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
		this.betAmountTeamA = betAmountTeamA;
		this.betAmountTeamB = betAmountTeamB;
		this.betAmountDraw = betAmountDraw;
	}
	
	 /**
     * Construtor da classe Match sem o atributo utilizado para criação da partida.
     * 
     * @param idEvent Identificador do evento associado à partida.
     * @param teamA Time A.
     * @param scoreTeamA Pontuação do Time A.
     * @param oddTeamA Odd para a vitória do Time A.
     * @param oddDraw Odd para o empate.
     * @param teamB Time B.
     * @param scoreTeamB Pontuação do Time B.
     * @param oddTeamB Odd para a vitória do Time B.
     * @param status Status atual da partida.
     * @param date Data e hora da partida.
     * @param betAmountTeamA Valor apostado no Time A.
     * @param betAmountTeamB Valor apostado no Time B.
     * @param betAmountDraw Valor apostado no empate.
     */
	public Match(Integer idEvent, Team teamA, Integer scoreTeamA, float oddTeamA, float oddDraw, Team teamB,
			Integer scoreTeamB, float oddTeamB, String status, LocalDateTime date, float betAmountTeamA, float betAmountTeamB, float betAmountDraw) {
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
		this.betAmountTeamA = betAmountTeamA;
		this.betAmountTeamB = betAmountTeamB;
		this.betAmountDraw = betAmountDraw;
	}

    /**
     * Calcula as odds com base nos valores apostados e na margem de lucro.
     */
	public void calculateOdd() {
		
		float profit = 0.8f;
		float totalAmount = this.betAmountDraw + this.betAmountTeamA + this.betAmountTeamB;
		float totalAmountWithProfit = totalAmount * profit;
		
		if(betAmountTeamA * profit > 1) {
			this.oddTeamA = oddCeiling(totalAmountWithProfit / (betAmountTeamA * profit));
		}else {
			this.oddTeamA = oddCeiling(totalAmountWithProfit / betAmountTeamA);
		}
		if(betAmountTeamB * profit > 1) {
			this.oddTeamB = oddCeiling(totalAmountWithProfit / (betAmountTeamB * profit));
		}else {
			this.oddTeamB = oddCeiling(totalAmountWithProfit / betAmountTeamB);
		}
		if(betAmountDraw * profit  > 1) {
			this.oddDraw = oddCeiling(totalAmountWithProfit / (betAmountDraw * profit));
		}else {
			this.oddDraw = oddCeiling(totalAmountWithProfit / betAmountDraw);
		}
		
	}

	private float oddCeiling(float odd){
			if(odd<=12f){
				return odd;
			}
			return 12f;
	}
	
	//Getters e Setters

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
	
	public void setStatus(String status) {
		this.status = status;
	}

	public float getBetAmountTeamA() {
		return this.betAmountTeamA;
	}

	public void setBetAmountTeamA(float betAmountTeamA) {
		this.betAmountTeamA = betAmountTeamA;
	}

	public float getBetAmountDraw() {
		return this.betAmountDraw;
	}

	public void setBetAmountDraw(float betAmountDraw) {
		this.betAmountDraw = betAmountDraw;
	}

	public float getBetAmountTeamB() {
		return this.betAmountTeamB;
	}

	public void setBetAmountTeamB(float betAmountTeamB) {
		this.betAmountTeamB = betAmountTeamB;
	}

	
	@Override
    public String toString() {
        return getTeamA().getName() + " x " + getTeamB().getName();
    }
	
}