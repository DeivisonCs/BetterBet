package models;

public class CommonUser extends User {

	public CommonUser(){super();}

	public CommonUser(Integer id, String name, int age, String email, String password, String permission, float balance) {
		super(id, name, age, email, password, permission, balance);
	}

	public CommonUser(String name, int age, String email, String password, String permission, float balance) {
		super(name, age, email, password, permission, balance);
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
