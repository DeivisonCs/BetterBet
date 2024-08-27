package models;

import javax.swing.ImageIcon;

public class AdminUser extends User {
	
	public AdminUser() {}

	public AdminUser(Integer id, String name,ImageIcon profileImage, String cpf, String email, String password,  String permission) {
		super(id, name, profileImage, cpf, email, password, permission);
	}

	public AdminUser(String name,ImageIcon profileImage, String cpf, String email, String password,  String permission) {
		super(name, profileImage, cpf, email, password, permission);
	}
}