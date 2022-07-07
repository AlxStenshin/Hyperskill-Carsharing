package ru.alxstn.carsharing.data.database;


import ru.alxstn.carsharing.data.car.Car;
import ru.alxstn.carsharing.data.car.CarDao;
import ru.alxstn.carsharing.data.car.CarDaoImpl;
import ru.alxstn.carsharing.data.company.Company;
import ru.alxstn.carsharing.data.company.CompanyDao;
import ru.alxstn.carsharing.data.company.CompanyDaoImpl;
import ru.alxstn.carsharing.data.user.User;
import ru.alxstn.carsharing.data.user.UserDao;
import ru.alxstn.carsharing.data.user.UserDaoImpl;

import java.util.List;

public class DatabaseDao {
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final UserDao userDao;

    public DatabaseDao(String[] args) {
        DatabaseManager.getInstance(args);
        companyDao = new CompanyDaoImpl();
        carDao = new CarDaoImpl();
        userDao = new UserDaoImpl();
    }

    public void addNewUser(User user) {
        userDao.addNewUser(user);
    }

    public void addNewCompany(Company company) {
        companyDao.addNewCompany(company);
    }

    public void addNewCar(int companyId, Car car) {
        carDao.addNewCar(companyId, car);
    }

    public List<Company> getAllCompanies() {
        return companyDao.getAllCompanies();
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public List<Car> getAllCarsByCompanyId(int companyId) {
        return carDao.getAllCarsByCompanyId(companyId);
    }

    public List<Car> getAllAvailableCarsByCompanyId(int companyId) {
        return carDao.getAllAvailableCarsByCompanyId(companyId);
    }

    public int getRentedCarId(int userId) {
        return userDao.getRentedCarId(userId);
    }

    public void updateRentedCarForUser(String carId, int userId) {
        userDao.updateRentedCarForUser(carId, userId);
    }

    public String getUserCarReport(int userId) {
        return carDao.buildUserReport(userId);
    }


}
