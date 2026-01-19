package com.university.services;


import com.university.notifications.InAppNotification;
import com.university.models.User;

public class AnnouncementService {

    private NotificationManager notificationManager = new NotificationManager();

    public void sendAnnouncement(Iterable<User> users, String message) {
        for (User user : users) {
            notificationManager.notify(
                new InAppNotification(user.getUsername(), message)
            );
        }
    }
}
