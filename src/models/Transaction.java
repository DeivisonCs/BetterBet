package models;

public class Transaction {
	
	private Integer id;
	private Integer userId;
	private String type;
	private double value;
	
	public Transaction (Integer id, Integer userId, String type, double value) {
		super();
		this.id = id;
		this.userId = userId;
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
	
	public Integer getUserId() {
		return userId;
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
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public void setValue(double value) {
		this.value = value;
	}
	
	
	
	
	
	
}
