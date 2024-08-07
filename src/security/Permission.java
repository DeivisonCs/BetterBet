package security;

public enum Permission {
	SUPERUSER("superUser"),
	ADMIN("admin"),
	USER("user");
	
	private final String typeUser;
	
	private Permission(String permission){
		this.typeUser = permission;
	}
	
	public String getTypeUser() {
		return typeUser;
	}
}
