package com.company.Repositories;

import com.company.Entities.User;
import jakarta.persistence.*;
import java.util.*;

public class UserRepository {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User findById(Integer id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    public User findByUsername(String username) {
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        return user;
    }

    public List<User> findAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    public User save(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
