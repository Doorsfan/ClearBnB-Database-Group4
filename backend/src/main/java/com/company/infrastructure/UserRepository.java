package com.company.infrastructure;

import com.company.domain.User;
import jakarta.persistence.*;
import java.util.List;

public class UserRepository {
    private final EntityManager entityManager; //DEBUGABLE Set to final

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public User findByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
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

    public User update(Long id, String username, String password, String email, Double balance) {
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

    public User remove(Long id) {
        User user = this.findById(id);
        try {
            entityManager.getTransaction().begin();
            // TODO Change to version management
            //entityManager.remove(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
