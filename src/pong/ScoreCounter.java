package pong;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScoreCounter extends GameObject {

	private BufferedImage image;
	private int leftScore, rightScore, numHeight, numWidth;
	private int[][] numbers;

	public ScoreCounter(int x, int y) {
		super(x, y, 0, 0);

		leftScore = 0;
		rightScore = 0;

		try {
			image = ImageIO.read(new File("./res/numbers.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		numHeight = image.getHeight() * 2;
		numWidth = image.getWidth() / 10 * 2;

		setWidth(3 * numWidth);
		setHeight(numHeight);
		setX(Game.WIDTH / 2 - getWidth() / 2);

		byte[] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		numbers = new int[10][numHeight * numWidth];

		for (int i = 0; i < numbers.length; i++) {
			for (int row = 0; row < numHeight / 2; row++) {
				for (int col = 0; col < numWidth / 2; col++) {
					int curCoord = row * image.getWidth() + i * numWidth / 2 + col;
					int color = (((bytes[curCoord * 3 + 2] == -1) ? 255 : bytes[curCoord * 3 + 2]) << 16) +
							(((bytes[curCoord * 3 + 1] == -1) ? 255 : bytes[curCoord * 3 + 1]) << 8) +
							((bytes[curCoord * 3] == -1) ? 255 : bytes[curCoord * 3]);
					if (color == 0xff00ff)
						color = 0xffffff;
					numbers[i][row * 2 * numWidth + col * 2] = color;
					numbers[i][(row * 2 + 1) * numWidth + col * 2] = color;
					numbers[i][(row * 2 + 1) * numWidth + col * 2 + 1] = color;
					numbers[i][row * 2 * numWidth + col * 2 + 1] = color;
				}
			}
		}
	}

	public void addScore(int player) {
		if (player > 0) {
			if (leftScore < 9)
				leftScore++;
			else {
				// TODO: Add Game Over state
				leftScore = 0;
				rightScore = 0;
				System.out.println("Game over! Right player won.");
			}
		} else if (player < 0) {
			if (rightScore < 9)
				rightScore++;
			else {
				// TODO: Add Game Over state
				leftScore = 0;
				rightScore = 0;
				System.out.println("Game over! Left player won.");
			}
		}
	}

	@Override
	public void render(int[] pixels) {

		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < numWidth; col++) {
				if (numbers[leftScore][row * numWidth + col] != 0xffffff)
					pixels[(y + row) * Game.WIDTH + x + col] = 0xffffff/*numbers[leftScore][row * numWidth + col]*/;
			}
		}

		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				if (col > getWidth() / 2 - 3 && col < getWidth() / 2 + 3)
					pixels[(y + row) * Game.WIDTH + x + col] = 0xffffff;
			}
		}

		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < numWidth; col++) {
				if (numbers[rightScore][row * numWidth + col] != 0xffffff)
					pixels[(y + row) * Game.WIDTH + x + numWidth * 2 + col] = 0xffffff /*numbers[rightScore][row * numWidth + col]*/;
			}
		}
	}

	@Override
	public void update() {

	}
}
