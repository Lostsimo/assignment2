import java.awt.*;

public class Volvo240 extends Car {

    private final static double trimFactor = 1.25;

    public Volvo240() {
        this(0, 0);
    }

    public Volvo240(double x, double y) {
        super(4, 100, Color.BLACK, "Volvo240", x, y);
    }

    @Override
    protected double speedFactor() {
        return super.speedFactor() * trimFactor;
    }
}