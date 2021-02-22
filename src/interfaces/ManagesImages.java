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
	 * Sets the dimension of the imageList array
	 *
	 * @param imageList
	 */
	public void setImageList (int[] imageList);

	/**
	 * Returns the position of the positive image.
	 *
	 * @return
	 */
	public int getPositiveImagePosition();

	/**
	 * Set image indexes.
	 *
	 */
	public void setIndexImages();

	/**
	 * Shuffle image indexes.
	 *
	 * @param totalImages
	 */
	public void shuffleIndexImages(int totalImages);

	/**
	 * Returns the ImageList array
	 * @return
	 */

	public int[] returnImageList();
}
