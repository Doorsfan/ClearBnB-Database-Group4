package com.company.infrastructure;

import com.company.domain.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findByUsername(String username) {
        List<User> user;

        try {
            user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username")
                    .setParameter("username", username)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e);
            user = null;
        }
        return user;
    }

    public List<User> findAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    public User save(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User update(Integer id, String username, String password, String email, Double balance) {
        User user = this.findById(id);
        try {
            entityManager.getTransaction().begin();
            if (username != null) {
                user.setUsername(username);
            }
            if (password != null) {
                user.setPassword(password);
            }
            if (email != null) {
                user.setEmail(email);
            }
            if (balance != null) {
                user.setBalance(balance);
            }
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User remove(Integer id) {
        User user = this.findById(id);
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
