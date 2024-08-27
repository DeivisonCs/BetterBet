package models;


/**
 * A classe Team representa um time ou equipe no sistema.
 * Ela contém informações básicas sobre o time, como o nome e o esporte ao qual pertence.
 */
public class Team {
	
	private Integer id;
	private String name;
	private String sport;
	
    /**
     * Construtor da classe Team que inicializa todos os atributos.
     * 
     * @param id Identificador único do time.
     * @param name Nome do time.
     * @param sport Esporte ao qual o time pertence.
     */
	public Team(Integer id, String name, String sport) {
		super();
		this.id = id;
		this.name = name;
		this.sport = sport;
	}
	
	
	/**
     * Construtor da classe Team sem o atributo id.
     * 
     * @param name Nome do time.
     * @param sport Esporte ao qual o time pertence.
     */
	public Team(String name, String sport) {
		super();
		this.name = name;
		this.sport = sport;
	}
	
	
	//Getters e Setters
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}
	
	@Override
    public String toString() {
        return getName();
    }
	
}