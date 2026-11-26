import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
 * This class represents the Controller part in the MVC pattern.
 * Its responsibilities are to listen to the View and responds in a appropriate manner by
 * modifying the model state and the updating the view.
 */

public class CarController {
    // The delay (ms) corresponds to 20 updates/second (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    ArrayList<Car> cars = new ArrayList<>();

    public void startTimer() {
        this.timer.start();
    }

    /* Each step, the TimerListener moves all cars in the list and tells the
     * view to update its images.
     */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                car.move();
                int x = (int) Math.round(car.getX());
                // flip y, otherwise "up" goes down on the screen
                int y = maxY() - (int) Math.round(car.getY());
                frame.drawPanel.moveit(i, x, y);
            }
            // repaint() calls the paintComponent method of the panel
            frame.drawPanel.repaint();
        }
    }

    private int maxY() {
        return Math.max(0, frame.drawPanel.getHeight() - DrawPanel.CAR_HEIGHT);
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars) {
            car.gas(gas);
        }
    }

    void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Car car : cars) {
            car.brake(brake);
        }
    }

    void turboOn() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOn();
            }
        }
    }

    void turboOff() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOff();
            }
        }
    }

    void turnLeft() {
        for (Car car : cars) {
            car.turnLeft();
        }
    }

    void turnRight() {
        for (Car car : cars) {
            car.turnRight();
        }
    }

    void startAllCars() {
        for (Car car : cars) {
            car.startEngine();
        }
    }

    void stopAllCars() {
        for (Car car : cars) {
            car.stopEngine();
        }
    }
}
