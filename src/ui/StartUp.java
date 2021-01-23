package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class StartUp extends JPanel {

    // Number of images in a row, hence the number of columns
    private int numberOfColumns;
    // Number of rows
    private int numberOfRows;
    // Number of images
    private int numberOfImages;
    // Grid UI Dimension
    private int dimension;
    // Random object to shuffle tiles
    private static final Random RANDOM = new Random();
    // Array of integers for storing the images
    private int[] images;
    // Size of individual image on UI
    private int imageSize;
    // Margin for the grid on the frame
    private int margin;
    // Grid UI Size
    private int gridSize;

    public StartUp(int numberOfColumns, int dim, int mar) {
        this.numberOfColumns = numberOfColumns;
        dimension = dim;
        margin = mar;
        numberOfRows = numberOfColumns / 4 * 3; // numberOfColumns/4*3 to keep a 4:3 aspect ratio
        numberOfImages = numberOfColumns * numberOfRows;
        images = new int[numberOfColumns * numberOfRows];

        // calculate grid size and image size
        gridSize = (dim - 2 * margin);
        imageSize = gridSize / numberOfColumns;

        // set the window size to accommodate the 4x3 layout
        setPreferredSize(new Dimension(dimension, imageSize * numberOfRows + 2 * margin));
        setBackground(Color.WHITE);

        startProgram();
    }

    private void startProgram() {

        reset(); // reset the array of images
        shuffle();
    }

    private void reset() {
        for (int i = 0; i < images.length; i++) {
            images[i] = (i + 1) % images.length; // ads 1, 2, 3...15, 0 to tiles array
        }
    }

    private void shuffle() {
        int n = numberOfImages; // it's 12 now (4x3)

        while (n > 1) {
            int r = RANDOM.nextInt(n--);
            int tmp = images[r];
            images[r] = images[n];
            images[n] = tmp;
        }
    }

    private void drawGrid(Graphics2D g2d) {

        for (int i = 0; i < images.length; i++) {
            // we convert 1D coords to 2D coords given the size of the 2D Array
            int r = i / numberOfColumns;
            int c = i % numberOfColumns;
            // we convert in coords on the UI
            int x = margin + c * imageSize;
            int y = margin + r * imageSize;

            BufferedImage img;
            int n = images[i];
            String path;

            if (n == 0) {
                int numberOfFiles = 14; // the current number of pictures in the resource folder
                int random = RANDOM.nextInt(numberOfFiles);
                path = "assets/Faces/Positive/" + random + ".jpg";

            } else
                path = "assets/Faces/Negative/" + n + ".jpg";

            try {
                img = ImageIO.read(getClass().getClassLoader().getResource(path));
                g2d.drawImage(img, x, y, imageSize, imageSize, null);
            } catch (IOException e) {
                System.out.println("Error loading image");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g2d) {
        super.paintComponent(g2d);
        Graphics2D g = (Graphics2D) g2d;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Cognitive Bias Modifier");
            frame.setResizable(false);
            frame.add(new StartUp(4, 750, 30), BorderLayout.CENTER);
            frame.pack();
            // center on the screen
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}