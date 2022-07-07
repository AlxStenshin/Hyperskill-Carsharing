package ru.alxstn.carsharing.menu.functional.company;

import ru.alxstn.carsharing.data.company.Company;
import ru.alxstn.carsharing.data.database.DatabaseDao;
import ru.alxstn.carsharing.menu.basic.ConsoleReader;
import ru.alxstn.carsharing.menu.basic.ReturnToPreviousState;
import ru.alxstn.carsharing.menu.basic.State;

public class CreateCompanyMenu extends ReturnToPreviousState implements ConsoleReader {
    private final DatabaseDao db;

    public CreateCompanyMenu(DatabaseDao db, State returnToState) {
        super(returnToState);
        this.db = db;
    }

    @Override
    public State runState() {
        System.out.println("\nEnter the company name:");
        String companyName = readInputString();

        if (companyName.isBlank() || companyName.isEmpty()) {
            System.out.println("Empty input");
        } else {
            db.addNewCompany(new Company(companyName));
            System.out.println("\nThe company was created!");
        }
        return returnToState;
    }
}
