package app.historyView;

import java.awt.Color;

import javax.swing.JFrame;

public class HistoryView {
	
	private JFrame frame;
	/**
	 * Launch the application.
	 */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    SwingUtilities.invokeLater(() -> {
//                    	CommonUser commonUser = new CommonUser(1, "John Doe", 30, "johndoe@example.com", "password123", "user", 500.0f);
//                        HomeUserUI window = new HomeUserUI(commonUser);
//                        window.frame.setVisible(true);
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

	/**
	 * Create the application.
	 */
	public HistoryView(Integer userId) {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.getContentPane().setBackground(new Color(40, 40, 40));
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		frame.setBounds(100, 100, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

}
