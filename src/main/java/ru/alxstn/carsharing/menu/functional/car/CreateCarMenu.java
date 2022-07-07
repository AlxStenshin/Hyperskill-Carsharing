package ru.alxstn.carsharing.menu.functional.car;


import ru.alxstn.carsharing.data.car.Car;
import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConsoleReader;
import ru.alxstn.carsharing.menu.basic.ReturnToPreviousState;
import ru.alxstn.carsharing.menu.basic.State;

public class CreateCarMenu extends ReturnToPreviousState implements ConsoleReader {
    private final DatabaseDao db;
    private final int companyId;

    public CreateCarMenu(DatabaseDao db, int companyId, State returnToState) {
        super(returnToState);
        this.db = db;
        this.companyId = companyId;
    }

    @Override
    public State runState() {
        System.out.println("\nEnter the car name:");
        String carName = readInputString();

        if (carName.isBlank() || carName.isEmpty()) {
            System.out.println("Empty input");
        } else {
            db.addNewCar(companyId, new Car(carName));
            System.out.println("The car was created!");
        }
        return returnToState;
    }
}
