package com.university.services;


import com.university.notifications.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private List<Notification> notifications = new ArrayList<>();

    public void notify(Notification notification) {
        notifications.add(notification);
        notification.send(); // sends via Email, SMS, or In-App
    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}
