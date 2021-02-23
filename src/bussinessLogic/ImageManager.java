package bussinessLogic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import interfaces.ManagesImages;

public class ImageManager implements ManagesImages {
	// Random object to shuffle the images index
	private static final Random RANDOM = new Random();
	// the current number of pictures in the resource folder
	private static final int numberOfFiles = 14;
	private static int[] imageList;
	
	public ImageManager(int[] imageList) {
		this.imageList = imageList;
	}

	@Override
	public BufferedImage loadImages(String path, int imageIndex) {
		if (imageIndex == 0) {
			path = path + "Positive/" + RANDOM.nextInt(numberOfFiles) + ".jpg";
		} else
			path = path + "Negative/" + imageIndex + ".jpg";

		try {
			return ImageIO.read(getClass().getClassLoader().getResource(path));
		} catch (IOException e) {
			System.out.println("Error loading image");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getPositiveImagePosition() {
		for (int i = 0; i < imageList.length; i++) {
			if (imageList[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void setIndexImages() {
		int imageListLength = imageList.length;

		for (int i = 0; i < imageListLength; i++) {
			imageList[i] = (i + 1) % imageListLength; // ads 1, 2, 3...11, 0 to tiles array
		}
	}

	@Override
	public void shuffleIndexImages() {
		int totalImages = getImageList().length;
		while (totalImages > 1) {
			int randomNumber = RANDOM.nextInt(totalImages--);
			int temporary = imageList[randomNumber];
			imageList[randomNumber] = imageList[totalImages];
			imageList[totalImages] = temporary;
		}
	}
	public int[] getImageList(){
		return imageList;
	}
}
