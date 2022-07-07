package ru.alxstn.carsharing.menu.functional.user;

import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ReturnToPreviousState;
import ru.alxstn.carsharing.menu.basic.State;

public class ReturnCustomerRentedCarMenu extends ReturnToPreviousState {
    private final DatabaseDao db;
    private final int userId;

    public ReturnCustomerRentedCarMenu(DatabaseDao db, int userId, State returnToState) {
        super(returnToState);
        this.db = db;
        this.userId = userId;
    }

    @Override
    public State runState() {
        int rentedCarId = db.getRentedCarId(userId);
        if (rentedCarId > 0) {
            db.updateRentedCarForUser("NULL", userId);
            System.out.println("\nYou've returned a rented car!\n");
        } else {
            System.out.println("\nYou didn't rent a car!\n");
        }
        return returnToState;
    }
}
