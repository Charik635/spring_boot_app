package ru.maslov.boot.spring_boot_app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maslov.boot.spring_boot_app.DAO.RoleDAO;
import ru.maslov.boot.spring_boot_app.DAO.UserDAO;
import ru.maslov.boot.spring_boot_app.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional

@Service
public class UserServiceIml implements UserService, UserDetailsService {
    private UserDAO usersDAO;
    private RoleDAO roleDAO;
@Autowired
    public UserServiceIml(UserDAO usersDAO, RoleDAO roleDAO) {
        this.usersDAO = usersDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public void addUser(User user) {
        if(user.getAdmin())
        {
        user.takeRole(roleDAO.getUserById(1L));
        }
        if(user.getUser()){
            user.takeRole(roleDAO.getUserById(2L));
        }
        usersDAO.addUser(user);
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        User userToBeUpdated = getUserById(id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setSurName(updatedUser.getSurName());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setPassword(updatedUser.getPassword());

    }

    @Override
    public void removeUser(int id) {
        usersDAO.removeUser(id);
    }

    @Override
    public User getUserById(int id) {
        return usersDAO.getUserById(id);
    }

    @Override
    public List<User> listOfUser() {
        return usersDAO.listOfUser();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return  usersDAO.getUserByEmail(s);

    }
}
