package pong;

public class Ball extends GameObject {

	private int radius, xmove = 0, ymove = 0;
	private boolean reset = false;

	public Ball(int x, int y, int radius) {
		super(x, y, radius * 2, radius * 2);
		this.radius = radius;
		xmove = 5;
		ymove = 5;
	}

	@Override
	public void update() {
		if (x + xmove - radius > 0 && x + xmove + radius < Game.WIDTH) {
			x += xmove;
		}
		if (y + ymove - radius > 0 && y + ymove + radius < Game.HEIGHT) {
			y += ymove;
		} else {
			ymove = ymove * -1;
			y += ymove;
		}
	}

	@Override
	public void render(int[] pixels) {
		for (int row = y - radius; row < y + radius; row++) {
			for (int col = (int) (x - (Math.sqrt(radius * radius - (y - row) * (y - row)))); col < x
					+ (Math.sqrt(radius * radius - (y - row) * (y - row))); col++) {
				pixels[row * Game.WIDTH + col] = (255 << 16) + (255 << 8) + 255;
			}
		}
	}

	public int checkCollision(Paddle p) {
		if (p.getX() < Game.WIDTH / 2) {
			if (y > p.getY() && y < p.getY() + p.getHeight()) {
				if (x - radius < p.getX() + p.getWidth() && xmove < 0) {
					xmove = xmove * -1;
					return 0;
				}
			} else {
				if (x - radius < p.getX() + p.getWidth()) {
					x = Game.WIDTH / 2;
					y = Game.HEIGHT / 2;
					xmove = xmove * -1;
					return -1;
				}
			}
		} else {
			if (y > p.getY() && y < p.getY() + p.getHeight()) {
				if (x + radius > p.getX() && xmove > 0) {
					xmove = xmove * -1;
					return 0;
				}
			} else {
				if (x + radius > p.getX()) {
					x = Game.WIDTH / 2;
					y = Game.HEIGHT / 2;
					xmove = xmove * -1;
					return 1;
				}
			}
		}
		return 0;
	}

	public boolean isReset() {
		if (reset) {
			reset = false;
			return true;
		} else
			return false;
	}
}
