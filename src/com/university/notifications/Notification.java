package com.university.notifications;

public abstract class Notification {

    protected String recipientId;
    protected String message;

    public Notification(String recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
    }

    public abstract void send();
}
