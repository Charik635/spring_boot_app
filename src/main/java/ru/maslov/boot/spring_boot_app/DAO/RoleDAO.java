package ru.maslov.boot.spring_boot_app.DAO;


import ru.maslov.boot.spring_boot_app.model.Role;

public interface RoleDAO  {
    Role getUserById(long id);
}

