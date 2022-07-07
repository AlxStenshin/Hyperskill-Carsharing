package ru.alxstn.carsharing.menu.functional.company;

import ru.alxstn.carsharing.data.company.Company;
import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;

import java.util.List;

public class CompanyListMenu extends ConfigurableMenuState {
    private final DatabaseDao db;
    private boolean managerRights = true;

    public void setManagerRights(boolean managerRights) {
        this.managerRights = managerRights;
    }

    public CompanyListMenu(DatabaseDao db, State returnToState) {
        super(returnToState);
        this.db = db;
        this.setMenuTitle("\nChoose the company:");
    }

    @Override
    public State runState() {
        List<Company> companyList = db.getAllCompanies();
        if (companyList.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return returnToState;
        } else {
            int counter = 1;
            for (Company c : companyList) {
                // List for Manager: Managing a company
                CompanyMenu newMenu = new CompanyMenu(db, c.getId(), c.getName(), returnToState);
                newMenu.setManagerRights(managerRights);
                this.addMenuItem(counter++, new MenuItem(c.getName()), menu -> newMenu);
            }
            this.addMenuItem(0, new MenuItem("Back"), menu -> returnToState);
        }
        return super.runState();
    }
}
