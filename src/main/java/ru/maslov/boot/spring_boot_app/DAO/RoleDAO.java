package ru.maslov.boot.spring_boot_app.DAO;


import ru.maslov.boot.spring_boot_app.model.Role;

import java.util.List;

public interface RoleDAO  {
    Role getRoleById(long id);
    public List<Role> getAllRoles();
}

