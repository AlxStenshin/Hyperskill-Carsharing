package ru.alxstn.carsharing;

import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;
import ru.alxstn.carsharing.menu.functional.company.ManagerMenu;
import ru.alxstn.carsharing.menu.functional.user.CreateCustomerMenu;
import ru.alxstn.carsharing.menu.functional.user.CustomerListMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseDao db = new DatabaseDao(args);

        var loginMenu = new ConfigurableMenuState(null);
        var managerMenu = new ManagerMenu(db, loginMenu);
        var customerListMenu = new CustomerListMenu(db, loginMenu);
        var createUserMenu = new CreateCustomerMenu(db, loginMenu);

        System.out.println();
        loginMenu.addMenuItem(1, new MenuItem("Log in as a manager"), menu -> managerMenu);
        loginMenu.addMenuItem(2, new MenuItem("Log in as a customer"), menu -> customerListMenu);
        loginMenu.addMenuItem(3, new MenuItem("Create a customer"), menu -> createUserMenu);
        loginMenu.addMenuItem(0, new MenuItem("Exit"), menu -> null);

        State starterState = loginMenu;
        while (starterState != null)
            starterState = starterState.runState();
    }
}