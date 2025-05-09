package org.example.task.repository;

import jakarta.transaction.Transactional;
import org.example.task.model.entity.Subscription;
import org.example.task.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

   @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId")
   Optional<Subscription> findByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Subscription s WHERE s.user.subscription IS NOT NULL")
    List<Subscription> findAllSubscriptionsForSubscribedUsers();

    @Query("SELECT s FROM Subscription s WHERE s.user = :user")
    Optional<Subscription> findByUser(@Param("user") User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Subscription s WHERE s.user = :user")
    void deleteByUser(@Param("user") User user);

    @Query("SELECT s FROM Subscription s WHERE s.reportHour = :hour")
    List<Subscription> findAllUsersToReport(@Param("hour") int hour);

}
