package models;

public class User {
	
	private Integer id;
	private String name;
	private int age;
	private String email;
	private String password;
	private String permission;

	public User(){}
	
	public User(Integer id, String name, int age, String email, String password,  String permission) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.password = password;
		this.permission = permission;
	}
	
	public User(String name, int age, String email, String password,  String permission) {
		this.name = name;
		this.age = age;
		this.email = email;
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
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public String getPermission() {return permission;}
	public void setPermission(String permission) {this.permission = permission;}

    public String toString() {
        return "Name: " + this.name +
            " | Email: " + this.email +
            " | Password: " + this.password +
            " | Permission: " + this.permission;
    }
}
