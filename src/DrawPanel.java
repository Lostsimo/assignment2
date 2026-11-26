import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel {
    static final int CAR_WIDTH = 100;
    static final int CAR_HEIGHT = 60;

    // One image and one position for each car, in the same order as the cars
    ArrayList<BufferedImage> carImages = new ArrayList<>();
    ArrayList<Point> carPoints = new ArrayList<>();

    void addCar(Car car) {
        BufferedImage image = null;
        // Print an error message in case file is not found with a try/catch block
        try {
            // You can remove the "pics" part if running outside of IntelliJ and
            // everything is in the same folder.
            image = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/" + car.getModelName() + ".jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        carImages.add(image);
        carPoints.add(new Point());
    }

    void moveit(int index, int x, int y) {
        carPoints.get(index).x = x;
        carPoints.get(index).y = y;
    }

    // Initializes the panel
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < carImages.size(); i++) {
            Point point = carPoints.get(i);
            g.drawImage(carImages.get(i), point.x, point.y, null); // see javadoc for more info on the parameters
        }
    }
}
