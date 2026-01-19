package com.university.notifications;

public class InAppNotification extends Notification {

    public InAppNotification(String recipientId, String message) {
        super(recipientId, message);
    }

    @Override
    public void send() {
        System.out.println("[IN-APP] User: " + recipientId);
        System.out.println("Message: " + message);
    }
}
