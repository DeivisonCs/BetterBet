package middleware;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import models.CommonUser;
import models.User;

public class UserMiddleware {
	private final String VALIDATED = "200";
	
	public String verifyNewUser(CommonUser user, String confirmPassword) {
		System.out.println("Middleware " + user.toString());
		
		// Validação do nome
		String validName = validName(user.getName());
		if(!validName.equals(VALIDATED)) {
			return validName;
		}
		
		// Valida CPF
		String validCpf = validCpf(user.getCpf());
		if(!validCpf.equals(VALIDATED)) {
			return validCpf;
		}
		
		// Valida Email
		String validEmail = validEmail(user.getEmail());
		if(!validEmail.equals(VALIDATED)) {
			return validEmail;
		}
		
		// Varifica se é maior de idade
		if(!isOlderThan18(user.getBirthDate())) {
			return "É necessário mais de 18 anos para criar conta na BetterBet!";
		}	
		
		// Validação senha
		String validPassword = validPassword(user.getPassword(), confirmPassword);
		if(!validPassword.equals(VALIDATED)) {
			return validPassword;
		}
		
		return VALIDATED;
	}
	
	
	private String validName(String name) { 
		if(name == null || name.equals("")) {
			return "Nome não pode ser nulo";
		}
		if(name.length() < 3) {
			return "Insira um nome válido!";
		}
		if(name.chars().anyMatch(Character::isDigit)) {
			return "Nome não deve conter números!";
		}
		
		return VALIDATED;
	}
	
	private String validCpf(String cpf) {
		if(cpf == null || cpf.equals("")) {
			return "CPF não pode ser nulo!";
		}
		if(cpf.length() != 14) {
			return "Insira um CPF válido!";
		}
		if(cpf.chars().mapToObj(c -> (char) c).anyMatch(Character::isAlphabetic)) {
			return "Insira um cpf válido";
		}
		
		return VALIDATED;
	}
	
	private String validEmail(String email) {
		if(email == null || email.equals("")) {
			return "Email não pode ser nulo!";
		}
		if(email.contains(" ")) {
			return "Email não deve conter espaços em branco!";
		}
		
		return VALIDATED;
	}
	
	
	private String validPassword(String password, String confirmPassword) {
		if(password == null || password.equals("")) {
			return "Senha não pode ser nula!";
		}
		if(password.length() < 3) {
			return "Senha deve conter ao menos 3 caracteres!";
		}
		if(confirmPassword == null) {
			return "Confirmar Senha não pode ser nulo!";
		}
		if(!password.equals(confirmPassword)) {
			return "As senhas não coincidem!";
		}
		if(password.contains(" ")) {
			return "Senha não deve conter espaços em branco!";
		}
		
		return VALIDATED;
	}
	
	
	private boolean isOlderThan18(String date) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate birthDate = LocalDate.parse(date, dateFormat);
		LocalDate dateNow = LocalDate.now();
		
		int age = Period.between(birthDate, dateNow).getYears();
		
		return age>=18;
	}
	

	public boolean isInsufficientBalance(CommonUser user, float betAmount) {
		return user.getBalance() - betAmount < 0;
	}
}
