package ru.alxstn.carsharing.menu.functional.user;

import ru.alxstn.carsharing.data.car.Car;
import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.MenuItem;
import ru.alxstn.carsharing.menu.basic.State;
import ru.alxstn.carsharing.menu.functional.car.CarMenu;

import java.util.List;

public class CustomerCarListMenu extends ConfigurableMenuState {
    private final DatabaseDao db;
    private final int companyId;
    private final int userId;

    public CustomerCarListMenu(DatabaseDao db, int userId, int companyId, State returnToState) {
        super(returnToState);
        this.db = db;
        this.companyId = companyId;
        this.userId = userId;
    }

    @Override
    public State runState() {
        List<Car> carList = db.getAllAvailableCarsByCompanyId(companyId);
        if (carList.isEmpty()) {
            System.out.println("\nThe car list is empty!");
            return returnToState;

        } else {
            // Show Car List as a menu
            this.setMenuTitle("\nChoose a car:");
            int id = 1;
            for (Car c : carList) {
                CarMenu newMenu = new CarMenu(db, id, c.getName(), userId, returnToState);
                this.addMenuItem(id++, new MenuItem(c.getName()), menu -> newMenu);
            }
            this.addMenuItem(0, new MenuItem("Back"), menu -> returnToState);
            return super.runState();
        }
    }
}

