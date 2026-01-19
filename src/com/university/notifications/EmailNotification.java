package com.university.notifications;

public class EmailNotification extends Notification {

    public EmailNotification(String recipientId, String message) {
        super(recipientId, message);
    }

    @Override
    public void send() {
        System.out.println("[EMAIL] To: " + recipientId);
        System.out.println("Message: " + message);
    }
}
