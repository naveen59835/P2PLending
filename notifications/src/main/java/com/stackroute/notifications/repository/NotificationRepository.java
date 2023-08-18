package com.stackroute.notifications.repository;

import com.stackroute.notifications.model.Notification;
import com.stackroute.notifications.model.NotificationMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
