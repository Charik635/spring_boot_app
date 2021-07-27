package ru.maslov.boot.spring_boot_app.DAO;


import ru.maslov.boot.spring_boot_app.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);


    void removeUser(int id);

    User getUserById(int id);
    User getUserByEmail(String email);

    List<User> listOfUser();
}
