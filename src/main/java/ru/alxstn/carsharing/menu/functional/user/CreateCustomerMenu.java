package ru.alxstn.carsharing.menu.functional.user;

import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.data.user.User;
import ru.alxstn.carsharing.menu.basic.ConsoleReader;
import ru.alxstn.carsharing.menu.basic.ReturnToPreviousState;
import ru.alxstn.carsharing.menu.basic.State;

public class CreateCustomerMenu extends ReturnToPreviousState implements ConsoleReader {
    private final DatabaseDao db;

    public CreateCustomerMenu(DatabaseDao db, State returnToState) {
        super(returnToState);
        this.db = db;
    }

    @Override
    public State runState() {
        System.out.println("\nEnter the customer name:");
        String customerName = readInputString();

        if (customerName.isBlank() || customerName.isEmpty()) {
            System.out.println("Empty Input");
        } else {
            db.addNewUser(new User(customerName));
            System.out.println("\nUser was created!");
        }
        return returnToState;
    }
}
