package middleware;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import exceptions.InvalidAddressException;
import exceptions.InvalidBirthDateException;
import exceptions.InvalidCpfException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPasswordException;
import models.CommonUser;
import models.User;

public class UserMiddleware {
	
	public void verifyNewUser(User user, String confirmPassword) throws InvalidNameException, InvalidCpfException, InvalidAddressException, InvalidEmailException, InvalidBirthDateException, InvalidPasswordException {
		System.out.println("Middleware " + user.toString());
		
		// Validação do nome
		validName(user.getName());
		
		// Valida CPF
		validCpf(user.getCpf());
		
		//Valida Address
		if(user.getPermission().equals("user")) {
			validAddress(((CommonUser) user).getAddress());
		}
		
		// Valida Email
		validEmail(user.getEmail());

		// Varifica se é maior de idade
		if(user.getPermission().equals("user") && !isOlderThan18(((CommonUser)user).getBirthDate())) {
			throw new InvalidBirthDateException("É necessário mais de 18 anos para criar conta na BetterBet!");
		}	
		
		// Validação senha
		validPassword(user.getPassword(), confirmPassword);
		
	}
	
	public void updateUser(User user, String newPassword, String confirmPassword) throws InvalidNameException, InvalidEmailException, InvalidPasswordException, InvalidAddressException {
		System.out.println("Update Middleware " + user.toString());
		
		// Validação do nome
		validName(user.getName());

		// Valida Email
		validEmail(user.getEmail());
		
		//Valida Endereço
		if(user.getPermission().equals("user")) {
			validAddress(((CommonUser) user).getAddress());
		}

		// Validação senha
		if(newPassword != null) {
			validPassword(newPassword, confirmPassword);
		}
	}
	
	
	private void validName(String name) throws InvalidNameException { 
		if(name == null || name.equals("")) {
			throw new InvalidNameException("Nome não pode ser nulo");
		}
		if(name.length() < 3) {
			throw new InvalidNameException("Insira um nome válido!");
		}
		if(name.chars().anyMatch(Character::isDigit)) {
			throw new InvalidNameException("Nome não deve conter números!");
		}
	}
	
	private void validCpf(String cpf) throws InvalidCpfException {
		if(cpf == null || cpf.equals("")) {
			throw new InvalidCpfException("CPF não pode ser nulo!");
		}
		if(cpf.length() != 14) {
			throw new InvalidCpfException("Insira um CPF válido!");
		}
		if(cpf.chars().mapToObj(c -> (char) c).anyMatch(Character::isAlphabetic)) {
			throw new InvalidCpfException("Insira um cpf válido");
		}
	}
	
	private void validEmail(String email) throws InvalidEmailException {
		if(email == null || email.equals("")) {
			throw new InvalidEmailException("Email não pode ser nulo!");
		}
		if(email.contains(" ")) {
			throw new InvalidEmailException("Email não deve conter espaços em branco!");
		}
	}
	
	private void validAddress(String address) throws InvalidAddressException {
		if(address == null || address.equals("")) {
			throw new InvalidAddressException("Endereço não pode ser nulo!");
		}
	}
	
	
	private void validPassword(String password, String confirmPassword) throws InvalidPasswordException {
		if(password == null || password.equals("")) {
			throw new InvalidPasswordException("Senha não pode ser nula!");
		}
		if(password.length() < 3) {
			throw new InvalidPasswordException("Senha deve conter ao menos 3 caracteres!");
		}
		if(confirmPassword == null) {
			throw new InvalidPasswordException("Confirmar Senha não pode ser nulo!");
		}
		if(!password.equals(confirmPassword)) {
			throw new InvalidPasswordException("As senhas não coincidem!");
		}
		if(password.contains(" ")) {
			throw new InvalidPasswordException("Senha não deve conter espaços em branco!");
		}
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
