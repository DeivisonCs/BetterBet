package models;

public abstract class User {
	
	private Integer id;
	private String name;
	private String cpf;
	private String email;
	private String password;
	private String permission;
	
	public User(){}
	
	public User(Integer id, String name, String cpf, String email, String password,  String permission) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
		this.permission = permission;
	}
	
	public User(String name, String cpf, String email, String password,  String permission) {
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.password = password;
		this.permission = permission;
	}


	// Getters and Setters
	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getPermission() {return permission;}
	public void setPermission(String permission) {this.permission = permission;}
	public String getCpf() {return cpf;}
	public void setCpf(String cpf) {this.cpf = cpf;}
	

    public String toString() {
        return "Name: " + this.name +
            " | Email: " + this.email +
            " | Password: " + this.password +
            " | Permission: " + this.permission;
    }
}
