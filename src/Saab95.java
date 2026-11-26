import java.awt.*;

public class Saab95 extends Car {

    private boolean turboOn;

    public Saab95() {
        this(0, 0);
    }

    public Saab95(double x, double y) {
        super(2, 125, Color.RED, "Saab95", x, y);
        turboOn = false;
    }

    public void setTurboOn() {
        turboOn = true;
    }

    public void setTurboOff() {
        turboOn = false;
    }

    @Override
    protected double speedFactor() {
        double turbo = 1;
        if(turboOn) turbo = 1.3;
        return super.speedFactor() * turbo;
    }
}
