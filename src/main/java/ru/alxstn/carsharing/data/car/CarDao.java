package ru.alxstn.carsharing.data.car;

import java.util.List;

public interface CarDao {
    String buildUserReport(int userId);

    //String getCarNameById(int carId);
    //int getCompanyIdByCarID(int carId);

    List<Car> getAllCarsByCompanyId(int companyId);
    List<Car> getAllAvailableCarsByCompanyId(int companyId);
    void addNewCar(int companyId, Car car);
}
