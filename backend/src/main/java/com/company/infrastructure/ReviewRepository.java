package com.company.infrastructure;

import com.company.domain.Review;
import com.company.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

public class ReviewRepository {
    private EntityManager entityManager;

    public ReviewRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Review findMostRecentForId(Integer reviewId) {
        return entityManager.createQuery("SELECT r FROM Review r WHERE r.reviewId = :reviewId " +
                "ORDER BY r.version DESC", Review.class)
                .setParameter("reviewId", reviewId)
                .setMaxResults(1)
                .getSingleResult();
    }

    public List<Review> findAllReviewsForListingOfId(Integer wantedId){
        return entityManager.createQuery("SELECT r FROM Review r WHERE r.postedToListingId = :wantedId")
                .setParameter("wantedId", wantedId)
                .getResultList();
    }

    public List<Review> findAllForId(Integer reviewId) {
        return entityManager.createQuery("SELECT r FROM Review r WHERE r.reviewId = :reviewId", Review.class)
                .setParameter("reviewId", reviewId)
                .getResultList();
    }

    public List<Review> findAllForAuthor(User author) {
        return entityManager.createQuery("SELECT r FROM Review r WHERE r.author = :author", Review.class)
                .setParameter("author", author)
                .getResultList();
    }

    public List<Review> findAllForTarget(User target) {
        return entityManager.createQuery("SELECT r FROM Review r WHERE r.target = :target", Review.class)
                .setParameter("target", target)
                .getResultList();
    }

    public List<Review> findAll() {
        return entityManager.createQuery("from Review").getResultList();
    }


    public Review save(Review review) {
        try {
            if(review.getReviewId() == null){
                var result = entityManager.createQuery("SELECT r.reviewId FROM Review r " +
                        "ORDER BY r.version DESC");

                var myResultList = result.getResultList();
                if(myResultList.size() > 0){
                    for(int i = 0; i < myResultList.size() + 1 ; i++){
                        System.out.println("i was: " + i);
                        if(i == myResultList.size()){
                            review.setReviewId((i+1));
                            break;
                        }
                        else if((i + 1) != Integer.parseInt(myResultList.get(i).toString())){
                            review.setReviewId((i+1));
                            break;
                        }
                    }

                }
                else{
                    review.setReviewId(1);
                }
            }
            entityManager.getTransaction().begin();
            entityManager.persist(review);
            entityManager.getTransaction().commit();
            return review;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Review update(Integer id, String comment, Integer rating) {
        Review review = this.findMostRecentForId(id).clone();
        review.setVersion(review.getVersion() + 1);
        review.setTimestamp(LocalDateTime.now());

        if (comment != null) {
            review.setComment(comment);
        }
        if (rating != null) {
            review.setRating(rating);
        }

        return this.save(review);
    }

    public Review remove(Review review) {
        Review nullReview = this.update(review.getReviewId(), null, null);
        nullReview.setTimestamp("Removed");
        return this.save(nullReview);
    }
}
