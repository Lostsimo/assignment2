void main() {
    // Instance of this class
    CarController cc = new CarController();

    cc.cars.add(new Volvo240());
    cc.cars.add(new Saab95(0, 100));

    // Start a new view and send a reference to the controller
    cc.frame = new CarView("CarSim 1.0", cc);
    for (Car car : cc.cars) {
        cc.frame.drawPanel.addCar(car);
    }
    cc.startTimer();
}