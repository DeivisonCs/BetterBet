package models;

public class AdminUser extends User {

	

	public AdminUser(Integer id, String name, int age, String email, String password, String permission, float balance) {
		super(id, name, age, email, password, permission, balance);
	}

	public AdminUser(String name, int age, String email, String password, String permission, float balance) {
		super(name, age, email, password, permission, balance);
	}

	public Event createEvent() {
		//Implementar
		return null;
	}
	
	public void removeEvent() {
		//Implementar
	}
	
}
