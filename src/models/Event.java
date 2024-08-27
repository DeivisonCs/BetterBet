package models;

import java.time.LocalDateTime;

/**
 * A classe Event representa um evento esportivo no sistema.
 * Ela contém informações sobre a data do evento, a modalidade e o nome do evento.
 */
public class Event {
	
	private Integer id;
	private LocalDateTime date;
	private String modality;
	private String name;
	
    /**
     * Construtor da classe Event que inicializa todos os atributos.
     * 
     * @param id Identificador único do evento.
     * @param date Data e hora em que o evento ocorre.
     * @param modality Modalidade do evento.
     * @param name Nome do evento.
     */
	public Event(Integer id, LocalDateTime date, String modality, String name) {
		super();
		this.id = id;
		this.date = date;
		this.modality = modality;
		this.name = name;
	}

	/**
     * Construtor da classe Event sem o atributo id, utilizado para criar o evento.
     * 
     * @param date Data e hora em que o evento ocorre.
     * @param modality Modalidade do evento.
     * @param name Nome do evento.
     */
	public Event(LocalDateTime date, String modality, String name) {
		super();
		this.date = date;
		this.modality = modality;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public String getModality() {
		return modality;
	}
	
	public void setModality(String modality) {
		this.modality = modality;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
    public String toString() {
        return getName();
    }
	
}