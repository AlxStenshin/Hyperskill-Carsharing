package ru.alxstn.carsharing.menu.functional.company;

import ru.alxstn.carsharing.data.car.Car;
import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConfigurableMenuState;
import ru.alxstn.carsharing.menu.basic.State;

import java.util.List;

public class ManagerCarListMenu extends ConfigurableMenuState {
    private final DatabaseDao db;
    private final int companyId;


    public ManagerCarListMenu(DatabaseDao db, int companyId, State returnToState) {
        super(returnToState);
        this.db = db;
        this.companyId = companyId;
    }

    @Override
    public State runState() {
        List<Car> carList = db.getAllCarsByCompanyId(companyId);
        if (carList.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            // Show Car List and return
            System.out.println("\nCar list:");
            int counter = 1;
            for (Car c : carList) {
                System.out.println(counter++ + ". " + c.getName());
            }
        }
        return returnToState;
    }
}
