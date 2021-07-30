package ru.maslov.boot.spring_boot_app.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.maslov.boot.spring_boot_app.model.Role;
import ru.maslov.boot.spring_boot_app.model.User;

import java.util.List;


public interface UserService  extends UserDetailsService {
    void addUser(User user);


    void updateUser(int id, User updatedUser);

    void removeUser(int id);

    User getUserById(int id);
    User getUserByEmail(String email);

    List<User> listOfUser();
    List<Role> getAllRoles();
    Role getRoleById(long id);
}
