package ru.alxstn.carsharing.menu.functional.user;

import ru.alxstn.carsharing.data.company.Company;
import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;

import java.util.List;

public class CustomerRentCarMenu extends ConfigurableMenuState {
    private final DatabaseDao db;
    private boolean managerRights = true;
    private int userId;

    public CustomerRentCarMenu(DatabaseDao db, int userId, State returnToState) {
        super(returnToState);
        this.db = db;
        this.userId = userId;
    }

    @Override
    public State runState() {
        if (db.getRentedCarId(userId) > 0) {
            System.out.println("\nYou've already rented a car!\n");
            return returnToState;
        }
        List<Company> companyList = db.getAllCompanies();
        if (companyList.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return returnToState;
        } else {
            int counter = 1;
            for (Company c : companyList) {
                // List for User: Creating a car list for each company
                CustomerCarListMenu customerCarListMenu = new CustomerCarListMenu(db, userId, c.getId(), returnToState);
                this.addMenuItem(counter++, new MenuItem(c.getName()), menu -> customerCarListMenu);
            }
            this.addMenuItem(0, new MenuItem("Back"), menu -> returnToState);
        }
        return super.runState();
    }
}
