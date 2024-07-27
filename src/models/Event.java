package models;

import java.time.LocalDateTime;

public class Event {
	
	private String id;
	private LocalDateTime date;
	private String modality;
	private String name;
	
	
	public Event(String id, LocalDateTime date, String modality, String name) {
		super();
		this.id = id;
		this.date = date;
		this.modality = modality;
		this.name = name;
	}

		
	public Event(LocalDateTime date, String modality, String name) {
		super();
		this.date = date;
		this.modality = modality;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
	
	
	
}
