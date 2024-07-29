package models;

public class CommonUser extends User {
	private float balance;

	public CommonUser(){}
	
	public CommonUser(Integer id, String name, int age, String email, String password, String permission, float balance) {
		super(id, name, age, email, password, permission);
		
		this.balance = balance;
	}

	public CommonUser(String name, int age, String email, String password, String permission, float balance) {
		super(name, age, email, password, permission);
		
		this.balance = balance;
	}
	
	public float getBalance() {
		return this.balance;
	}
	
	public void setBalance(float balance) {
		this.balance = balance;
	}

	@Override
    public String toString() {
        return "CommonUser{\n"+ 
				"\tName: " + this.getName() +
				" | Email: " + this.getEmail() +
				" | Password: " + this.getPassword() +
				" | Balance: " + this.getBalance() +
				" | Permission: " + this.getPermission() +
			"\n}";
    }
}
