package app;

import java.awt.EventQueue;

import app.auth.SignUpView;
import database.InitDatabase;

public class App {

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
