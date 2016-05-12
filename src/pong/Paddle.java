package pong;

public class Paddle extends GameObject {

	public static final int SPEED = 5;

	private int movement;

	public Paddle(int x, int y, int width, int height) {
		super(x, y, width, height);
		movement = 0;
	}

	@Override
	public void update() {
		if (y + movement > 0 && y + movement + height < Game.HEIGHT) {
			y += movement;
		}
	}

	@Override
	public void render(int[] pixels) {
		for (int row = y; row < y + height; row++) {
			for (int col = x; col < x + width; col++) {
				pixels[row * Game.WIDTH + col] = (255 << 16) + (255 << 8) + 255;
			}
		}
	}

	public void move(int dir) {
		movement = SPEED * dir;
	}
}
