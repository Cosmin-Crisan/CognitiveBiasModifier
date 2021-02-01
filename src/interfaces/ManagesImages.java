/**
 * 
 */
package interfaces;

import java.awt.image.BufferedImage;

/**
 * @author Cosmin
 * 
 *         loads images
 */
public interface ManagesImages {
	/**
	 * loading images from the specified path
	 * 
	 * @param path       - path to images
	 * @param imageIndex - the index of the image.
	 */
	public BufferedImage loadImages(String path, int imageIndex);

	/**
	 * Returns the position of the positive image.
	 * 
	 * @param imageList
	 * @return
	 */
	public int getPositiveImagePosition(int[] imageList);

	/**
	 * Set image indexes.
	 * 
	 * @param imageList
	 */
	public void setIndexImages(int[] imageList);

	/**
	 * Shuffle image indexes.
	 *
	 * @param imageList
	 * @param totalImages
	 */
	public void shuffleIndexImages(int[] imageList, int totalImages);

}
