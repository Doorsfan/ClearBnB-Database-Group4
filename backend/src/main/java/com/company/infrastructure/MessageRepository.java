package com.company.infrastructure;

import com.company.domain.Booking;
import com.company.domain.Message;
import com.company.domain.User;
import jakarta.persistence.*;

import java.util.List;

public class MessageRepository {
    private EntityManager entityManager;

    public MessageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Message findById(Integer id) {
        return entityManager.find(Message.class, id);
    }

    public List<Message> findAllForUser(User user) {
        return entityManager.createQuery("SELECT m FROM Message m WHERE m.writtenByUser = :user OR m.recipientUser = :user", Message.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Message> findAll() {
        return entityManager.createQuery("from Message").getResultList();
    }

    public Message save(Message message) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(message);
            entityManager.getTransaction().commit();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}