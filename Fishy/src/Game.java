import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Game implements Runnable{
	/**
	public static void main(String[] args) {
		FishCourt t = new FishCourt();
		JFrame f = new JFrame("Fishy Fishs Version");
		f.add(t);
		f.setVisible(true);
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setTitle("Moving Ball");
	}**/

	public void run() {
		final JFrame frame = new JFrame("Fishy");
		frame.setLocation(200, 100);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Swimming...");
		status_panel.add(status);

		// Main playing area
		final FishCourt court = new FishCourt(status);
		frame.add(court, BorderLayout.CENTER);

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("Play Again");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});
		control_panel.add(reset);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		court.reset();
		
		
		//Instructions Window
		final JFrame window = new JFrame("Instructions");
		window.setLocation(200, 600);
		window.setSize(600, 100);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		JPanel Score = new JPanel();
		
		
		


		
		JTextArea text = new JTextArea(
				"Welcome to Fishy!  The rules of the game are simple.  You start out as a small fish"
				+ " and get bigger by eating fish smaller than you.  You lose by getting eaten by"
				+ " a fish larger than you.  You control your fish using the four directional keys"
				);
		//JPanel instrpanel= new JPanel();
		//instrpanel.add(text);
		text.setLineWrap(true);
		window.add(text);
	}

	

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
