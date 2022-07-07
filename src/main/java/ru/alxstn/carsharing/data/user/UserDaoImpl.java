package ru.alxstn.carsharing.data.user;


import ru.alxstn.carsharing.data.database.BasicTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.alxstn.carsharing.data.car.CarDaoImpl.CARS_COLUMN_ID;
import static ru.alxstn.carsharing.data.car.CarDaoImpl.CARS_TABLE_NAME;


public class UserDaoImpl extends BasicTable implements UserDao {

    public static  final String USERS_TABLE_NAME = "customer";
    public static final String USERS_COLUMN_ID = "id";
    public static final  String USERS_COLUMN_NAME = "name";
    public static  final String USERS_COLUMN_RENTED_CAR_ID = "rented_car_id";

    final private static String CREATE_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME + " (" +
                    USERS_COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                    USERS_COLUMN_NAME + " VARCHAR(255) NOT NULL, UNIQUE (" + USERS_COLUMN_NAME + "), " +
                    USERS_COLUMN_RENTED_CAR_ID + " INT, " +
                    "CONSTRAINT " + USERS_COLUMN_RENTED_CAR_ID + " FOREIGN KEY (" + USERS_COLUMN_RENTED_CAR_ID + ") " +
                    "REFERENCES " + CARS_TABLE_NAME + "(" + CARS_COLUMN_ID + ")" +
                    ");";

    final private static String INSERT_USER =
            "INSERT INTO %s (%s) VALUES ('%s');";

    final private static String SELECT_ALL =
            "SELECT * FROM %s ORDER BY " + USERS_COLUMN_ID + " ASC;";

    // Param: Int UserId
    final private static String GET_RENTED_CAR_ID =
            "SELECT " + USERS_COLUMN_RENTED_CAR_ID +
                    " FROM " + USERS_TABLE_NAME +
                    " WHERE (" + USERS_COLUMN_ID +
                    " = %s);";

    // Param: Int CarId, Int UserId
    final private static String UPDATE_RENTED_CAR_FOR_USER_ID =
            "UPDATE " + USERS_TABLE_NAME +
            " SET " + USERS_COLUMN_RENTED_CAR_ID + "=%s" +
            " WHERE " + USERS_COLUMN_ID + "=%s;";

    public UserDaoImpl() {
        super(USERS_TABLE_NAME);
        //dropTableIfExists();
        createTable();
    }

    protected boolean createTable() {
        return dbManager.execute(CREATE_USERS_TABLE);
    }

    private String buildInsertNewUserQuery(User user) {
        return String.format(INSERT_USER, USERS_TABLE_NAME, USERS_COLUMN_NAME, user.getName());
    }

    @Override
    public void addNewUser(User user) {
        dbManager.executeUpdate(buildInsertNewUserQuery(user));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            ResultSet users = dbManager
                    .getConnection()
                    .createStatement()
                    .executeQuery(String.format(SELECT_ALL, USERS_TABLE_NAME));
            while (users.next()) {
                result.add(new User(
                        users.getInt(USERS_COLUMN_ID),
                        users.getString(USERS_COLUMN_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String buildGetRentedCarIdQuery(int userId) {
        return String.format(GET_RENTED_CAR_ID, userId);
    }

    @Override
    public int getRentedCarId(int userId) {
        int id = -1;
        try {
            ResultSet result = dbManager
                    .getConnection()
                    .createStatement()
                    .executeQuery(buildGetRentedCarIdQuery(userId));
            if (result.next()) {
                id = result.getInt(USERS_COLUMN_RENTED_CAR_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private String buildUpdateRentedCarForUserQuery(String carId, int userId) {
        return String.format(UPDATE_RENTED_CAR_FOR_USER_ID, carId, userId);
    }

    @Override
    public void updateRentedCarForUser(String carId, int userId) {
        dbManager.executeUpdate(buildUpdateRentedCarForUserQuery(carId, userId));
    }

}
