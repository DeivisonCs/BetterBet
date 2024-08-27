package app;

import java.awt.EventQueue;

import app.auth.SignUpView;
import database.InitDatabase;

/**
 * A classe principal da aplicação, responsável por iniciar o processo de execução do aplicativo.
 */
public class App {

	 /**
     * Método principal que é o ponto de entrada da aplicação.
     * 
     * @param args Argumentos da linha de comando (não utilizados neste caso).
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitDatabase.initializeDatabase();
					new SignUpView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
