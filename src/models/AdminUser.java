package models;


public class AdminUser extends User {

	

	public AdminUser(Integer id, String name, String cpf, String email, String password,  String permission, float balance) {
		super(id, name, cpf, email, password, permission);
	}

	public AdminUser(String name, String cpf, String email, String password,  String permission, float balance) {
		super(name, cpf, email, password, permission);
	}

	public Event createEvent() {
		return null;
	}
	
	public void removeEvent() {
	}
}
