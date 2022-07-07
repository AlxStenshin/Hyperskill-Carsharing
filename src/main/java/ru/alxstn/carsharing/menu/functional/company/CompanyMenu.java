package ru.alxstn.carsharing.menu.functional.company;

import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;
import ru.alxstn.carsharing.menu.functional.car.CreateCarMenu;

public class CompanyMenu extends ConfigurableMenuState {
    private final int companyId;
    private final DatabaseDao db;
    private boolean managerRights = true;

    public void setManagerRights(boolean managerRights) {
        this.managerRights = managerRights;
    }

    public CompanyMenu(DatabaseDao db, int companyId, String companyName, State returnToState) {
        super(returnToState);
        this.db = db;
        this.companyId = companyId;
        this.setMenuTitle("\n'" + companyName + "' company:");
    }

    @Override
    public State runState() {
        if (managerRights) {
            var carListMenu = new ManagerCarListMenu(db, companyId, this);
            var createCarMenu = new CreateCarMenu(db, companyId, this);

            this.addMenuItem(1, new MenuItem("Car list"), menu -> carListMenu);
            this.addMenuItem(2, new MenuItem("Create a car"), menu -> createCarMenu);
            this.addMenuItem(0, new MenuItem("Back"), menu -> returnToState);
        }
        else {
            this.setMenuTitle("\nChoose a car:");

        }
        return super.runState();
    }
}
