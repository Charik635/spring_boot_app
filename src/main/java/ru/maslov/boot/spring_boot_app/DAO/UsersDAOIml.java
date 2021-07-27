package ru.maslov.boot.spring_boot_app.DAO;

import org.springframework.stereotype.Repository;
import ru.maslov.boot.spring_boot_app.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UsersDAOIml implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public void removeUser(int id) {
        entityManager.remove(getUserById(id));
        entityManager.flush();

    }


    @Override
    public User getUserById(int id) {
        TypedQuery<User> q = entityManager.createQuery("select DISTINCT u from User u left join fetch u.roles where u.id=:id", User.class);
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> q = entityManager.createQuery("select distinct u from User  u left join fetch u.roles where u.username=:email", User.class);
        q.setParameter("email", email);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<User> listOfUser() {
        List<User> s = entityManager.createQuery("select distinct u from User u left join fetch u.roles", User.class).getResultList();
        return s;
    }
}
