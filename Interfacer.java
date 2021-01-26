package buzz;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Provides an updated JPanel method for rendering the interface
 * of the game Buzz
 * 
 * @author Darren McCartney
 * @version 1.0
 * @see javax.swing.JPanel
 * @see Buzz
 *
 */

public class Interfacer extends JPanel {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Buzz.buzz.render(g);
	}
}
