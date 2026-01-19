package com.university.notifications;

public class SMSNotification extends Notification {

    public SMSNotification(String recipientId, String message) {
        super(recipientId, message);
    }

    @Override
    public void send() {
        System.out.println("[SMS] To: " + recipientId);
        System.out.println("Message: " + message);
    }
}
