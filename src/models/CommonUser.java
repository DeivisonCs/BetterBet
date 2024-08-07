package models;

public class CommonUser extends User {
	private float balance;
	private String address;
	private String birthDate;
	
	public CommonUser(){}
	
	public CommonUser(Integer id, String name, String cpf, String birthDate, String email, String address, String password,  String permission, float balance) {
		super(id, name, cpf, email, password, permission);
		
		this.birthDate = birthDate;
		this.address= address;
		this.balance = balance;
	}

	public CommonUser(String name, String cpf, String birthDate, String email, String address, String password,  String permission, float balance) {
		super(name, cpf, email, password, permission);
		
		this.birthDate = birthDate;
		this.address= address;
		this.balance = balance;
	}
	
	public float getBalance() {return this.balance;}
	public void setBalance(float balance) {this.balance = balance;}
	public String getBirthDate() {return birthDate;}
	public void setBirthDate(String birthDate) {this.birthDate = birthDate;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}

	@Override
    public String toString() {
        return "CommonUser{\n"+ 
				"\tName: " + this.getName() +
				" | Email: " + this.getEmail() +
				" | CPF: " + this.getCpf() +
				" | BirthDate: " + this.getBirthDate() +
				" | Address: " + this.getAddress() +
				" | Password: " + this.getPassword() +
				" | Balance: " + this.getBalance() +
				" | Permission: " + this.getPermission() +
			"\n}";
    }
}
