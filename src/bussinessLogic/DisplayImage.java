/**
 * 
 */
package bussinessLogic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import interfaces.DisplayingImages;

/**
 * @author Cosmin
 *
 */
public class DisplayImage implements DisplayingImages {

	@Override
	public void loadImages(String path) {
		// code for loading images
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Error loading image");
			e.printStackTrace();
		}

	}

}
