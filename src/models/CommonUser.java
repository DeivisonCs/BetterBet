package models;

import javax.swing.ImageIcon;

/**
 * A classe CommonUser representa um usuário comum no sistema.
 * Ela herda da classe User e adiciona atributos específicos como saldo, endereço e data de nascimento.
 */
public class CommonUser extends User {
	private float balance;
	private String address;
	private String birthDate;
	
	public CommonUser(){}
	
	
	 /**
     * Construtor da classe CommonUser que inicializa todos os atributos.
     * 
     * @param id Identificador único do usuário.
     * @param name Nome do usuário.
     * @param profileImage Imagem de perfil do usuário.
     * @param cpf CPF do usuário.
     * @param birthDate Data de nascimento do usuário.
     * @param email Email do usuário.
     * @param address Endereço do usuário.
     * @param password Senha do usuário.
     * @param permission Permissão do usuário.
     * @param balance Saldo atual do usuário.
     */
	public CommonUser(Integer id, String name, ImageIcon profileImage, String cpf, String birthDate, String email, String address, String password,  String permission, float balance) {
		super(id, name, profileImage, cpf, email, password, permission);
		
		this.birthDate = birthDate;
		this.address= address;
		this.balance = balance;
	}

	
	 /**
     * Construtor da classe CommonUser sem o atributo id.
     * 
     * @param name Nome do usuário.
     * @param profileImage Imagem de perfil do usuário.
     * @param cpf CPF do usuário.
     * @param birthDate Data de nascimento do usuário.
     * @param email Email do usuário.
     * @param address Endereço do usuário.
     * @param password Senha do usuário.
     * @param permission Permissão do usuário.
     * @param balance Saldo atual do usuário.
     */
	public CommonUser(String name, ImageIcon profileImage, String cpf, String birthDate, String email, String address, String password,  String permission, float balance) {
		super(name, profileImage, cpf, email, password, permission);
		
		this.birthDate = birthDate;
		this.address= address;
		this.balance = balance;
	}
	
	//Getters e Setters
	public float getBalance() {return this.balance;}
	public void setBalance(float balance) {this.balance = balance;}
	public String getBirthDate() {return birthDate;}
	public void setBirthDate(String birthDate) {this.birthDate = birthDate;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}

	
	 /**
     * Retorna uma representação em string do usuário comum, incluindo todos os atributos relevantes.
     * 
     * @return String com as informações do usuário.
     */
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
