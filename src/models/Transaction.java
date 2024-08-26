package models;

import java.time.LocalDateTime;

public class Transaction {
	
	private Integer id;
	private Integer userId;
	private String type;
	private Double value;
	private LocalDateTime date; 
	
	public Transaction (Integer id, Integer userId, String type, Double value, LocalDateTime date) {
		super();
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.value = value;
		this.date = date;
		
	}
	
	public Transaction (Integer userId, String type, Double value, LocalDateTime date) {
		super();
		this.userId = userId;
		this.type = type;
		this.value = value;
		this.date = date;
	}
	
	
	public Integer getId() {
		return id;
	}
	
	
	public String getType() {
		return type;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public double getValue() {
		return value;
	}
	
	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public void setValue(Double value) {
		this.value = value;
	}
	
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
	
	
}
