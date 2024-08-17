package security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHandler {
	
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public static boolean validPassword(String password, String encriptedPassword) {
		return BCrypt.checkpw(password, encriptedPassword);
	}
}

//
//import java.security.SecureRandom;
//import java.util.Base64;
//
//public class PasswordHandler {
//	private final int ITERATIONS = 10000;
//	private final int KEY_LENGTH = 256; // Em bits
//	private final SecureRandom rand = new SecureRandom();
//	
//	public String generateSalt() {
//		byte[] salt = new byte[16]; // Gera um array de 16 bits para armazenar o salt
//		rand.nextBytes(salt); // Preenche o array com byte aleatórios
//		return Base64.getEncoder().encodeToString(salt); // Converte o salt em string
//	}
//	
//	public String hashPassword(String password, String salt) {
//		
//	}
//}
