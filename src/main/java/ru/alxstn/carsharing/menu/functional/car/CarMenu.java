package ru.alxstn.carsharing.menu.functional.car;


import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ReturnToPreviousState;
import ru.alxstn.carsharing.menu.basic.State;

public class CarMenu extends ReturnToPreviousState {
    private final int carId;
    private final int userId;
    private final String carName;
    private final DatabaseDao db;

    public CarMenu(DatabaseDao db, int carId, String carName, int userId, State returnToState) {
        super(returnToState);
        this.db = db;
        this.carId = carId;
        this.carName = carName;
        this.userId = userId;
    }

    @Override
    public State runState() {
        int rentedCarId = db.getRentedCarId(userId);
        if (rentedCarId > 0) {
            System.out.println("\nYou've already rented a car!");
        } else {
            db.updateRentedCarForUser(Integer.toString(carId), userId);
            System.out.println(String.format("\nYou rented '%s'", carName));
        }
        return returnToState;
    }
}
