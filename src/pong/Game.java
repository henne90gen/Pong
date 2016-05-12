package pong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements Runnable, KeyListener {

	public static final String NAME = "Pong";
	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH * 9 / 16;
	public static final double UPDATES_PER_SECOND = 60D;
	public static final int PADDLE_WIDTH = 10;
	public static final int PADDLE_HEIGHT = 50;

	private boolean running = false;
	private long now = 0;

	private Window window;
	private int[] pixels;
	private Animation anim;
	private ScoreCounter scoreCounter;
	private Paddle leftPaddle, rightPaddle;
	private Ball ball;

	public Game() {
		running = true;
		window = new Window(WIDTH, HEIGHT, NAME, this);
		pixels = window.getPixels();
		anim = new Animation(5);
		scoreCounter = new ScoreCounter(WIDTH/2 - 10, 10);
		leftPaddle = new Paddle(4, 2, PADDLE_WIDTH, PADDLE_HEIGHT);
		rightPaddle = new Paddle(Game.WIDTH - 14, 2, PADDLE_WIDTH, PADDLE_HEIGHT);
		ball = new Ball(WIDTH / 2, HEIGHT / 2, 10);
	}

	@Override
	public void run() {

		long last = System.nanoTime();
		long timer = System.nanoTime();
		double nsPerUpdate = 1000000000D / UPDATES_PER_SECOND;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - last) / nsPerUpdate;
			last = now;

			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}

			render();
			frames++;

			if (System.nanoTime() - timer > 1000000000) {
				timer += 1000000000;
				window.setTitle(NAME + " ups: " + updates + ", fps: " + frames);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void update() {
		anim.update();
		leftPaddle.update();
		rightPaddle.update();
		ball.update();
		scoreCounter.addScore(ball.checkCollision(leftPaddle));
		scoreCounter.addScore(ball.checkCollision(rightPaddle));
	}

	private void render() {
		window.clear();
		anim.render(pixels);
		leftPaddle.render(pixels);
		rightPaddle.render(pixels);
		ball.render(pixels);
		scoreCounter.render(pixels);
		window.render();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			leftPaddle.move(-1);
		} else if (key == KeyEvent.VK_S) {
			leftPaddle.move(1);
		} else if (key == KeyEvent.VK_UP) {
			rightPaddle.move(-1);
		} else if (key == KeyEvent.VK_DOWN) {
			rightPaddle.move(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_S)
			leftPaddle.move(0);
		else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
			rightPaddle.move(0);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public synchronized void stop() {
		running = false;
	}

	public static void main(String[] args) {
		new Thread(new Game()).start();
	}
}
