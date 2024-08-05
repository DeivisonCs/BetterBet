package middleware;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import models.CommonUser;

public class UserMiddleware {
	public String verifyNewUser(CommonUser user) {
		
		String nullFields = checkCommonUserFields(user);
		if(nullFields != "200") {
			return nullFields; 
		}
		
		if(!isOlderThan18(user.getBirthDate())) {
			return "É necessário mais de 18 anos para criar conta na BetterBet!";
		}
		
		return "200";
	}
	
	private String checkCommonUserFields(CommonUser user) {
		
		if(user.getName() == null) {
			return "Nome não pode ser nulo";
		}
		if(user.getCpf() == null) {
			return "CPF não pode ser nulo";
		}
		if(user.getEmail() == null) {
			return "Email não pode ser nulo";
		}
		if(user.getPassword() == null) {
			return "Senha não pode ser nula";
		}
		
		return "200";
	}
	
	private boolean isOlderThan18(String date) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate birthDate = LocalDate.parse(date, dateFormat);
		LocalDate dateNow = LocalDate.now();
		
		int age = Period.between(birthDate, dateNow).getYears();
		
		return age>=18;
	}
}
