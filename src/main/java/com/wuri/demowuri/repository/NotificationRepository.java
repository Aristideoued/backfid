package com.wuri.demowuri.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wuri.demowuri.model.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByPersonneId(Long personneId);

    List<Notification> findByPersonneIdAndLuFalse(Long personneId);
}

