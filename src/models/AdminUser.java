package models;

import javax.swing.ImageIcon;
/**
 * A classe AdminUser representa um usuário administrador no sistema.
 * Os administradores têm permissões especiais, como criar eventos e partidas.
 */
public class AdminUser extends User {
	
	public AdminUser() {}

    /**
     * Construtor da classe AdminUser que inicializa todos os atributos.
     * 
     * @param id Identificador único do usuário.
     * @param name Nome do usuário.
     * @param profileImage Imagem de perfil do usuário.
     * @param cpf CPF do usuário.
     * @param email Email do usuário.
     * @param password Senha do usuário.
     * @param permission Permissão do usuário (deve ser "ADMIN" para um administrador).
     */
	public AdminUser(Integer id, String name,ImageIcon profileImage, String cpf, String email, String password,  String permission) {
		super(id, name, profileImage, cpf, email, password, permission);
	}

    /**
     * Construtor da classe AdminUser sem o atributo id.
     * 
     * @param name Nome do usuário.
     * @param profileImage Imagem de perfil do usuário.
     * @param cpf CPF do usuário.
     * @param email Email do usuário.
     * @param password Senha do usuário.
     * @param permission Permissão do usuário (deve ser "ADMIN" para um administrador).
     */
	public AdminUser(String name,ImageIcon profileImage, String cpf, String email, String password,  String permission) {
		super(name, profileImage, cpf, email, password, permission);
	}

	public Event createEvent() {
		return null;
	}
	
	public void removeEvent() {
	}
}
