package models;

import java.time.LocalDateTime;

public class Ticket {

	private Integer id;
	private Integer idUser;
	private LocalDateTime timeStamp;
	private float odd;
	private float expectedProfit;
	private float amount;
	
	
	public Ticket(Integer id, float odd, LocalDateTime timeStamp, Integer idUser, float expectedProfit, float amount) {
		super();
		this.id = id;
		this.odd = odd;
		this.timeStamp = timeStamp;
		this.idUser = idUser;
		this.expectedProfit = expectedProfit;
		this.amount = amount;
	}
	

	public Ticket(float odd, Integer idUser, float expectedProfit, float amount) {
		super();
		this.odd = odd;
		this.idUser = idUser;
		this.expectedProfit = expectedProfit;
		this.amount = amount;
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
	
	
	
}
