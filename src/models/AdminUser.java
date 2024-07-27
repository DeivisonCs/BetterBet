package models;

public class AdminUser extends User {

	

	public AdminUser(Integer id, String name, String email, String password, float balance) {
		super(id, name, email, password, balance);
		// TODO Auto-generated constructor stub
	}

	public AdminUser(String name, String email, String password, float balance) {
		super(name, email, password, balance);
		// TODO Auto-generated constructor stub
	}

	public Event createEvent() {
		//Implementar
		return null;
	}
	
	public void removeEvent() {
		//Implementar
	}
	
}
