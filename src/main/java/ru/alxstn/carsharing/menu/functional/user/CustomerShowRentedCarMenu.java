package ru.alxstn.carsharing.menu.functional.user;

import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ReturnToPreviousState;
import ru.alxstn.carsharing.menu.basic.State;

public class CustomerShowRentedCarMenu extends ReturnToPreviousState {
    private final DatabaseDao db;
    private final int userId;

    public CustomerShowRentedCarMenu(DatabaseDao db, int userId, State returnToState) {
        super(returnToState);
        this.db = db;
        this.userId = userId;
    }

    @Override
    public State runState() {
        String report = db.getUserCarReport(userId);
        if (report.isEmpty())
            System.out.println("\nYou didn't rent a car!\n");
        else
            System.out.println("\n" + report + "\n");

        return returnToState;
    }
}

