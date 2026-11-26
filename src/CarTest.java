public class CarTest {
    private static int failures = 0;

    private static void reportResult(String testName, boolean success) {
        IO.println((success ? "SUCCESS: " : "FAILURE: ") + testName);
        if (!success) {
            failures++;
        }
    }

    private static boolean approxEquals(double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

    /** Tests whether a Saab95 with turbo enabled
     *  gains more speed than one without.
     */
    private static void testTurbo() {
        Saab95 slowSaab = new Saab95();
        Saab95 fastSaab = new Saab95();
        fastSaab.setTurboOn();
        slowSaab.setTurboOff();
        fastSaab.gas(0.5);
        slowSaab.gas(0.5);
        boolean result = slowSaab.getCurrentSpeed() < fastSaab.getCurrentSpeed();
        reportResult("Turbo yields more speed", result);
    }

    private static void testStartEngine() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        reportResult("startEngine sets speed to 0.1", approxEquals(volvo.getCurrentSpeed(), 0.1));
    }

    private static void testStopEngine() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.gas(1);
        volvo.stopEngine();
        reportResult("stopEngine sets speed to 0", approxEquals(volvo.getCurrentSpeed(), 0));
    }

    // 100 * 0.01 * 1.25 = 1.25
    private static void testVolvoAcceleration() {
        Volvo240 volvo = new Volvo240();
        volvo.gas(1);
        reportResult("Volvo240 full gas adds 1.25 speed", approxEquals(volvo.getCurrentSpeed(), 1.25));
    }

    private static void testMoveUp() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.move();
        boolean result = approxEquals(volvo.getY(), 0.1) && approxEquals(volvo.getX(), 0);
        reportResult("Moving while facing up increases y only", result);
    }

    private static void testMoveDown() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.turnLeft();
        volvo.turnLeft();
        volvo.move();
        boolean result = volvo.getY() < 0 && approxEquals(volvo.getX(), 0);
        reportResult("Moving while facing down decreases y only", result);
    }

    private static void testTurnLeftThenMove() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.turnLeft();
        saab.move();
        boolean result = saab.getX() < 0 && approxEquals(saab.getY(), 0);
        reportResult("Turn left then move decreases x", result);
    }

    private static void testTurnRightThenMove() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.turnRight();
        saab.move();
        boolean result = saab.getX() > 0 && approxEquals(saab.getY(), 0);
        reportResult("Turn right then move increases x", result);
    }

    private static void testFullTurn() {
        Volvo240 volvo = new Volvo240();
        String start = volvo.getDirection();
        for (int i = 0; i < 4; i++) {
            volvo.turnRight();
        }
        boolean rightOk = volvo.getDirection().equals(start);
        for (int i = 0; i < 4; i++) {
            volvo.turnLeft();
        }
        boolean leftOk = volvo.getDirection().equals(start);
        reportResult("Four turns in one direction is a full circle", rightOk && leftOk);
    }

    private static void testLeftThenRightCancels() {
        Volvo240 volvo = new Volvo240();
        String start = volvo.getDirection();
        volvo.turnLeft();
        volvo.turnRight();
        reportResult("Turn left then right faces the original direction", volvo.getDirection().equals(start));
    }

    // Task 4

    private static void testGasRejectsOutOfRange() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        double before = volvo.getCurrentSpeed();
        volvo.gas(1.5);
        volvo.gas(-0.5);
        reportResult("gas ignores values outside [0,1]", approxEquals(volvo.getCurrentSpeed(), before));
    }

    private static void testBrakeRejectsOutOfRange() {
        Volvo240 volvo = new Volvo240();
        volvo.gas(1);
        double before = volvo.getCurrentSpeed();
        volvo.brake(2);
        volvo.brake(-1);
        reportResult("brake ignores values outside [0,1]", approxEquals(volvo.getCurrentSpeed(), before));
    }

    private static void testSpeedNeverAboveEnginePower() {
        Saab95 saab = new Saab95();
        saab.setTurboOn();
        for (int i = 0; i < 1000; i++) {
            saab.gas(1);
        }
        boolean result = saab.getCurrentSpeed() <= saab.getEnginePower()
                && approxEquals(saab.getCurrentSpeed(), saab.getEnginePower());
        reportResult("Speed is capped at engine power", result);
    }

    private static void testSpeedNeverBelowZero() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        for (int i = 0; i < 1000; i++) {
            volvo.brake(1);
        }
        reportResult("Speed never goes below 0", volvo.getCurrentSpeed() >= 0);
    }

    private static void testGasNeverDecreasesSpeed() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.gas(0.5);
        double before = volvo.getCurrentSpeed();
        volvo.gas(0);
        boolean zeroOk = volvo.getCurrentSpeed() >= before;
        volvo.gas(0.3);
        boolean smallOk = volvo.getCurrentSpeed() >= before;
        reportResult("gas never decreases speed", zeroOk && smallOk);
    }

    private static void testBrakeNeverIncreasesSpeed() {
        Saab95 saab = new Saab95();
        saab.gas(1);
        double before = saab.getCurrentSpeed();
        saab.brake(0);
        boolean zeroOk = saab.getCurrentSpeed() <= before;
        saab.brake(0.4);
        boolean smallOk = saab.getCurrentSpeed() <= before;
        reportResult("brake never increases speed", zeroOk && smallOk);
    }

    static void main() {
        testTurbo();
        testStartEngine();
        testStopEngine();
        testVolvoAcceleration();
        testMoveUp();
        testMoveDown();
        testTurnLeftThenMove();
        testTurnRightThenMove();
        testFullTurn();
        testLeftThenRightCancels();

        testGasRejectsOutOfRange();
        testBrakeRejectsOutOfRange();
        testSpeedNeverAboveEnginePower();
        testSpeedNeverBelowZero();
        testGasNeverDecreasesSpeed();
        testBrakeNeverIncreasesSpeed();

        IO.println(failures == 0 ? "\nAll tests passed." : "\n" + failures + " test(s) failed.");
    }
}
