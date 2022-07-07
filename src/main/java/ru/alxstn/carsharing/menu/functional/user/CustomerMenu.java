package ru.alxstn.carsharing.menu.functional.user;

import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.data.user.User;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;

public class CustomerMenu extends ConfigurableMenuState {

    public CustomerMenu(DatabaseDao db, User user, State returnToState) {
        super(returnToState);

        var rentACarMenu = new CustomerRentCarMenu(db, user.getId(), this);
        var returnRentedCarMenu = new CustomerReturnRentedCarMenu(db, user.getId(), this);
        var showRentedCarMenu = new CustomerShowRentedCarMenu(db, user.getId(), this);

        this.addMenuItem(1, new MenuItem("Rent a car"), menu -> rentACarMenu);
        this.addMenuItem(2, new MenuItem("Return a rented car"), menu -> returnRentedCarMenu);
        this.addMenuItem(3, new MenuItem("My rented car"), menu -> showRentedCarMenu);
        this.addMenuItem(0, new MenuItem("Back"), menu -> returnToState);
    }
}
