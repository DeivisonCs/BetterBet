package models;

public class Team {
	
	private Integer id;
	private String name;
	private String sport;
	
	
	public Team(Integer id, String name, String sport) {
		super();
		this.id = id;
		this.name = name;
		this.sport = sport;
	}
	
	
	public Team(String name, String sport) {
		super();
		this.name = name;
		this.sport = sport;
	}
	
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