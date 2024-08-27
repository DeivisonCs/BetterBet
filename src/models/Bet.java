package models;

/**
 * A classe Bet representa uma aposta em um evento esportivo.
 * Ela armazena as odds para os diferentes resultados de um jogo no momento da aposta e as informações sobre o ticket e a aposta selecionada.
 */
public class Bet {
	private Integer id;
	private float oddTeamA;
	private float oddTeamB;
	private float oddDraw;
	private Match match;
	private Integer idTicket;
	private String selectedBet;
	
    /**
     * Construtor da classe Bet que inicializa todos os atributos.
     * 
     * @param id Identificador único da aposta.
     * @param oddTeamA Odds para o time A vencer.
     * @param oddTeamB Odds para o time B vencer.
     * @param oddDraw Odds para um empate.
     * @param match Jogo ao qual a aposta está associada.
     * @param idTicket Identificador do ticket associado à aposta.
     * @param selectedBet Resultado selecionado para a aposta.
     */
	public Bet(Integer id, float oddTeamA, float oddTeamB,float oddDraw, Match match, Integer idTicket, String selectedBet) {
		super();
		this.id = id;
		this.oddTeamA = oddTeamA;
		this.oddTeamB = oddTeamB;
		this.oddDraw = oddDraw;
		this.match = match;
		this.idTicket = idTicket;
		this.selectedBet = selectedBet;
	}

    /**
     * Construtor da classe Bet sem o atributo id.
     * 
     * @param oddTeamA Odds para o time A vencer.
     * @param oddTeamB Odds para o time B vencer.
     * @param oddDraw Odds para um empate.
     * @param match Jogo ao qual a aposta está associada.
     * @param idTicket Identificador do ticket associado à aposta.
     * @param selectedBet Resultado selecionado para a aposta.
     */
	public Bet(float oddTeamA, float oddTeamB,float oddDraw, Match match, Integer idTicket, String selectedBet) {
		super();
		this.oddTeamA = oddTeamA;
		this.oddTeamB = oddTeamB;
		this.oddDraw = oddDraw;
		this.match = match;
		this.idTicket = idTicket;
		this.selectedBet = selectedBet;
	}

    /**
     * Construtor da classe Bet sem os atributos id e idTicket.
     * 
     * @param oddTeamA Odds para o time A vencer.
     * @param oddTeamB Odds para o time B vencer.
     * @param oddDraw Odds para um empate.
     * @param match Jogo ao qual a aposta está associada.
     * @param selectedBet Resultado selecionado para a aposta.
     */
	public Bet(float oddTeamA, float oddTeamB,float oddDraw, Match match, String selectedBet) {
		super();
		this.oddTeamA = oddTeamA;
		this.oddTeamB = oddTeamB;
		this.oddDraw = oddDraw;
		this.match = match;
		this.selectedBet = selectedBet;
	}
	
	
	//Getters e Setters
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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
