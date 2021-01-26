package buzz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Creates a side scroller, flight path game with a simple UI. Users have to fly
 * between a number of gates, each one increasing their score.
 * 
 * @author Darren McCartney
 * @version 1.0
 *
 */

public class Buzz implements ActionListener, MouseListener, KeyListener {

	public static Buzz buzz;
	public final int WIDTH = 800;
	public final int HEIGHT = 800;
	public Interfacer interfacer;
	public Rectangle fly;
	public ArrayList<Rectangle> gates;
	public int ticks;
	public int yMotion;
	public int score;
	public boolean gameOver; 
	public boolean started;
	public Random r;
	public static final Color BROWN = new Color(102,51,0);
	
	/**
	 * Creates the frame for the game, adds vital components for input,
	 * initialises game fly and gates
	 */

	public Buzz() {
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);

		interfacer = new Interfacer();
		r = new Random();

		jframe.add(interfacer);
		jframe.setTitle("Buzz");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);

		fly = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 15);
		gates = new ArrayList<Rectangle>();

		addGate(true);
		addGate(true);
		addGate(true);
		addGate(true);

		timer.start();
	}
	
	/**
	 * Creates the gates using two Rectangles with random positioning of the opening each time
	 * 
	 * @param start allows determination of whether the gate is the first gate or not
	 */

	public void addGate(boolean start) {
		int space = 300;
		int width = 100;
		int height = 50 + r.nextInt(300);

		if (start) {
			gates.add(new Rectangle(WIDTH + width + gates.size() * 300, HEIGHT - height - 120, width, height));
			gates.add(new Rectangle(WIDTH + width + (gates.size() - 1) * 300, 0, width, HEIGHT - height - space));
		} else {
			gates.add(new Rectangle(gates.get(gates.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			gates.add(new Rectangle(gates.get(gates.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	/**
	 * Renders the gates on the interface with correct colour and sizing
	 * 
	 * @param g instance of Graphics 
	 * @param gate the object to be rendered
	 */
	
	public void displayGate(Graphics g, Rectangle gate) {
		g.setColor(BROWN);
		g.fillRect(gate.x, gate.y, gate.width, gate.height);
	}
	
	/**
	 * Allows the user to fly through the gates by increasing y position
	 */

	public void jump() {
		if (gameOver) {
			fly = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 15);
			gates.clear();
			yMotion = 0;
			score = 0;

			addGate(true);
			addGate(true);
			addGate(true);
			addGate(true);

			gameOver = false;
		}

		if (!started) {
			started = true;
		} else if (!gameOver) {
			if (yMotion > 0) {
				yMotion = 0;
			}

			yMotion -= 10;
		}
	}
	
	/**
	 * Keeps track of score and collisions which end the game
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = 10;

		ticks++;

		if (started) {
			for (int i = 0; i < gates.size(); i++) {
				Rectangle gate = gates.get(i);

				gate.x -= speed;
			}

			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2;
			}

			for (int i = 0; i < gates.size(); i++) {
				Rectangle gate = gates.get(i);

				if (gate.x + gate.width < 0) {
					gates.remove(gate);

					if (gate.y == 0) {
						addGate(false);
					}
				}
			}

			fly.y += yMotion;

			for (Rectangle gate : gates) {
				if (gate.y == 0 && fly.x + fly.width / 2 > gate.x + gate.width / 2 - 10
						&& fly.x + fly.width / 2 < gate.x + gate.width / 2 + 10) {
					score++;
				}

				if (gate.intersects(fly)) {
					gameOver = true;

					if (fly.x <= gate.x) {
						fly.x = gate.x - fly.width;

					} else {
						if (gate.y != 0) {
							fly.y = gate.y - fly.height;
						} else if (fly.y < gate.height) {
							fly.y = gate.height;
						}
					}
				}
			}

			if (fly.y > HEIGHT - 120 || fly.y < 0) {
				gameOver = true;
			}

			if (fly.y + yMotion >= HEIGHT - 120) {
				fly.y = HEIGHT - 120 - fly.height;
				gameOver = true;
			}
		}

		interfacer.repaint();
	}
	
	/**
	 * Renders the interface of the game 
	 * 
	 * @param g instance of Graphics
	 */

	public void render(Graphics g) {
		g.setColor(Color.green.darker().darker());
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);

		g.setColor(Color.blue.darker().darker());
		g.fillRect(fly.x, fly.y, fly.width, fly.height);

		for (Rectangle gate : gates) {
			displayGate(g, gate);
		}

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 50));

		if (!started) {
			g.drawString("Use spacebar or mouseclick", 60, HEIGHT / 2 - 150);
			g.drawString("to pass through the hedge", 80, HEIGHT / 2 - 100);
			g.drawString("Click to start!", 240, HEIGHT / 2 + 100);
		}

		if (gameOver) {
			g.setFont(new Font("Arial", 1, 100));
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
		}

		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	public static void main(String[] args) {
		buzz = new Buzz();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

}