package pong;

public class Animation {

	private int totalTime;
	private int color = 255;
	private int currCycle = 1;
	private boolean blackToWhite = true;
	// Number between 0 and 1 that defines the amount of that color that's going to be in the resulting color
	private double mulR, mulG, mulB;
	
	public Animation(int totalTime) {
		this.totalTime = totalTime;
		mulR = 1;
		mulG = 0.1;
		mulB = 0.1;
	}

	public void update() {
		color = (int) (255 / (totalTime * Game.UPDATES_PER_SECOND) * currCycle);
		if (currCycle < totalTime * Game.UPDATES_PER_SECOND && blackToWhite)
			currCycle++;
		else if (currCycle > 0) {
			blackToWhite = false;
			currCycle--;
		} else 
			blackToWhite = true;
	}

	public void render(int[] pixels) {
		for (int y = 0; y < Game.HEIGHT; y++) {
			for (int x = 0; x < Game.WIDTH; x++) {
				pixels[y * Game.WIDTH + x] = ((int)(color*mulR) << 16) + ((int)(color*mulG) << 8) + (int)(color*mulB);
			}
		}
	}
}
