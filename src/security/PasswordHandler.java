package security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * A classe PasswordHandler é responsável por fornecer métodos para criptografar
 * senhas e validá-las usando o algoritmo BCrypt. Ela oferece métodos para
 * gerar o hash de uma senha e verificar se uma senha fornecida corresponde a um
 * hash armazenado.
 */
public class PasswordHandler {
	
    /**
     * Gera um hash seguro para a senha fornecida utilizando o algoritmo BCrypt.
     *
     * @param password A senha em texto que será criptografada.
     * @return Uma string contendo o hash criptografado da senha.
     */
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
    /**
     * Verifica se a senha fornecida em texto plano corresponde ao hash criptografado
     * armazenado.
     *
     * @param password A senha em texto que será verificada.
     * @param encryptedPassword O hash criptografado armazenado da senha.
     * @return true se a senha corresponde ao hash, false caso contrário.
     */
	public static boolean validPassword(String password, String encriptedPassword) {
		return BCrypt.checkpw(password, encriptedPassword);
	}
}
