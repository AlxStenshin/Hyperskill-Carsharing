package ru.alxstn.carsharing.menu.functional.company;


import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;

public class ManagerMenu extends ConfigurableMenuState {

    public ManagerMenu(DatabaseDao db, State returnToState) {
        super(returnToState);
        this.setMenuTitle("\n");

        var companyListMenu = new CompanyListMenu(db, this);
        var createCompanyMenu = new CreateCompanyMenu(db, this);

        this.addMenuItem(1, new MenuItem("Company list"), menu -> companyListMenu);
        this.addMenuItem(2, new MenuItem("Create a company"), menu -> createCompanyMenu);
        this.addMenuItem(0, new MenuItem("Back"), menu -> returnToState);
    }
}
