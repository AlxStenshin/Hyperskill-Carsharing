package ru.alxstn.carsharing.data.car;


import ru.alxstn.carsharing.data.database.BasicTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.alxstn.carsharing.data.company.CompanyDaoImpl.*;
import static ru.alxstn.carsharing.data.user.UserDaoImpl.*;

public class CarDaoImpl extends BasicTable implements CarDao {

    static public final String CARS_TABLE_NAME = "car";
    static public final String CARS_COLUMN_ID = "id";
    static final String CARS_COLUMN_COMPANY_ID = "company_id";
    static final String CARS_COLUMN_NAME = "name";

    final private static String CREATE_CAR_TABLE =
            "CREATE TABLE IF NOT EXISTS " + CARS_TABLE_NAME + " (" +
                    CARS_COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                    CARS_COLUMN_NAME + " VARCHAR(255) NOT NULL, UNIQUE (" + CARS_COLUMN_NAME + "), " +
                    CARS_COLUMN_COMPANY_ID + " INT NOT NULL, " +
                    "CONSTRAINT " + CARS_COLUMN_COMPANY_ID + " FOREIGN KEY (" + CARS_COLUMN_COMPANY_ID + ") " +
                    "REFERENCES " + COMPANY_TABLE_NAME + "(" + COMPANY_COLUMN_ID + ")" +
                    ");";

    // Param: <Int CompanyID, String CarName>
    final private static String INSERT_CAR =
            "INSERT INTO " + CARS_TABLE_NAME + " (%s, %s) VALUES (%s, '%s');";

    //// Param: Int carId
    //final private static String SELECT_CAR_NAME_BY_ID =
    //        "SELECT " + CARS_COLUMN_NAME +
    //                " FROM " + CARS_TABLE_NAME +
    //                " WHERE (" + CARS_COLUMN_ID + " = %s" + ");";

    // Param: String companyName
    final private static String SELECT_ALL_BY_COMPANY =
            "SELECT " + CARS_COLUMN_ID + ", " + CARS_COLUMN_NAME + ", " + CARS_COLUMN_COMPANY_ID +
                    " FROM " + CARS_TABLE_NAME +
                    " WHERE (" + CARS_COLUMN_COMPANY_ID + " = %s" + ")" + ";";

    /**
     *  Param: String CompanyName
     *  Left Selection = All Cars, Right Selection = All users with null cars = Not-Rented cars
     *  Using LEFT JOIN
     *
     *  Example:
     *  SELECT car.id, car.name
     *  FROM car LEFT JOIN customer
     *  ON car.id = customer.rented_car_id
     *  WHERE (customer.name IS NULL AND car.COMPANY_ID=1);
     */
    final private static String SELECT_ALL_AVAILABLE_BY_COMPANY =
            "SELECT " + CARS_TABLE_NAME + "." + CARS_COLUMN_ID + ", " +
                    CARS_TABLE_NAME + "." + CARS_COLUMN_NAME +
                    " FROM " + CARS_TABLE_NAME +
                    " LEFT JOIN " + USERS_TABLE_NAME +
                    " ON " + CARS_TABLE_NAME + "." + CARS_COLUMN_ID + " = " +
                    USERS_TABLE_NAME + "." + USERS_COLUMN_RENTED_CAR_ID +
                    " WHERE (" +
                    USERS_TABLE_NAME + "." + USERS_COLUMN_NAME + " IS NULL" +
                    " AND " +
                    CARS_TABLE_NAME + "." + CARS_COLUMN_COMPANY_ID + " = %s" +
                    ");";


    /**
     *  Param: userId
     *
     * Example:
     * SELECT car.NAME AS carName, company.NAME AS companyName
     * FROM CUSTOMER
     * JOIN CAR
     * ON customer.RENTED_CAR_ID = car.id
     * JOIN COMPANY
     * ON car.COMPANY_ID = company.ID
     * WHERE (customer.id = 1);
     */
    final private static String SELECT_USER_RENTED_CAR_INFO =
            "SELECT " + CARS_TABLE_NAME + "." + CARS_COLUMN_NAME + " AS CARNAME, " +
                    COMPANY_TABLE_NAME + "." + COMPANY_COLUMN_NAME + " AS COMPANYNAME" +
                    " FROM " + USERS_TABLE_NAME +
                    " JOIN " + CARS_TABLE_NAME +
                    " ON " + USERS_TABLE_NAME + "." + USERS_COLUMN_RENTED_CAR_ID + "=" +
                    CARS_TABLE_NAME + "." + CARS_COLUMN_ID +
                    " JOIN " + COMPANY_TABLE_NAME +
                    " ON " + CARS_TABLE_NAME + "." + CARS_COLUMN_COMPANY_ID + "=" +
                    COMPANY_TABLE_NAME + "." + COMPANY_COLUMN_ID +
                    " WHERE (" + USERS_TABLE_NAME + "." + USERS_COLUMN_ID + " = %s);";


    public CarDaoImpl() {
        super(CARS_TABLE_NAME);
        //dropTableIfExists();
        createTable();
    }

    public boolean createTable() {
        return dbManager.execute(CREATE_CAR_TABLE);
    }

    @Override
    public List<Car> getAllCarsByCompanyId(int companyId) {
        List<Car> result = new ArrayList<>();
        try {
            ResultSet cars = dbManager
                    .getConnection()
                    .createStatement()
                    .executeQuery(String.format(SELECT_ALL_BY_COMPANY, companyId));
            while (cars.next()) {
                result.add(new Car(
                        cars.getInt(CARS_COLUMN_ID),
                        cars.getString(CARS_COLUMN_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addNewCar(int companyId, Car car) {
        dbManager.executeUpdate(
                String.format(INSERT_CAR,
                CARS_COLUMN_COMPANY_ID, CARS_COLUMN_NAME,
                companyId, car.getName()));
    }

    @Override
    public List<Car> getAllAvailableCarsByCompanyId(int companyId) {
        List<Car> result = new ArrayList<>();
        try {
            ResultSet cars = dbManager
                    .getConnection()
                    .createStatement()
                    .executeQuery(String.format(SELECT_ALL_AVAILABLE_BY_COMPANY,  companyId));
            while (cars.next()) {
                result.add(new Car(
                        cars.getInt(CARS_COLUMN_ID),
                        cars.getString(CARS_COLUMN_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param userId
     * @return report
     *
     * Example:
     *  Your rented car:
     *  Hyundai Venue
     *  Company:
     *  Car To Go
     */
    @Override
    public String buildUserReport(int userId) {
        String report = "";
        ResultSet result = null;
        try {
            result = dbManager
                    .getConnection()
                    .createStatement()
                    .executeQuery(String.format(SELECT_USER_RENTED_CAR_INFO, userId));
            if (result.next()) {
                report += "Your rented car:\n";
                report += result.getString("CARNAME");
                report += "\nCompany:\n";
                report += result.getString("COMPANYNAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
}
