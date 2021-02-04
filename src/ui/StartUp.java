package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import bussinessLogic.ImageManager;
import interfaces.ManagesImages;

public class StartUp extends JPanel {

	// Number of images in a row, hence the number of columns
	private int numberOfColumns;
	// Number of rows
	private int numberOfRows;
	// Number of images
	private int numberOfImages;
	// Array of integers for storing the position of images
	private int[] images;
	// Size of individual image on UI
	private int imageSize;
	// Margin for the grid on the frame
	private int gridMargin;
	// Grid UI Size
	private int gridSize;
	// Position of the positive image
	private int positiveImagePosition;


	/**
	 * @see ManagesImages
	 */
	private ManagesImages imageManager;

	private StartUp(int numberOfColumns, int gridDimension, int gridMargin, ManagesImages imageManager) {
		initialize(numberOfColumns, gridDimension, gridMargin, imageManager);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// get position of the click
				int ex = e.getX() - gridMargin;
				int ey = e.getY() - gridMargin;

				// click in the grid ?
				if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize)
					return;

				// get position in the grid
				int c1 = ex / imageSize;
				int r1 = ey / imageSize;

				// we convert in the 1D coord
				int clickPosition = r1 * numberOfColumns + c1;

				// restart the program if the user clicks on the positive image
				if (clickPosition == positiveImagePosition) {
					startProgram();
					repaint();
				}

			}
		});

	}

	/**
	 * @param numberOfColumns
	 * @param gridDimension
	 * @param gridMargin
	 */
	private void initialize(int numberOfColumns, int gridDimension, int gridMargin, ManagesImages imageManager) {
		this.imageManager = imageManager;

		this.numberOfColumns = numberOfColumns;
		this.gridMargin = gridMargin;
		this.numberOfRows = (numberOfColumns / 4) * 3; // numberOfColumns/4*3 to keep a 4:3 aspect ratio
		this.numberOfImages = numberOfColumns * numberOfRows;
		this.images = new int[numberOfImages];

		// calculate grid size and image size
		this.gridSize = (gridDimension - 2 * gridMargin);
		this.imageSize = gridSize / numberOfColumns;

		// set the window size to accommodate the 4x3 layout
		setPreferredSize(new Dimension(gridDimension, imageSize * numberOfRows + 2 * gridMargin));
		setBackground(Color.WHITE);

		startProgram();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Cognitive Bias Modifier");
			frame.setResizable(false);
			frame.add(new StartUp(4, 750, 30, new ImageManager(new int[12])), BorderLayout.CENTER);
			frame.pack();
			// center on the screen
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

	// starts the program
	private void startProgram() {
		// set indexes in the array of images
		this.imageManager.setIndexImages();
		// shuffle the index position of each image
		this.imageManager.shuffleIndexImages(numberOfImages);
		// get the location of the positive image
		positiveImagePosition = this.imageManager.getPositiveImagePosition();
		images = this.imageManager.returnArray();
	}

	private void buildImages(Graphics2D g2d) {

		for (int i = 0; i < images.length; i++) {
			// we convert 1D coords to 2D coords given the size of the 2D Array
			int r = i / numberOfColumns;
			int c = i % numberOfColumns;
			// we convert in coords on the UI
			int x = gridMargin + c * imageSize;
			int y = gridMargin + r * imageSize;

			BufferedImage image = this.imageManager.loadImages("assets/Faces/", images[i]);
			g2d.drawImage(image, x, y, imageSize, imageSize, null);
		}
	}

	@Override
	protected void paintComponent(Graphics g2d) {
		super.paintComponent(g2d);
		Graphics2D g = (Graphics2D) g2d;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		buildImages(g);
	}
}
