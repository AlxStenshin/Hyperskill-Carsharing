package ru.alxstn.carsharing.data.user;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    void addNewUser(User user);
    int getRentedCarId(int userId);
    void updateRentedCarForUser(String carId, int userId);
}
