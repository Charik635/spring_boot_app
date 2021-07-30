package ru.maslov.boot.spring_boot_app.DAO;

import org.springframework.stereotype.Repository;
import ru.maslov.boot.spring_boot_app.model.Role;
import ru.maslov.boot.spring_boot_app.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class RoleDAOIml implements RoleDAO{
private Role user;
private Role admin;
@PersistenceContext
private EntityManager entityManager;

    @Override
    public Role getRoleById(long id) {
        TypedQuery<Role> q = entityManager.createQuery("select r from Role  r where r.id=:id", Role.class);
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> s = entityManager.createQuery("select  r from Role r ", Role.class).getResultList();
        return s;
    }

}
