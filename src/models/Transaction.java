package models;

public class Transaction {
	
	private Integer id;
	private String type;
	private double value;
	
	public Transaction (Integer id, String type, double value) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
		
	}
	
	public Transaction (String type, double value) {
		super();
		this.type = type;
		this.value = value;
	}
	
	
	public Integer getId() {
		return id;
	}
	
	
	public String getType() {
		return type;
	}

	
	public double getValue() {
		return value;
	}
	

	public void setId(Integer id) {
		this.id = id;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public void setValue(double value) {
		this.value = value;
	}
	
	
	
	
	
	
}
