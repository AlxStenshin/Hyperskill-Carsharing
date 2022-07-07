package ru.alxstn.carsharing.menu.functional.user;

import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.data.user.User;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;

import java.util.List;

public class CustomerListMenu extends ConfigurableMenuState {
    private final DatabaseDao db;

    public CustomerListMenu(DatabaseDao db, State returnToState) {
        super(returnToState);
        this.db = db;
        this.setMenuTitle("\nChoose a customer:");
    }

    @Override
    public State runState() {
        List<User> userList = db.getAllUsers();
        if (userList.isEmpty()) {
            System.out.println("The customer list is empty!");
            return returnToState;
        } else {
            for (User user : userList) {
                CustomerMenu newMenu = new CustomerMenu(db, user, returnToState);
                this.addMenuItem(user.getId(), new MenuItem(user.getName()), menu -> newMenu);
            }
            this.addMenuItem(0, new MenuItem("Back"), menu -> returnToState);
        }
        return super.runState();
    }
}
