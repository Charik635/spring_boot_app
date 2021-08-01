package ru.maslov.boot.spring_boot_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maslov.boot.spring_boot_app.DAO.RoleDAO;
import ru.maslov.boot.spring_boot_app.DAO.UserDAO;
import ru.maslov.boot.spring_boot_app.model.Role;
import ru.maslov.boot.spring_boot_app.model.User;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional

@Service
public class UserServiceIml implements UserService, UserDetailsService {
    private UserDAO usersDAO;
    private RoleDAO roleDAO;
@Autowired
    private BCryptPasswordEncoder passwordEncoder;
@Autowired
    public UserServiceIml(UserDAO usersDAO, RoleDAO roleDAO) {
        this.usersDAO = usersDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public void addUser(User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role :user.getRoles()) {
            if(role.getRole().contains("ROLE_ADMIN")) {
                roles.add(roleDAO.getRoleById(1L));
            }
            if(role.getRole().contains("ROLE_USER")) {
                roles.add(roleDAO.getRoleById(2L));
            }
        }
        user.setRoles(roles);
        usersDAO.addUser(user);
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        User userToBeUpdated = usersDAO.getUserById(id);
        Set<Role> roles = new HashSet<>();
        for (Role role :updatedUser.getRoles()) {
            if(role.getRole().contains("ROLE_ADMIN")) {
                roles.add(roleDAO.getRoleById(1L));
            }
            if(role.getRole().contains("ROLE_USER")) {
                roles.add(roleDAO.getRoleById(2L));
            }
        }
        userToBeUpdated.setRoles(roles);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setSurName(updatedUser.getSurName());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setAge(updatedUser.getAge());

        userToBeUpdated.getRoles().remove(roleDAO.getRoleById(2L));
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
    public User getUserByEmail(String email) {
        return getUserByEmail(email);
    }

    @Override
    public List<User> listOfUser() {
        return usersDAO.listOfUser();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Role getRoleById(long id) {
        return roleDAO.getRoleById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return  usersDAO.getUserByEmail(s);

    }

}
