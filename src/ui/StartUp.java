package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class StartUp extends JPanel {

    // Random object to shuffle tiles
    private static final Random RANDOM = new Random();
    // the current number of pictures in the resource folder
    private static final int numberOfFiles = 14;
    // Number of images in a row, hence the number of columns
    private final int numberOfColumns;
    // Number of rows
    private final int numberOfRows;
    // Number of images
    private final int numberOfImages;
    // Grid UI Dimension
    private final int gridDimension;
    // Array of integers for storing the position of images
    private final int[] images;
    // Size of individual image on UI
    private final int imageSize;
    // Margin for the grid on the frame
    private final int gridMargin;
    // Grid UI Size
    private final int gridSize;
    // Position of the positive image
    private int positiveImagePosition;

    private StartUp(int numberOfColumns, int gridDimension, int gridMargin) {
        this.numberOfColumns = numberOfColumns;
        this.gridDimension = gridDimension;
        this.gridMargin = gridMargin;
        numberOfRows = numberOfColumns / 4 * 3; // numberOfColumns/4*3 to keep a 4:3 aspect ratio
        numberOfImages = numberOfColumns * numberOfRows;
        images = new int[numberOfImages];

        // calculate grid size and image size
        gridSize = (gridDimension - 2 * gridMargin);
        imageSize = gridSize / numberOfColumns;

        // set the window size to accommodate the 4x3 layout
        setPreferredSize(new Dimension(gridDimension, imageSize * numberOfRows + 2 * gridMargin));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // used for checking if the positive image is clicked

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
                    restart();
                    repaint();
                }

            }
        });

        startProgram();
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

    // restarts the program
    private void restart() {
        startProgram();
    }

    // starts the program
    private void startProgram() {

        reset(); // reset the array of images
        shuffle();
        getPositiveImagePosition();
    }

    private void reset() {
        for (int i = 0; i < this.images.length; i++) {
            this.images[i] = (i + 1) % this.images.length; // ads 1, 2, 3...11, 0 to tiles array
        }
    }

    private void shuffle() {
        int n = numberOfImages; // it's 12 now (4x3)

        while (n > 1) {
            int r = RANDOM.nextInt(n--);
            int temporary = images[r];
            images[r] = images[n];
            images[n] = temporary;
        }
    }

    private void getPositiveImagePosition() {
        for (int i = 0; i < images.length; i++) {
            if (images[i] == 0) {
                positiveImagePosition = i;
                break;
            }
        }
    }

    private void buildImages(Graphics2D g2d) {

        for (int i = 0; i < images.length; i++) {
            // we convert 1D coords to 2D coords given the size of the 2D Array
            int r = i / numberOfColumns;
            int c = i % numberOfColumns;
            // we convert in coords on the UI
            int x = gridMargin + c * imageSize;
            int y = gridMargin + r * imageSize;

            BufferedImage img;
            int n = images[i];
            String path;

            if (n == 0) {

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
        buildImages(g);
    }
}
