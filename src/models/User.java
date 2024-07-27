package models;

public abstract class User {
	
	
	private Integer id;
	private String name;
	private String email;
	private String password;
	private float balance;
	
	
	public User(Integer id, String name, String email, String password, float balance) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.balance = balance;
	}
	
	public User(String name, String email, String password, float balance) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.balance = balance;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	
	
	
	
}
