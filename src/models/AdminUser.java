package models;

public class AdminUser extends User {

	

	public AdminUser(Integer id, String name, int age, String email, String password, String permission) {
		super(id, name, age, email, password, permission);
	}

	public AdminUser(String name, int age, String email, String password, String permission) {
		super(name, age, email, password, permission);
	}

	public Event createEvent() {
		return null;
	}
	
	public void removeEvent() {
	}
}
